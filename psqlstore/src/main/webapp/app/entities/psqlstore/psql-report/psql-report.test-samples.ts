import dayjs from 'dayjs/esm';

import { IPsqlReport, NewPsqlReport } from './psql-report.model';

export const sampleWithRequiredData: IPsqlReport = {
  id: '511514f0-12d1-4f36-86b6-5aad5fb2e43a',
  fileName: 'analogy ack',
  fileExtension: 'mmm nightl',
  createDate: dayjs('2026-05-30T07:46'),
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithPartialData: IPsqlReport = {
  id: 'a85380e4-cd4d-4f57-ad91-f6071986a6ac',
  fileName: 'amidst oof dearly',
  fileExtension: 'circa meh',
  createDate: dayjs('2026-05-30T04:59'),
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithFullData: IPsqlReport = {
  id: '6d996dae-2ab0-484c-aa8c-5461c7117fb9',
  fileName: 'manage',
  fileExtension: 'yippee',
  createDate: dayjs('2026-05-30T08:45'),
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
  approved: false,
};

export const sampleWithNewData: NewPsqlReport = {
  fileName: 'suddenly',
  fileExtension: 'fully yowz',
  createDate: dayjs('2026-05-30T13:07'),
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
