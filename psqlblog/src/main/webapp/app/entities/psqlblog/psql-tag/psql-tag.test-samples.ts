import { IPsqlTag, NewPsqlTag } from './psql-tag.model';

export const sampleWithRequiredData: IPsqlTag = {
  id: '8cefd32b-b199-4276-9251-fb581d61b46a',
  name: 'indeed',
};

export const sampleWithPartialData: IPsqlTag = {
  id: '186c87ab-e183-4457-991a-7ee0d056eba6',
  name: 'apropos',
  nameEmbedding: undefined,
};

export const sampleWithFullData: IPsqlTag = {
  id: 'f5643e66-d059-4d9d-b833-0f77a0425465',
  name: 'print',
  description: 'judgementally',
  nameEmbedding: undefined,
  descriptionEmbedding: undefined,
};

export const sampleWithNewData: NewPsqlTag = {
  name: 'whether nectarine',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
