import { Injectable } from '@angular/core';
import Note from '../models/Note';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  readonly API_URL = 'https://deveps.ddns.net/api/notes/';
  notes: Note[];
  constructor(private http: HttpClient) {
    this.notes = [];
  }

  getNotes() {
    return this.http.get<Note[]>(this.API_URL);
  }

  createNote(note: Note) {
    return this.http.post<Note>(this.API_URL, note);
  }

  deleteNote(id: number) {
    return this.http.delete<void>(`${this.API_URL}${id}`);
  }

  updateTitle(id: number, newTitle: string) {
    return this.http.put<Note>(`${this.API_URL}${id}`, { title: newTitle });
  }

  updateMarked(id: number, marked: boolean) {
    return this.http.patch<Note>(`${this.API_URL}${id}`, { marked: marked });
  }

  createId = () => {
    return Date.now().toString(36) + Math.random().toString(36).slice(2);
  };
  // createId = () => crypto.randomUUID();
}
