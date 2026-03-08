import { Component, EventEmitter, Output, input } from '@angular/core';
import { SlicePipe } from '@angular/common';
import Note from '../../models/Note';
import { NoteService } from '../../services/note.service';

@Component({
  selector: 'app-note-card',
  imports: [SlicePipe],
  templateUrl: './note-card.html',
  styleUrl: './note-card.scss',
})
export class NoteCard {
  note = input<Note>();
  @Output() noteDeleted = new EventEmitter<number>();
  @Output() noteUpdated = new EventEmitter<void>();

  constructor(private noteService: NoteService) {}

  toggleMarked(id: number | undefined, event: Event) {
    if (!id) return;
    const checked = (event.target as HTMLInputElement).checked;
    const n = this.note()!;
    this.noteService.updateNote(id, {
      title: n.title,
      content: n.content,
      marked: checked,
      categoryId: n.categoryId,
    }).subscribe({
      next: () => this.noteUpdated.emit(),
      error: (e) => console.error('Error al actualizar la nota:', e),
    });
  }

  deleteNote(id: number | undefined) {
    if (!id) return;
    if (!window.confirm('¿Eliminar esta nota?')) return;
    this.noteService.deleteNote(id).subscribe({
      next: () => this.noteDeleted.emit(id),
      error: (e) => console.error('Error al eliminar la nota:', e),
    });
  }
}
