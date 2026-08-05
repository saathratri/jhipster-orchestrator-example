import dayjs from 'dayjs/esm';

import { ICassPost, NewCassPost } from './cass-post.model';

export const sampleWithRequiredData: ICassPost = {
  compositeId: { createdDate: dayjs('2024-01-02T12:00:00Z'), addedDateTime: dayjs('2024-01-02T12:00:00Z'), postId: 'sample-postId-1' },
};

export const sampleWithPartialData: ICassPost = {
  compositeId: { createdDate: dayjs('2024-01-03T12:00:00Z'), addedDateTime: dayjs('2024-01-03T12:00:00Z'), postId: 'sample-postId-2' },
  title: 'sample-title-2',
  content: 'sample-content-2',
};

export const sampleWithFullData: ICassPost = {
  compositeId: { createdDate: dayjs('2024-01-04T12:00:00Z'), addedDateTime: dayjs('2024-01-04T12:00:00Z'), postId: 'sample-postId-3' },
  title: 'sample-title-3',
  content: 'sample-content-3',
  publishedDateTime: dayjs('2024-01-04T12:00:00Z'),
  sentDate: dayjs('2024-01-04T12:00:00Z'),
};

export const sampleWithNewData: NewCassPost = {
  compositeId: { createdDate: dayjs('2024-01-05T12:00:00Z'), addedDateTime: dayjs('2024-01-05T12:00:00Z'), postId: 'sample-postId-4' },
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
