package net.ddns.deveps.notes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CategoryRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^#([0-9A-Fa-f]{6})$", message = "El color debe ser un código hex válido (ej: #3b82f6)")
    private String color;
}
