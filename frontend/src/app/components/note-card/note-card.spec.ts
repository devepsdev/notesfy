import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoteCard } from './note-card';

describe('NoteCard', () => {
  let component: NoteCard;
  let fixture: ComponentFixture<NoteCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NoteCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoteCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
