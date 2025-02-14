package com.astrapay.service;

import com.astrapay.dto.ApiResponseDto;
import com.astrapay.dto.NoteDto;
import com.astrapay.dto.NoteResponseDto;
import com.astrapay.entity.Note;
import com.astrapay.repository.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    private final ModelMapper modelMapper;

    public NoteService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ApiResponseDto<Collection<NoteResponseDto>> getAllNotes() {
        if(noteRepository.getAllNotes().isEmpty()){
            return ApiResponseDto.success(new ArrayList<>(), "No Notes Added");
        }

        Collection<Note> notes  = noteRepository.getAllNotes();
        System.out.println(notes);

        Collection<NoteResponseDto> notesDto = notes.stream().map(note -> modelMapper.map(note, NoteResponseDto.class))
                .collect(Collectors.toList());

        return new ApiResponseDto<>(notesDto);
    }

    public ApiResponseDto<NoteResponseDto> getNoteById(UUID id) {
        try{
            Note findNote = noteRepository.getNoteById(id);
            if(findNote == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note Not Found");
            }
            NoteResponseDto noteResponseDto = modelMapper.map(findNote, NoteResponseDto.class);
            return new ApiResponseDto<>(noteResponseDto);

        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get note", e);
        }
    }

    public ApiResponseDto<NoteResponseDto> createNote(NoteDto noteDto) {
        try{
            UUID id = UUID.randomUUID();
            Note entity = modelMapper.map(noteDto, Note.class);
            entity.setId(id);
            noteRepository.createNote(id,entity);
            NoteResponseDto noteResponseDto = modelMapper.map(entity, NoteResponseDto.class);
            return ApiResponseDto.success(noteResponseDto, "Note Successfully Added");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add note", e);
        }

    }

    public ApiResponseDto<NoteResponseDto> updateNote(UUID id, NoteDto noteDto) {
        try{
            if(noteRepository.getNoteById(id) == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note Not Found");
            }
            Note entity = modelMapper.map(noteDto, Note.class);
            entity.setId(id);
            noteRepository.updateNote(id,entity);
            NoteResponseDto noteResponseDto = modelMapper.map(entity, NoteResponseDto.class);
            return ApiResponseDto.success(noteResponseDto, "Note Successfully Edited");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update note", e);

        }
    }

    public ApiResponseDto<NoteResponseDto> deleteNote(UUID id) {
        try{
            Note findNote = noteRepository.getNoteById(id);
            if(findNote == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note Not Found");
            }

            String title = findNote.getTitle();
            noteRepository.deleteNote(id);
            NoteResponseDto noteResponseDto = modelMapper.map(findNote, NoteResponseDto.class);
            return ApiResponseDto.success(noteResponseDto,"Notes with Title: " + title + " Successfully Deleted");

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete note", e);
        }
    }
}
