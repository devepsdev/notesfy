import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Notes } from './pages/notes/notes';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'notes', component: Notes },
];
