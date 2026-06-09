import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICassSaathratriEntity2, NewCassSaathratriEntity2 } from '../cass-saathratri-entity-2.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<
  T extends { compositeId: { entityTypeId: unknown; yearOfDateAdded: unknown; arrivalDate: unknown; blogId: unknown } },
> = Partial<Omit<T, 'compositeId'>> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassSaathratriEntity2 for edit and NewCassSaathratriEntity2FormGroupInput for create.
 */
type CassSaathratriEntity2FormGroupInput = ICassSaathratriEntity2 | PartialWithRequiredKeyOf<NewCassSaathratriEntity2>;

type CassSaathratriEntity2FormDefaults = Pick<NewCassSaathratriEntity2, 'compositeId'>;

type CassSaathratriEntity2FormGroupContent = {
  compositeId: FormGroup<{
    entityTypeId: FormControl<ICassSaathratriEntity2['compositeId']['entityTypeId']>;
    yearOfDateAdded: FormControl<ICassSaathratriEntity2['compositeId']['yearOfDateAdded']>;
    arrivalDate: FormControl<ICassSaathratriEntity2['compositeId']['arrivalDate']>;
    blogId: FormControl<ICassSaathratriEntity2['compositeId']['blogId']>;
  }>;
  entityName: FormControl<ICassSaathratriEntity2['entityName']>;
  entityDescription: FormControl<ICassSaathratriEntity2['entityDescription']>;
  entityCost: FormControl<ICassSaathratriEntity2['entityCost']>;
  departureDate: FormControl<ICassSaathratriEntity2['departureDate']>;
};

export type CassSaathratriEntity2FormGroup = FormGroup<CassSaathratriEntity2FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassSaathratriEntity2FormService {
  createCassSaathratriEntity2FormGroup(
    cassSaathratriEntity2: CassSaathratriEntity2FormGroupInput = {
      compositeId: { entityTypeId: null, yearOfDateAdded: null, arrivalDate: null, blogId: null },
    },
  ): CassSaathratriEntity2FormGroup {
    const cassSaathratriEntity2RawValue = {
      ...this.getFormDefaults(),
      ...cassSaathratriEntity2,
    };
    return new FormGroup<CassSaathratriEntity2FormGroupContent>({
      compositeId: new FormGroup({
        entityTypeId: new FormControl(
          {
            value: cassSaathratriEntity2RawValue.compositeId.entityTypeId,
            disabled: cassSaathratriEntity2RawValue.compositeId.entityTypeId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        yearOfDateAdded: new FormControl(
          {
            value: cassSaathratriEntity2RawValue.compositeId.yearOfDateAdded,
            disabled: cassSaathratriEntity2RawValue.compositeId.yearOfDateAdded !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        arrivalDate: new FormControl(
          {
            value: cassSaathratriEntity2RawValue.compositeId.arrivalDate,
            disabled: cassSaathratriEntity2RawValue.compositeId.arrivalDate !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        blogId: new FormControl(
          { value: cassSaathratriEntity2RawValue.compositeId.blogId, disabled: cassSaathratriEntity2RawValue.compositeId.blogId !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      entityName: new FormControl(cassSaathratriEntity2RawValue.entityName),
      entityDescription: new FormControl(cassSaathratriEntity2RawValue.entityDescription),
      entityCost: new FormControl(cassSaathratriEntity2RawValue.entityCost),
      departureDate: new FormControl(cassSaathratriEntity2RawValue.departureDate),
    });
  }

  getCassSaathratriEntity2(form: CassSaathratriEntity2FormGroup): ICassSaathratriEntity2 | NewCassSaathratriEntity2 {
    return form.getRawValue() as ICassSaathratriEntity2 | NewCassSaathratriEntity2;
  }

  resetForm(form: CassSaathratriEntity2FormGroup, cassSaathratriEntity2: CassSaathratriEntity2FormGroupInput): void {
    const cassSaathratriEntity2RawValue = { ...this.getFormDefaults(), ...cassSaathratriEntity2 };
    form.reset(
      {
        ...cassSaathratriEntity2RawValue,
        compositeId: {
          entityTypeId: { value: cassSaathratriEntity2RawValue.compositeId.entityTypeId, disabled: true },
          yearOfDateAdded: { value: cassSaathratriEntity2RawValue.compositeId.yearOfDateAdded, disabled: true },
          arrivalDate: { value: cassSaathratriEntity2RawValue.compositeId.arrivalDate, disabled: true },
          blogId: { value: cassSaathratriEntity2RawValue.compositeId.blogId, disabled: true },
        },
        departureDate: cassSaathratriEntity2RawValue.departureDate,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassSaathratriEntity2FormDefaults {
    return {
      compositeId: {
        entityTypeId: null,
        yearOfDateAdded: null,
        arrivalDate: null,
        blogId: null,
      },
    };
  }
}
