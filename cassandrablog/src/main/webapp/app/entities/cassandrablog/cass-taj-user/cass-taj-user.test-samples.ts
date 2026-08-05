import { ICassTajUser, NewCassTajUser } from './cass-taj-user.model';

export const sampleWithRequiredData: ICassTajUser = {
  id: 'sample-id-1',
};

export const sampleWithPartialData: ICassTajUser = {
  id: 'sample-id-2',
  login: 'sample-login-2',
};

export const sampleWithFullData: ICassTajUser = {
  id: 'sample-id-3',
  login: 'sample-login-3',
};

export const sampleWithNewData: NewCassTajUser = {
  id: 'sample-id-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
