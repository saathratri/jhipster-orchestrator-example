import { IPsqlProduct, NewPsqlProduct } from './psql-product.model';

export const sampleWithRequiredData: IPsqlProduct = {
  id: '447d4a27-1f98-47a8-86d6-1df6c8e307e9',
  title: 'appliance considering',
  price: 27431.56,
};

export const sampleWithPartialData: IPsqlProduct = {
  id: '598f6b69-5d97-4257-8835-108eac55770b',
  title: 'frankly fooey foolishly',
  price: 10618.48,
};

export const sampleWithFullData: IPsqlProduct = {
  id: '5004bf33-d885-4ae6-8421-d8bde9ea68ab',
  title: 'meanwhile',
  price: 7233.37,
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithNewData: NewPsqlProduct = {
  title: 'fooey atop designation',
  price: 30459.04,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
