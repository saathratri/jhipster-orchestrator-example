import dayjs from 'dayjs/esm';

import { ICassLandingPageByOrganization, NewCassLandingPageByOrganization } from './cass-landing-page-by-organization.model';

export const sampleWithRequiredData: ICassLandingPageByOrganization = {
  organizationId: 'sample-organizationId-1',
};

export const sampleWithPartialData: ICassLandingPageByOrganization = {
  organizationId: 'sample-organizationId-2',
  detailsText: { key2: 'val-2' },
  detailsDecimal: { key2: 2 },
};

export const sampleWithFullData: ICassLandingPageByOrganization = {
  organizationId: 'sample-organizationId-3',
  detailsText: { key3: 'val-3' },
  detailsDecimal: { key3: 3 },
  detailsBoolean: { key3: false },
  detailsBigInt: { key3: dayjs('2024-01-04T12:00:00Z') },
};

export const sampleWithNewData: NewCassLandingPageByOrganization = {
  organizationId: 'sample-organizationId-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
