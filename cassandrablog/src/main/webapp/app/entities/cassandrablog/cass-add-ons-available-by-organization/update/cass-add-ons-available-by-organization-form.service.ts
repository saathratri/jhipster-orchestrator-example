import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICassAddOnsAvailableByOrganization, NewCassAddOnsAvailableByOrganization } from '../cass-add-ons-available-by-organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<
  T extends { compositeId: { organizationId: unknown; entityType: unknown; entityId: unknown; addOnId: unknown } },
> = Partial<Omit<T, 'compositeId'>> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassAddOnsAvailableByOrganization for edit and NewCassAddOnsAvailableByOrganizationFormGroupInput for create.
 */
type CassAddOnsAvailableByOrganizationFormGroupInput =
  | ICassAddOnsAvailableByOrganization
  | PartialWithRequiredKeyOf<NewCassAddOnsAvailableByOrganization>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICassAddOnsAvailableByOrganization | NewCassAddOnsAvailableByOrganization> = Omit<T, 'addOnDetailsBigInt'> & {
  addOnDetailsBigInt: Record<string, string> | null | undefined;
};

type CassAddOnsAvailableByOrganizationFormRawValue = FormValueOf<ICassAddOnsAvailableByOrganization>;

type NewCassAddOnsAvailableByOrganizationFormRawValue = FormValueOf<NewCassAddOnsAvailableByOrganization>;

type CassAddOnsAvailableByOrganizationFormDefaults = Pick<
  NewCassAddOnsAvailableByOrganization,
  'addOnDetailsBoolean' | 'addOnDetailsBigInt' | 'compositeId'
>;

type CassAddOnsAvailableByOrganizationFormGroupContent = {
  compositeId: FormGroup<{
    organizationId: FormControl<CassAddOnsAvailableByOrganizationFormRawValue['compositeId']['organizationId']>;
    entityType: FormControl<CassAddOnsAvailableByOrganizationFormRawValue['compositeId']['entityType']>;
    entityId: FormControl<CassAddOnsAvailableByOrganizationFormRawValue['compositeId']['entityId']>;
    addOnId: FormControl<CassAddOnsAvailableByOrganizationFormRawValue['compositeId']['addOnId']>;
  }>;
  addOnType: FormControl<CassAddOnsAvailableByOrganizationFormRawValue['addOnType']>;
  addOnDetailsText: FormControl<CassAddOnsAvailableByOrganizationFormRawValue['addOnDetailsText']>;
  addOnDetailsDecimal: FormControl<CassAddOnsAvailableByOrganizationFormRawValue['addOnDetailsDecimal']>;
  addOnDetailsBoolean: FormControl<CassAddOnsAvailableByOrganizationFormRawValue['addOnDetailsBoolean']>;
  addOnDetailsBigInt: FormControl<CassAddOnsAvailableByOrganizationFormRawValue['addOnDetailsBigInt']>;
};

export type CassAddOnsAvailableByOrganizationFormGroup = FormGroup<CassAddOnsAvailableByOrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassAddOnsAvailableByOrganizationFormService {
  createCassAddOnsAvailableByOrganizationFormGroup(
    cassAddOnsAvailableByOrganization: CassAddOnsAvailableByOrganizationFormGroupInput = {
      compositeId: { organizationId: null, entityType: null, entityId: null, addOnId: null },
    },
  ): CassAddOnsAvailableByOrganizationFormGroup {
    const cassAddOnsAvailableByOrganizationRawValue =
      this.convertCassAddOnsAvailableByOrganizationToCassAddOnsAvailableByOrganizationRawValue({
        ...this.getFormDefaults(),
        ...cassAddOnsAvailableByOrganization,
      });
    return new FormGroup<CassAddOnsAvailableByOrganizationFormGroupContent>({
      compositeId: new FormGroup({
        organizationId: new FormControl(
          {
            value: cassAddOnsAvailableByOrganizationRawValue.compositeId.organizationId,
            disabled: cassAddOnsAvailableByOrganizationRawValue.compositeId.organizationId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        entityType: new FormControl(
          {
            value: cassAddOnsAvailableByOrganizationRawValue.compositeId.entityType,
            disabled: cassAddOnsAvailableByOrganizationRawValue.compositeId.entityType !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        entityId: new FormControl(
          {
            value: cassAddOnsAvailableByOrganizationRawValue.compositeId.entityId,
            disabled: cassAddOnsAvailableByOrganizationRawValue.compositeId.entityId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        addOnId: new FormControl(
          {
            value: cassAddOnsAvailableByOrganizationRawValue.compositeId.addOnId,
            disabled: cassAddOnsAvailableByOrganizationRawValue.compositeId.addOnId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      addOnType: new FormControl(cassAddOnsAvailableByOrganizationRawValue.addOnType),
      addOnDetailsText: new FormControl(cassAddOnsAvailableByOrganizationRawValue.addOnDetailsText),
      addOnDetailsDecimal: new FormControl(cassAddOnsAvailableByOrganizationRawValue.addOnDetailsDecimal),
      addOnDetailsBoolean: new FormControl(cassAddOnsAvailableByOrganizationRawValue.addOnDetailsBoolean),
      addOnDetailsBigInt: new FormControl(cassAddOnsAvailableByOrganizationRawValue.addOnDetailsBigInt),
    });
  }

  getCassAddOnsAvailableByOrganization(
    form: CassAddOnsAvailableByOrganizationFormGroup,
  ): ICassAddOnsAvailableByOrganization | NewCassAddOnsAvailableByOrganization {
    return this.convertCassAddOnsAvailableByOrganizationRawValueToCassAddOnsAvailableByOrganization(
      form.getRawValue() as CassAddOnsAvailableByOrganizationFormRawValue | NewCassAddOnsAvailableByOrganizationFormRawValue,
    );
  }

  resetForm(
    form: CassAddOnsAvailableByOrganizationFormGroup,
    cassAddOnsAvailableByOrganization: CassAddOnsAvailableByOrganizationFormGroupInput,
  ): void {
    const cassAddOnsAvailableByOrganizationRawValue =
      this.convertCassAddOnsAvailableByOrganizationToCassAddOnsAvailableByOrganizationRawValue({
        ...this.getFormDefaults(),
        ...cassAddOnsAvailableByOrganization,
      });
    form.reset(
      {
        ...cassAddOnsAvailableByOrganizationRawValue,
        compositeId: {
          organizationId: { value: cassAddOnsAvailableByOrganizationRawValue.compositeId.organizationId, disabled: true },
          entityType: { value: cassAddOnsAvailableByOrganizationRawValue.compositeId.entityType, disabled: true },
          entityId: { value: cassAddOnsAvailableByOrganizationRawValue.compositeId.entityId, disabled: true },
          addOnId: { value: cassAddOnsAvailableByOrganizationRawValue.compositeId.addOnId, disabled: true },
        },
        addOnDetailsBigInt: cassAddOnsAvailableByOrganizationRawValue.addOnDetailsBigInt,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassAddOnsAvailableByOrganizationFormDefaults {
    return {
      compositeId: {
        organizationId: null,
        entityType: null,
        entityId: null,
        addOnId: null,
      },
    };
  }

  private convertCassAddOnsAvailableByOrganizationRawValueToCassAddOnsAvailableByOrganization(
    rawCassAddOnsAvailableByOrganization: CassAddOnsAvailableByOrganizationFormRawValue | NewCassAddOnsAvailableByOrganizationFormRawValue,
  ): ICassAddOnsAvailableByOrganization | NewCassAddOnsAvailableByOrganization {
    return {
      ...rawCassAddOnsAvailableByOrganization,
      compositeId: {
        ...rawCassAddOnsAvailableByOrganization.compositeId,
      },
      addOnDetailsBigInt: rawCassAddOnsAvailableByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(
            Object.entries(rawCassAddOnsAvailableByOrganization.addOnDetailsBigInt).map(([key, value]) => [
              key,
              typeof value === 'number' ? dayjs(value) : dayjs(value, DATE_TIME_FORMAT),
            ]),
          )
        : {},
    };
  }

  private convertCassAddOnsAvailableByOrganizationToCassAddOnsAvailableByOrganizationRawValue(
    cassAddOnsAvailableByOrganization:
      | ICassAddOnsAvailableByOrganization
      | (Partial<NewCassAddOnsAvailableByOrganization> & CassAddOnsAvailableByOrganizationFormDefaults),
  ): CassAddOnsAvailableByOrganizationFormRawValue | PartialWithRequiredKeyOf<NewCassAddOnsAvailableByOrganizationFormRawValue> {
    return {
      ...cassAddOnsAvailableByOrganization,
      compositeId: {
        ...cassAddOnsAvailableByOrganization.compositeId,
      },
      addOnDetailsBigInt: cassAddOnsAvailableByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(
            Object.entries(cassAddOnsAvailableByOrganization.addOnDetailsBigInt).map(([key, value]) => [
              key,
              value.format(DATE_TIME_FORMAT),
            ]),
          )
        : {},
    };
  }
}
