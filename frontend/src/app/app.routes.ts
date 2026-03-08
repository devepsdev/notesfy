import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Notes } from './pages/notes/notes';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'login', loadComponent: () => import('./pages/login/login').then(m => m.Login) },
  { path: 'register', loadComponent: () => import('./pages/register/register').then(m => m.Register) },
  { path: 'notes', component: Notes, canActivate: [authGuard] },
  { path: 'categories', loadComponent: () => import('./pages/categories/categories').then(m => m.Categories), canActivate: [authGuard] },
  { path: '**', redirectTo: '' },
];
