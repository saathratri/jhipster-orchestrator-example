import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-saathratri-entity-3.test-samples';

import { CassSaathratriEntity3FormService } from './cass-saathratri-entity-3-form.service';

describe('CassSaathratriEntity3 Form Service', () => {
  let service: CassSaathratriEntity3FormService;

  beforeEach(() => {
    service = TestBed.inject(CassSaathratriEntity3FormService);
  });

  describe('Service methods', () => {
    describe('createCassSaathratriEntity3FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassSaathratriEntity3FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            departureDate: expect.any(Object),
            tags: expect.any(Object),
          }),
        );
      });

      it('passing ICassSaathratriEntity3 should create a new form with FormGroup', () => {
        const formGroup = service.createCassSaathratriEntity3FormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            departureDate: expect.any(Object),
            tags: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassSaathratriEntity3', () => {
      it('should return NewCassSaathratriEntity3 for default CassSaathratriEntity3 initial value', () => {
        const formGroup = service.createCassSaathratriEntity3FormGroup(sampleWithNewData);

        const cassSaathratriEntity3 = service.getCassSaathratriEntity3(formGroup);

        expect(cassSaathratriEntity3).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassSaathratriEntity3 for empty CassSaathratriEntity3 initial value', () => {
        const formGroup = service.createCassSaathratriEntity3FormGroup();

        const cassSaathratriEntity3 = service.getCassSaathratriEntity3(formGroup);

        expect(cassSaathratriEntity3).toMatchObject({});
      });

      it('should return ICassSaathratriEntity3', () => {
        const formGroup = service.createCassSaathratriEntity3FormGroup(sampleWithRequiredData);

        const cassSaathratriEntity3 = service.getCassSaathratriEntity3(formGroup);

        expect(cassSaathratriEntity3).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassSaathratriEntity3 should keep the key control disabled', () => {
        const formGroup = service.createCassSaathratriEntity3FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.entityType.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.entityType.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassSaathratriEntity3FormGroup();

        service.resetForm(formGroup, { compositeId: { entityType: null, createdTimeId: null } });

        expect(formGroup.controls.compositeId.controls.entityType.disabled).toBe(true);
      });
    });
  });
});
