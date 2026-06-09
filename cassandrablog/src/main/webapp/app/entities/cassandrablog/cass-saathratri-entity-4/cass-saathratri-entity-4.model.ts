export interface ICassSaathratriEntity4 {
  compositeId: ICassSaathratriEntity4Id;
  attributeValue?: string | null;
}
export interface ICassSaathratriEntity4Id {
  organizationId: string | null;
  attributeKey: string | null;
}

export type NewCassSaathratriEntity4 = Omit<ICassSaathratriEntity4, 'compositeId'> & { compositeId: ICassSaathratriEntity4Id };
