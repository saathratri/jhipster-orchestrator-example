import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICassBlog, NewCassBlog } from '../cass-blog.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { compositeId: { category: unknown; blogId: unknown } }> = Partial<Omit<T, 'compositeId'>> & {
  compositeId: T['compositeId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassBlog for edit and NewCassBlogFormGroupInput for create.
 */
type CassBlogFormGroupInput = ICassBlog | PartialWithRequiredKeyOf<NewCassBlog>;

type CassBlogFormDefaults = Pick<NewCassBlog, 'compositeId'>;

type CassBlogFormGroupContent = {
  compositeId: FormGroup<{
    category: FormControl<ICassBlog['compositeId']['category']>;
    blogId: FormControl<ICassBlog['compositeId']['blogId']>;
  }>;
  handle: FormControl<ICassBlog['handle']>;
  content: FormControl<ICassBlog['content']>;
};

export type CassBlogFormGroup = FormGroup<CassBlogFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassBlogFormService {
  createCassBlogFormGroup(cassBlog: CassBlogFormGroupInput = { compositeId: { category: null, blogId: null } }): CassBlogFormGroup {
    const cassBlogRawValue = {
      ...this.getFormDefaults(),
      ...cassBlog,
    };
    return new FormGroup<CassBlogFormGroupContent>({
      compositeId: new FormGroup({
        category: new FormControl(
          { value: cassBlogRawValue.compositeId.category, disabled: cassBlogRawValue.compositeId.category !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        blogId: new FormControl(
          { value: cassBlogRawValue.compositeId.blogId, disabled: cassBlogRawValue.compositeId.blogId !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      handle: new FormControl(cassBlogRawValue.handle, {
        validators: [Validators.required, Validators.minLength(3)],
      }),
      content: new FormControl(cassBlogRawValue.content, {
        validators: [Validators.required],
      }),
    });
  }

  getCassBlog(form: CassBlogFormGroup): ICassBlog | NewCassBlog {
    return form.getRawValue() as ICassBlog | NewCassBlog;
  }

  resetForm(form: CassBlogFormGroup, cassBlog: CassBlogFormGroupInput): void {
    const cassBlogRawValue = { ...this.getFormDefaults(), ...cassBlog };
    form.reset(
      {
        ...cassBlogRawValue,
        compositeId: {
          category: { value: cassBlogRawValue.compositeId.category, disabled: true },
          blogId: { value: cassBlogRawValue.compositeId.blogId, disabled: true },
        },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassBlogFormDefaults {
    return {
      compositeId: {
        category: null,
        blogId: null,
      },
    };
  }
}
