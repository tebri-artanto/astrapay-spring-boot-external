package com.astrapay.repository;

import com.astrapay.entity.Note;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class NoteRepository {
    private final Map<UUID, Note> notes = new ConcurrentHashMap<>();


    public Collection<Note> getAllNotes(){
        return notes.values();
    }

    public Note getNoteById(UUID id){
        return notes.getOrDefault(id, null);
    }

    public void createNote(UUID id, Note note){
        notes.put(id, note);
    }

    public void updateNote(UUID id, Note note){
        notes.put(id, note);
    }

    public void deleteNote(UUID id){
        notes.remove(id);
    }

}
