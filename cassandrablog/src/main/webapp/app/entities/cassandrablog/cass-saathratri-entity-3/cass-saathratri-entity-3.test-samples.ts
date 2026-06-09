import dayjs from 'dayjs/esm';

import { ICassSaathratriEntity3, NewCassSaathratriEntity3 } from './cass-saathratri-entity-3.model';

export const sampleWithRequiredData: ICassSaathratriEntity3 = {
  compositeId: { entityType: 'sample-entityType-1', createdTimeId: 'sample-createdTimeId-1' },
};

export const sampleWithPartialData: ICassSaathratriEntity3 = {
  compositeId: { entityType: 'sample-entityType-2', createdTimeId: 'sample-createdTimeId-2' },
  entityName: 'sample-entityName-2',
  entityDescription: 'sample-entityDescription-2',
  entityCost: 1002,
};

export const sampleWithFullData: ICassSaathratriEntity3 = {
  compositeId: { entityType: 'sample-entityType-3', createdTimeId: 'sample-createdTimeId-3' },
  entityName: 'sample-entityName-3',
  entityDescription: 'sample-entityDescription-3',
  entityCost: 1003,
  departureDate: dayjs('2024-01-04T12:00:00Z'),
  tags: new Set(['sample-3']),
};

export const sampleWithNewData: NewCassSaathratriEntity3 = {
  compositeId: { entityType: 'sample-entityType-4', createdTimeId: 'sample-createdTimeId-4' },
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
