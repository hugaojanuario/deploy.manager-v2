export interface  Version {
  id: string;
  numberVersion: string;
  dateRelease: string;
  changelog: string;
  activate: boolean;
}

export interface CreateVersionRequest{
  numberVersion: string;
  dateRelease: string;
  changelog: string;
}

export interface UpdateVersionRequest{
  numberVersion?: string;
  dateRelease?: string;
  changelog?: string;
  activate?: boolean;
}
