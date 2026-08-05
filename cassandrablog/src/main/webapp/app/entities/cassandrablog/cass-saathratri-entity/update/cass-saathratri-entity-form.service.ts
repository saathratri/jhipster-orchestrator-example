import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICassSaathratriEntity, NewCassSaathratriEntity } from '../cass-saathratri-entity.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { entityId: unknown }> = Partial<Omit<T, 'entityId'>> & { entityId: T['entityId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassSaathratriEntity for edit and NewCassSaathratriEntityFormGroupInput for create.
 */
type CassSaathratriEntityFormGroupInput = ICassSaathratriEntity | PartialWithRequiredKeyOf<NewCassSaathratriEntity>;

type CassSaathratriEntityFormDefaults = Pick<NewCassSaathratriEntity, 'entityId'>;

type CassSaathratriEntityFormGroupContent = {
  entityId: FormControl<ICassSaathratriEntity['entityId']>;
  entityName: FormControl<ICassSaathratriEntity['entityName']>;
  entityDescription: FormControl<ICassSaathratriEntity['entityDescription']>;
  entityCost: FormControl<ICassSaathratriEntity['entityCost']>;
  createdId: FormControl<ICassSaathratriEntity['createdId']>;
  createdTimeId: FormControl<ICassSaathratriEntity['createdTimeId']>;
};

export type CassSaathratriEntityFormGroup = FormGroup<CassSaathratriEntityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassSaathratriEntityFormService {
  createCassSaathratriEntityFormGroup(
    cassSaathratriEntity: CassSaathratriEntityFormGroupInput = { entityId: '' },
  ): CassSaathratriEntityFormGroup {
    const cassSaathratriEntityRawValue = {
      ...this.getFormDefaults(),
      ...cassSaathratriEntity,
    };
    return new FormGroup<CassSaathratriEntityFormGroupContent>({
      entityId: new FormControl(
        { value: cassSaathratriEntityRawValue.entityId, disabled: cassSaathratriEntityRawValue.entityId !== '' },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      entityName: new FormControl(cassSaathratriEntityRawValue.entityName),
      entityDescription: new FormControl(cassSaathratriEntityRawValue.entityDescription),
      entityCost: new FormControl(cassSaathratriEntityRawValue.entityCost),
      createdId: new FormControl(cassSaathratriEntityRawValue.createdId),
      createdTimeId: new FormControl(cassSaathratriEntityRawValue.createdTimeId),
    });
  }

  getCassSaathratriEntity(form: CassSaathratriEntityFormGroup): ICassSaathratriEntity | NewCassSaathratriEntity {
    return form.getRawValue() as ICassSaathratriEntity | NewCassSaathratriEntity;
  }

  resetForm(form: CassSaathratriEntityFormGroup, cassSaathratriEntity: CassSaathratriEntityFormGroupInput): void {
    const cassSaathratriEntityRawValue = { ...this.getFormDefaults(), ...cassSaathratriEntity };
    form.reset(
      {
        ...cassSaathratriEntityRawValue,
        entityId: { value: cassSaathratriEntityRawValue.entityId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassSaathratriEntityFormDefaults {
    return {
      entityId: '',
    };
  }
}
