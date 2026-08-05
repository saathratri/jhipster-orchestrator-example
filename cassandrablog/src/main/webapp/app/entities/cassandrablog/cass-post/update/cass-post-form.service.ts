import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICassPost, NewCassPost } from '../cass-post.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { compositeId: { createdDate: unknown; addedDateTime: unknown; postId: unknown } }> = Partial<
  Omit<T, 'compositeId'>
> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassPost for edit and NewCassPostFormGroupInput for create.
 */
type CassPostFormGroupInput = ICassPost | PartialWithRequiredKeyOf<NewCassPost>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICassPost | NewCassPost> = Omit<T, 'addedDateTime' | 'publishedDateTime'> & {
  addedDateTime?: string | null;
  publishedDateTime?: string | null;
};

type CassPostFormRawValue = FormValueOf<ICassPost>;

type NewCassPostFormRawValue = FormValueOf<NewCassPost>;

type CassPostFormDefaults = Pick<NewCassPost, 'publishedDateTime' | 'compositeId'>;

type CassPostFormGroupContent = {
  compositeId: FormGroup<{
    createdDate: FormControl<CassPostFormRawValue['compositeId']['createdDate']>;
    addedDateTime: FormControl<CassPostFormRawValue['compositeId']['addedDateTime']>;
    postId: FormControl<CassPostFormRawValue['compositeId']['postId']>;
  }>;
  title: FormControl<CassPostFormRawValue['title']>;
  content: FormControl<CassPostFormRawValue['content']>;
  publishedDateTime: FormControl<CassPostFormRawValue['publishedDateTime']>;
  sentDate: FormControl<CassPostFormRawValue['sentDate']>;
};

export type CassPostFormGroup = FormGroup<CassPostFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassPostFormService {
  createCassPostFormGroup(
    cassPost: CassPostFormGroupInput = { compositeId: { createdDate: null, addedDateTime: null, postId: null } },
  ): CassPostFormGroup {
    const cassPostRawValue = this.convertCassPostToCassPostRawValue({
      ...this.getFormDefaults(),
      ...cassPost,
    });
    return new FormGroup<CassPostFormGroupContent>({
      compositeId: new FormGroup({
        createdDate: new FormControl(
          { value: cassPostRawValue.compositeId.createdDate, disabled: cassPostRawValue.compositeId.createdDate !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        addedDateTime: new FormControl(
          { value: cassPostRawValue.compositeId.addedDateTime, disabled: cassPostRawValue.compositeId.addedDateTime !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        postId: new FormControl(
          { value: cassPostRawValue.compositeId.postId, disabled: cassPostRawValue.compositeId.postId !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      title: new FormControl(cassPostRawValue.title, {
        validators: [Validators.required],
      }),
      content: new FormControl(cassPostRawValue.content, {
        validators: [Validators.required],
      }),
      publishedDateTime: new FormControl(cassPostRawValue.publishedDateTime),
      sentDate: new FormControl(cassPostRawValue.sentDate),
    });
  }

  getCassPost(form: CassPostFormGroup): ICassPost | NewCassPost {
    return this.convertCassPostRawValueToCassPost(form.getRawValue() as CassPostFormRawValue | NewCassPostFormRawValue);
  }

  resetForm(form: CassPostFormGroup, cassPost: CassPostFormGroupInput): void {
    const cassPostRawValue = this.convertCassPostToCassPostRawValue({ ...this.getFormDefaults(), ...cassPost });
    form.reset(
      {
        ...cassPostRawValue,
        compositeId: {
          createdDate: { value: cassPostRawValue.compositeId.createdDate, disabled: true },
          addedDateTime: { value: cassPostRawValue.compositeId.addedDateTime, disabled: true },
          postId: { value: cassPostRawValue.compositeId.postId, disabled: true },
        },
        publishedDateTime: cassPostRawValue.publishedDateTime,
        sentDate: cassPostRawValue.sentDate,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassPostFormDefaults {
    return {
      compositeId: {
        createdDate: null,
        addedDateTime: null,
        postId: null,
      },
    };
  }

  private convertCassPostRawValueToCassPost(rawCassPost: CassPostFormRawValue | NewCassPostFormRawValue): ICassPost | NewCassPost {
    return {
      ...rawCassPost,
      compositeId: {
        ...rawCassPost.compositeId,
        addedDateTime:
          typeof rawCassPost.compositeId.addedDateTime === 'number'
            ? dayjs(rawCassPost.compositeId.addedDateTime)
            : dayjs(rawCassPost.compositeId.addedDateTime, DATE_TIME_FORMAT),
      },
      publishedDateTime:
        typeof rawCassPost.publishedDateTime === 'number'
          ? dayjs(rawCassPost.publishedDateTime)
          : dayjs(rawCassPost.publishedDateTime, DATE_TIME_FORMAT),
    };
  }

  private convertCassPostToCassPostRawValue(
    cassPost: ICassPost | (Partial<NewCassPost> & CassPostFormDefaults),
  ): CassPostFormRawValue | PartialWithRequiredKeyOf<NewCassPostFormRawValue> {
    return {
      ...cassPost,
      compositeId: {
        ...cassPost.compositeId,
        addedDateTime: cassPost.compositeId.addedDateTime ?? null,
      },
      publishedDateTime: cassPost.publishedDateTime ? cassPost.publishedDateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
