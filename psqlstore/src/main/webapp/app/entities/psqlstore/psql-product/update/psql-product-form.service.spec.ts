import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../psql-product.test-samples';

import { PsqlProductFormService } from './psql-product-form.service';

describe('PsqlProduct Form Service', () => {
  let service: PsqlProductFormService;

  beforeEach(() => {
    service = TestBed.inject(PsqlProductFormService);
  });

  describe('Service methods', () => {
    describe('createPsqlProductFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPsqlProductFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            price: expect.any(Object),
            image: expect.any(Object),
          }),
        );
      });

      it('passing IPsqlProduct should create a new form with FormGroup', () => {
        const formGroup = service.createPsqlProductFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            price: expect.any(Object),
            image: expect.any(Object),
          }),
        );
      });
    });

    describe('getPsqlProduct', () => {
      it('should return NewPsqlProduct for default PsqlProduct initial value', () => {
        const formGroup = service.createPsqlProductFormGroup(sampleWithNewData);

        const psqlProduct = service.getPsqlProduct(formGroup);

        expect(psqlProduct).toMatchObject(sampleWithNewData);
      });

      it('should return NewPsqlProduct for empty PsqlProduct initial value', () => {
        const formGroup = service.createPsqlProductFormGroup();

        const psqlProduct = service.getPsqlProduct(formGroup);

        expect(psqlProduct).toMatchObject({});
      });

      it('should return IPsqlProduct', () => {
        const formGroup = service.createPsqlProductFormGroup(sampleWithRequiredData);

        const psqlProduct = service.getPsqlProduct(formGroup);

        expect(psqlProduct).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPsqlProduct should not enable id FormControl', () => {
        const formGroup = service.createPsqlProductFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPsqlProduct should disable id FormControl', () => {
        const formGroup = service.createPsqlProductFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
