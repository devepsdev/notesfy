package net.ddns.deveps.notes.controllers;

import jakarta.validation.Valid;
import net.ddns.deveps.notes.dto.CategoryRequestDTO;
import net.ddns.deveps.notes.dto.CategoryResponseDTO;
import net.ddns.deveps.notes.entities.User;
import net.ddns.deveps.notes.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(categoryService.findAllByUser(user));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> save(@Valid @RequestBody CategoryRequestDTO dto,
                                                    @AuthenticationPrincipal User user) {
        return ResponseEntity.status(201).body(categoryService.save(dto, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody CategoryRequestDTO dto,
                                                      @AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok(categoryService.update(id, dto, user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        try {
            categoryService.delete(id, user);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
