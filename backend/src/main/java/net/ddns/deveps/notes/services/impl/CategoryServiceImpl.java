package net.ddns.deveps.notes.services.impl;

import net.ddns.deveps.notes.dto.CategoryRequestDTO;
import net.ddns.deveps.notes.dto.CategoryResponseDTO;
import net.ddns.deveps.notes.entities.Category;
import net.ddns.deveps.notes.entities.User;
import net.ddns.deveps.notes.repositories.CategoryRepository;
import net.ddns.deveps.notes.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDTO> findAllByUser(User user) {
        return categoryRepository.findByUser(user)
                .stream()
                .map(CategoryResponseDTO::fromEntity)
                .toList();
    }

    @Override
    public CategoryResponseDTO save(CategoryRequestDTO dto, User user) {
        Category category = Category.builder()
                .name(dto.getName())
                .color(dto.getColor())
                .user(user)
                .build();
        return CategoryResponseDTO.fromEntity(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto, User user) {
        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada o sin permisos"));
        category.setName(dto.getName());
        category.setColor(dto.getColor());
        return CategoryResponseDTO.fromEntity(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id, User user) {
        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada o sin permisos"));
        categoryRepository.delete(category);
    }
}
