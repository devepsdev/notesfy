package net.ddns.deveps.notes.controllers;

import jakarta.validation.Valid;
import net.ddns.deveps.notes.dto.NoteDTO;
import net.ddns.deveps.notes.entities.Note;
import net.ddns.deveps.notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notes/")
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> findAll() {
        List<NoteDTO> noteDTOList = noteService.findAll()
                .stream()
                .map(NoteDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(noteDTOList);
    }

    @GetMapping("{id}")
    public ResponseEntity<NoteDTO> findById(@PathVariable Long id) {
        return noteService.findById(id)
                .map(note -> ResponseEntity.ok(NoteDTO.fromEntity(note)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NoteDTO> save(@Valid @RequestBody NoteDTO noteDTO) {
        Note savedNote = noteService.save(Note.builder()
                .title(noteDTO.getTitle())
                .marked(noteDTO.isMarked())
                .build());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/api/note/{id}")
                .buildAndExpand(savedNote.getId())
                .toUri();
        return ResponseEntity.created(location).body(NoteDTO.fromEntity(savedNote));
    }

    @PutMapping("{id}")
    public ResponseEntity<NoteDTO> updateTitle(@PathVariable Long id, @Valid @RequestBody NoteDTO noteDTO) {
        if (noteService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Note updatedNote = noteService.updateTitle(id, noteDTO);
        return ResponseEntity.ok(NoteDTO.fromEntity(updatedNote));
    }

    @PatchMapping("{id}")
    public ResponseEntity<NoteDTO> updateMarked(@PathVariable Long id, @Valid @RequestBody NoteDTO noteDTO) {
        if (noteService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Note updatedNote = noteService.updateMarked(id, noteDTO);
        return ResponseEntity.ok(NoteDTO.fromEntity(updatedNote));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (noteService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
