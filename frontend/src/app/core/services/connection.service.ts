import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Connection, CreateConnectionRequest, UpdateConnectionRequest } from '../models/connection.model';

const API_URL = 'http://localhost:8080/deploy/connection';

@Injectable({ providedIn: 'root' })
export class ConnectionService {

  constructor(private http: HttpClient) {}

  findAll(page: number = 0, size: number = 10): Observable<any> {
    return this.http.get<any>(`${API_URL}?page=${page}&size=${size}`);
  }

  findById(id: string): Observable<Connection> {
    return this.http.get<Connection>(`${API_URL}/${id}`);
  }

  findByClientId(clientId: string): Observable<Connection[]> {
    return this.http.get<Connection[]>(`${API_URL}/client/${clientId}`);
  }

  create(request: CreateConnectionRequest): Observable<Connection> {
    return this.http.post<Connection>(API_URL, request);
  }

  update(id: string, request: UpdateConnectionRequest): Observable<Connection> {
    return this.http.put<Connection>(`${API_URL}/${id}`, request);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${API_URL}/${id}`);
  }
}
