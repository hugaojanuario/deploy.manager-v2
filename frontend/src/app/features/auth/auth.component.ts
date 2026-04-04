import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent {
  activeTab: 'login' | 'register' = 'login';

  loginData = { email: '', password: '' };
  registerData = { email: '', password: '', confirmPassword: '' };

  errorMessage = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  setTab(tab: 'login' | 'register') {
    this.activeTab = tab;
    this.errorMessage = '';
  }

  onLogin() {
    this.loading = true;
    this.errorMessage = '';

    this.authService.login(this.loginData).subscribe({
      next: (response) => {
        this.authService.salvarToken(response.token);
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.errorMessage = 'E-mail ou senha inválidos.';
        this.loading = false;
      }
    });
  }

  onRegister() {
    if (this.registerData.password !== this.registerData.confirmPassword) {
      this.errorMessage = 'As senhas não coincidem.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.authService.register({
      email: this.registerData.email,
      password: this.registerData.password
    }).subscribe({
      next: () => {
        this.setTab('login');
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Erro ao criar conta. Tente novamente.';
        this.loading = false;
      }
    });
  }
}
