import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-product.test-samples';

import { CassProductFormService } from './cass-product-form.service';

describe('CassProduct Form Service', () => {
  let service: CassProductFormService;

  beforeEach(() => {
    service = TestBed.inject(CassProductFormService);
  });

  describe('Service methods', () => {
    describe('createCassProductFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassProductFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            price: expect.any(Object),
            image: expect.any(Object),
            addedDate: expect.any(Object),
          }),
        );
      });

      it('passing ICassProduct should create a new form with FormGroup', () => {
        const formGroup = service.createCassProductFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            price: expect.any(Object),
            image: expect.any(Object),
            addedDate: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassProduct', () => {
      it('should return NewCassProduct for default CassProduct initial value', () => {
        const formGroup = service.createCassProductFormGroup(sampleWithNewData);

        const cassProduct = service.getCassProduct(formGroup);

        expect(cassProduct).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassProduct for empty CassProduct initial value', () => {
        const formGroup = service.createCassProductFormGroup();

        const cassProduct = service.getCassProduct(formGroup);

        expect(cassProduct).toMatchObject({});
      });

      it('should return ICassProduct', () => {
        const formGroup = service.createCassProductFormGroup(sampleWithRequiredData);

        const cassProduct = service.getCassProduct(formGroup);

        expect(cassProduct).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassProduct should keep the key control disabled', () => {
        const formGroup = service.createCassProductFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassProductFormGroup();

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
