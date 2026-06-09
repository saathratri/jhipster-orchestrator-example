export interface IPsqlProduct {
  id: string;
  title?: string | null;
  price?: number | null;
  image?: string | null;
  imageContentType?: string | null;
}

export type NewPsqlProduct = Omit<IPsqlProduct, 'id'> & { id: null };
