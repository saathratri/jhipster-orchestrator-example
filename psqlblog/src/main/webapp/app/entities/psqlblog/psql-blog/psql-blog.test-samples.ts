import { IPsqlBlog, NewPsqlBlog } from './psql-blog.model';

export const sampleWithRequiredData: IPsqlBlog = {
  id: '842d00f3-56dc-453d-948c-ab8771c911d2',
  name: 'pace extra-large teammate',
  handle: 'humidity about buck',
};

export const sampleWithPartialData: IPsqlBlog = {
  id: '399d7ea2-512f-495a-9df5-ccf994c83f61',
  name: 'regularly above',
  handle: 'upbeat',
};

export const sampleWithFullData: IPsqlBlog = {
  id: 'b8682d2a-f409-4859-840f-c9b3dbdd3404',
  name: 'antique yieldingly',
  handle: 'far for furthermore',
};

export const sampleWithNewData: NewPsqlBlog = {
  name: 'emboss or',
  handle: 'eek',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
