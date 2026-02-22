package net.ddns.deveps.notes.repositories;

import net.ddns.deveps.notes.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
