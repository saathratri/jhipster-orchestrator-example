import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICassTag, NewCassTag } from '../cass-tag.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassTag for edit and NewCassTagFormGroupInput for create.
 */
type CassTagFormGroupInput = ICassTag | PartialWithRequiredKeyOf<NewCassTag>;

type CassTagFormDefaults = Pick<NewCassTag, 'id'>;

type CassTagFormGroupContent = {
  id: FormControl<ICassTag['id']>;
  name: FormControl<ICassTag['name']>;
  description: FormControl<ICassTag['description']>;
};

export type CassTagFormGroup = FormGroup<CassTagFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassTagFormService {
  createCassTagFormGroup(cassTag: CassTagFormGroupInput = { id: '' }): CassTagFormGroup {
    const cassTagRawValue = {
      ...this.getFormDefaults(),
      ...cassTag,
    };
    return new FormGroup<CassTagFormGroupContent>({
      id: new FormControl(
        { value: cassTagRawValue.id, disabled: cassTagRawValue.id !== '' },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(cassTagRawValue.name, {
        validators: [Validators.required, Validators.minLength(2)],
      }),
      description: new FormControl(cassTagRawValue.description),
    });
  }

  getCassTag(form: CassTagFormGroup): ICassTag | NewCassTag {
    return form.getRawValue() as ICassTag | NewCassTag;
  }

  resetForm(form: CassTagFormGroup, cassTag: CassTagFormGroupInput): void {
    const cassTagRawValue = { ...this.getFormDefaults(), ...cassTag };
    form.reset(
      {
        ...cassTagRawValue,
        id: { value: cassTagRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassTagFormDefaults {
    return {
      id: '',
    };
  }
}
