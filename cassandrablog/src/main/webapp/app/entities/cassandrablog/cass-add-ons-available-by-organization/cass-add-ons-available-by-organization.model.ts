import dayjs from 'dayjs/esm';

export interface ICassAddOnsAvailableByOrganization {
  compositeId: ICassAddOnsAvailableByOrganizationId;
  addOnType?: string | null;
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
}
export interface ICassAddOnsAvailableByOrganizationId {
  organizationId: string | null;
  entityType: string | null;
  entityId: string | null;
  addOnId: string | null;
}

export type NewCassAddOnsAvailableByOrganization = Omit<ICassAddOnsAvailableByOrganization, 'compositeId'> & {
  compositeId: ICassAddOnsAvailableByOrganizationId;
};
