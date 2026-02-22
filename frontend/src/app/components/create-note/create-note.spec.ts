import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNote } from './create-note';

describe('CreateNote', () => {
  let component: CreateNote;
  let fixture: ComponentFixture<CreateNote>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateNote]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateNote);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
