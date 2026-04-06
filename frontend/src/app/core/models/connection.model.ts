import { Client } from './client.model'

export type ConnectionType = 'TEAMVIEWER' | 'ANYDESK' | 'ANYVIEWER' | 'HOPTODESK';

export interface Connection{
  id: string;
  client: Client;
  userMachine: string;
  passwordMachine: string;
  userDb: string;
  passwordDb: string;
  idRemoteConnection: string;
  passwordRemoteConnection: string;
  connectionType: ConnectionType;
  activate: boolean;
}

export interface CreateConnectionRequest{
  clientId: string,
  userMachine: string;
  passwordMachine: string;
  userDb: string;
  passwordDb: string;
  idRemoteConnection: string;
  passwordRemoteConnection: string;
  connectionType: ConnectionType;
}

export interface UpdateConnectionRequest{
  userMachine?: string;
  passwordMachine?: string;
  userDb?: string;
  passwordDb?: string;
  idRemoteConnection?: string;
  passwordRemoteConnection?: string;
  connectionType?: ConnectionType;
  activate?: boolean;
}
