export interface ICassSaathratriEntity {
  entityId: string;
  entityName?: string | null;
  entityDescription?: string | null;
  entityCost?: number | null;
  createdId?: string | null;
  createdTimeId?: string | null;
}

export type NewCassSaathratriEntity = Omit<ICassSaathratriEntity, 'entityId'> & { entityId: string };
