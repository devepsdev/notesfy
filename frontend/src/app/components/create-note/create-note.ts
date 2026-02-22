import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Note from '../../models/Note';
import { NoteService } from '../../services/note.service';

@Component({
  selector: 'app-create-note',
  imports: [FormsModule],
  templateUrl: './create-note.html',
  styleUrl: './create-note.scss',
})
export class CreateNote {
  noteTitle: string = '';
  constructor(public noteService: NoteService) {}

  handleSubmit = () => {
    if (!this.noteTitle) return;

    const newNote: Note = {
      title: this.noteTitle,
      marked: false,
    };
    this.createNote(newNote);
  };

  createNote(newNote: Note) {
    this.noteService.createNote(newNote).subscribe({
      next: (createdNote) => {
        // Agregar la nueva nota al inicio del array local
        this.noteService.notes.unshift(createdNote);
        this.noteTitle = '';
      },
      error: (e) => {
        console.error('Error al crear la nota:', e);
      },
    });
  }
}
