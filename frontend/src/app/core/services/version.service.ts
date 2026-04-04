import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Version, CreateVersionRequest, UpdateVersionRequest } from '../models/version.model';

const API_URL = 'http://localhost:8080/deploy/version';

@Injectable({ providedIn: 'root' })
export class VersionService {

  constructor(private http: HttpClient) {}

  findAll(page: number = 0, size: number = 10): Observable<any> {
    return this.http.get<any>(`${API_URL}?page=${page}&size=${size}`);
  }

  findById(id: string): Observable<Version> {
    return this.http.get<Version>(`${API_URL}/${id}`);
  }

  create(request: CreateVersionRequest): Observable<Version> {
    return this.http.post<Version>(API_URL, request);
  }

  update(id: string, request: UpdateVersionRequest): Observable<Version> {
    return this.http.put<Version>(`${API_URL}/${id}`, request);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${API_URL}/${id}`);
  }
}
