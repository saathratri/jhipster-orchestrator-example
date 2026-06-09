import { HttpClient, HttpParams, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassSaathratriEntity2, ICassSaathratriEntity2Id, NewCassSaathratriEntity2 } from '../cass-saathratri-entity-2.model';
export type PartialUpdateCassSaathratriEntity2 = Partial<ICassSaathratriEntity2> & Pick<ICassSaathratriEntity2, 'compositeId'>;

type RestOf<T extends ICassSaathratriEntity2 | NewCassSaathratriEntity2> = Omit<T, 'arrivalDate' | 'blogId' | 'departureDate'> & {
  compositeId: {
    arrivalDate?: number | null;
  };
  departureDate?: number | null;
};

export type RestCassSaathratriEntity2 = RestOf<ICassSaathratriEntity2>;

export type NewRestCassSaathratriEntity2 = RestOf<NewCassSaathratriEntity2>;

export type PartialUpdateRestCassSaathratriEntity2 = RestOf<PartialUpdateCassSaathratriEntity2>;

export type EntityResponseType = HttpResponse<ICassSaathratriEntity2>;
export type EntityArrayResponseType = HttpResponse<ICassSaathratriEntity2[]>;

@Injectable()
export class CassSaathratriEntity2sService {
  readonly cassSaathratriEntity2sParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly cassSaathratriEntity2sResource = httpResource<RestCassSaathratriEntity2[]>(() => {
    const params = this.cassSaathratriEntity2sParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassSaathratriEntity2 that have been fetched.
   */
  readonly cassSaathratriEntity2s = computed(() =>
    (this.cassSaathratriEntity2sResource.hasValue() ? this.cassSaathratriEntity2sResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-saathratri-entity-2-s', 'cassandrablog');

  protected convertValueFromServer(restCassSaathratriEntity2: RestCassSaathratriEntity2): ICassSaathratriEntity2 {
    return {
      ...restCassSaathratriEntity2,
      compositeId: {
        entityTypeId: restCassSaathratriEntity2.compositeId.entityTypeId,

        yearOfDateAdded: restCassSaathratriEntity2.compositeId.yearOfDateAdded,
        arrivalDate: restCassSaathratriEntity2.compositeId.arrivalDate ? dayjs(restCassSaathratriEntity2.compositeId.arrivalDate) : null,

        blogId: restCassSaathratriEntity2.compositeId.blogId,
      },
      departureDate: restCassSaathratriEntity2.departureDate ? dayjs(restCassSaathratriEntity2.departureDate) : null,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class CassSaathratriEntity2Service extends CassSaathratriEntity2sService {
  protected readonly http = inject(HttpClient);

  create(cassSaathratriEntity2: NewCassSaathratriEntity2): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassSaathratriEntity2);
    return this.http
      .post<RestCassSaathratriEntity2>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cassSaathratriEntity2: ICassSaathratriEntity2): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassSaathratriEntity2);
    return this.http
      .put<RestCassSaathratriEntity2>(
        `${this.resourceUrl}/${cassSaathratriEntity2.compositeId.entityTypeId}/${cassSaathratriEntity2.compositeId.yearOfDateAdded}/${copy.compositeId.arrivalDate}/${cassSaathratriEntity2.compositeId.blogId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cassSaathratriEntity2: PartialUpdateCassSaathratriEntity2): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassSaathratriEntity2);
    return this.http
      .patch<RestCassSaathratriEntity2>(
        `${this.resourceUrl}/${cassSaathratriEntity2.compositeId.entityTypeId}/${cassSaathratriEntity2.compositeId.yearOfDateAdded}/${copy.compositeId.arrivalDate}/${cassSaathratriEntity2.compositeId.blogId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(entityTypeId: string, yearOfDateAdded: number, arrivalDate: number, blogId: string): Observable<EntityResponseType> {
    const data = { entityTypeId, yearOfDateAdded, arrivalDate, blogId };
    return this.http
      .get<RestCassSaathratriEntity2>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassSaathratriEntity2[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassSaathratriEntity2[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdPageable(entityTypeId: string, req?: any): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedPageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDatePageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdPageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    blogId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('blogId', blogId);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanPageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqualPageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanPageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqualPageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanPageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    blogId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('blogId', blogId);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqualPageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    blogId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('blogId', blogId);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanPageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    blogId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('blogId', blogId);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqualPageable(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    blogId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('blogId', blogId);
    return this.http
      .get<
        RestCassSaathratriEntity2[]
      >(`${this.resourceUrl}/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
    entityTypeId: string,
    yearOfDateAdded: number,
    arrivalDate: number,
    blogId: string,
  ): Observable<EntityResponseType> {
    let options = new HttpParams();
    options = options.set('entityTypeId', entityTypeId);
    options = options.set('yearOfDateAdded', yearOfDateAdded);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('blogId', blogId);
    return this.http
      .get<RestCassSaathratriEntity2>(
        `${this.resourceUrl}/find-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id`,
        { params: options, observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  delete(cassSaathratriEntity2: ICassSaathratriEntity2): Observable<HttpResponse<{}>> {
    const copy = this.convertValueFromClient(cassSaathratriEntity2);
    return this.http.delete(
      `${this.resourceUrl}/${cassSaathratriEntity2.compositeId.entityTypeId}/${cassSaathratriEntity2.compositeId.yearOfDateAdded}/${copy.compositeId.arrivalDate}/${cassSaathratriEntity2.compositeId.blogId}`,
      { observe: 'response' },
    );
  }

  getCassSaathratriEntity2Identifier(cassSaathratriEntity2: Pick<ICassSaathratriEntity2, 'compositeId'>): ICassSaathratriEntity2Id {
    return cassSaathratriEntity2.compositeId;
  }
  compareCassSaathratriEntity2(
    o1: Pick<ICassSaathratriEntity2, 'compositeId'> | null,
    o2: Pick<ICassSaathratriEntity2, 'compositeId'> | null,
  ): boolean {
    return o1 && o2 ? this.getCassSaathratriEntity2Identifier(o1) === this.getCassSaathratriEntity2Identifier(o2) : o1 === o2;
  }
  addCassSaathratriEntity2ToCollectionIfMissing<Type extends Pick<ICassSaathratriEntity2, 'compositeId'>>(
    cassSaathratriEntity2Collection: Type[],
    ...cassSaathratriEntity2sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassSaathratriEntity2s: Type[] = cassSaathratriEntity2sToCheck.filter(isPresent);
    if (cassSaathratriEntity2s.length > 0) {
      const cassSaathratriEntity2CollectionIdentifiers = cassSaathratriEntity2Collection.map(cassSaathratriEntity2Item =>
        this.getCassSaathratriEntity2Identifier(cassSaathratriEntity2Item),
      );
      const cassSaathratriEntity2sToAdd = cassSaathratriEntity2s.filter(cassSaathratriEntity2Item => {
        const cassSaathratriEntity2Identifier = this.getCassSaathratriEntity2Identifier(cassSaathratriEntity2Item);
        if (cassSaathratriEntity2CollectionIdentifiers.includes(cassSaathratriEntity2Identifier)) {
          return false;
        }
        cassSaathratriEntity2CollectionIdentifiers.push(cassSaathratriEntity2Identifier);
        return true;
      });
      return [...cassSaathratriEntity2sToAdd, ...cassSaathratriEntity2Collection];
    }
    return cassSaathratriEntity2Collection;
  }

  protected convertValueFromClient<T extends ICassSaathratriEntity2 | NewCassSaathratriEntity2 | PartialUpdateCassSaathratriEntity2>(
    cassSaathratriEntity2: T,
  ): RestOf<T> {
    return {
      ...cassSaathratriEntity2,
      compositeId: {
        ...cassSaathratriEntity2.compositeId,

        arrivalDate: cassSaathratriEntity2.compositeId.arrivalDate ? cassSaathratriEntity2.compositeId.arrivalDate.valueOf() : null,
      },
      departureDate: cassSaathratriEntity2.departureDate ? cassSaathratriEntity2.departureDate.valueOf() : null,
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestCassSaathratriEntity2>): HttpResponse<ICassSaathratriEntity2> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCassSaathratriEntity2[]>): HttpResponse<ICassSaathratriEntity2[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
