package net.ddns.deveps.notes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteRequestDTO {
    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 5000)
    private String content;

    private boolean marked;

    private Long categoryId;
}
