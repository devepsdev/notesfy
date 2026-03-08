import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NoteService } from '../../services/note.service';
import { CategoryService } from '../../services/category.service';
import Category from '../../models/Category';

@Component({
  selector: 'app-note-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './note-form.html',
})
export class NoteForm implements OnInit {
  @Output() noteCreated = new EventEmitter<void>();

  form = new FormGroup({
    title: new FormControl('', [Validators.required, Validators.maxLength(100)]),
    content: new FormControl('', [Validators.required, Validators.maxLength(5000)]),
    categoryId: new FormControl<number | null>(null),
    marked: new FormControl(false),
  });

  categories: Category[] = [];
  isLoading = false;

  constructor(private noteService: NoteService, private categoryService: CategoryService) {}

  ngOnInit() {
    this.categoryService.getCategories().subscribe({
      next: (cats) => (this.categories = cats),
      error: () => (this.categories = []),
    });
  }

  get title() { return this.form.get('title')!; }
  get content() { return this.form.get('content')!; }

  handleSubmit() {
    if (this.form.invalid) return;
    this.isLoading = true;

    const { title, content, categoryId, marked } = this.form.value;
    this.noteService.createNote({
      title: title!,
      content: content ?? undefined,
      marked: marked ?? false,
      categoryId: categoryId ?? undefined,
    }).subscribe({
      next: () => {
        this.form.reset({ title: '', content: '', categoryId: null, marked: false });
        this.isLoading = false;
        this.noteCreated.emit();
      },
      error: () => (this.isLoading = false),
    });
  }
}
