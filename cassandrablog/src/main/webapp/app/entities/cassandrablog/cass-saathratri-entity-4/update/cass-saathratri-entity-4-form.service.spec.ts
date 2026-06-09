import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-saathratri-entity-4.test-samples';

import { CassSaathratriEntity4FormService } from './cass-saathratri-entity-4-form.service';

describe('CassSaathratriEntity4 Form Service', () => {
  let service: CassSaathratriEntity4FormService;

  beforeEach(() => {
    service = TestBed.inject(CassSaathratriEntity4FormService);
  });

  describe('Service methods', () => {
    describe('createCassSaathratriEntity4FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassSaathratriEntity4FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            attributeValue: expect.any(Object),
          }),
        );
      });

      it('passing ICassSaathratriEntity4 should create a new form with FormGroup', () => {
        const formGroup = service.createCassSaathratriEntity4FormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            attributeValue: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassSaathratriEntity4', () => {
      it('should return NewCassSaathratriEntity4 for default CassSaathratriEntity4 initial value', () => {
        const formGroup = service.createCassSaathratriEntity4FormGroup(sampleWithNewData);

        const cassSaathratriEntity4 = service.getCassSaathratriEntity4(formGroup);

        expect(cassSaathratriEntity4).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassSaathratriEntity4 for empty CassSaathratriEntity4 initial value', () => {
        const formGroup = service.createCassSaathratriEntity4FormGroup();

        const cassSaathratriEntity4 = service.getCassSaathratriEntity4(formGroup);

        expect(cassSaathratriEntity4).toMatchObject({});
      });

      it('should return ICassSaathratriEntity4', () => {
        const formGroup = service.createCassSaathratriEntity4FormGroup(sampleWithRequiredData);

        const cassSaathratriEntity4 = service.getCassSaathratriEntity4(formGroup);

        expect(cassSaathratriEntity4).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassSaathratriEntity4 should keep the key control disabled', () => {
        const formGroup = service.createCassSaathratriEntity4FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassSaathratriEntity4FormGroup();

        service.resetForm(formGroup, { compositeId: { organizationId: null, attributeKey: null } });

        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
