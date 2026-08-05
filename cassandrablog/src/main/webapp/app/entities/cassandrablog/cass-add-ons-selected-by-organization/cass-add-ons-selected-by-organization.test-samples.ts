import dayjs from 'dayjs/esm';

import { ICassAddOnsSelectedByOrganization, NewCassAddOnsSelectedByOrganization } from './cass-add-ons-selected-by-organization.model';

export const sampleWithRequiredData: ICassAddOnsSelectedByOrganization = {
  compositeId: {
    organizationId: 'sample-organizationId-1',
    arrivalDate: dayjs('2024-01-02T12:00:00Z'),
    accountNumber: 'sample-accountNumber-1',
    createdTimeId: 'sample-createdTimeId-1',
  },
};

export const sampleWithPartialData: ICassAddOnsSelectedByOrganization = {
  compositeId: {
    organizationId: 'sample-organizationId-2',
    arrivalDate: dayjs('2024-01-03T12:00:00Z'),
    accountNumber: 'sample-accountNumber-2',
    createdTimeId: 'sample-createdTimeId-2',
  },
  departureDate: dayjs('2024-01-03T12:00:00Z'),
  customerId: 'sample-customerId-2',
  customerFirstName: 'sample-customerFirstName-2',
  customerLastName: 'sample-customerLastName-2',
  customerUpdatedEmail: 'sample-customerUpdatedEmail-2',
  customerUpdatedPhoneNumber: 'sample-customerUpdatedPhoneNumber-2',
};

export const sampleWithFullData: ICassAddOnsSelectedByOrganization = {
  compositeId: {
    organizationId: 'sample-organizationId-3',
    arrivalDate: dayjs('2024-01-04T12:00:00Z'),
    accountNumber: 'sample-accountNumber-3',
    createdTimeId: 'sample-createdTimeId-3',
  },
  departureDate: dayjs('2024-01-04T12:00:00Z'),
  customerId: 'sample-customerId-3',
  customerFirstName: 'sample-customerFirstName-3',
  customerLastName: 'sample-customerLastName-3',
  customerUpdatedEmail: 'sample-customerUpdatedEmail-3',
  customerUpdatedPhoneNumber: 'sample-customerUpdatedPhoneNumber-3',
  customerEstimatedArrivalTime: 'sample-customerEstimatedArrivalTime-3',
  tinyUrlShortCode: 'sample-tinyUrlShortCode-3',
  addOnDetailsText: { key3: 'val-3' },
  addOnDetailsDecimal: { key3: 3 },
  addOnDetailsBoolean: { key3: false },
  addOnDetailsBigInt: { key3: dayjs('2024-01-04T12:00:00Z') },
};

export const sampleWithNewData: NewCassAddOnsSelectedByOrganization = {
  compositeId: {
    organizationId: 'sample-organizationId-4',
    arrivalDate: dayjs('2024-01-05T12:00:00Z'),
    accountNumber: 'sample-accountNumber-4',
    createdTimeId: 'sample-createdTimeId-4',
  },
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
