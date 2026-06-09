import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPsqlReport, NewPsqlReport } from '../psql-report.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPsqlReport for edit and NewPsqlReportFormGroupInput for create.
 */
type PsqlReportFormGroupInput = IPsqlReport | PartialWithRequiredKeyOf<NewPsqlReport>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPsqlReport | NewPsqlReport> = Omit<T, 'createDate'> & {
  createDate?: string | null;
};

type PsqlReportFormRawValue = FormValueOf<IPsqlReport>;

type NewPsqlReportFormRawValue = FormValueOf<NewPsqlReport>;

type PsqlReportFormDefaults = Pick<NewPsqlReport, 'id' | 'createDate' | 'approved'>;

type PsqlReportFormGroupContent = {
  id: FormControl<PsqlReportFormRawValue['id'] | NewPsqlReport['id']>;
  fileName: FormControl<PsqlReportFormRawValue['fileName']>;
  fileExtension: FormControl<PsqlReportFormRawValue['fileExtension']>;
  createDate: FormControl<PsqlReportFormRawValue['createDate']>;
  file: FormControl<PsqlReportFormRawValue['file']>;
  fileContentType: FormControl<PsqlReportFormRawValue['fileContentType']>;
  approved: FormControl<PsqlReportFormRawValue['approved']>;
  product: FormControl<PsqlReportFormRawValue['product']>;
};

export type PsqlReportFormGroup = FormGroup<PsqlReportFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PsqlReportFormService {
  createPsqlReportFormGroup(psqlReport?: PsqlReportFormGroupInput): PsqlReportFormGroup {
    const psqlReportRawValue = this.convertPsqlReportToPsqlReportRawValue({
      ...this.getFormDefaults(),
      ...(psqlReport ?? { id: null }),
    });
    return new FormGroup<PsqlReportFormGroupContent>({
      id: new FormControl(
        { value: psqlReportRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fileName: new FormControl(psqlReportRawValue.fileName, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      fileExtension: new FormControl(psqlReportRawValue.fileExtension, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      createDate: new FormControl(psqlReportRawValue.createDate, {
        validators: [Validators.required],
      }),
      file: new FormControl(psqlReportRawValue.file, {
        validators: [Validators.required],
      }),
      fileContentType: new FormControl(psqlReportRawValue.fileContentType),
      approved: new FormControl(psqlReportRawValue.approved),
      product: new FormControl(psqlReportRawValue.product),
    });
  }

  getPsqlReport(form: PsqlReportFormGroup): IPsqlReport | NewPsqlReport {
    return this.convertPsqlReportRawValueToPsqlReport(form.getRawValue() as PsqlReportFormRawValue | NewPsqlReportFormRawValue);
  }

  resetForm(form: PsqlReportFormGroup, psqlReport: PsqlReportFormGroupInput): void {
    const psqlReportRawValue = this.convertPsqlReportToPsqlReportRawValue({ ...this.getFormDefaults(), ...psqlReport });
    form.reset({
      ...psqlReportRawValue,
      id: { value: psqlReportRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): PsqlReportFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createDate: currentTime,
      approved: false,
    };
  }

  private convertPsqlReportRawValueToPsqlReport(
    rawPsqlReport: PsqlReportFormRawValue | NewPsqlReportFormRawValue,
  ): IPsqlReport | NewPsqlReport {
    return {
      ...rawPsqlReport,
      createDate: dayjs(rawPsqlReport.createDate, DATE_TIME_FORMAT),
    };
  }

  private convertPsqlReportToPsqlReportRawValue(
    psqlReport: IPsqlReport | (Partial<NewPsqlReport> & PsqlReportFormDefaults),
  ): PsqlReportFormRawValue | PartialWithRequiredKeyOf<NewPsqlReportFormRawValue> {
    return {
      ...psqlReport,
      createDate: psqlReport.createDate ? psqlReport.createDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
