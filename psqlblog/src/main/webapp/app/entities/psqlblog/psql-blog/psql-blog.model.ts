import { IPsqlTajUser } from 'app/entities/psqlblog/psql-taj-user/psql-taj-user.model';

export interface IPsqlBlog {
  id: string;
  name?: string | null;
  handle?: string | null;

  tajUser?: Pick<IPsqlTajUser, 'id' | 'login'> | null;
}

export type NewPsqlBlog = Omit<IPsqlBlog, 'id'> & { id: null };
