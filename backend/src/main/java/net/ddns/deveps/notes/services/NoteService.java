package net.ddns.deveps.notes.services;

import net.ddns.deveps.notes.dto.NoteDTO;
import net.ddns.deveps.notes.entities.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    List<Note> findAll();
    Optional<Note> findById(Long id);
    Note save(Note note);
    Note updateTitle(Long id, NoteDTO noteDTO);
    Note updateMarked(Long id, NoteDTO noteDTO);
    void deleteById(Long id);
}
