import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ClientService } from '../../core/services/client.service';
import { VersionService } from '../../core/services/version.service';
import { Client, CreateClientRequest, UpdateClientRequest } from '../../core/models/client.model';
import { Version } from '../../core/models/version.model';

@Component({
  selector: 'app-clients',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './clients.component.html',
  styleUrl: './clients.component.scss'
})
export class ClientsComponent implements OnInit {
  clients: Client[] = [];
  versions: Version[] = [];
  searchTerm = '';
  loading = false;

  showModal = false;
  isEditing = false;
  selectedId = '';

  form: CreateClientRequest = { name: '', city: '', state: '', contact: '', versionId: '' };

  constructor(
    private clientService: ClientService,
    private versionService: VersionService
  ) {}

  ngOnInit() {
    this.loadClients();
    this.loadVersions();
  }

  loadClients() {
    this.loading = true;
    this.clientService.findAll(0, 50).subscribe({
      next: (page) => { this.clients = page.content; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  loadVersions() {
    this.versionService.findAll(0, 100).subscribe({
      next: (page) => { this.versions = page.content; }
    });
  }

  get filteredClients(): Client[] {
    if (!this.searchTerm) return this.clients;
    const term = this.searchTerm.toLowerCase();
    return this.clients.filter(c =>
      c.name.toLowerCase().includes(term) ||
      c.city.toLowerCase().includes(term) ||
      c.state.toLowerCase().includes(term)
    );
  }

  openCreateModal() {
    this.isEditing = false;
    this.form = { name: '', city: '', state: '', contact: '', versionId: '' };
    this.showModal = true;
  }

  openEditModal(client: Client) {
    this.isEditing = true;
    this.selectedId = client.id;
    this.form = {
      name: client.name,
      city: client.city,
      state: client.state,
      contact: client.contact,
      versionId: client.actualVersion?.id || ''
    };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  save() {
    if (this.isEditing) {
      this.clientService.update(this.selectedId, this.form as UpdateClientRequest).subscribe({
        next: () => { this.closeModal(); this.loadClients(); }
      });
    } else {
      this.clientService.create(this.form).subscribe({
        next: () => { this.closeModal(); this.loadClients(); }
      });
    }
  }

  delete(id: string) {
    if (confirm('Deseja desativar este cliente?')) {
      this.clientService.delete(id).subscribe({
        next: () => { this.loadClients(); }
      });
    }
  }
}
