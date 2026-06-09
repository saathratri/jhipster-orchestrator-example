import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPsqlTag, NewPsqlTag } from '../psql-tag.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPsqlTag for edit and NewPsqlTagFormGroupInput for create.
 */
type PsqlTagFormGroupInput = IPsqlTag | PartialWithRequiredKeyOf<NewPsqlTag>;

type PsqlTagFormDefaults = Pick<NewPsqlTag, 'id' | 'posts'>;

type PsqlTagFormGroupContent = {
  id: FormControl<IPsqlTag['id'] | NewPsqlTag['id']>;
  name: FormControl<IPsqlTag['name']>;
  description: FormControl<IPsqlTag['description']>;
  nameEmbedding: FormControl<IPsqlTag['nameEmbedding']>;
  descriptionEmbedding: FormControl<IPsqlTag['descriptionEmbedding']>;
  posts: FormControl<IPsqlTag['posts']>;
};

export type PsqlTagFormGroup = FormGroup<PsqlTagFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PsqlTagFormService {
  createPsqlTagFormGroup(psqlTag?: PsqlTagFormGroupInput): PsqlTagFormGroup {
    const psqlTagRawValue = {
      ...this.getFormDefaults(),
      ...(psqlTag ?? { id: null }),
    };
    return new FormGroup<PsqlTagFormGroupContent>({
      id: new FormControl(
        { value: psqlTagRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(psqlTagRawValue.name, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      description: new FormControl(psqlTagRawValue.description, {
        validators: [Validators.maxLength(255)],
      }),
      nameEmbedding: new FormControl(psqlTagRawValue.nameEmbedding),
      descriptionEmbedding: new FormControl(psqlTagRawValue.descriptionEmbedding),
      posts: new FormControl(psqlTagRawValue.posts ?? []),
    });
  }

  getPsqlTag(form: PsqlTagFormGroup): IPsqlTag | NewPsqlTag {
    return form.getRawValue() as IPsqlTag | NewPsqlTag;
  }

  resetForm(form: PsqlTagFormGroup, psqlTag: PsqlTagFormGroupInput): void {
    const psqlTagRawValue = { ...this.getFormDefaults(), ...psqlTag };
    form.reset({
      ...psqlTagRawValue,
      id: { value: psqlTagRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): PsqlTagFormDefaults {
    return {
      id: null,
      posts: [],
    };
  }
}
