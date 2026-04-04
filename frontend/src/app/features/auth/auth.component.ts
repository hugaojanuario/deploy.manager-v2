import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
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
  requestEmail = '';

  errorMessage = '';
  successMessage = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router, private http: HttpClient) {}

  setTab(tab: 'login' | 'register') {
    this.activeTab = tab;
    this.errorMessage = '';
    this.successMessage = '';
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

  onRequestAccess() {
    if (!this.requestEmail) {
      this.errorMessage = 'Informe seu e-mail para solicitar acesso.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const payload = {
      access_key: '1418d683-9d3a-4476-ab3e-e11378cc2a11',
      subject: 'Solicitação de acesso — Deploy Manager',
      from_name: 'Deploy Manager',
      message: `O usuário ${this.requestEmail} está solicitando acesso ao Deploy Manager.`
    };

    this.http.post('https://api.web3forms.com/submit', payload).subscribe({
      next: () => {
        this.successMessage = 'Solicitação enviada! Aguarde o contato do administrador.';
        this.requestEmail = '';
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Erro ao enviar solicitação. Tente novamente.';
        this.loading = false;
      }
    });
  }
}
