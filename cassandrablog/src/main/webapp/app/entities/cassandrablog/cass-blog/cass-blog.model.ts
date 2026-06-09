export interface ICassBlog {
  compositeId: ICassBlogId;
  handle?: string | null;
  content?: string | null;
}
export interface ICassBlogId {
  category: string | null;
  blogId: string | null;
}

export type NewCassBlog = Omit<ICassBlog, 'compositeId'> & { compositeId: ICassBlogId };
