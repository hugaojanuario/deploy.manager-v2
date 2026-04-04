import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { VersionService } from '../../core/services/version.service';
import { Version, CreateVersionRequest, UpdateVersionRequest } from '../../core/models/version.model';

@Component({
  selector: 'app-versions',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './versions.component.html',
  styleUrl: './versions.component.scss'
})
export class VersionsComponent implements OnInit {
  versions: Version[] = [];
  searchTerm = '';
  loading = false;

  showModal = false;
  isEditing = false;
  selectedId = '';

  form: CreateVersionRequest = { numberVersion: '', dateRelease: '', changelog: '' };

  constructor(private versionService: VersionService) {}

  ngOnInit() {
    this.loadVersions();
  }

  loadVersions() {
    this.loading = true;
    this.versionService.findAll(0, 50).subscribe({
      next: (page) => { this.versions = page.content; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  get filteredVersions(): Version[] {
    if (!this.searchTerm) return this.versions;
    const term = this.searchTerm.toLowerCase();
    return this.versions.filter(v => v.numberVersion.toLowerCase().includes(term));
  }

  openCreateModal() {
    this.isEditing = false;
    this.form = { numberVersion: '', dateRelease: '', changelog: '' };
    this.showModal = true;
  }

  openEditModal(version: Version) {
    this.isEditing = true;
    this.selectedId = version.id;
    this.form = {
      numberVersion: version.numberVersion,
      dateRelease: version.dateRelease,
      changelog: version.changelog
    };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  save() {
    if (this.isEditing) {
      this.versionService.update(this.selectedId, this.form as UpdateVersionRequest).subscribe({
        next: () => { this.closeModal(); this.loadVersions(); }
      });
    } else {
      this.versionService.create(this.form).subscribe({
        next: () => { this.closeModal(); this.loadVersions(); }
      });
    }
  }

  delete(id: string) {
    if (confirm('Deseja desativar esta versão?')) {
      this.versionService.delete(id).subscribe({
        next: () => { this.loadVersions(); }
      });
    }
  }
}
