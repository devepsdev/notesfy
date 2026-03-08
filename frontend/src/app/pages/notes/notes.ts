import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Header } from '../../components/header/header';
import { NoteService } from '../../services/note.service';
import { CategoryService } from '../../services/category.service';
import { NoteCard } from '../../components/note-card/note-card';
import { NoteForm } from '../../components/note-form/note-form';
import { Pagination } from '../../components/pagination/pagination';
import Note from '../../models/Note';
import Category from '../../models/Category';
import { debounceTime, distinctUntilChanged, Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-notes',
  imports: [Header, NoteCard, NoteForm, Pagination, ReactiveFormsModule],
  templateUrl: './notes.html',
  styleUrl: './notes.scss',
})
export class Notes implements OnInit, OnDestroy {
  notes: Note[] = [];
  categories: Category[] = [];
  totalPages = 0;
  currentPage = 0;
  hasError = false;
  isLoading = true;

  filters = new FormGroup({
    searchText: new FormControl(''),
    categoryId: new FormControl<number | null>(null),
    dateFrom: new FormControl(''),
    dateTo: new FormControl(''),
    sortBy: new FormControl('updatedAt'),
    sortDir: new FormControl('desc'),
  });

  private destroy$ = new Subject<void>();

  constructor(private noteService: NoteService, private categoryService: CategoryService) {}

  ngOnInit() {
    this.categoryService.getCategories().subscribe({
      next: (cats) => (this.categories = cats),
      error: () => {},
    });

    this.filters.get('searchText')!.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      takeUntil(this.destroy$)
    ).subscribe(() => {
      this.currentPage = 0;
      this.loadNotes();
    });

    this.filters.get('categoryId')!.valueChanges.pipe(takeUntil(this.destroy$))
      .subscribe(() => { this.currentPage = 0; this.loadNotes(); });
    this.filters.get('dateFrom')!.valueChanges.pipe(takeUntil(this.destroy$))
      .subscribe(() => { this.currentPage = 0; this.loadNotes(); });
    this.filters.get('dateTo')!.valueChanges.pipe(takeUntil(this.destroy$))
      .subscribe(() => { this.currentPage = 0; this.loadNotes(); });
    this.filters.get('sortBy')!.valueChanges.pipe(takeUntil(this.destroy$))
      .subscribe(() => { this.currentPage = 0; this.loadNotes(); });
    this.filters.get('sortDir')!.valueChanges.pipe(takeUntil(this.destroy$))
      .subscribe(() => { this.currentPage = 0; this.loadNotes(); });

    this.loadNotes();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadNotes() {
    this.isLoading = true;
    const f = this.filters.value;
    this.noteService.searchNotes({
      searchText: f.searchText || undefined,
      categoryId: f.categoryId || undefined,
      dateFrom: f.dateFrom || undefined,
      dateTo: f.dateTo || undefined,
      page: this.currentPage,
      size: 10,
      sortBy: f.sortBy || 'updatedAt',
      sortDir: f.sortDir || 'desc',
    }).subscribe({
      next: (page) => {
        this.notes = page.content;
        this.totalPages = page.totalPages;
        this.hasError = false;
        this.isLoading = false;
      },
      error: () => {
        this.hasError = true;
        this.isLoading = false;
      },
    });
  }

  onPageChange(page: number) {
    this.currentPage = page;
    this.loadNotes();
  }

  onNoteCreated() {
    this.currentPage = 0;
    this.loadNotes();
  }

  onNoteDeleted() {
    this.loadNotes();
  }

  onNoteUpdated() {
    this.loadNotes();
  }
}
