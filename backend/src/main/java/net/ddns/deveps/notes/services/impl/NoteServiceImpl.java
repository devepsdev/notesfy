package net.ddns.deveps.notes.services.impl;

import net.ddns.deveps.notes.dto.NoteDTO;
import net.ddns.deveps.notes.entities.Note;
import net.ddns.deveps.notes.repositories.NoteRepository;
import net.ddns.deveps.notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note updateTitle(Long id, NoteDTO noteDTO) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Nota no encontrada"));
        note.setTitle(noteDTO.getTitle());
        return noteRepository.save(note);
    }

    @Override
    public Note updateMarked(Long id, NoteDTO noteDTO) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Nota no encontrada"));
        note.setMarked(noteDTO.isMarked());
        return noteRepository.save(note);
    }

    @Override
    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }
}
