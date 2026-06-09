import { IPsqlPost } from 'app/entities/psqlblog/psql-post/psql-post.model';

export interface IPsqlTag {
  id: string;
  name?: string | null;
  description?: string | null;
  nameEmbedding?: number[] | null;
  descriptionEmbedding?: number[] | null;

  posts?: Pick<IPsqlPost, 'id'>[] | null;
}

export type NewPsqlTag = Omit<IPsqlTag, 'id'> & { id: null };
