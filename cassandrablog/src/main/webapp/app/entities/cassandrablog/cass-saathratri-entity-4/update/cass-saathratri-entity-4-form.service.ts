import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICassSaathratriEntity4, NewCassSaathratriEntity4 } from '../cass-saathratri-entity-4.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { compositeId: { organizationId: unknown; attributeKey: unknown } }> = Partial<
  Omit<T, 'compositeId'>
> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassSaathratriEntity4 for edit and NewCassSaathratriEntity4FormGroupInput for create.
 */
type CassSaathratriEntity4FormGroupInput = ICassSaathratriEntity4 | PartialWithRequiredKeyOf<NewCassSaathratriEntity4>;

type CassSaathratriEntity4FormDefaults = Pick<NewCassSaathratriEntity4, 'compositeId'>;

type CassSaathratriEntity4FormGroupContent = {
  compositeId: FormGroup<{
    organizationId: FormControl<ICassSaathratriEntity4['compositeId']['organizationId']>;
    attributeKey: FormControl<ICassSaathratriEntity4['compositeId']['attributeKey']>;
  }>;
  attributeValue: FormControl<ICassSaathratriEntity4['attributeValue']>;
};

export type CassSaathratriEntity4FormGroup = FormGroup<CassSaathratriEntity4FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassSaathratriEntity4FormService {
  createCassSaathratriEntity4FormGroup(
    cassSaathratriEntity4: CassSaathratriEntity4FormGroupInput = { compositeId: { organizationId: null, attributeKey: null } },
  ): CassSaathratriEntity4FormGroup {
    const cassSaathratriEntity4RawValue = {
      ...this.getFormDefaults(),
      ...cassSaathratriEntity4,
    };
    return new FormGroup<CassSaathratriEntity4FormGroupContent>({
      compositeId: new FormGroup({
        organizationId: new FormControl(
          {
            value: cassSaathratriEntity4RawValue.compositeId.organizationId,
            disabled: cassSaathratriEntity4RawValue.compositeId.organizationId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        attributeKey: new FormControl(
          {
            value: cassSaathratriEntity4RawValue.compositeId.attributeKey,
            disabled: cassSaathratriEntity4RawValue.compositeId.attributeKey !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      attributeValue: new FormControl(cassSaathratriEntity4RawValue.attributeValue),
    });
  }

  getCassSaathratriEntity4(form: CassSaathratriEntity4FormGroup): ICassSaathratriEntity4 | NewCassSaathratriEntity4 {
    return form.getRawValue() as ICassSaathratriEntity4 | NewCassSaathratriEntity4;
  }

  resetForm(form: CassSaathratriEntity4FormGroup, cassSaathratriEntity4: CassSaathratriEntity4FormGroupInput): void {
    const cassSaathratriEntity4RawValue = { ...this.getFormDefaults(), ...cassSaathratriEntity4 };
    form.reset(
      {
        ...cassSaathratriEntity4RawValue,
        compositeId: {
          organizationId: { value: cassSaathratriEntity4RawValue.compositeId.organizationId, disabled: true },
          attributeKey: { value: cassSaathratriEntity4RawValue.compositeId.attributeKey, disabled: true },
        },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassSaathratriEntity4FormDefaults {
    return {
      compositeId: {
        organizationId: null,
        attributeKey: null,
      },
    };
  }
}
