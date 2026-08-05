export interface ICassTajUser {
  id: string;
  login?: string | null;
}

export type NewCassTajUser = Omit<ICassTajUser, 'id'> & { id: string };
