import { ICassTag, NewCassTag } from './cass-tag.model';

export const sampleWithRequiredData: ICassTag = {
  id: 'sample-id-1',
};

export const sampleWithPartialData: ICassTag = {
  id: 'sample-id-2',
  name: 'sample-name-2',
};

export const sampleWithFullData: ICassTag = {
  id: 'sample-id-3',
  name: 'sample-name-3',
  description: 'sample-description-3',
};

export const sampleWithNewData: NewCassTag = {
  id: 'sample-id-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
