import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICassProduct, NewCassProduct } from '../cass-product.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICassProduct for edit and NewCassProductFormGroupInput for create.
 */
type CassProductFormGroupInput = ICassProduct | PartialWithRequiredKeyOf<NewCassProduct>;

type CassProductFormDefaults = Pick<NewCassProduct, 'id'>;

type CassProductFormGroupContent = {
  id: FormControl<ICassProduct['id']>;
  title: FormControl<ICassProduct['title']>;
  price: FormControl<ICassProduct['price']>;
  image: FormControl<ICassProduct['image']>;
  imageContentType: FormControl<ICassProduct['imageContentType']>;
  addedDate: FormControl<ICassProduct['addedDate']>;
};

export type CassProductFormGroup = FormGroup<CassProductFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CassProductFormService {
  createCassProductFormGroup(cassProduct: CassProductFormGroupInput = { id: '' }): CassProductFormGroup {
    const cassProductRawValue = {
      ...this.getFormDefaults(),
      ...cassProduct,
    };
    return new FormGroup<CassProductFormGroupContent>({
      id: new FormControl(
        { value: cassProductRawValue.id, disabled: cassProductRawValue.id !== '' },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      title: new FormControl(cassProductRawValue.title, {
        validators: [Validators.required],
      }),
      price: new FormControl(cassProductRawValue.price, {
        validators: [Validators.required, Validators.min(0)],
      }),
      image: new FormControl(cassProductRawValue.image),
      imageContentType: new FormControl(cassProductRawValue.imageContentType),
      addedDate: new FormControl(cassProductRawValue.addedDate, {
        validators: [Validators.required],
      }),
    });
  }

  getCassProduct(form: CassProductFormGroup): ICassProduct | NewCassProduct {
    return form.getRawValue() as ICassProduct | NewCassProduct;
  }

  resetForm(form: CassProductFormGroup, cassProduct: CassProductFormGroupInput): void {
    const cassProductRawValue = { ...this.getFormDefaults(), ...cassProduct };
    form.reset(
      {
        ...cassProductRawValue,
        id: { value: cassProductRawValue.id, disabled: true },
        addedDate: cassProductRawValue.addedDate,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CassProductFormDefaults {
    return {
      id: '',
    };
  }
}
