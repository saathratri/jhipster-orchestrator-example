import dayjs from 'dayjs/esm';

export interface ICassProduct {
  id: string;
  title?: string | null;
  price?: number | null;
  image?: string | null;
  imageContentType?: string | null;
  addedDate?: dayjs.Dayjs | null;
}

export type NewCassProduct = Omit<ICassProduct, 'id'> & { id: string };
