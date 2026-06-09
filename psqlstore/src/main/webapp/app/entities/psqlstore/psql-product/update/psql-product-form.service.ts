import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPsqlProduct, NewPsqlProduct } from '../psql-product.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPsqlProduct for edit and NewPsqlProductFormGroupInput for create.
 */
type PsqlProductFormGroupInput = IPsqlProduct | PartialWithRequiredKeyOf<NewPsqlProduct>;

type PsqlProductFormDefaults = Pick<NewPsqlProduct, 'id'>;

type PsqlProductFormGroupContent = {
  id: FormControl<IPsqlProduct['id'] | NewPsqlProduct['id']>;
  title: FormControl<IPsqlProduct['title']>;
  price: FormControl<IPsqlProduct['price']>;
  image: FormControl<IPsqlProduct['image']>;
  imageContentType: FormControl<IPsqlProduct['imageContentType']>;
};

export type PsqlProductFormGroup = FormGroup<PsqlProductFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PsqlProductFormService {
  createPsqlProductFormGroup(psqlProduct?: PsqlProductFormGroupInput): PsqlProductFormGroup {
    const psqlProductRawValue = {
      ...this.getFormDefaults(),
      ...(psqlProduct ?? { id: null }),
    };
    return new FormGroup<PsqlProductFormGroupContent>({
      id: new FormControl(
        { value: psqlProductRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      title: new FormControl(psqlProductRawValue.title, {
        validators: [Validators.required],
      }),
      price: new FormControl(psqlProductRawValue.price, {
        validators: [Validators.required, Validators.min(0)],
      }),
      image: new FormControl(psqlProductRawValue.image),
      imageContentType: new FormControl(psqlProductRawValue.imageContentType),
    });
  }

  getPsqlProduct(form: PsqlProductFormGroup): IPsqlProduct | NewPsqlProduct {
    return form.getRawValue() as IPsqlProduct | NewPsqlProduct;
  }

  resetForm(form: PsqlProductFormGroup, psqlProduct: PsqlProductFormGroupInput): void {
    const psqlProductRawValue = { ...this.getFormDefaults(), ...psqlProduct };
    form.reset({
      ...psqlProductRawValue,
      id: { value: psqlProductRawValue.id, disabled: true },
    });
  }

  private getFormDefaults(): PsqlProductFormDefaults {
    return {
      id: null,
    };
  }
}
