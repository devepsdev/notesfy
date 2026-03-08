import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import Note from '../models/Note';
import PageResponse from '../models/PageResponse';
import { environment } from '../../environments/environment';

export interface SearchParams {
  searchText?: string;
  categoryId?: number;
  dateFrom?: string;
  dateTo?: string;
  page?: number;
  size?: number;
  sortBy?: string;
  sortDir?: string;
}

@Injectable({ providedIn: 'root' })
export class NoteService {
  private readonly API_URL = `${environment.apiUrl}/notes`;

  constructor(private http: HttpClient) {}

  getNotes(page = 0, size = 10, sortBy = 'updatedAt', sortDir = 'desc'): Observable<PageResponse<Note>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);
    return this.http.get<PageResponse<Note>>(this.API_URL, { params });
  }

  searchNotes(search: SearchParams): Observable<PageResponse<Note>> {
    let params = new HttpParams()
      .set('page', search.page ?? 0)
      .set('size', search.size ?? 10)
      .set('sortBy', search.sortBy ?? 'updatedAt')
      .set('sortDir', search.sortDir ?? 'desc');

    if (search.searchText) params = params.set('searchText', search.searchText);
    if (search.categoryId) params = params.set('categoryId', search.categoryId);
    if (search.dateFrom) params = params.set('dateFrom', search.dateFrom);
    if (search.dateTo) params = params.set('dateTo', search.dateTo);

    return this.http.get<PageResponse<Note>>(`${this.API_URL}/search`, { params });
  }

  createNote(note: { title: string; content?: string; marked: boolean; categoryId?: number }): Observable<Note> {
    return this.http.post<Note>(this.API_URL, note);
  }

  updateNote(id: number, note: { title: string; content?: string; marked: boolean; categoryId?: number }): Observable<Note> {
    return this.http.put<Note>(`${this.API_URL}/${id}`, note);
  }

  deleteNote(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
