import dayjs from 'dayjs/esm';

import { IPsqlPost, NewPsqlPost } from './psql-post.model';

export const sampleWithRequiredData: IPsqlPost = {
  id: '7248bfe3-82d1-42d7-83f4-811d5818c1ec',
  title: 'lid',
  content: '../fake-data/blob/hipster.txt',
  date: dayjs('2026-07-07T05:28'),
};

export const sampleWithPartialData: IPsqlPost = {
  id: '0848e54a-07ad-407b-ad28-e05b3fb7fb1b',
  title: 'finally',
  content: '../fake-data/blob/hipster.txt',
  date: dayjs('2026-07-06T20:50'),
};

export const sampleWithFullData: IPsqlPost = {
  id: 'e32ef4a4-d237-47be-927f-42aa835b1c16',
  title: 'but',
  content: '../fake-data/blob/hipster.txt',
  date: dayjs('2026-07-07T13:03'),
};

export const sampleWithNewData: NewPsqlPost = {
  title: 'sleepily why',
  content: '../fake-data/blob/hipster.txt',
  date: dayjs('2026-07-07T16:03'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
