import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-saathratri-entity-2.test-samples';

import { CassSaathratriEntity2FormService } from './cass-saathratri-entity-2-form.service';

describe('CassSaathratriEntity2 Form Service', () => {
  let service: CassSaathratriEntity2FormService;

  beforeEach(() => {
    service = TestBed.inject(CassSaathratriEntity2FormService);
  });

  describe('Service methods', () => {
    describe('createCassSaathratriEntity2FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassSaathratriEntity2FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            departureDate: expect.any(Object),
          }),
        );
      });

      it('passing ICassSaathratriEntity2 should create a new form with FormGroup', () => {
        const formGroup = service.createCassSaathratriEntity2FormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            departureDate: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassSaathratriEntity2', () => {
      it('should return NewCassSaathratriEntity2 for default CassSaathratriEntity2 initial value', () => {
        const formGroup = service.createCassSaathratriEntity2FormGroup(sampleWithNewData);

        const cassSaathratriEntity2 = service.getCassSaathratriEntity2(formGroup);

        expect(cassSaathratriEntity2).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassSaathratriEntity2 for empty CassSaathratriEntity2 initial value', () => {
        const formGroup = service.createCassSaathratriEntity2FormGroup();

        const cassSaathratriEntity2 = service.getCassSaathratriEntity2(formGroup);

        expect(cassSaathratriEntity2).toMatchObject({});
      });

      it('should return ICassSaathratriEntity2', () => {
        const formGroup = service.createCassSaathratriEntity2FormGroup(sampleWithRequiredData);

        const cassSaathratriEntity2 = service.getCassSaathratriEntity2(formGroup);

        expect(cassSaathratriEntity2).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassSaathratriEntity2 should keep the key control disabled', () => {
        const formGroup = service.createCassSaathratriEntity2FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.entityTypeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.entityTypeId.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassSaathratriEntity2FormGroup();

        service.resetForm(formGroup, { compositeId: { entityTypeId: null, yearOfDateAdded: null, arrivalDate: null, blogId: null } });

        expect(formGroup.controls.compositeId.controls.entityTypeId.disabled).toBe(true);
      });
    });
  });
});
