import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPsqlTajUser, NewPsqlTajUser } from '../psql-taj-user.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPsqlTajUser for edit and NewPsqlTajUserFormGroupInput for create.
 */
type PsqlTajUserFormGroupInput = IPsqlTajUser | PartialWithRequiredKeyOf<NewPsqlTajUser>;

type PsqlTajUserFormDefaults = Pick<NewPsqlTajUser, 'id'>;

type PsqlTajUserFormGroupContent = {
  id: FormControl<IPsqlTajUser['id'] | NewPsqlTajUser['id']>;
  login: FormControl<IPsqlTajUser['login']>;
};

export type PsqlTajUserFormGroup = FormGroup<PsqlTajUserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PsqlTajUserFormService {
  createPsqlTajUserFormGroup(psqlTajUser?: PsqlTajUserFormGroupInput): PsqlTajUserFormGroup {
    const psqlTajUserRawValue = {
      ...this.getFormDefaults(),
      ...(psqlTajUser ?? { id: null }),
    };
    return new FormGroup<PsqlTajUserFormGroupContent>({
      id: new FormControl(
        { value: psqlTajUserRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      login: new FormControl(psqlTajUserRawValue.login, {
        validators: [Validators.required, Validators.minLength(7)],
      }),
    });
  }

  getPsqlTajUser(form: PsqlTajUserFormGroup): IPsqlTajUser | NewPsqlTajUser {
    return form.getRawValue() as IPsqlTajUser | NewPsqlTajUser;
  }

  resetForm(form: PsqlTajUserFormGroup, psqlTajUser: PsqlTajUserFormGroupInput): void {
    const psqlTajUserRawValue = { ...this.getFormDefaults(), ...psqlTajUser };
    form.reset({
      ...psqlTajUserRawValue,
      id: { value: psqlTajUserRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): PsqlTajUserFormDefaults {
    return {
      id: null,
    };
  }
}
