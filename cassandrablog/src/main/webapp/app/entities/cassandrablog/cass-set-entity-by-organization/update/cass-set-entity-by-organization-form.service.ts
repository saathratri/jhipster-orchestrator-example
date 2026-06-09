import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICassSetEntityByOrganization, NewCassSetEntityByOrganization } from '../cass-set-entity-by-organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { organizationId: unknown }> = Partial<Omit<T, 'organizationId'>> & {
  organizationId: T['organizationId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassSetEntityByOrganization for edit and NewCassSetEntityByOrganizationFormGroupInput for create.
 */
type CassSetEntityByOrganizationFormGroupInput = ICassSetEntityByOrganization | PartialWithRequiredKeyOf<NewCassSetEntityByOrganization>;

type CassSetEntityByOrganizationFormDefaults = Pick<NewCassSetEntityByOrganization, 'organizationId'>;

type CassSetEntityByOrganizationFormGroupContent = {
  organizationId: FormControl<ICassSetEntityByOrganization['organizationId']>;
  tags: FormControl<ICassSetEntityByOrganization['tags']>;
};

export type CassSetEntityByOrganizationFormGroup = FormGroup<CassSetEntityByOrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassSetEntityByOrganizationFormService {
  createCassSetEntityByOrganizationFormGroup(
    cassSetEntityByOrganization: CassSetEntityByOrganizationFormGroupInput = { organizationId: '' },
  ): CassSetEntityByOrganizationFormGroup {
    const cassSetEntityByOrganizationRawValue = {
      ...this.getFormDefaults(),
      ...cassSetEntityByOrganization,
    };
    return new FormGroup<CassSetEntityByOrganizationFormGroupContent>({
      organizationId: new FormControl(
        { value: cassSetEntityByOrganizationRawValue.organizationId, disabled: cassSetEntityByOrganizationRawValue.organizationId !== '' },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tags: new FormControl(cassSetEntityByOrganizationRawValue.tags),
    });
  }

  getCassSetEntityByOrganization(
    form: CassSetEntityByOrganizationFormGroup,
  ): ICassSetEntityByOrganization | NewCassSetEntityByOrganization {
    return form.getRawValue() as ICassSetEntityByOrganization | NewCassSetEntityByOrganization;
  }

  resetForm(form: CassSetEntityByOrganizationFormGroup, cassSetEntityByOrganization: CassSetEntityByOrganizationFormGroupInput): void {
    const cassSetEntityByOrganizationRawValue = { ...this.getFormDefaults(), ...cassSetEntityByOrganization };
    form.reset(
      {
        ...cassSetEntityByOrganizationRawValue,
        organizationId: { value: cassSetEntityByOrganizationRawValue.organizationId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassSetEntityByOrganizationFormDefaults {
    return {
      organizationId: '',
    };
  }
}
