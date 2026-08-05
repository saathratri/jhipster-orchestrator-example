import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-add-ons-available-by-organization.test-samples';

import { CassAddOnsAvailableByOrganizationFormService } from './cass-add-ons-available-by-organization-form.service';

describe('CassAddOnsAvailableByOrganization Form Service', () => {
  let service: CassAddOnsAvailableByOrganizationFormService;

  beforeEach(() => {
    service = TestBed.inject(CassAddOnsAvailableByOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createCassAddOnsAvailableByOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassAddOnsAvailableByOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            addOnType: expect.any(Object),
            addOnDetailsText: expect.any(Object),
            addOnDetailsDecimal: expect.any(Object),
            addOnDetailsBoolean: expect.any(Object),
            addOnDetailsBigInt: expect.any(Object),
          }),
        );
      });

      it('passing ICassAddOnsAvailableByOrganization should create a new form with FormGroup', () => {
        const formGroup = service.createCassAddOnsAvailableByOrganizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            addOnType: expect.any(Object),
            addOnDetailsText: expect.any(Object),
            addOnDetailsDecimal: expect.any(Object),
            addOnDetailsBoolean: expect.any(Object),
            addOnDetailsBigInt: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassAddOnsAvailableByOrganization', () => {
      it('should return NewCassAddOnsAvailableByOrganization for default CassAddOnsAvailableByOrganization initial value', () => {
        const formGroup = service.createCassAddOnsAvailableByOrganizationFormGroup(sampleWithNewData);

        const cassAddOnsAvailableByOrganization = service.getCassAddOnsAvailableByOrganization(formGroup);

        expect(cassAddOnsAvailableByOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassAddOnsAvailableByOrganization for empty CassAddOnsAvailableByOrganization initial value', () => {
        const formGroup = service.createCassAddOnsAvailableByOrganizationFormGroup();

        const cassAddOnsAvailableByOrganization = service.getCassAddOnsAvailableByOrganization(formGroup);

        expect(cassAddOnsAvailableByOrganization).toMatchObject({});
      });

      it('should return ICassAddOnsAvailableByOrganization', () => {
        const formGroup = service.createCassAddOnsAvailableByOrganizationFormGroup(sampleWithRequiredData);

        const cassAddOnsAvailableByOrganization = service.getCassAddOnsAvailableByOrganization(formGroup);

        expect(cassAddOnsAvailableByOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassAddOnsAvailableByOrganization should keep the key control disabled', () => {
        const formGroup = service.createCassAddOnsAvailableByOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassAddOnsAvailableByOrganizationFormGroup();

        service.resetForm(formGroup, { compositeId: { organizationId: null, entityType: null, entityId: null, addOnId: null } });

        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
