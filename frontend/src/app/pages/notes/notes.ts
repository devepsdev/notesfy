import { Component, OnInit } from '@angular/core';
import { Header } from '../../components/header/header';
import { NoteService } from '../../services/note.service';
import { NoteCard } from '../../components/note-card/note-card';
import { CreateNote } from '../../components/create-note/create-note';

@Component({
  selector: 'app-notes',
  imports: [Header, NoteCard, CreateNote],
  templateUrl: './notes.html',
  styleUrl: './notes.scss',
})
export class Notes implements OnInit {
  hasError: boolean = false;
  isLoading: boolean = true;

  constructor(public noteService: NoteService) {}

  ngOnInit(): void {
    this.getNotes();
  }

  getNotes() {
    this.noteService.getNotes().subscribe({
      next: (data) => {
        this.noteService.notes = data.reverse();
        this.isLoading = false;
        this.hasError = false;
      },
      error: (e) => {
        console.log(e);
        this.hasError = true;
      },
    });
  }
}
