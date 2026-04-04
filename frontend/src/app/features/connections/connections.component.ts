import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ConnectionService } from '../../core/services/connection.service';
import { ClientService } from '../../core/services/client.service';
import { Connection, CreateConnectionRequest, UpdateConnectionRequest, ConnectionType } from '../../core/models/connection.model';
import { Client } from '../../core/models/client.model';

@Component({
  selector: 'app-connections',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './connections.component.html',
  styleUrl: './connections.component.scss'
})
export class ConnectionsComponent implements OnInit {
  connections: Connection[] = [];
  clients: Client[] = [];
  searchTerm = '';
  loading = false;

  showModal = false;
  isEditing = false;
  selectedId = '';

  connectionTypes: ConnectionType[] = ['TEAMVIEWER', 'ANYDESK', 'ANYVIEWER'];

  form: CreateConnectionRequest = {
    clientId: '',
    userMachine: '',
    passwordMachine: '',
    userDb: '',
    passwordDb: '',
    idRemoteConnection: '',
    passwordRemoteConnection: '',
    connectionType: 'TEAMVIEWER'
  };

  constructor(
    private connectionService: ConnectionService,
    private clientService: ClientService
  ) {}

  ngOnInit() {
    this.loadConnections();
    this.loadClients();
  }

  loadConnections() {
    this.loading = true;
    this.connectionService.findAll(0, 50).subscribe({
      next: (page) => { this.connections = page.content; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  loadClients() {
    this.clientService.findAll(0, 100).subscribe({
      next: (page) => { this.clients = page.content; }
    });
  }

  get filteredConnections(): Connection[] {
    if (!this.searchTerm) return this.connections;
    const term = this.searchTerm.toLowerCase();
    return this.connections.filter(c =>
      c.client?.name.toLowerCase().includes(term) ||
      c.connectionType.toLowerCase().includes(term) ||
      c.idRemoteConnection.toLowerCase().includes(term)
    );
  }

  openCreateModal() {
    this.isEditing = false;
    this.form = {
      clientId: '',
      userMachine: '',
      passwordMachine: '',
      userDb: '',
      passwordDb: '',
      idRemoteConnection: '',
      passwordRemoteConnection: '',
      connectionType: 'TEAMVIEWER'
    };
    this.showModal = true;
  }

  openEditModal(conn: Connection) {
    this.isEditing = true;
    this.selectedId = conn.id;
    this.form = {
      clientId: conn.client?.id || '',
      userMachine: conn.userMachine,
      passwordMachine: conn.passwordMachine,
      userDb: conn.userDb,
      passwordDb: conn.passwordDb,
      idRemoteConnection: conn.idRemoteConnection,
      passwordRemoteConnection: conn.passwordRemoteConnection,
      connectionType: conn.connectionType
    };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  save() {
    if (this.isEditing) {
      this.connectionService.update(this.selectedId, this.form as UpdateConnectionRequest).subscribe({
        next: () => { this.closeModal(); this.loadConnections(); }
      });
    } else {
      this.connectionService.create(this.form).subscribe({
        next: () => { this.closeModal(); this.loadConnections(); }
      });
    }
  }

  delete(id: string) {
    if (confirm('Deseja desativar esta conexão?')) {
      this.connectionService.delete(id).subscribe({
        next: () => { this.loadConnections(); }
      });
    }
  }

  getBadgeClass(type: ConnectionType): string {
    const map: Record<ConnectionType, string> = {
      TEAMVIEWER: 'badge--teamviewer',
      ANYDESK: 'badge--anydesk',
      ANYVIEWER: 'badge--anyviewer'
    };
    return map[type];
  }
}
