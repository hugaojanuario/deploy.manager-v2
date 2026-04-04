import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest, RegisterRequest, AuthResponse } from '../models/auth.model';

const API_URL = 'http://localhost:8080';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${API_URL}/auth/login`, request);
  }

  register(request: RegisterRequest): Observable<any> {
    return this.http.post(`${API_URL}/auth/register`, request);
  }

  salvarToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getLoggedUserEmail(): string {
    const token = this.getToken();
    if (!token) return '';
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.sub || '';
    } catch {
      return '';
    }
  }

  getLoggedUserInitials(): string {
    const email = this.getLoggedUserEmail();
    if (!email) return '?';
    return email.substring(0, 2).toUpperCase();
  }
}
