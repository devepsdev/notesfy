package net.ddns.deveps.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ddns.deveps.notes.entities.Note;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseDTO {
    private Long id;
    private UUID uuid;
    private String title;
    private String content;
    private boolean marked;
    private Long categoryId;
    private String categoryName;
    private String categoryColor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoteResponseDTO fromEntity(Note note) {
        return NoteResponseDTO.builder()
                .id(note.getId())
                .uuid(note.getUuid())
                .title(note.getTitle())
                .content(note.getContent())
                .marked(note.isMarked())
                .categoryId(note.getCategory() != null ? note.getCategory().getId() : null)
                .categoryName(note.getCategory() != null ? note.getCategory().getName() : null)
                .categoryColor(note.getCategory() != null ? note.getCategory().getColor() : null)
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }
}
