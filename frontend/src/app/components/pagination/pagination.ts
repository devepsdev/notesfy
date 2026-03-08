import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  standalone: true,
  templateUrl: './pagination.html',
})
export class Pagination {
  @Input() totalPages = 0;
  @Input() currentPage = 0;
  @Output() pageChange = new EventEmitter<number>();

  get pages(): (number | '...')[] {
    if (this.totalPages <= 7) {
      return Array.from({ length: this.totalPages }, (_, i) => i);
    }
    const pages: (number | '...')[] = [0];
    if (this.currentPage > 2) pages.push('...');
    for (let i = Math.max(1, this.currentPage - 1); i <= Math.min(this.totalPages - 2, this.currentPage + 1); i++) {
      pages.push(i);
    }
    if (this.currentPage < this.totalPages - 3) pages.push('...');
    pages.push(this.totalPages - 1);
    return pages;
  }

  goTo(page: number | '...') {
    if (typeof page === 'number') {
      this.pageChange.emit(page);
    }
  }

  prev() {
    if (this.currentPage > 0) this.pageChange.emit(this.currentPage - 1);
  }

  next() {
    if (this.currentPage < this.totalPages - 1) this.pageChange.emit(this.currentPage + 1);
  }
}
