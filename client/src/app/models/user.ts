export interface User {
  id?: number
  name?: string
  password?: string
  email?: string
  clientType?: string,
}

export interface ClientType {
  value: string;
}
