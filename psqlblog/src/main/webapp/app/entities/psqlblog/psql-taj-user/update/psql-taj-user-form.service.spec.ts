import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../psql-taj-user.test-samples';

import { PsqlTajUserFormService } from './psql-taj-user-form.service';

describe('PsqlTajUser Form Service', () => {
  let service: PsqlTajUserFormService;

  beforeEach(() => {
    service = TestBed.inject(PsqlTajUserFormService);
  });

  describe('Service methods', () => {
    describe('createPsqlTajUserFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPsqlTajUserFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            login: expect.any(Object),
          }),
        );
      });

      it('passing IPsqlTajUser should create a new form with FormGroup', () => {
        const formGroup = service.createPsqlTajUserFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            login: expect.any(Object),
          }),
        );
      });
    });

    describe('getPsqlTajUser', () => {
      it('should return NewPsqlTajUser for default PsqlTajUser initial value', () => {
        const formGroup = service.createPsqlTajUserFormGroup(sampleWithNewData);

        const psqlTajUser = service.getPsqlTajUser(formGroup);

        expect(psqlTajUser).toMatchObject(sampleWithNewData);
      });

      it('should return NewPsqlTajUser for empty PsqlTajUser initial value', () => {
        const formGroup = service.createPsqlTajUserFormGroup();

        const psqlTajUser = service.getPsqlTajUser(formGroup);

        expect(psqlTajUser).toMatchObject({});
      });

      it('should return IPsqlTajUser', () => {
        const formGroup = service.createPsqlTajUserFormGroup(sampleWithRequiredData);

        const psqlTajUser = service.getPsqlTajUser(formGroup);

        expect(psqlTajUser).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPsqlTajUser should not enable id FormControl', () => {
        const formGroup = service.createPsqlTajUserFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPsqlTajUser should disable id FormControl', () => {
        const formGroup = service.createPsqlTajUserFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
