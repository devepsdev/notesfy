package net.ddns.deveps.notes.services;

import net.ddns.deveps.notes.dto.NoteRequestDTO;
import net.ddns.deveps.notes.dto.NoteResponseDTO;
import net.ddns.deveps.notes.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface NoteService {
    Page<NoteResponseDTO> search(String searchText, Long categoryId, LocalDate dateFrom, LocalDate dateTo,
                                 Pageable pageable, User user);
    Optional<NoteResponseDTO> findById(Long id, User user);
    NoteResponseDTO save(NoteRequestDTO dto, User user);
    NoteResponseDTO update(Long id, NoteRequestDTO dto, User user);
    void deleteById(Long id, User user);
}
