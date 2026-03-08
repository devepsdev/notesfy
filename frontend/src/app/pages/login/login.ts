import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
})
export class Login {
  form = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(3)]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
  });

  errorMessage = '';
  isLoading = false;

  constructor(private authService: AuthService, private router: Router) {}

  get username() { return this.form.get('username')!; }
  get password() { return this.form.get('password')!; }

  handleSubmit() {
    if (this.form.invalid) return;
    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login({
      username: this.username.value!,
      password: this.password.value!,
    }).subscribe({
      next: () => this.router.navigate(['/notes']),
      error: () => {
        this.errorMessage = 'Usuario o contraseña incorrectos';
        this.isLoading = false;
      },
    });
  }
}
