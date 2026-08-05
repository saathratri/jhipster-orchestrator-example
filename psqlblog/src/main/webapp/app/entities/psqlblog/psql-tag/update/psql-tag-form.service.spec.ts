import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../psql-tag.test-samples';

import { PsqlTagFormService } from './psql-tag-form.service';

describe('PsqlTag Form Service', () => {
  let service: PsqlTagFormService;

  beforeEach(() => {
    service = TestBed.inject(PsqlTagFormService);
  });

  describe('Service methods', () => {
    describe('createPsqlTagFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPsqlTagFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            nameEmbedding: expect.any(Object),
            descriptionEmbedding: expect.any(Object),
            posts: expect.any(Object),
          }),
        );
      });

      it('passing IPsqlTag should create a new form with FormGroup', () => {
        const formGroup = service.createPsqlTagFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            nameEmbedding: expect.any(Object),
            descriptionEmbedding: expect.any(Object),
            posts: expect.any(Object),
          }),
        );
      });
    });

    describe('getPsqlTag', () => {
      it('should return NewPsqlTag for default PsqlTag initial value', () => {
        const formGroup = service.createPsqlTagFormGroup(sampleWithNewData);

        const psqlTag = service.getPsqlTag(formGroup);

        expect(psqlTag).toMatchObject(sampleWithNewData);
      });

      it('should return NewPsqlTag for empty PsqlTag initial value', () => {
        const formGroup = service.createPsqlTagFormGroup();

        const psqlTag = service.getPsqlTag(formGroup);

        expect(psqlTag).toMatchObject({});
      });

      it('should return IPsqlTag', () => {
        const formGroup = service.createPsqlTagFormGroup(sampleWithRequiredData);

        const psqlTag = service.getPsqlTag(formGroup);

        expect(psqlTag).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPsqlTag should not enable id FormControl', () => {
        const formGroup = service.createPsqlTagFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPsqlTag should disable id FormControl', () => {
        const formGroup = service.createPsqlTagFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
