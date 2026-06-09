import dayjs from 'dayjs/esm';

import { ICassProduct, NewCassProduct } from './cass-product.model';

export const sampleWithRequiredData: ICassProduct = {
  id: 'sample-id-1',
};

export const sampleWithPartialData: ICassProduct = {
  id: 'sample-id-2',
  title: 'sample-title-2',
  price: 1002,
};

export const sampleWithFullData: ICassProduct = {
  id: 'sample-id-3',
  title: 'sample-title-3',
  price: 1003,
  image: 'sample-image-3',
  addedDate: dayjs('2024-01-04T12:00:00Z'),
};

export const sampleWithNewData: NewCassProduct = {
  id: 'sample-id-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
