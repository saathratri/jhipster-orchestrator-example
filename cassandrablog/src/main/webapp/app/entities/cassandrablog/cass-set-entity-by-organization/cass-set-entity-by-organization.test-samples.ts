import { ICassSetEntityByOrganization, NewCassSetEntityByOrganization } from './cass-set-entity-by-organization.model';

export const sampleWithRequiredData: ICassSetEntityByOrganization = {
  organizationId: 'sample-organizationId-1',
};

export const sampleWithPartialData: ICassSetEntityByOrganization = {
  organizationId: 'sample-organizationId-2',
  tags: new Set(['sample-2']),
};

export const sampleWithFullData: ICassSetEntityByOrganization = {
  organizationId: 'sample-organizationId-3',
  tags: new Set(['sample-3']),
};

export const sampleWithNewData: NewCassSetEntityByOrganization = {
  organizationId: 'sample-organizationId-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
