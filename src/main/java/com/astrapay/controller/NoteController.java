package com.astrapay.controller;


import com.astrapay.dto.ApiResponseDto;
import com.astrapay.dto.NoteDto;
import com.astrapay.dto.NoteResponseDto;
import com.astrapay.service.NoteService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@Api(value = "NoteController")
@RequestMapping("/v1/api/notes")
@CrossOrigin(originPatterns = "*")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<Collection<NoteResponseDto>>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponseDto<NoteResponseDto>> getNoteById(@PathVariable UUID id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<NoteResponseDto>> createNote(@Valid @RequestBody NoteDto note) {
        return new ResponseEntity<>(noteService.createNote(note), HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<ApiResponseDto<NoteResponseDto>> UpdateNote(@PathVariable UUID id, @Valid @RequestBody NoteDto note) {
        return new ResponseEntity<>(noteService.updateNote(id, note), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponseDto<NoteResponseDto>> deleteNote(@PathVariable UUID id) {
        return new ResponseEntity<>(noteService.deleteNote(id), HttpStatus.OK);

    }

}
