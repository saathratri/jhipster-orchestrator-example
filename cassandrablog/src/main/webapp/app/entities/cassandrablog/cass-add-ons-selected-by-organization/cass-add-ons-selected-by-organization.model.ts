import dayjs from 'dayjs/esm';

export interface ICassAddOnsSelectedByOrganization {
  compositeId: ICassAddOnsSelectedByOrganizationId;
  departureDate?: dayjs.Dayjs | null;
  customerId?: string | null;
  customerFirstName?: string | null;
  customerLastName?: string | null;
  customerUpdatedEmail?: string | null;
  customerUpdatedPhoneNumber?: string | null;
  customerEstimatedArrivalTime?: string | null;
  tinyUrlShortCode?: string | null;
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
}
export interface ICassAddOnsSelectedByOrganizationId {
  organizationId: string | null;
  arrivalDate: dayjs.Dayjs | null;
  accountNumber: string | null;
  createdTimeId: string | null;
}

export type NewCassAddOnsSelectedByOrganization = Omit<ICassAddOnsSelectedByOrganization, 'compositeId'> & {
  compositeId: ICassAddOnsSelectedByOrganizationId;
};
