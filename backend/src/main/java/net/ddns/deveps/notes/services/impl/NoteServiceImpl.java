package net.ddns.deveps.notes.services.impl;

import net.ddns.deveps.notes.dto.NoteRequestDTO;
import net.ddns.deveps.notes.dto.NoteResponseDTO;
import net.ddns.deveps.notes.entities.Category;
import net.ddns.deveps.notes.entities.Note;
import net.ddns.deveps.notes.entities.User;
import net.ddns.deveps.notes.repositories.CategoryRepository;
import net.ddns.deveps.notes.repositories.NoteRepository;
import net.ddns.deveps.notes.repositories.specifications.NoteSpecification;
import net.ddns.deveps.notes.services.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    public NoteServiceImpl(NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<NoteResponseDTO> search(String searchText, Long categoryId, LocalDate dateFrom,
                                        LocalDate dateTo, Pageable pageable, User user) {
        Specification<Note> spec = Specification.where(NoteSpecification.hasUser(user));

        if (searchText != null && !searchText.isBlank()) {
            spec = spec.and(NoteSpecification.titleOrContentContains(searchText));
        }
        if (categoryId != null) {
            spec = spec.and(NoteSpecification.hasCategory(categoryId));
        }
        if (dateFrom != null) {
            spec = spec.and(NoteSpecification.createdAfter(dateFrom));
        }
        if (dateTo != null) {
            spec = spec.and(NoteSpecification.createdBefore(dateTo));
        }

        return noteRepository.findAll(spec, pageable).map(NoteResponseDTO::fromEntity);
    }

    @Override
    public Optional<NoteResponseDTO> findById(Long id, User user) {
        return noteRepository.findById(id)
                .filter(note -> note.getUser() != null && note.getUser().getId().equals(user.getId()))
                .map(NoteResponseDTO::fromEntity);
    }

    @Override
    public NoteResponseDTO save(NoteRequestDTO dto, User user) {
        Category category = resolveCategory(dto.getCategoryId(), user);
        Note note = Note.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .marked(dto.isMarked())
                .user(user)
                .category(category)
                .build();
        return NoteResponseDTO.fromEntity(noteRepository.save(note));
    }

    @Override
    public NoteResponseDTO update(Long id, NoteRequestDTO dto, User user) {
        Note note = noteRepository.findById(id)
                .filter(n -> n.getUser() != null && n.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Nota no encontrada o sin permisos"));

        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setMarked(dto.isMarked());
        note.setCategory(resolveCategory(dto.getCategoryId(), user));

        return NoteResponseDTO.fromEntity(noteRepository.save(note));
    }

    @Override
    public void deleteById(Long id, User user) {
        Note note = noteRepository.findById(id)
                .filter(n -> n.getUser() != null && n.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Nota no encontrada o sin permisos"));
        noteRepository.delete(note);
    }

    private Category resolveCategory(Long categoryId, User user) {
        if (categoryId == null) return null;
        return categoryRepository.findByIdAndUser(categoryId, user)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
}
