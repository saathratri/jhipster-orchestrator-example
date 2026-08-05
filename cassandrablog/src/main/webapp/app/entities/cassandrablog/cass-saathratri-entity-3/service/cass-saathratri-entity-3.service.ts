import { HttpClient, HttpParams, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassSaathratriEntity3, ICassSaathratriEntity3Id, NewCassSaathratriEntity3 } from '../cass-saathratri-entity-3.model';
export type PartialUpdateCassSaathratriEntity3 = Partial<ICassSaathratriEntity3> & Pick<ICassSaathratriEntity3, 'compositeId'>;

type RestOf<T extends ICassSaathratriEntity3 | NewCassSaathratriEntity3> = Omit<T, 'createdTimeId' | 'departureDate' | 'tags'> & {
  compositeId: {};
  departureDate?: number | null;
  tags?: Set<string> | null;
};

export type RestCassSaathratriEntity3 = RestOf<ICassSaathratriEntity3>;

export type NewRestCassSaathratriEntity3 = RestOf<NewCassSaathratriEntity3>;

export type PartialUpdateRestCassSaathratriEntity3 = RestOf<PartialUpdateCassSaathratriEntity3>;

export type EntityResponseType = HttpResponse<ICassSaathratriEntity3>;
export type EntityArrayResponseType = HttpResponse<ICassSaathratriEntity3[]>;

@Injectable()
export class CassSaathratriEntity3sService {
  readonly cassSaathratriEntity3sParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly cassSaathratriEntity3sResource = httpResource<RestCassSaathratriEntity3[]>(() => {
    const params = this.cassSaathratriEntity3sParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassSaathratriEntity3 that have been fetched.
   */
  readonly cassSaathratriEntity3s = computed(() =>
    (this.cassSaathratriEntity3sResource.hasValue() ? this.cassSaathratriEntity3sResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-saathratri-entity-3-s', 'cassandrablog');

  protected convertValueFromServer(restCassSaathratriEntity3: RestCassSaathratriEntity3): ICassSaathratriEntity3 {
    return {
      ...restCassSaathratriEntity3,
      compositeId: {
        entityType: restCassSaathratriEntity3.compositeId.entityType,

        createdTimeId: restCassSaathratriEntity3.compositeId.createdTimeId,
      },
      departureDate: restCassSaathratriEntity3.departureDate ? dayjs(restCassSaathratriEntity3.departureDate) : null,
      tags: restCassSaathratriEntity3.tags ? new Set(restCassSaathratriEntity3.tags) : new Set<string>(),
    };
  }
}

@Injectable({ providedIn: 'root' })
export class CassSaathratriEntity3Service extends CassSaathratriEntity3sService {
  protected readonly http = inject(HttpClient);

  create(cassSaathratriEntity3: NewCassSaathratriEntity3): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassSaathratriEntity3);
    return this.http
      .post<RestCassSaathratriEntity3>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cassSaathratriEntity3: ICassSaathratriEntity3): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassSaathratriEntity3);
    return this.http
      .put<RestCassSaathratriEntity3>(
        `${this.resourceUrl}/${cassSaathratriEntity3.compositeId.entityType}/${cassSaathratriEntity3.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cassSaathratriEntity3: PartialUpdateCassSaathratriEntity3): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassSaathratriEntity3);
    return this.http
      .patch<RestCassSaathratriEntity3>(
        `${this.resourceUrl}/${cassSaathratriEntity3.compositeId.entityType}/${cassSaathratriEntity3.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(entityType: string, createdTimeId: string): Observable<EntityResponseType> {
    const data = { entityType, createdTimeId };
    return this.http
      .get<RestCassSaathratriEntity3>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassSaathratriEntity3[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassSaathratriEntity3[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypePageable(entityType: string, req?: any): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityType', entityType);
    return this.http
      .get<
        RestCassSaathratriEntity3[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdPageable(
    entityType: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityType', entityType);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<
        RestCassSaathratriEntity3[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable(
    entityType: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityType', entityType);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<
        RestCassSaathratriEntity3[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable(
    entityType: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityType', entityType);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<
        RestCassSaathratriEntity3[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable(
    entityType: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityType', entityType);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<
        RestCassSaathratriEntity3[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
    entityType: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityType', entityType);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<
        RestCassSaathratriEntity3[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(entityType: string, createdTimeId: string): Observable<EntityResponseType> {
    let options = new HttpParams();
    options = options.set('entityType', entityType);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<RestCassSaathratriEntity3>(`${this.resourceUrl}/find-by-composite-id-entity-type-and-composite-id-created-time-id`, {
        params: options,
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  delete(cassSaathratriEntity3: ICassSaathratriEntity3): Observable<HttpResponse<{}>> {
    const copy = this.convertValueFromClient(cassSaathratriEntity3);
    return this.http.delete(
      `${this.resourceUrl}/${cassSaathratriEntity3.compositeId.entityType}/${cassSaathratriEntity3.compositeId.createdTimeId}`,
      { observe: 'response' },
    );
  }

  getCassSaathratriEntity3Identifier(cassSaathratriEntity3: Pick<ICassSaathratriEntity3, 'compositeId'>): ICassSaathratriEntity3Id {
    return cassSaathratriEntity3.compositeId;
  }
  compareCassSaathratriEntity3(
    o1: Pick<ICassSaathratriEntity3, 'compositeId'> | null,
    o2: Pick<ICassSaathratriEntity3, 'compositeId'> | null,
  ): boolean {
    return o1 && o2 ? this.getCassSaathratriEntity3Identifier(o1) === this.getCassSaathratriEntity3Identifier(o2) : o1 === o2;
  }
  addCassSaathratriEntity3ToCollectionIfMissing<Type extends Pick<ICassSaathratriEntity3, 'compositeId'>>(
    cassSaathratriEntity3Collection: Type[],
    ...cassSaathratriEntity3sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassSaathratriEntity3s: Type[] = cassSaathratriEntity3sToCheck.filter(isPresent);
    if (cassSaathratriEntity3s.length > 0) {
      const cassSaathratriEntity3CollectionIdentifiers = cassSaathratriEntity3Collection.map(cassSaathratriEntity3Item =>
        this.getCassSaathratriEntity3Identifier(cassSaathratriEntity3Item),
      );
      const cassSaathratriEntity3sToAdd = cassSaathratriEntity3s.filter(cassSaathratriEntity3Item => {
        const cassSaathratriEntity3Identifier = this.getCassSaathratriEntity3Identifier(cassSaathratriEntity3Item);
        if (cassSaathratriEntity3CollectionIdentifiers.includes(cassSaathratriEntity3Identifier)) {
          return false;
        }
        cassSaathratriEntity3CollectionIdentifiers.push(cassSaathratriEntity3Identifier);
        return true;
      });
      return [...cassSaathratriEntity3sToAdd, ...cassSaathratriEntity3Collection];
    }
    return cassSaathratriEntity3Collection;
  }

  protected convertValueFromClient<T extends ICassSaathratriEntity3 | NewCassSaathratriEntity3 | PartialUpdateCassSaathratriEntity3>(
    cassSaathratriEntity3: T,
  ): RestOf<T> {
    return {
      ...cassSaathratriEntity3,
      compositeId: {
        ...cassSaathratriEntity3.compositeId,
      },
      departureDate: cassSaathratriEntity3.departureDate ? cassSaathratriEntity3.departureDate.valueOf() : null,
      tags: cassSaathratriEntity3.tags ? Array.from(cassSaathratriEntity3.tags) : [],
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestCassSaathratriEntity3>): HttpResponse<ICassSaathratriEntity3> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCassSaathratriEntity3[]>): HttpResponse<ICassSaathratriEntity3[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
