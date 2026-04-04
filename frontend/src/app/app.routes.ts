import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/auth.component').then(m => m.AuthComponent)
  },
  {
    path: '',
    loadComponent: () => import('./shared/components/layout/layout.component').then(m => m.LayoutComponent),
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },
      {
        path: 'clients',
        loadComponent: () => import('./features/clients/clients.component').then(m => m.ClientsComponent)
      },
      {
        path: 'versions',
        loadComponent: () => import('./features/versions/versions.component').then(m => m.VersionsComponent)
      },
      {
        path: 'connections',
        loadComponent: () => import('./features/connections/connections.component').then(m => m.ConnectionsComponent)
      },
      { path: '**', redirectTo: '/dashboard' }
    ]
  }
];
