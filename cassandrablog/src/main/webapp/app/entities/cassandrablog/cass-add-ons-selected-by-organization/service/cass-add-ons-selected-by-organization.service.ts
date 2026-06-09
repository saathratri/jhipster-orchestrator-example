import { HttpClient, HttpParams, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import {
  ICassAddOnsSelectedByOrganization,
  ICassAddOnsSelectedByOrganizationId,
  NewCassAddOnsSelectedByOrganization,
} from '../cass-add-ons-selected-by-organization.model';
export type PartialUpdateCassAddOnsSelectedByOrganization = Partial<ICassAddOnsSelectedByOrganization> &
  Pick<ICassAddOnsSelectedByOrganization, 'compositeId'>;

type RestOf<T extends ICassAddOnsSelectedByOrganization | NewCassAddOnsSelectedByOrganization> = Omit<
  T,
  'arrivalDate' | 'createdTimeId' | 'departureDate' | 'addOnDetailsBigInt'
> & {
  compositeId: {
    arrivalDate?: number | null;
  };
  departureDate?: number | null;
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
};

export type RestCassAddOnsSelectedByOrganization = RestOf<ICassAddOnsSelectedByOrganization>;

export type NewRestCassAddOnsSelectedByOrganization = RestOf<NewCassAddOnsSelectedByOrganization>;

export type PartialUpdateRestCassAddOnsSelectedByOrganization = RestOf<PartialUpdateCassAddOnsSelectedByOrganization>;

export type EntityResponseType = HttpResponse<ICassAddOnsSelectedByOrganization>;
export type EntityArrayResponseType = HttpResponse<ICassAddOnsSelectedByOrganization[]>;

@Injectable()
export class CassAddOnsSelectedByOrganizationsService {
  readonly cassAddOnsSelectedByOrganizationsParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly cassAddOnsSelectedByOrganizationsResource = httpResource<RestCassAddOnsSelectedByOrganization[]>(() => {
    const params = this.cassAddOnsSelectedByOrganizationsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassAddOnsSelectedByOrganization that have been fetched.
   */
  readonly cassAddOnsSelectedByOrganizations = computed(() =>
    (this.cassAddOnsSelectedByOrganizationsResource.hasValue() ? this.cassAddOnsSelectedByOrganizationsResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor(
    'api/cass-add-ons-selected-by-organizations',
    'cassandrablog',
  );

  protected convertValueFromServer(
    restCassAddOnsSelectedByOrganization: RestCassAddOnsSelectedByOrganization,
  ): ICassAddOnsSelectedByOrganization {
    return {
      ...restCassAddOnsSelectedByOrganization,
      compositeId: {
        organizationId: restCassAddOnsSelectedByOrganization.compositeId.organizationId,
        arrivalDate: restCassAddOnsSelectedByOrganization.compositeId.arrivalDate
          ? dayjs(restCassAddOnsSelectedByOrganization.compositeId.arrivalDate)
          : null,

        accountNumber: restCassAddOnsSelectedByOrganization.compositeId.accountNumber,

        createdTimeId: restCassAddOnsSelectedByOrganization.compositeId.createdTimeId,
      },
      departureDate: restCassAddOnsSelectedByOrganization.departureDate ? dayjs(restCassAddOnsSelectedByOrganization.departureDate) : null,
      addOnDetailsBigInt: restCassAddOnsSelectedByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(Object.entries(restCassAddOnsSelectedByOrganization.addOnDetailsBigInt).map(([k, v]) => [k, dayjs(v)]))
        : {},
    };
  }
}

@Injectable({ providedIn: 'root' })
export class CassAddOnsSelectedByOrganizationService extends CassAddOnsSelectedByOrganizationsService {
  protected readonly http = inject(HttpClient);

  create(cassAddOnsSelectedByOrganization: NewCassAddOnsSelectedByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassAddOnsSelectedByOrganization);
    return this.http
      .post<RestCassAddOnsSelectedByOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cassAddOnsSelectedByOrganization: ICassAddOnsSelectedByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassAddOnsSelectedByOrganization);
    return this.http
      .put<RestCassAddOnsSelectedByOrganization>(
        `${this.resourceUrl}/${cassAddOnsSelectedByOrganization.compositeId.organizationId}/${copy.compositeId.arrivalDate}/${cassAddOnsSelectedByOrganization.compositeId.accountNumber}/${cassAddOnsSelectedByOrganization.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cassAddOnsSelectedByOrganization: PartialUpdateCassAddOnsSelectedByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassAddOnsSelectedByOrganization);
    return this.http
      .patch<RestCassAddOnsSelectedByOrganization>(
        `${this.resourceUrl}/${cassAddOnsSelectedByOrganization.compositeId.organizationId}/${copy.compositeId.arrivalDate}/${cassAddOnsSelectedByOrganization.compositeId.accountNumber}/${cassAddOnsSelectedByOrganization.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(organizationId: string, arrivalDate: number, accountNumber: string, createdTimeId: string): Observable<EntityResponseType> {
    const data = { organizationId, arrivalDate, accountNumber, createdTimeId };
    return this.http
      .get<RestCassAddOnsSelectedByOrganization>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassAddOnsSelectedByOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassAddOnsSelectedByOrganization[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdPageable(organizationId: string, req?: any): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable(
    organizationId: string,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('accountNumber', accountNumber);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('accountNumber', accountNumber);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable(
    organizationId: string,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable(
    organizationId: string,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable(
    organizationId: string,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable(
    organizationId: string,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('accountNumber', accountNumber);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('accountNumber', accountNumber);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('accountNumber', accountNumber);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('accountNumber', accountNumber);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<
        RestCassAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
  ): Observable<EntityResponseType> {
    let options = new HttpParams();
    options = options.set('organizationId', organizationId);
    options = options.set('arrivalDate', arrivalDate);
    options = options.set('accountNumber', accountNumber);
    options = options.set('createdTimeId', createdTimeId);
    return this.http
      .get<RestCassAddOnsSelectedByOrganization>(
        `${this.resourceUrl}/find-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id`,
        { params: options, observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  delete(cassAddOnsSelectedByOrganization: ICassAddOnsSelectedByOrganization): Observable<HttpResponse<{}>> {
    const copy = this.convertValueFromClient(cassAddOnsSelectedByOrganization);
    return this.http.delete(
      `${this.resourceUrl}/${cassAddOnsSelectedByOrganization.compositeId.organizationId}/${copy.compositeId.arrivalDate}/${cassAddOnsSelectedByOrganization.compositeId.accountNumber}/${cassAddOnsSelectedByOrganization.compositeId.createdTimeId}`,
      { observe: 'response' },
    );
  }

  getCassAddOnsSelectedByOrganizationIdentifier(
    cassAddOnsSelectedByOrganization: Pick<ICassAddOnsSelectedByOrganization, 'compositeId'>,
  ): ICassAddOnsSelectedByOrganizationId {
    return cassAddOnsSelectedByOrganization.compositeId;
  }
  compareCassAddOnsSelectedByOrganization(
    o1: Pick<ICassAddOnsSelectedByOrganization, 'compositeId'> | null,
    o2: Pick<ICassAddOnsSelectedByOrganization, 'compositeId'> | null,
  ): boolean {
    return o1 && o2
      ? this.getCassAddOnsSelectedByOrganizationIdentifier(o1) === this.getCassAddOnsSelectedByOrganizationIdentifier(o2)
      : o1 === o2;
  }
  addCassAddOnsSelectedByOrganizationToCollectionIfMissing<Type extends Pick<ICassAddOnsSelectedByOrganization, 'compositeId'>>(
    cassAddOnsSelectedByOrganizationCollection: Type[],
    ...cassAddOnsSelectedByOrganizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassAddOnsSelectedByOrganizations: Type[] = cassAddOnsSelectedByOrganizationsToCheck.filter(isPresent);
    if (cassAddOnsSelectedByOrganizations.length > 0) {
      const cassAddOnsSelectedByOrganizationCollectionIdentifiers = cassAddOnsSelectedByOrganizationCollection.map(
        cassAddOnsSelectedByOrganizationItem => this.getCassAddOnsSelectedByOrganizationIdentifier(cassAddOnsSelectedByOrganizationItem),
      );
      const cassAddOnsSelectedByOrganizationsToAdd = cassAddOnsSelectedByOrganizations.filter(cassAddOnsSelectedByOrganizationItem => {
        const cassAddOnsSelectedByOrganizationIdentifier = this.getCassAddOnsSelectedByOrganizationIdentifier(
          cassAddOnsSelectedByOrganizationItem,
        );
        if (cassAddOnsSelectedByOrganizationCollectionIdentifiers.includes(cassAddOnsSelectedByOrganizationIdentifier)) {
          return false;
        }
        cassAddOnsSelectedByOrganizationCollectionIdentifiers.push(cassAddOnsSelectedByOrganizationIdentifier);
        return true;
      });
      return [...cassAddOnsSelectedByOrganizationsToAdd, ...cassAddOnsSelectedByOrganizationCollection];
    }
    return cassAddOnsSelectedByOrganizationCollection;
  }

  protected convertValueFromClient<
    T extends ICassAddOnsSelectedByOrganization | NewCassAddOnsSelectedByOrganization | PartialUpdateCassAddOnsSelectedByOrganization,
  >(cassAddOnsSelectedByOrganization: T): RestOf<T> {
    return {
      ...cassAddOnsSelectedByOrganization,
      compositeId: {
        ...cassAddOnsSelectedByOrganization.compositeId,

        arrivalDate: cassAddOnsSelectedByOrganization.compositeId.arrivalDate
          ? cassAddOnsSelectedByOrganization.compositeId.arrivalDate.valueOf()
          : null,
      },
      departureDate: cassAddOnsSelectedByOrganization.departureDate ? cassAddOnsSelectedByOrganization.departureDate.valueOf() : null,
      /* eslint-disable @typescript-eslint/no-unnecessary-condition */
      addOnDetailsBigInt: cassAddOnsSelectedByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(Object.entries(cassAddOnsSelectedByOrganization.addOnDetailsBigInt).map(([k, v]) => [k, v?.valueOf()]))
        : {},
      /* eslint-enable @typescript-eslint/no-unnecessary-condition */
    } as RestOf<T>;
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestCassAddOnsSelectedByOrganization>,
  ): HttpResponse<ICassAddOnsSelectedByOrganization> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestCassAddOnsSelectedByOrganization[]>,
  ): HttpResponse<ICassAddOnsSelectedByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
