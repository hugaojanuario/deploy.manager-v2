import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client, CreateClientRequest, UpdateClientRequest } from '../models/client.model';

const API_URL = 'http://localhost:8080/deploy/client';

@Injectable({ providedIn: 'root' })
export class ClientService {

  constructor(private http: HttpClient) {}

  findAll(page: number = 0, size: number = 10): Observable<any> {
    return this.http.get<any>(`${API_URL}?page=${page}&size=${size}`);
  }

  findById(id: string): Observable<Client> {
    return this.http.get<Client>(`${API_URL}/${id}`);
  }

  findByName(name: string): Observable<Client[]> {
    return this.http.get<Client[]>(`${API_URL}/search?name=${name}`);
  }

  create(request: CreateClientRequest): Observable<Client> {
    return this.http.post<Client>(API_URL, request);
  }

  update(id: string, request: UpdateClientRequest): Observable<Client> {
    return this.http.put<Client>(`${API_URL}/${id}`, request);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${API_URL}/${id}`);
  }
}
