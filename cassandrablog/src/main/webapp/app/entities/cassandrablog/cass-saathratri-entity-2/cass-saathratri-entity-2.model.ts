import dayjs from 'dayjs/esm';

export interface ICassSaathratriEntity2 {
  compositeId: ICassSaathratriEntity2Id;
  entityName?: string | null;
  entityDescription?: string | null;
  entityCost?: number | null;
  departureDate?: dayjs.Dayjs | null;
}
export interface ICassSaathratriEntity2Id {
  entityTypeId: string | null;
  yearOfDateAdded: number | null;
  arrivalDate: dayjs.Dayjs | null;
  blogId: string | null;
}

export type NewCassSaathratriEntity2 = Omit<ICassSaathratriEntity2, 'compositeId'> & { compositeId: ICassSaathratriEntity2Id };
