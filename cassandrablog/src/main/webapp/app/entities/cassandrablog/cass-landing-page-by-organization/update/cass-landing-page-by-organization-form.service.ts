import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICassLandingPageByOrganization, NewCassLandingPageByOrganization } from '../cass-landing-page-by-organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { organizationId: unknown }> = Partial<Omit<T, 'organizationId'>> & {
  organizationId: T['organizationId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassLandingPageByOrganization for edit and NewCassLandingPageByOrganizationFormGroupInput for create.
 */
type CassLandingPageByOrganizationFormGroupInput =
  | ICassLandingPageByOrganization
  | PartialWithRequiredKeyOf<NewCassLandingPageByOrganization>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICassLandingPageByOrganization | NewCassLandingPageByOrganization> = Omit<T, 'detailsBigInt'> & {
  detailsBigInt: Record<string, string> | null | undefined;
};

type CassLandingPageByOrganizationFormRawValue = FormValueOf<ICassLandingPageByOrganization>;

type NewCassLandingPageByOrganizationFormRawValue = FormValueOf<NewCassLandingPageByOrganization>;

type CassLandingPageByOrganizationFormDefaults = Pick<
  NewCassLandingPageByOrganization,
  'organizationId' | 'detailsBoolean' | 'detailsBigInt'
>;

type CassLandingPageByOrganizationFormGroupContent = {
  organizationId: FormControl<CassLandingPageByOrganizationFormRawValue['organizationId']>;
  detailsText: FormControl<CassLandingPageByOrganizationFormRawValue['detailsText']>;
  detailsDecimal: FormControl<CassLandingPageByOrganizationFormRawValue['detailsDecimal']>;
  detailsBoolean: FormControl<CassLandingPageByOrganizationFormRawValue['detailsBoolean']>;
  detailsBigInt: FormControl<CassLandingPageByOrganizationFormRawValue['detailsBigInt']>;
};

export type CassLandingPageByOrganizationFormGroup = FormGroup<CassLandingPageByOrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassLandingPageByOrganizationFormService {
  createCassLandingPageByOrganizationFormGroup(
    cassLandingPageByOrganization: CassLandingPageByOrganizationFormGroupInput = { organizationId: '' },
  ): CassLandingPageByOrganizationFormGroup {
    const cassLandingPageByOrganizationRawValue = this.convertCassLandingPageByOrganizationToCassLandingPageByOrganizationRawValue({
      ...this.getFormDefaults(),
      ...cassLandingPageByOrganization,
    });
    return new FormGroup<CassLandingPageByOrganizationFormGroupContent>({
      organizationId: new FormControl(
        {
          value: cassLandingPageByOrganizationRawValue.organizationId,
          disabled: cassLandingPageByOrganizationRawValue.organizationId !== '',
        },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      detailsText: new FormControl(cassLandingPageByOrganizationRawValue.detailsText),
      detailsDecimal: new FormControl(cassLandingPageByOrganizationRawValue.detailsDecimal),
      detailsBoolean: new FormControl(cassLandingPageByOrganizationRawValue.detailsBoolean),
      detailsBigInt: new FormControl(cassLandingPageByOrganizationRawValue.detailsBigInt),
    });
  }

  getCassLandingPageByOrganization(
    form: CassLandingPageByOrganizationFormGroup,
  ): ICassLandingPageByOrganization | NewCassLandingPageByOrganization {
    return this.convertCassLandingPageByOrganizationRawValueToCassLandingPageByOrganization(
      form.getRawValue() as CassLandingPageByOrganizationFormRawValue | NewCassLandingPageByOrganizationFormRawValue,
    );
  }

  resetForm(
    form: CassLandingPageByOrganizationFormGroup,
    cassLandingPageByOrganization: CassLandingPageByOrganizationFormGroupInput,
  ): void {
    const cassLandingPageByOrganizationRawValue = this.convertCassLandingPageByOrganizationToCassLandingPageByOrganizationRawValue({
      ...this.getFormDefaults(),
      ...cassLandingPageByOrganization,
    });
    form.reset(
      {
        ...cassLandingPageByOrganizationRawValue,
        organizationId: { value: cassLandingPageByOrganizationRawValue.organizationId, disabled: true },
        detailsBigInt: cassLandingPageByOrganizationRawValue.detailsBigInt,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassLandingPageByOrganizationFormDefaults {
    return {
      organizationId: '',
    };
  }

  private convertCassLandingPageByOrganizationRawValueToCassLandingPageByOrganization(
    rawCassLandingPageByOrganization: CassLandingPageByOrganizationFormRawValue | NewCassLandingPageByOrganizationFormRawValue,
  ): ICassLandingPageByOrganization | NewCassLandingPageByOrganization {
    return {
      ...rawCassLandingPageByOrganization,
      detailsBigInt: rawCassLandingPageByOrganization.detailsBigInt
        ? Object.fromEntries(
            Object.entries(rawCassLandingPageByOrganization.detailsBigInt).map(([key, value]) => [
              key,
              typeof value === 'number' ? dayjs(value) : dayjs(value, DATE_TIME_FORMAT),
            ]),
          )
        : {},
    };
  }

  private convertCassLandingPageByOrganizationToCassLandingPageByOrganizationRawValue(
    cassLandingPageByOrganization:
      | ICassLandingPageByOrganization
      | (Partial<NewCassLandingPageByOrganization> & CassLandingPageByOrganizationFormDefaults),
  ): CassLandingPageByOrganizationFormRawValue | PartialWithRequiredKeyOf<NewCassLandingPageByOrganizationFormRawValue> {
    return {
      ...cassLandingPageByOrganization,
      detailsBigInt: cassLandingPageByOrganization.detailsBigInt
        ? Object.fromEntries(
            Object.entries(cassLandingPageByOrganization.detailsBigInt).map(([key, value]) => [key, value.format(DATE_TIME_FORMAT)]),
          )
        : {},
    };
  }
}
