import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPsqlBlog, NewPsqlBlog } from '../psql-blog.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPsqlBlog for edit and NewPsqlBlogFormGroupInput for create.
 */
type PsqlBlogFormGroupInput = IPsqlBlog | PartialWithRequiredKeyOf<NewPsqlBlog>;

type PsqlBlogFormDefaults = Pick<NewPsqlBlog, 'id'>;

type PsqlBlogFormGroupContent = {
  id: FormControl<IPsqlBlog['id'] | NewPsqlBlog['id']>;
  name: FormControl<IPsqlBlog['name']>;
  handle: FormControl<IPsqlBlog['handle']>;
  tajUser: FormControl<IPsqlBlog['tajUser']>;
};

export type PsqlBlogFormGroup = FormGroup<PsqlBlogFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PsqlBlogFormService {
  createPsqlBlogFormGroup(psqlBlog?: PsqlBlogFormGroupInput): PsqlBlogFormGroup {
    const psqlBlogRawValue = {
      ...this.getFormDefaults(),
      ...(psqlBlog ?? { id: null }),
    };
    return new FormGroup<PsqlBlogFormGroupContent>({
      id: new FormControl(
        { value: psqlBlogRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(psqlBlogRawValue.name, {
        validators: [Validators.required, Validators.minLength(3)],
      }),
      handle: new FormControl(psqlBlogRawValue.handle, {
        validators: [Validators.required, Validators.minLength(2)],
      }),
      tajUser: new FormControl(psqlBlogRawValue.tajUser),
    });
  }

  getPsqlBlog(form: PsqlBlogFormGroup): IPsqlBlog | NewPsqlBlog {
    return form.getRawValue() as IPsqlBlog | NewPsqlBlog;
  }

  resetForm(form: PsqlBlogFormGroup, psqlBlog: PsqlBlogFormGroupInput): void {
    const psqlBlogRawValue = { ...this.getFormDefaults(), ...psqlBlog };
    form.reset({
      ...psqlBlogRawValue,
      id: { value: psqlBlogRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): PsqlBlogFormDefaults {
    return {
      id: null,
    };
  }
}
