import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoryService } from '../../services/category.service';
import { Header } from '../../components/header/header';
import Category from '../../models/Category';

@Component({
  selector: 'app-categories',
  standalone: true,
  imports: [ReactiveFormsModule, Header],
  templateUrl: './categories.html',
})
export class Categories implements OnInit {
  categories: Category[] = [];
  editingId: number | null = null;

  form = new FormGroup({
    name: new FormControl('', [Validators.required]),
    color: new FormControl('#3b82f6', [
      Validators.required,
      Validators.pattern(/^#([0-9A-Fa-f]{6})$/),
    ]),
  });

  get name() { return this.form.get('name')!; }
  get color() { return this.form.get('color')!; }

  constructor(private categoryService: CategoryService) {}

  ngOnInit() {
    this.loadCategories();
  }

  loadCategories() {
    this.categoryService.getCategories().subscribe({
      next: (cats) => (this.categories = cats),
      error: (e) => console.error('Error cargando categorías:', e),
    });
  }

  handleSubmit() {
    if (this.form.invalid) return;
    const data = { name: this.name.value!, color: this.color.value! };

    const obs = this.editingId
      ? this.categoryService.updateCategory(this.editingId, data)
      : this.categoryService.createCategory(data);

    obs.subscribe({
      next: () => {
        this.form.reset({ name: '', color: '#3b82f6' });
        this.editingId = null;
        this.loadCategories();
      },
      error: (e) => console.error('Error guardando categoría:', e),
    });
  }

  startEdit(cat: Category) {
    this.editingId = cat.id;
    this.form.setValue({ name: cat.name, color: cat.color });
  }

  cancelEdit() {
    this.editingId = null;
    this.form.reset({ name: '', color: '#3b82f6' });
  }

  deleteCategory(id: number) {
    if (!window.confirm('¿Eliminar esta categoría?')) return;
    this.categoryService.deleteCategory(id).subscribe({
      next: () => this.loadCategories(),
      error: (e) => console.error('Error eliminando categoría:', e),
    });
  }
}
