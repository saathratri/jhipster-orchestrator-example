import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-landing-page-by-organization.test-samples';

import { CassLandingPageByOrganizationFormService } from './cass-landing-page-by-organization-form.service';

describe('CassLandingPageByOrganization Form Service', () => {
  let service: CassLandingPageByOrganizationFormService;

  beforeEach(() => {
    service = TestBed.inject(CassLandingPageByOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createCassLandingPageByOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassLandingPageByOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            detailsText: expect.any(Object),
            detailsDecimal: expect.any(Object),
            detailsBoolean: expect.any(Object),
            detailsBigInt: expect.any(Object),
          }),
        );
      });

      it('passing ICassLandingPageByOrganization should create a new form with FormGroup', () => {
        const formGroup = service.createCassLandingPageByOrganizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            detailsText: expect.any(Object),
            detailsDecimal: expect.any(Object),
            detailsBoolean: expect.any(Object),
            detailsBigInt: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassLandingPageByOrganization', () => {
      it('should return NewCassLandingPageByOrganization for default CassLandingPageByOrganization initial value', () => {
        const formGroup = service.createCassLandingPageByOrganizationFormGroup(sampleWithNewData);

        const cassLandingPageByOrganization = service.getCassLandingPageByOrganization(formGroup);

        expect(cassLandingPageByOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassLandingPageByOrganization for empty CassLandingPageByOrganization initial value', () => {
        const formGroup = service.createCassLandingPageByOrganizationFormGroup();

        const cassLandingPageByOrganization = service.getCassLandingPageByOrganization(formGroup);

        expect(cassLandingPageByOrganization).toMatchObject({});
      });

      it('should return ICassLandingPageByOrganization', () => {
        const formGroup = service.createCassLandingPageByOrganizationFormGroup(sampleWithRequiredData);

        const cassLandingPageByOrganization = service.getCassLandingPageByOrganization(formGroup);

        expect(cassLandingPageByOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassLandingPageByOrganization should keep the key control disabled', () => {
        const formGroup = service.createCassLandingPageByOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassLandingPageByOrganizationFormGroup();

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
