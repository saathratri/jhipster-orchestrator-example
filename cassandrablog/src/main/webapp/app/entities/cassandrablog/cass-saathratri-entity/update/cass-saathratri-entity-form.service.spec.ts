import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-saathratri-entity.test-samples';

import { CassSaathratriEntityFormService } from './cass-saathratri-entity-form.service';

describe('CassSaathratriEntity Form Service', () => {
  let service: CassSaathratriEntityFormService;

  beforeEach(() => {
    service = TestBed.inject(CassSaathratriEntityFormService);
  });

  describe('Service methods', () => {
    describe('createCassSaathratriEntityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassSaathratriEntityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            entityId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            createdId: expect.any(Object),
            createdTimeId: expect.any(Object),
          }),
        );
      });

      it('passing ICassSaathratriEntity should create a new form with FormGroup', () => {
        const formGroup = service.createCassSaathratriEntityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            entityId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            createdId: expect.any(Object),
            createdTimeId: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassSaathratriEntity', () => {
      it('should return NewCassSaathratriEntity for default CassSaathratriEntity initial value', () => {
        const formGroup = service.createCassSaathratriEntityFormGroup(sampleWithNewData);

        const cassSaathratriEntity = service.getCassSaathratriEntity(formGroup);

        expect(cassSaathratriEntity).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassSaathratriEntity for empty CassSaathratriEntity initial value', () => {
        const formGroup = service.createCassSaathratriEntityFormGroup();

        const cassSaathratriEntity = service.getCassSaathratriEntity(formGroup);

        expect(cassSaathratriEntity).toMatchObject({});
      });

      it('should return ICassSaathratriEntity', () => {
        const formGroup = service.createCassSaathratriEntityFormGroup(sampleWithRequiredData);

        const cassSaathratriEntity = service.getCassSaathratriEntity(formGroup);

        expect(cassSaathratriEntity).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassSaathratriEntity should keep the key control disabled', () => {
        const formGroup = service.createCassSaathratriEntityFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.entityId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.entityId.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassSaathratriEntityFormGroup();

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.entityId.disabled).toBe(true);
      });
    });
  });
});
