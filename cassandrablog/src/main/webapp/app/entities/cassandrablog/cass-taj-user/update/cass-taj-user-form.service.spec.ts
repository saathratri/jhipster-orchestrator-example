import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-taj-user.test-samples';

import { CassTajUserFormService } from './cass-taj-user-form.service';

describe('CassTajUser Form Service', () => {
  let service: CassTajUserFormService;

  beforeEach(() => {
    service = TestBed.inject(CassTajUserFormService);
  });

  describe('Service methods', () => {
    describe('createCassTajUserFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassTajUserFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            login: expect.any(Object),
          }),
        );
      });

      it('passing ICassTajUser should create a new form with FormGroup', () => {
        const formGroup = service.createCassTajUserFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            login: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassTajUser', () => {
      it('should return NewCassTajUser for default CassTajUser initial value', () => {
        const formGroup = service.createCassTajUserFormGroup(sampleWithNewData);

        const cassTajUser = service.getCassTajUser(formGroup);

        expect(cassTajUser).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassTajUser for empty CassTajUser initial value', () => {
        const formGroup = service.createCassTajUserFormGroup();

        const cassTajUser = service.getCassTajUser(formGroup);

        expect(cassTajUser).toMatchObject({});
      });

      it('should return ICassTajUser', () => {
        const formGroup = service.createCassTajUserFormGroup(sampleWithRequiredData);

        const cassTajUser = service.getCassTajUser(formGroup);

        expect(cassTajUser).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassTajUser should keep the key control disabled', () => {
        const formGroup = service.createCassTajUserFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassTajUserFormGroup();

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
