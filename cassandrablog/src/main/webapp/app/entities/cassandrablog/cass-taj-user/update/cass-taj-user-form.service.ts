import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICassTajUser, NewCassTajUser } from '../cass-taj-user.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassTajUser for edit and NewCassTajUserFormGroupInput for create.
 */
type CassTajUserFormGroupInput = ICassTajUser | PartialWithRequiredKeyOf<NewCassTajUser>;

type CassTajUserFormDefaults = Pick<NewCassTajUser, 'id'>;

type CassTajUserFormGroupContent = {
  id: FormControl<ICassTajUser['id']>;
  login: FormControl<ICassTajUser['login']>;
};

export type CassTajUserFormGroup = FormGroup<CassTajUserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassTajUserFormService {
  createCassTajUserFormGroup(cassTajUser: CassTajUserFormGroupInput = { id: '' }): CassTajUserFormGroup {
    const cassTajUserRawValue = {
      ...this.getFormDefaults(),
      ...cassTajUser,
    };
    return new FormGroup<CassTajUserFormGroupContent>({
      id: new FormControl(
        { value: cassTajUserRawValue.id, disabled: cassTajUserRawValue.id !== '' },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      login: new FormControl(cassTajUserRawValue.login, {
        validators: [Validators.required, Validators.minLength(6)],
      }),
    });
  }

  getCassTajUser(form: CassTajUserFormGroup): ICassTajUser | NewCassTajUser {
    return form.getRawValue() as ICassTajUser | NewCassTajUser;
  }

  resetForm(form: CassTajUserFormGroup, cassTajUser: CassTajUserFormGroupInput): void {
    const cassTajUserRawValue = { ...this.getFormDefaults(), ...cassTajUser };
    form.reset(
      {
        ...cassTajUserRawValue,
        id: { value: cassTajUserRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassTajUserFormDefaults {
    return {
      id: '',
    };
  }
}
