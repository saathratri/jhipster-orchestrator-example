import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICassAddOnsSelectedByOrganization, NewCassAddOnsSelectedByOrganization } from '../cass-add-ons-selected-by-organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<
  T extends { compositeId: { organizationId: unknown; arrivalDate: unknown; accountNumber: unknown; createdTimeId: unknown } },
> = Partial<Omit<T, 'compositeId'>> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassAddOnsSelectedByOrganization for edit and NewCassAddOnsSelectedByOrganizationFormGroupInput for create.
 */
type CassAddOnsSelectedByOrganizationFormGroupInput =
  | ICassAddOnsSelectedByOrganization
  | PartialWithRequiredKeyOf<NewCassAddOnsSelectedByOrganization>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICassAddOnsSelectedByOrganization | NewCassAddOnsSelectedByOrganization> = Omit<T, 'addOnDetailsBigInt'> & {
  addOnDetailsBigInt: Record<string, string> | null | undefined;
};

type CassAddOnsSelectedByOrganizationFormRawValue = FormValueOf<ICassAddOnsSelectedByOrganization>;

type NewCassAddOnsSelectedByOrganizationFormRawValue = FormValueOf<NewCassAddOnsSelectedByOrganization>;

type CassAddOnsSelectedByOrganizationFormDefaults = Pick<
  NewCassAddOnsSelectedByOrganization,
  'addOnDetailsBoolean' | 'addOnDetailsBigInt' | 'compositeId'
>;

type CassAddOnsSelectedByOrganizationFormGroupContent = {
  compositeId: FormGroup<{
    organizationId: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['compositeId']['organizationId']>;
    arrivalDate: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['compositeId']['arrivalDate']>;
    accountNumber: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['compositeId']['accountNumber']>;
    createdTimeId: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['compositeId']['createdTimeId']>;
  }>;
  departureDate: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['departureDate']>;
  customerId: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['customerId']>;
  customerFirstName: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['customerFirstName']>;
  customerLastName: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['customerLastName']>;
  customerUpdatedEmail: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['customerUpdatedEmail']>;
  customerUpdatedPhoneNumber: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['customerUpdatedPhoneNumber']>;
  customerEstimatedArrivalTime: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['customerEstimatedArrivalTime']>;
  tinyUrlShortCode: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['tinyUrlShortCode']>;
  addOnDetailsText: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['addOnDetailsText']>;
  addOnDetailsDecimal: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['addOnDetailsDecimal']>;
  addOnDetailsBoolean: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['addOnDetailsBoolean']>;
  addOnDetailsBigInt: FormControl<CassAddOnsSelectedByOrganizationFormRawValue['addOnDetailsBigInt']>;
};

export type CassAddOnsSelectedByOrganizationFormGroup = FormGroup<CassAddOnsSelectedByOrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassAddOnsSelectedByOrganizationFormService {
  createCassAddOnsSelectedByOrganizationFormGroup(
    cassAddOnsSelectedByOrganization: CassAddOnsSelectedByOrganizationFormGroupInput = {
      compositeId: { organizationId: null, arrivalDate: null, accountNumber: null, createdTimeId: null },
    },
  ): CassAddOnsSelectedByOrganizationFormGroup {
    const cassAddOnsSelectedByOrganizationRawValue = this.convertCassAddOnsSelectedByOrganizationToCassAddOnsSelectedByOrganizationRawValue(
      {
        ...this.getFormDefaults(),
        ...cassAddOnsSelectedByOrganization,
      },
    );
    return new FormGroup<CassAddOnsSelectedByOrganizationFormGroupContent>({
      compositeId: new FormGroup({
        organizationId: new FormControl(
          {
            value: cassAddOnsSelectedByOrganizationRawValue.compositeId.organizationId,
            disabled: cassAddOnsSelectedByOrganizationRawValue.compositeId.organizationId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        arrivalDate: new FormControl(
          {
            value: cassAddOnsSelectedByOrganizationRawValue.compositeId.arrivalDate,
            disabled: cassAddOnsSelectedByOrganizationRawValue.compositeId.arrivalDate !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        accountNumber: new FormControl(
          {
            value: cassAddOnsSelectedByOrganizationRawValue.compositeId.accountNumber,
            disabled: cassAddOnsSelectedByOrganizationRawValue.compositeId.accountNumber !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        createdTimeId: new FormControl(
          {
            value: cassAddOnsSelectedByOrganizationRawValue.compositeId.createdTimeId,
            disabled: cassAddOnsSelectedByOrganizationRawValue.compositeId.createdTimeId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      departureDate: new FormControl(cassAddOnsSelectedByOrganizationRawValue.departureDate),
      customerId: new FormControl(cassAddOnsSelectedByOrganizationRawValue.customerId),
      customerFirstName: new FormControl(cassAddOnsSelectedByOrganizationRawValue.customerFirstName),
      customerLastName: new FormControl(cassAddOnsSelectedByOrganizationRawValue.customerLastName),
      customerUpdatedEmail: new FormControl(cassAddOnsSelectedByOrganizationRawValue.customerUpdatedEmail),
      customerUpdatedPhoneNumber: new FormControl(cassAddOnsSelectedByOrganizationRawValue.customerUpdatedPhoneNumber),
      customerEstimatedArrivalTime: new FormControl(cassAddOnsSelectedByOrganizationRawValue.customerEstimatedArrivalTime),
      tinyUrlShortCode: new FormControl(cassAddOnsSelectedByOrganizationRawValue.tinyUrlShortCode),
      addOnDetailsText: new FormControl(cassAddOnsSelectedByOrganizationRawValue.addOnDetailsText),
      addOnDetailsDecimal: new FormControl(cassAddOnsSelectedByOrganizationRawValue.addOnDetailsDecimal),
      addOnDetailsBoolean: new FormControl(cassAddOnsSelectedByOrganizationRawValue.addOnDetailsBoolean),
      addOnDetailsBigInt: new FormControl(cassAddOnsSelectedByOrganizationRawValue.addOnDetailsBigInt),
    });
  }

  getCassAddOnsSelectedByOrganization(
    form: CassAddOnsSelectedByOrganizationFormGroup,
  ): ICassAddOnsSelectedByOrganization | NewCassAddOnsSelectedByOrganization {
    return this.convertCassAddOnsSelectedByOrganizationRawValueToCassAddOnsSelectedByOrganization(
      form.getRawValue() as CassAddOnsSelectedByOrganizationFormRawValue | NewCassAddOnsSelectedByOrganizationFormRawValue,
    );
  }

  resetForm(
    form: CassAddOnsSelectedByOrganizationFormGroup,
    cassAddOnsSelectedByOrganization: CassAddOnsSelectedByOrganizationFormGroupInput,
  ): void {
    const cassAddOnsSelectedByOrganizationRawValue = this.convertCassAddOnsSelectedByOrganizationToCassAddOnsSelectedByOrganizationRawValue(
      { ...this.getFormDefaults(), ...cassAddOnsSelectedByOrganization },
    );
    form.reset(
      {
        ...cassAddOnsSelectedByOrganizationRawValue,
        compositeId: {
          organizationId: { value: cassAddOnsSelectedByOrganizationRawValue.compositeId.organizationId, disabled: true },
          arrivalDate: { value: cassAddOnsSelectedByOrganizationRawValue.compositeId.arrivalDate, disabled: true },
          accountNumber: { value: cassAddOnsSelectedByOrganizationRawValue.compositeId.accountNumber, disabled: true },
          createdTimeId: { value: cassAddOnsSelectedByOrganizationRawValue.compositeId.createdTimeId, disabled: true },
        },
        departureDate: cassAddOnsSelectedByOrganizationRawValue.departureDate,
        addOnDetailsBigInt: cassAddOnsSelectedByOrganizationRawValue.addOnDetailsBigInt,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassAddOnsSelectedByOrganizationFormDefaults {
    return {
      compositeId: {
        organizationId: null,
        arrivalDate: null,
        accountNumber: null,
        createdTimeId: null,
      },
    };
  }

  private convertCassAddOnsSelectedByOrganizationRawValueToCassAddOnsSelectedByOrganization(
    rawCassAddOnsSelectedByOrganization: CassAddOnsSelectedByOrganizationFormRawValue | NewCassAddOnsSelectedByOrganizationFormRawValue,
  ): ICassAddOnsSelectedByOrganization | NewCassAddOnsSelectedByOrganization {
    return {
      ...rawCassAddOnsSelectedByOrganization,
      compositeId: {
        ...rawCassAddOnsSelectedByOrganization.compositeId,
      },
      addOnDetailsBigInt: rawCassAddOnsSelectedByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(
            Object.entries(rawCassAddOnsSelectedByOrganization.addOnDetailsBigInt).map(([key, value]) => [
              key,
              typeof value === 'number' ? dayjs(value) : dayjs(value, DATE_TIME_FORMAT),
            ]),
          )
        : {},
    };
  }

  private convertCassAddOnsSelectedByOrganizationToCassAddOnsSelectedByOrganizationRawValue(
    cassAddOnsSelectedByOrganization:
      | ICassAddOnsSelectedByOrganization
      | (Partial<NewCassAddOnsSelectedByOrganization> & CassAddOnsSelectedByOrganizationFormDefaults),
  ): CassAddOnsSelectedByOrganizationFormRawValue | PartialWithRequiredKeyOf<NewCassAddOnsSelectedByOrganizationFormRawValue> {
    return {
      ...cassAddOnsSelectedByOrganization,
      compositeId: {
        ...cassAddOnsSelectedByOrganization.compositeId,
      },
      addOnDetailsBigInt: cassAddOnsSelectedByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(
            Object.entries(cassAddOnsSelectedByOrganization.addOnDetailsBigInt).map(([key, value]) => [
              key,
              value.format(DATE_TIME_FORMAT),
            ]),
          )
        : {},
    };
  }
}
