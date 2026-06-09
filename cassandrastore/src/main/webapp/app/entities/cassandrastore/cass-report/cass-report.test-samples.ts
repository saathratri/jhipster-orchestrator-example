import dayjs from 'dayjs/esm';

import { ICassReport, NewCassReport } from './cass-report.model';

export const sampleWithRequiredData: ICassReport = {
  id: 'sample-id-1',
};

export const sampleWithPartialData: ICassReport = {
  id: 'sample-id-2',
  fileName: 'sample-fileName-2',
  fileExtension: 'sample-fileExtension-2',
  createDate: dayjs('2024-01-03T12:00:00Z'),
};

export const sampleWithFullData: ICassReport = {
  id: 'sample-id-3',
  fileName: 'sample-fileName-3',
  fileExtension: 'sample-fileExtension-3',
  createDate: dayjs('2024-01-04T12:00:00Z'),
  file: 'sample-file-3',
  approved: false,
};

export const sampleWithNewData: NewCassReport = {
  id: 'sample-id-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
