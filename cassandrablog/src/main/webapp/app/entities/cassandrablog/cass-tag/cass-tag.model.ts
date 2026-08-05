export interface ICassTag {
  id: string;
  name?: string | null;
  description?: string | null;
  nameEmbedding?: number[] | null;
  descriptionEmbedding?: number[] | null;
}

export type NewCassTag = Omit<ICassTag, 'id'> & { id: string };
