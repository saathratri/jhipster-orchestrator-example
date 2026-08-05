import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassProduct, NewCassProduct } from '../cass-product.model';
export type PartialUpdateCassProduct = Partial<ICassProduct> & Pick<ICassProduct, 'id'>;

type RestOf<T extends ICassProduct | NewCassProduct> = Omit<T, 'addedDate'> & {
  addedDate?: number | null;
};

export type RestCassProduct = RestOf<ICassProduct>;

export type NewRestCassProduct = RestOf<NewCassProduct>;

export type PartialUpdateRestCassProduct = RestOf<PartialUpdateCassProduct>;

export type EntityResponseType = HttpResponse<ICassProduct>;
export type EntityArrayResponseType = HttpResponse<ICassProduct[]>;

@Injectable()
export class CassProductsService {
  readonly cassProductsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly cassProductsResource = httpResource<RestCassProduct[]>(() => {
    const params = this.cassProductsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassProduct that have been fetched.
   */
  readonly cassProducts = computed(() =>
    (this.cassProductsResource.hasValue() ? this.cassProductsResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-products', 'cassandrastore');

  protected convertValueFromServer(restCassProduct: RestCassProduct): ICassProduct {
    return {
      ...restCassProduct,
      addedDate: restCassProduct.addedDate ? dayjs(restCassProduct.addedDate) : null,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class CassProductService extends CassProductsService {
  protected readonly http = inject(HttpClient);

  create(cassProduct: NewCassProduct): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassProduct);
    return this.http
      .post<RestCassProduct>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cassProduct: ICassProduct): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassProduct);
    return this.http
      .put<RestCassProduct>(`${this.resourceUrl}/${this.getCassProductIdentifier(cassProduct)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cassProduct: PartialUpdateCassProduct): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassProduct);
    return this.http
      .patch<RestCassProduct>(`${this.resourceUrl}/${this.getCassProductIdentifier(cassProduct)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCassProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassProduct[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassProduct[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCassProductIdentifier(cassProduct: Pick<ICassProduct, 'id'>): string {
    return cassProduct.id;
  }

  compareCassProduct(o1: Pick<ICassProduct, 'id'> | null, o2: Pick<ICassProduct, 'id'> | null): boolean {
    return o1 && o2 ? this.getCassProductIdentifier(o1) === this.getCassProductIdentifier(o2) : o1 === o2;
  }

  addCassProductToCollectionIfMissing<Type extends Pick<ICassProduct, 'id'>>(
    cassProductCollection: Type[],
    ...cassProductsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassProducts: Type[] = cassProductsToCheck.filter(isPresent);
    if (cassProducts.length > 0) {
      const cassProductCollectionIdentifiers = cassProductCollection.map(cassProductItem => this.getCassProductIdentifier(cassProductItem));
      const cassProductsToAdd = cassProducts.filter(cassProductItem => {
        const cassProductIdentifier = this.getCassProductIdentifier(cassProductItem);
        if (cassProductCollectionIdentifiers.includes(cassProductIdentifier)) {
          return false;
        }
        cassProductCollectionIdentifiers.push(cassProductIdentifier);
        return true;
      });
      return [...cassProductsToAdd, ...cassProductCollection];
    }
    return cassProductCollection;
  }

  protected convertValueFromClient<T extends ICassProduct | NewCassProduct | PartialUpdateCassProduct>(cassProduct: T): RestOf<T> {
    return {
      ...cassProduct,
      addedDate: cassProduct.addedDate ? cassProduct.addedDate.valueOf() : null,
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestCassProduct>): HttpResponse<ICassProduct> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCassProduct[]>): HttpResponse<ICassProduct[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
