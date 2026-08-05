import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-blog.test-samples';

import { CassBlogFormService } from './cass-blog-form.service';

describe('CassBlog Form Service', () => {
  let service: CassBlogFormService;

  beforeEach(() => {
    service = TestBed.inject(CassBlogFormService);
  });

  describe('Service methods', () => {
    describe('createCassBlogFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassBlogFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            handle: expect.any(Object),
            content: expect.any(Object),
          }),
        );
      });

      it('passing ICassBlog should create a new form with FormGroup', () => {
        const formGroup = service.createCassBlogFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            handle: expect.any(Object),
            content: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassBlog', () => {
      it('should return NewCassBlog for default CassBlog initial value', () => {
        const formGroup = service.createCassBlogFormGroup(sampleWithNewData);

        const cassBlog = service.getCassBlog(formGroup);

        expect(cassBlog).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassBlog for empty CassBlog initial value', () => {
        const formGroup = service.createCassBlogFormGroup();

        const cassBlog = service.getCassBlog(formGroup);

        expect(cassBlog).toMatchObject({});
      });

      it('should return ICassBlog', () => {
        const formGroup = service.createCassBlogFormGroup(sampleWithRequiredData);

        const cassBlog = service.getCassBlog(formGroup);

        expect(cassBlog).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassBlog should keep the key control disabled', () => {
        const formGroup = service.createCassBlogFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.category.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.category.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassBlogFormGroup();

        service.resetForm(formGroup, { compositeId: { category: null, blogId: null } });

        expect(formGroup.controls.compositeId.controls.category.disabled).toBe(true);
      });
    });
  });
});
