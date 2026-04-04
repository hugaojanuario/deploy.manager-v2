import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../core/services/client.service';
import { VersionService } from '../../core/services/version.service';
import { ConnectionService } from '../../core/services/connection.service';
import { Client } from '../../core/models/client.model';
import { Version } from '../../core/models/version.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  totalClients = 0;
  totalVersions = 0;
  totalConnections = 0;

  recentClients: Client[] = [];
  recentVersions: Version[] = [];

  constructor(
    private clientService: ClientService,
    private versionService: VersionService,
    private connectionService: ConnectionService
  ) {}

  ngOnInit() {
    this.clientService.findAll(0, 5).subscribe({
      next: (page) => {
        this.totalClients = page.totalElements;
        this.recentClients = page.content;
      }
    });

    this.versionService.findAll(0, 5).subscribe({
      next: (page) => {
        this.totalVersions = page.totalElements;
        this.recentVersions = page.content;
      }
    });

    this.connectionService.findAll(0, 1).subscribe({
      next: (page) => {
        this.totalConnections = page.totalElements;
      }
    });
  }
}
