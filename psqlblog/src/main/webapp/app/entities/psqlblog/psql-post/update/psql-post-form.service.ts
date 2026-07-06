import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPsqlPost, NewPsqlPost } from '../psql-post.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPsqlPost for edit and NewPsqlPostFormGroupInput for create.
 */
type PsqlPostFormGroupInput = IPsqlPost | PartialWithRequiredKeyOf<NewPsqlPost>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPsqlPost | NewPsqlPost> = Omit<T, 'date'> & {
  date?: string | null;
};

type PsqlPostFormRawValue = FormValueOf<IPsqlPost>;

type NewPsqlPostFormRawValue = FormValueOf<NewPsqlPost>;

type PsqlPostFormDefaults = Pick<NewPsqlPost, 'id' | 'date' | 'tags'>;

type PsqlPostFormGroupContent = {
  id: FormControl<PsqlPostFormRawValue['id'] | NewPsqlPost['id']>;
  title: FormControl<PsqlPostFormRawValue['title']>;
  content: FormControl<PsqlPostFormRawValue['content']>;
  date: FormControl<PsqlPostFormRawValue['date']>;
  blog: FormControl<PsqlPostFormRawValue['blog']>;
  tags: FormControl<PsqlPostFormRawValue['tags']>;
};

export type PsqlPostFormGroup = FormGroup<PsqlPostFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PsqlPostFormService {
  createPsqlPostFormGroup(psqlPost?: PsqlPostFormGroupInput): PsqlPostFormGroup {
    const psqlPostRawValue = this.convertPsqlPostToPsqlPostRawValue({
      ...this.getFormDefaults(),
      ...(psqlPost ?? { id: null }),
    });
    return new FormGroup<PsqlPostFormGroupContent>({
      id: new FormControl(
        { value: psqlPostRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      title: new FormControl(psqlPostRawValue.title, {
        validators: [Validators.required],
      }),
      content: new FormControl(psqlPostRawValue.content, {
        validators: [Validators.required],
      }),
      date: new FormControl(psqlPostRawValue.date, {
        validators: [Validators.required],
      }),
      blog: new FormControl(psqlPostRawValue.blog),
      tags: new FormControl(psqlPostRawValue.tags ?? []),
    });
  }

  getPsqlPost(form: PsqlPostFormGroup): IPsqlPost | NewPsqlPost {
    return this.convertPsqlPostRawValueToPsqlPost(form.getRawValue());
  }

  resetForm(form: PsqlPostFormGroup, psqlPost: PsqlPostFormGroupInput): void {
    const psqlPostRawValue = this.convertPsqlPostToPsqlPostRawValue({ ...this.getFormDefaults(), ...psqlPost });
    form.reset({
      ...psqlPostRawValue,
      id: { value: psqlPostRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): PsqlPostFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
      tags: [],
    };
  }

  private convertPsqlPostRawValueToPsqlPost(rawPsqlPost: PsqlPostFormRawValue | NewPsqlPostFormRawValue): IPsqlPost | NewPsqlPost {
    return {
      ...rawPsqlPost,
      date: dayjs(rawPsqlPost.date, DATE_TIME_FORMAT),
    };
  }

  private convertPsqlPostToPsqlPostRawValue(
    psqlPost: IPsqlPost | (Partial<NewPsqlPost> & PsqlPostFormDefaults),
  ): PsqlPostFormRawValue | PartialWithRequiredKeyOf<NewPsqlPostFormRawValue> {
    return {
      ...psqlPost,
      date: psqlPost.date ? psqlPost.date.format(DATE_TIME_FORMAT) : undefined,
      tags: psqlPost.tags ?? [],
    };
  }
}
