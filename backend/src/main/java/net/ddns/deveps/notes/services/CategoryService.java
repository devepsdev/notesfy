package net.ddns.deveps.notes.services;

import net.ddns.deveps.notes.dto.CategoryRequestDTO;
import net.ddns.deveps.notes.dto.CategoryResponseDTO;
import net.ddns.deveps.notes.entities.User;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> findAllByUser(User user);
    CategoryResponseDTO save(CategoryRequestDTO dto, User user);
    CategoryResponseDTO update(Long id, CategoryRequestDTO dto, User user);
    void delete(Long id, User user);
}
