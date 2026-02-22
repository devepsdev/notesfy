import { Component, input } from '@angular/core';
import Note from '../../models/Note';
import { NoteService } from '../../services/note.service';

@Component({
  selector: 'app-note-card',
  imports: [],
  templateUrl: './note-card.html',
  styleUrl: './note-card.scss',
})
export class NoteCard {
  note = input<Note>();
  constructor(public noteService: NoteService) {}

  updateTitle(id: number | undefined, e: Event) {
    if (!id) return;
    const inputHtml = e.target as HTMLInputElement;
    const newTitle = inputHtml.value;
    this.noteService.updateTitle(id, newTitle).subscribe({
      next: () => {
        const noteToUpdate = this.noteService.notes.find(
          (note) => note.id === id
        );
        if (noteToUpdate) {
          noteToUpdate.title = newTitle;
        }
      },
      error: (e) => {
        console.error('Error al actualizar la nota:', e);
      },
    });
  }

  deleteNote(id: number | undefined) {
    if (!id) return;
    this.noteService.deleteNote(id).subscribe({
      next: () => {
        this.noteService.notes = this.noteService.notes.filter(
          (note) => note.id !== id
        );
      },
      error: (e) => {
        console.error('Error al eliminar la nota:', e);
      },
    });
  }

  updateMarked(id: number | undefined, event?: Event) {
    if (!id) return;

    let newMarkedState: boolean;

    if (event) {
      const checkbox = event.target as HTMLInputElement;
      newMarkedState = checkbox.checked;
    } else {
      const currentNote = this.noteService.notes.find((note) => note.id === id);
      if (!currentNote) return;
      newMarkedState = !currentNote.marked;
    }

    this.noteService.updateMarked(id, newMarkedState).subscribe({
      next: () => {
        const noteToUpdate = this.noteService.notes.find(
          (note) => note.id === id
        );
        if (noteToUpdate) {
          noteToUpdate.marked = newMarkedState;
        }
      },
      error: (e) => {
        console.error('Error al actualizar el estado marcado:', e);
      },
    });
  }
}
