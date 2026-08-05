import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IPsqlProduct, NewPsqlProduct } from '../psql-product.model';

export type PartialUpdatePsqlProduct = Partial<IPsqlProduct> & Pick<IPsqlProduct, 'id'>;

@Injectable()
export class PsqlProductsService {
  readonly psqlProductsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly psqlProductsResource = httpResource<IPsqlProduct[]>(() => {
    const params = this.psqlProductsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of psqlProduct that have been fetched. It is updated when the psqlProductsResource emits a new value.
   * In case of error while fetching the psqlProducts, the signal is set to an empty array.
   */
  readonly psqlProducts = computed(() => (this.psqlProductsResource.hasValue() ? this.psqlProductsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/psql-products', 'psqlstore');
}

@Injectable({ providedIn: 'root' })
export class PsqlProductService extends PsqlProductsService {
  protected readonly http = inject(HttpClient);

  create(psqlProduct: NewPsqlProduct): Observable<IPsqlProduct> {
    return this.http.post<IPsqlProduct>(this.resourceUrl, psqlProduct);
  }

  update(psqlProduct: IPsqlProduct): Observable<IPsqlProduct> {
    return this.http.put<IPsqlProduct>(
      `${this.resourceUrl}/${encodeURIComponent(this.getPsqlProductIdentifier(psqlProduct))}`,
      psqlProduct,
    );
  }

  partialUpdate(psqlProduct: PartialUpdatePsqlProduct): Observable<IPsqlProduct> {
    return this.http.patch<IPsqlProduct>(
      `${this.resourceUrl}/${encodeURIComponent(this.getPsqlProductIdentifier(psqlProduct))}`,
      psqlProduct,
    );
  }

  find(id: string): Observable<IPsqlProduct> {
    return this.http.get<IPsqlProduct>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<IPsqlProduct[]>> {
    const options = createRequestOption(req);
    return this.http.get<IPsqlProduct[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getPsqlProductIdentifier(psqlProduct: Pick<IPsqlProduct, 'id'>): string {
    return psqlProduct.id;
  }

  comparePsqlProduct(o1: Pick<IPsqlProduct, 'id'> | null, o2: Pick<IPsqlProduct, 'id'> | null): boolean {
    return o1 && o2 ? this.getPsqlProductIdentifier(o1) === this.getPsqlProductIdentifier(o2) : o1 === o2;
  }

  addPsqlProductToCollectionIfMissing<Type extends Pick<IPsqlProduct, 'id'>>(
    psqlProductCollection: Type[],
    ...psqlProductsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const psqlProducts: Type[] = psqlProductsToCheck.filter(isPresent);
    if (psqlProducts.length > 0) {
      const psqlProductCollectionIdentifiers = psqlProductCollection.map(psqlProductItem => this.getPsqlProductIdentifier(psqlProductItem));
      const psqlProductsToAdd = psqlProducts.filter(psqlProductItem => {
        const psqlProductIdentifier = this.getPsqlProductIdentifier(psqlProductItem);
        if (psqlProductCollectionIdentifiers.includes(psqlProductIdentifier)) {
          return false;
        }
        psqlProductCollectionIdentifiers.push(psqlProductIdentifier);
        return true;
      });
      return [...psqlProductsToAdd, ...psqlProductCollection];
    }
    return psqlProductCollection;
  }
}
