package net.ddns.deveps.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ddns.deveps.notes.entities.Category;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private UUID uuid;
    private String name;
    private String color;

    public static CategoryResponseDTO fromEntity(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .uuid(category.getUuid())
                .name(category.getName())
                .color(category.getColor())
                .build();
    }
}
