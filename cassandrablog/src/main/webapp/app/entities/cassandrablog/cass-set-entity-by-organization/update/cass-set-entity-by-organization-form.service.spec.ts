import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-set-entity-by-organization.test-samples';

import { CassSetEntityByOrganizationFormService } from './cass-set-entity-by-organization-form.service';

describe('CassSetEntityByOrganization Form Service', () => {
  let service: CassSetEntityByOrganizationFormService;

  beforeEach(() => {
    service = TestBed.inject(CassSetEntityByOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createCassSetEntityByOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassSetEntityByOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            tags: expect.any(Object),
          }),
        );
      });

      it('passing ICassSetEntityByOrganization should create a new form with FormGroup', () => {
        const formGroup = service.createCassSetEntityByOrganizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            tags: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassSetEntityByOrganization', () => {
      it('should return NewCassSetEntityByOrganization for default CassSetEntityByOrganization initial value', () => {
        const formGroup = service.createCassSetEntityByOrganizationFormGroup(sampleWithNewData);

        const cassSetEntityByOrganization = service.getCassSetEntityByOrganization(formGroup);

        expect(cassSetEntityByOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassSetEntityByOrganization for empty CassSetEntityByOrganization initial value', () => {
        const formGroup = service.createCassSetEntityByOrganizationFormGroup();

        const cassSetEntityByOrganization = service.getCassSetEntityByOrganization(formGroup);

        expect(cassSetEntityByOrganization).toMatchObject({});
      });

      it('should return ICassSetEntityByOrganization', () => {
        const formGroup = service.createCassSetEntityByOrganizationFormGroup(sampleWithRequiredData);

        const cassSetEntityByOrganization = service.getCassSetEntityByOrganization(formGroup);

        expect(cassSetEntityByOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassSetEntityByOrganization should keep the key control disabled', () => {
        const formGroup = service.createCassSetEntityByOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassSetEntityByOrganizationFormGroup();

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
