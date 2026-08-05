import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-add-ons-selected-by-organization.test-samples';

import { CassAddOnsSelectedByOrganizationFormService } from './cass-add-ons-selected-by-organization-form.service';

describe('CassAddOnsSelectedByOrganization Form Service', () => {
  let service: CassAddOnsSelectedByOrganizationFormService;

  beforeEach(() => {
    service = TestBed.inject(CassAddOnsSelectedByOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createCassAddOnsSelectedByOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassAddOnsSelectedByOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            departureDate: expect.any(Object),
            customerId: expect.any(Object),
            customerFirstName: expect.any(Object),
            customerLastName: expect.any(Object),
            customerUpdatedEmail: expect.any(Object),
            customerUpdatedPhoneNumber: expect.any(Object),
            customerEstimatedArrivalTime: expect.any(Object),
            tinyUrlShortCode: expect.any(Object),
            addOnDetailsText: expect.any(Object),
            addOnDetailsDecimal: expect.any(Object),
            addOnDetailsBoolean: expect.any(Object),
            addOnDetailsBigInt: expect.any(Object),
          }),
        );
      });

      it('passing ICassAddOnsSelectedByOrganization should create a new form with FormGroup', () => {
        const formGroup = service.createCassAddOnsSelectedByOrganizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            departureDate: expect.any(Object),
            customerId: expect.any(Object),
            customerFirstName: expect.any(Object),
            customerLastName: expect.any(Object),
            customerUpdatedEmail: expect.any(Object),
            customerUpdatedPhoneNumber: expect.any(Object),
            customerEstimatedArrivalTime: expect.any(Object),
            tinyUrlShortCode: expect.any(Object),
            addOnDetailsText: expect.any(Object),
            addOnDetailsDecimal: expect.any(Object),
            addOnDetailsBoolean: expect.any(Object),
            addOnDetailsBigInt: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassAddOnsSelectedByOrganization', () => {
      it('should return NewCassAddOnsSelectedByOrganization for default CassAddOnsSelectedByOrganization initial value', () => {
        const formGroup = service.createCassAddOnsSelectedByOrganizationFormGroup(sampleWithNewData);

        const cassAddOnsSelectedByOrganization = service.getCassAddOnsSelectedByOrganization(formGroup);

        expect(cassAddOnsSelectedByOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassAddOnsSelectedByOrganization for empty CassAddOnsSelectedByOrganization initial value', () => {
        const formGroup = service.createCassAddOnsSelectedByOrganizationFormGroup();

        const cassAddOnsSelectedByOrganization = service.getCassAddOnsSelectedByOrganization(formGroup);

        expect(cassAddOnsSelectedByOrganization).toMatchObject({});
      });

      it('should return ICassAddOnsSelectedByOrganization', () => {
        const formGroup = service.createCassAddOnsSelectedByOrganizationFormGroup(sampleWithRequiredData);

        const cassAddOnsSelectedByOrganization = service.getCassAddOnsSelectedByOrganization(formGroup);

        expect(cassAddOnsSelectedByOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassAddOnsSelectedByOrganization should keep the key control disabled', () => {
        const formGroup = service.createCassAddOnsSelectedByOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassAddOnsSelectedByOrganizationFormGroup();

        service.resetForm(formGroup, {
          compositeId: { organizationId: null, arrivalDate: null, accountNumber: null, createdTimeId: null },
        });

        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
