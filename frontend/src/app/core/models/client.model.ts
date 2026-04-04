import { Version } from './version.model'

export interface Client{
  id: string;
  name: string;
  city: string;
  state: string;
  contact: string;
  actualVersion: Version;
  activate: boolean;
}

export interface CreateClientRequest{
  name: string;
  city: string;
  state: string;
  contact: string;
  versionId: string;
}

export interface UpdateClientRequest{
  name?: string;
  city?: string;
  state?: string;
  contact?: string;
  versionId?: string;
  activate?: boolean;
}
