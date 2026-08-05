import dayjs from 'dayjs/esm';

import { ICassSaathratriEntity2, NewCassSaathratriEntity2 } from './cass-saathratri-entity-2.model';

export const sampleWithRequiredData: ICassSaathratriEntity2 = {
  compositeId: {
    entityTypeId: 'sample-entityTypeId-1',
    yearOfDateAdded: 1001,
    arrivalDate: dayjs('2024-01-02T12:00:00Z'),
    blogId: 'sample-blogId-1',
  },
};

export const sampleWithPartialData: ICassSaathratriEntity2 = {
  compositeId: {
    entityTypeId: 'sample-entityTypeId-2',
    yearOfDateAdded: 1002,
    arrivalDate: dayjs('2024-01-03T12:00:00Z'),
    blogId: 'sample-blogId-2',
  },
  entityName: 'sample-entityName-2',
  entityDescription: 'sample-entityDescription-2',
};

export const sampleWithFullData: ICassSaathratriEntity2 = {
  compositeId: {
    entityTypeId: 'sample-entityTypeId-3',
    yearOfDateAdded: 1003,
    arrivalDate: dayjs('2024-01-04T12:00:00Z'),
    blogId: 'sample-blogId-3',
  },
  entityName: 'sample-entityName-3',
  entityDescription: 'sample-entityDescription-3',
  entityCost: 1003,
  departureDate: dayjs('2024-01-04T12:00:00Z'),
};

export const sampleWithNewData: NewCassSaathratriEntity2 = {
  compositeId: {
    entityTypeId: 'sample-entityTypeId-4',
    yearOfDateAdded: 1004,
    arrivalDate: dayjs('2024-01-05T12:00:00Z'),
    blogId: 'sample-blogId-4',
  },
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
