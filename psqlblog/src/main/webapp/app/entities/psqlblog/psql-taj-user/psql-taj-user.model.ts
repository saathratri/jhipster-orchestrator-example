export interface IPsqlTajUser {
  id: string;
  login?: string | null;
}

export type NewPsqlTajUser = Omit<IPsqlTajUser, 'id'> & { id: null };
