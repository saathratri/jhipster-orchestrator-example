import { ICassBlog, NewCassBlog } from './cass-blog.model';

export const sampleWithRequiredData: ICassBlog = {
  compositeId: { category: 'sample-category-1', blogId: 'sample-blogId-1' },
};

export const sampleWithPartialData: ICassBlog = {
  compositeId: { category: 'sample-category-2', blogId: 'sample-blogId-2' },
  handle: 'sample-handle-2',
};

export const sampleWithFullData: ICassBlog = {
  compositeId: { category: 'sample-category-3', blogId: 'sample-blogId-3' },
  handle: 'sample-handle-3',
  content: 'sample-content-3',
};

export const sampleWithNewData: NewCassBlog = {
  compositeId: { category: 'sample-category-4', blogId: 'sample-blogId-4' },
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
