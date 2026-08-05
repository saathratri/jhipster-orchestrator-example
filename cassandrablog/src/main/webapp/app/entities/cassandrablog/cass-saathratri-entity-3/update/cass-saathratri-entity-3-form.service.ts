import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICassSaathratriEntity3, NewCassSaathratriEntity3 } from '../cass-saathratri-entity-3.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { compositeId: { entityType: unknown; createdTimeId: unknown } }> = Partial<
  Omit<T, 'compositeId'>
> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassSaathratriEntity3 for edit and NewCassSaathratriEntity3FormGroupInput for create.
 */
type CassSaathratriEntity3FormGroupInput = ICassSaathratriEntity3 | PartialWithRequiredKeyOf<NewCassSaathratriEntity3>;

type CassSaathratriEntity3FormDefaults = Pick<NewCassSaathratriEntity3, 'compositeId'>;

type CassSaathratriEntity3FormGroupContent = {
  compositeId: FormGroup<{
    entityType: FormControl<ICassSaathratriEntity3['compositeId']['entityType']>;
    createdTimeId: FormControl<ICassSaathratriEntity3['compositeId']['createdTimeId']>;
  }>;
  entityName: FormControl<ICassSaathratriEntity3['entityName']>;
  entityDescription: FormControl<ICassSaathratriEntity3['entityDescription']>;
  entityCost: FormControl<ICassSaathratriEntity3['entityCost']>;
  departureDate: FormControl<ICassSaathratriEntity3['departureDate']>;
  tags: FormControl<ICassSaathratriEntity3['tags']>;
};

export type CassSaathratriEntity3FormGroup = FormGroup<CassSaathratriEntity3FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassSaathratriEntity3FormService {
  createCassSaathratriEntity3FormGroup(
    cassSaathratriEntity3: CassSaathratriEntity3FormGroupInput = { compositeId: { entityType: null, createdTimeId: null } },
  ): CassSaathratriEntity3FormGroup {
    const cassSaathratriEntity3RawValue = {
      ...this.getFormDefaults(),
      ...cassSaathratriEntity3,
    };
    return new FormGroup<CassSaathratriEntity3FormGroupContent>({
      compositeId: new FormGroup({
        entityType: new FormControl(
          {
            value: cassSaathratriEntity3RawValue.compositeId.entityType,
            disabled: cassSaathratriEntity3RawValue.compositeId.entityType !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        createdTimeId: new FormControl(
          {
            value: cassSaathratriEntity3RawValue.compositeId.createdTimeId,
            disabled: cassSaathratriEntity3RawValue.compositeId.createdTimeId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      entityName: new FormControl(cassSaathratriEntity3RawValue.entityName),
      entityDescription: new FormControl(cassSaathratriEntity3RawValue.entityDescription),
      entityCost: new FormControl(cassSaathratriEntity3RawValue.entityCost),
      departureDate: new FormControl(cassSaathratriEntity3RawValue.departureDate),
      tags: new FormControl(cassSaathratriEntity3RawValue.tags),
    });
  }

  getCassSaathratriEntity3(form: CassSaathratriEntity3FormGroup): ICassSaathratriEntity3 | NewCassSaathratriEntity3 {
    return form.getRawValue() as ICassSaathratriEntity3 | NewCassSaathratriEntity3;
  }

  resetForm(form: CassSaathratriEntity3FormGroup, cassSaathratriEntity3: CassSaathratriEntity3FormGroupInput): void {
    const cassSaathratriEntity3RawValue = { ...this.getFormDefaults(), ...cassSaathratriEntity3 };
    form.reset(
      {
        ...cassSaathratriEntity3RawValue,
        compositeId: {
          entityType: { value: cassSaathratriEntity3RawValue.compositeId.entityType, disabled: true },
          createdTimeId: { value: cassSaathratriEntity3RawValue.compositeId.createdTimeId, disabled: true },
        },
        departureDate: cassSaathratriEntity3RawValue.departureDate,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassSaathratriEntity3FormDefaults {
    return {
      compositeId: {
        entityType: null,
        createdTimeId: null,
      },
    };
  }
}
