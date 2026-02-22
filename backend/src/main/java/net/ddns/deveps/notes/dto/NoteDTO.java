package net.ddns.deveps.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ddns.deveps.notes.entities.Note;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDTO {
    private Long id;
    private String title;
    private boolean marked;

    public static NoteDTO fromEntity(Note note) {
        return NoteDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .marked(note.isMarked())
                .build();
    }
}
