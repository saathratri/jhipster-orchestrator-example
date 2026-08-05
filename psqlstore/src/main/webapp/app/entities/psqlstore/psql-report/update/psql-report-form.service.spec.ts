import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../psql-report.test-samples';

import { PsqlReportFormService } from './psql-report-form.service';

describe('PsqlReport Form Service', () => {
  let service: PsqlReportFormService;

  beforeEach(() => {
    service = TestBed.inject(PsqlReportFormService);
  });

  describe('Service methods', () => {
    describe('createPsqlReportFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPsqlReportFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileName: expect.any(Object),
            fileExtension: expect.any(Object),
            createDate: expect.any(Object),
            file: expect.any(Object),
            approved: expect.any(Object),
            product: expect.any(Object),
          }),
        );
      });

      it('passing IPsqlReport should create a new form with FormGroup', () => {
        const formGroup = service.createPsqlReportFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileName: expect.any(Object),
            fileExtension: expect.any(Object),
            createDate: expect.any(Object),
            file: expect.any(Object),
            approved: expect.any(Object),
            product: expect.any(Object),
          }),
        );
      });
    });

    describe('getPsqlReport', () => {
      it('should return NewPsqlReport for default PsqlReport initial value', () => {
        const formGroup = service.createPsqlReportFormGroup(sampleWithNewData);

        const psqlReport = service.getPsqlReport(formGroup);

        expect(psqlReport).toMatchObject(sampleWithNewData);
      });

      it('should return NewPsqlReport for empty PsqlReport initial value', () => {
        const formGroup = service.createPsqlReportFormGroup();

        const psqlReport = service.getPsqlReport(formGroup);

        expect(psqlReport).toMatchObject({});
      });

      it('should return IPsqlReport', () => {
        const formGroup = service.createPsqlReportFormGroup(sampleWithRequiredData);

        const psqlReport = service.getPsqlReport(formGroup);

        expect(psqlReport).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPsqlReport should not enable id FormControl', () => {
        const formGroup = service.createPsqlReportFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPsqlReport should disable id FormControl', () => {
        const formGroup = service.createPsqlReportFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
