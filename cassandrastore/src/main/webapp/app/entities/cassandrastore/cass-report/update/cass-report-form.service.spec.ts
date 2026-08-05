import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cass-report.test-samples';

import { CassReportFormService } from './cass-report-form.service';

describe('CassReport Form Service', () => {
  let service: CassReportFormService;

  beforeEach(() => {
    service = TestBed.inject(CassReportFormService);
  });

  describe('Service methods', () => {
    describe('createCassReportFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCassReportFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileName: expect.any(Object),
            fileExtension: expect.any(Object),
            createDate: expect.any(Object),
            file: expect.any(Object),
            approved: expect.any(Object),
          }),
        );
      });

      it('passing ICassReport should create a new form with FormGroup', () => {
        const formGroup = service.createCassReportFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fileName: expect.any(Object),
            fileExtension: expect.any(Object),
            createDate: expect.any(Object),
            file: expect.any(Object),
            approved: expect.any(Object),
          }),
        );
      });
    });

    describe('getCassReport', () => {
      it('should return NewCassReport for default CassReport initial value', () => {
        const formGroup = service.createCassReportFormGroup(sampleWithNewData);

        const cassReport = service.getCassReport(formGroup);

        expect(cassReport).toMatchObject(sampleWithNewData);
      });

      it('should return NewCassReport for empty CassReport initial value', () => {
        const formGroup = service.createCassReportFormGroup();

        const cassReport = service.getCassReport(formGroup);

        expect(cassReport).toMatchObject({});
      });

      it('should return ICassReport', () => {
        const formGroup = service.createCassReportFormGroup(sampleWithRequiredData);

        const cassReport = service.getCassReport(formGroup);

        expect(cassReport).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICassReport should keep the key control disabled', () => {
        const formGroup = service.createCassReportFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createCassReportFormGroup();

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
