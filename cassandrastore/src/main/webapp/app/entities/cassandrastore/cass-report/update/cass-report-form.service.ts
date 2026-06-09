import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICassReport, NewCassReport } from '../cass-report.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassReport for edit and NewCassReportFormGroupInput for create.
 */
type CassReportFormGroupInput = ICassReport | PartialWithRequiredKeyOf<NewCassReport>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICassReport | NewCassReport> = Omit<T, 'createDate'> & {
  createDate?: string | null;
};

type CassReportFormRawValue = FormValueOf<ICassReport>;

type NewCassReportFormRawValue = FormValueOf<NewCassReport>;

type CassReportFormDefaults = Pick<NewCassReport, 'id' | 'createDate' | 'approved'>;

type CassReportFormGroupContent = {
  id: FormControl<CassReportFormRawValue['id']>;
  fileName: FormControl<CassReportFormRawValue['fileName']>;
  fileExtension: FormControl<CassReportFormRawValue['fileExtension']>;
  createDate: FormControl<CassReportFormRawValue['createDate']>;
  file: FormControl<CassReportFormRawValue['file']>;
  fileContentType: FormControl<CassReportFormRawValue['fileContentType']>;
  approved: FormControl<CassReportFormRawValue['approved']>;
};

export type CassReportFormGroup = FormGroup<CassReportFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassReportFormService {
  createCassReportFormGroup(cassReport: CassReportFormGroupInput = { id: '' }): CassReportFormGroup {
    const cassReportRawValue = this.convertCassReportToCassReportRawValue({
      ...this.getFormDefaults(),
      ...cassReport,
    });
    return new FormGroup<CassReportFormGroupContent>({
      id: new FormControl(
        { value: cassReportRawValue.id, disabled: cassReportRawValue.id !== '' },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fileName: new FormControl(cassReportRawValue.fileName, {
        validators: [Validators.required],
      }),
      fileExtension: new FormControl(cassReportRawValue.fileExtension, {
        validators: [Validators.required],
      }),
      createDate: new FormControl(cassReportRawValue.createDate, {
        validators: [Validators.required],
      }),
      file: new FormControl(cassReportRawValue.file),
      fileContentType: new FormControl(cassReportRawValue.fileContentType),
      approved: new FormControl(cassReportRawValue.approved),
    });
  }

  getCassReport(form: CassReportFormGroup): ICassReport | NewCassReport {
    return this.convertCassReportRawValueToCassReport(form.getRawValue() as CassReportFormRawValue | NewCassReportFormRawValue);
  }

  resetForm(form: CassReportFormGroup, cassReport: CassReportFormGroupInput): void {
    const cassReportRawValue = this.convertCassReportToCassReportRawValue({ ...this.getFormDefaults(), ...cassReport });
    form.reset(
      {
        ...cassReportRawValue,
        id: { value: cassReportRawValue.id, disabled: true },
        createDate: cassReportRawValue.createDate,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassReportFormDefaults {
    return {
      id: '',
      approved: false,
    };
  }

  private convertCassReportRawValueToCassReport(
    rawCassReport: CassReportFormRawValue | NewCassReportFormRawValue,
  ): ICassReport | NewCassReport {
    return {
      ...rawCassReport,
      createDate:
        typeof rawCassReport.createDate === 'number' ? dayjs(rawCassReport.createDate) : dayjs(rawCassReport.createDate, DATE_TIME_FORMAT),
    };
  }

  private convertCassReportToCassReportRawValue(
    cassReport: ICassReport | (Partial<NewCassReport> & CassReportFormDefaults),
  ): CassReportFormRawValue | PartialWithRequiredKeyOf<NewCassReportFormRawValue> {
    return {
      ...cassReport,
      createDate: cassReport.createDate ? cassReport.createDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
