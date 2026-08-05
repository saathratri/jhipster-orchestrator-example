import dayjs from 'dayjs/esm';

export interface ICassLandingPageByOrganization {
  organizationId: string;
  detailsText?: Record<string, string> | null;
  detailsDecimal?: Record<string, number> | null;
  detailsBoolean?: Record<string, boolean> | null;
  detailsBigInt?: Record<string, dayjs.Dayjs> | null;
}

export type NewCassLandingPageByOrganization = Omit<ICassLandingPageByOrganization, 'organizationId'> & { organizationId: string };
