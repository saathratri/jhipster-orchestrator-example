import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-post.test-samples';

import { CassPostFormService } from './cass-post-form.service';

describe('CassPost Form Service', () => {
  let service: CassPostFormService;

  beforeEach(() => {
    service = TestBed.inject(CassPostFormService);
  });

  describe('Service methods', () => {
    describe('createCassPostFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassPostFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            publishedDateTime: expect.any(Object),
            sentDate: expect.any(Object),
          }),
        );
      });

      it('passing ICassPost should create a new form with FormGroup', () => {
        const formGroup = service.createCassPostFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            publishedDateTime: expect.any(Object),
            sentDate: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassPost', () => {
      it('should return NewCassPost for default CassPost initial value', () => {
        const formGroup = service.createCassPostFormGroup(sampleWithNewData);

        const cassPost = service.getCassPost(formGroup);

        expect(cassPost).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassPost for empty CassPost initial value', () => {
        const formGroup = service.createCassPostFormGroup();

        const cassPost = service.getCassPost(formGroup);

        expect(cassPost).toMatchObject({});
      });

      it('should return ICassPost', () => {
        const formGroup = service.createCassPostFormGroup(sampleWithRequiredData);

        const cassPost = service.getCassPost(formGroup);

        expect(cassPost).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassPost should keep the key control disabled', () => {
        const formGroup = service.createCassPostFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.createdDate.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.createdDate.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassPostFormGroup();

        service.resetForm(formGroup, { compositeId: { createdDate: null, addedDateTime: null, postId: null } });

        expect(formGroup.controls.compositeId.controls.createdDate.disabled).toBe(true);
      });
    });
  });
});
