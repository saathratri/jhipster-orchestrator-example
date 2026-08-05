import dayjs from 'dayjs/esm';

import { ICassAddOnsAvailableByOrganization, NewCassAddOnsAvailableByOrganization } from './cass-add-ons-available-by-organization.model';

export const sampleWithRequiredData: ICassAddOnsAvailableByOrganization = {
  compositeId: {
    organizationId: 'sample-organizationId-1',
    entityType: 'sample-entityType-1',
    entityId: 'sample-entityId-1',
    addOnId: 'sample-addOnId-1',
  },
};

export const sampleWithPartialData: ICassAddOnsAvailableByOrganization = {
  compositeId: {
    organizationId: 'sample-organizationId-2',
    entityType: 'sample-entityType-2',
    entityId: 'sample-entityId-2',
    addOnId: 'sample-addOnId-2',
  },
  addOnType: 'sample-addOnType-2',
  addOnDetailsText: { key2: 'val-2' },
  addOnDetailsDecimal: { key2: 2 },
};

export const sampleWithFullData: ICassAddOnsAvailableByOrganization = {
  compositeId: {
    organizationId: 'sample-organizationId-3',
    entityType: 'sample-entityType-3',
    entityId: 'sample-entityId-3',
    addOnId: 'sample-addOnId-3',
  },
  addOnType: 'sample-addOnType-3',
  addOnDetailsText: { key3: 'val-3' },
  addOnDetailsDecimal: { key3: 3 },
  addOnDetailsBoolean: { key3: false },
  addOnDetailsBigInt: { key3: dayjs('2024-01-04T12:00:00Z') },
};

export const sampleWithNewData: NewCassAddOnsAvailableByOrganization = {
  compositeId: {
    organizationId: 'sample-organizationId-4',
    entityType: 'sample-entityType-4',
    entityId: 'sample-entityId-4',
    addOnId: 'sample-addOnId-4',
  },
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
