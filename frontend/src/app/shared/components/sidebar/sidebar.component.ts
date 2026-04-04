import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  userEmail = '';
  userInitials = '';

  constructor(private authService: AuthService, private router: Router) {
    this.userEmail = this.authService.getLoggedUserEmail();
    this.userInitials = this.authService.getLoggedUserInitials();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
