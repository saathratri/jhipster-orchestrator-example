import dayjs from 'dayjs/esm';

export interface ICassSaathratriEntity3 {
  compositeId: ICassSaathratriEntity3Id;
  entityName?: string | null;
  entityDescription?: string | null;
  entityCost?: number | null;
  departureDate?: dayjs.Dayjs | null;
  tags?: Set<string> | null;
}
export interface ICassSaathratriEntity3Id {
  entityType: string | null;
  createdTimeId: string | null;
}

export type NewCassSaathratriEntity3 = Omit<ICassSaathratriEntity3, 'compositeId'> & { compositeId: ICassSaathratriEntity3Id };
