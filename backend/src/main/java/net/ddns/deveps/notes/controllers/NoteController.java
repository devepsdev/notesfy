package net.ddns.deveps.notes.controllers;

import jakarta.validation.Valid;
import net.ddns.deveps.notes.dto.NoteRequestDTO;
import net.ddns.deveps.notes.dto.NoteResponseDTO;
import net.ddns.deveps.notes.entities.User;
import net.ddns.deveps.notes.services.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NoteResponseDTO>> search(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @AuthenticationPrincipal User user) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(noteService.search(searchText, categoryId, dateFrom, dateTo, pageable, user));
    }

    @GetMapping
    public ResponseEntity<Page<NoteResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @AuthenticationPrincipal User user) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(noteService.search(null, null, null, null, pageable, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> findById(@PathVariable Long id,
                                                    @AuthenticationPrincipal User user) {
        return noteService.findById(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> save(@Valid @RequestBody NoteRequestDTO dto,
                                                @AuthenticationPrincipal User user) {
        NoteResponseDTO saved = noteService.save(dto, user);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody NoteRequestDTO dto,
                                                  @AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(noteService.update(id, dto, user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id,
                                           @AuthenticationPrincipal User user) {
        try {
            noteService.deleteById(id, user);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
