import { IPsqlTajUser, NewPsqlTajUser } from './psql-taj-user.model';

export const sampleWithRequiredData: IPsqlTajUser = {
  id: '8f0570e3-ec38-4b0c-a570-44e5b325d837',
  login: 'notXXXX',
};

export const sampleWithPartialData: IPsqlTajUser = {
  id: '59720920-321f-4431-83fa-6b7310cbd396',
  login: 'whoXXXX',
};

export const sampleWithFullData: IPsqlTajUser = {
  id: 'b46abfd8-eb30-48ba-a24a-c6fd59fcbb3d',
  login: 'brr briefly',
};

export const sampleWithNewData: NewPsqlTajUser = {
  login: 'marimba',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
