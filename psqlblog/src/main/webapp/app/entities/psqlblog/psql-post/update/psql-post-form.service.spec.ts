import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../psql-post.test-samples';

import { PsqlPostFormService } from './psql-post-form.service';

describe('PsqlPost Form Service', () => {
  let service: PsqlPostFormService;

  beforeEach(() => {
    service = TestBed.inject(PsqlPostFormService);
  });

  describe('Service methods', () => {
    describe('createPsqlPostFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPsqlPostFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            date: expect.any(Object),
            blog: expect.any(Object),
            tags: expect.any(Object),
          }),
        );
      });

      it('passing IPsqlPost should create a new form with FormGroup', () => {
        const formGroup = service.createPsqlPostFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            date: expect.any(Object),
            blog: expect.any(Object),
            tags: expect.any(Object),
          }),
        );
      });
    });

    describe('getPsqlPost', () => {
      it('should return NewPsqlPost for default PsqlPost initial value', () => {
        const formGroup = service.createPsqlPostFormGroup(sampleWithNewData);

        const psqlPost = service.getPsqlPost(formGroup);

        expect(psqlPost).toMatchObject(sampleWithNewData);
      });

      it('should return NewPsqlPost for empty PsqlPost initial value', () => {
        const formGroup = service.createPsqlPostFormGroup();

        const psqlPost = service.getPsqlPost(formGroup);

        expect(psqlPost).toMatchObject({});
      });

      it('should return IPsqlPost', () => {
        const formGroup = service.createPsqlPostFormGroup(sampleWithRequiredData);

        const psqlPost = service.getPsqlPost(formGroup);

        expect(psqlPost).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPsqlPost should not enable id FormControl', () => {
        const formGroup = service.createPsqlPostFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPsqlPost should disable id FormControl', () => {
        const formGroup = service.createPsqlPostFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
