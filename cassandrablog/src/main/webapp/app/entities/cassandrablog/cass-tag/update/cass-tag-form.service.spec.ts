import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-tag.test-samples';

import { CassTagFormService } from './cass-tag-form.service';

describe('CassTag Form Service', () => {
  let service: CassTagFormService;

  beforeEach(() => {
    service = TestBed.inject(CassTagFormService);
  });

  describe('Service methods', () => {
    describe('createCassTagFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassTagFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });

      it('passing ICassTag should create a new form with FormGroup', () => {
        const formGroup = service.createCassTagFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassTag', () => {
      it('should return NewCassTag for default CassTag initial value', () => {
        const formGroup = service.createCassTagFormGroup(sampleWithNewData);

        const cassTag = service.getCassTag(formGroup);

        expect(cassTag).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassTag for empty CassTag initial value', () => {
        const formGroup = service.createCassTagFormGroup();

        const cassTag = service.getCassTag(formGroup);

        expect(cassTag).toMatchObject({});
      });

      it('should return ICassTag', () => {
        const formGroup = service.createCassTagFormGroup(sampleWithRequiredData);

        const cassTag = service.getCassTag(formGroup);

        expect(cassTag).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassTag should keep the key control disabled', () => {
        const formGroup = service.createCassTagFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassTagFormGroup();

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
