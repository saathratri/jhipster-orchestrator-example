import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../psql-blog.test-samples';

import { PsqlBlogFormService } from './psql-blog-form.service';

describe('PsqlBlog Form Service', () => {
  let service: PsqlBlogFormService;

  beforeEach(() => {
    service = TestBed.inject(PsqlBlogFormService);
  });

  describe('Service methods', () => {
    describe('createPsqlBlogFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPsqlBlogFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            handle: expect.any(Object),
            tajUser: expect.any(Object),
          }),
        );
      });

      it('passing IPsqlBlog should create a new form with FormGroup', () => {
        const formGroup = service.createPsqlBlogFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            handle: expect.any(Object),
            tajUser: expect.any(Object),
          }),
        );
      });
    });

    describe('getPsqlBlog', () => {
      it('should return NewPsqlBlog for default PsqlBlog initial value', () => {
        const formGroup = service.createPsqlBlogFormGroup(sampleWithNewData);

        const psqlBlog = service.getPsqlBlog(formGroup);

        expect(psqlBlog).toMatchObject(sampleWithNewData);
      });

      it('should return NewPsqlBlog for empty PsqlBlog initial value', () => {
        const formGroup = service.createPsqlBlogFormGroup();

        const psqlBlog = service.getPsqlBlog(formGroup);

        expect(psqlBlog).toMatchObject({});
      });

      it('should return IPsqlBlog', () => {
        const formGroup = service.createPsqlBlogFormGroup(sampleWithRequiredData);

        const psqlBlog = service.getPsqlBlog(formGroup);

        expect(psqlBlog).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPsqlBlog should not enable id FormControl', () => {
        const formGroup = service.createPsqlBlogFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPsqlBlog should disable id FormControl', () => {
        const formGroup = service.createPsqlBlogFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
