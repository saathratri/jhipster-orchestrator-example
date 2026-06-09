import { HttpClient, HttpParams, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import {
  ICassAddOnsAvailableByOrganization,
  ICassAddOnsAvailableByOrganizationId,
  NewCassAddOnsAvailableByOrganization,
} from '../cass-add-ons-available-by-organization.model';
export type PartialUpdateCassAddOnsAvailableByOrganization = Partial<ICassAddOnsAvailableByOrganization> &
  Pick<ICassAddOnsAvailableByOrganization, 'compositeId'>;

type RestOf<T extends ICassAddOnsAvailableByOrganization | NewCassAddOnsAvailableByOrganization> = Omit<T, 'addOnDetailsBigInt'> & {
  compositeId: {};
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
};

export type RestCassAddOnsAvailableByOrganization = RestOf<ICassAddOnsAvailableByOrganization>;

export type NewRestCassAddOnsAvailableByOrganization = RestOf<NewCassAddOnsAvailableByOrganization>;

export type PartialUpdateRestCassAddOnsAvailableByOrganization = RestOf<PartialUpdateCassAddOnsAvailableByOrganization>;

export type EntityResponseType = HttpResponse<ICassAddOnsAvailableByOrganization>;
export type EntityArrayResponseType = HttpResponse<ICassAddOnsAvailableByOrganization[]>;

@Injectable()
export class CassAddOnsAvailableByOrganizationsService {
  readonly cassAddOnsAvailableByOrganizationsParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly cassAddOnsAvailableByOrganizationsResource = httpResource<RestCassAddOnsAvailableByOrganization[]>(() => {
    const params = this.cassAddOnsAvailableByOrganizationsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassAddOnsAvailableByOrganization that have been fetched.
   */
  readonly cassAddOnsAvailableByOrganizations = computed(() =>
    (this.cassAddOnsAvailableByOrganizationsResource.hasValue() ? this.cassAddOnsAvailableByOrganizationsResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor(
    'api/cass-add-ons-available-by-organizations',
    'cassandrablog',
  );

  protected convertValueFromServer(
    restCassAddOnsAvailableByOrganization: RestCassAddOnsAvailableByOrganization,
  ): ICassAddOnsAvailableByOrganization {
    return {
      ...restCassAddOnsAvailableByOrganization,
      compositeId: {
        organizationId: restCassAddOnsAvailableByOrganization.compositeId.organizationId,

        entityType: restCassAddOnsAvailableByOrganization.compositeId.entityType,

        entityId: restCassAddOnsAvailableByOrganization.compositeId.entityId,

        addOnId: restCassAddOnsAvailableByOrganization.compositeId.addOnId,
      },
      addOnDetailsBigInt: restCassAddOnsAvailableByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(Object.entries(restCassAddOnsAvailableByOrganization.addOnDetailsBigInt).map(([k, v]) => [k, dayjs(v)]))
        : {},
    };
  }
}

@Injectable({ providedIn: 'root' })
export class CassAddOnsAvailableByOrganizationService extends CassAddOnsAvailableByOrganizationsService {
  protected readonly http = inject(HttpClient);

  create(cassAddOnsAvailableByOrganization: NewCassAddOnsAvailableByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassAddOnsAvailableByOrganization);
    return this.http
      .post<RestCassAddOnsAvailableByOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassAddOnsAvailableByOrganization);
    return this.http
      .put<RestCassAddOnsAvailableByOrganization>(
        `${this.resourceUrl}/${cassAddOnsAvailableByOrganization.compositeId.organizationId}/${cassAddOnsAvailableByOrganization.compositeId.entityType}/${cassAddOnsAvailableByOrganization.compositeId.entityId}/${cassAddOnsAvailableByOrganization.compositeId.addOnId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cassAddOnsAvailableByOrganization: PartialUpdateCassAddOnsAvailableByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassAddOnsAvailableByOrganization);
    return this.http
      .patch<RestCassAddOnsAvailableByOrganization>(
        `${this.resourceUrl}/${cassAddOnsAvailableByOrganization.compositeId.organizationId}/${cassAddOnsAvailableByOrganization.compositeId.entityType}/${cassAddOnsAvailableByOrganization.compositeId.entityId}/${cassAddOnsAvailableByOrganization.compositeId.addOnId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(organizationId: string, entityType: string, entityId: string, addOnId: string): Observable<EntityResponseType> {
    const data = { organizationId, entityType, entityId, addOnId };
    return this.http
      .get<RestCassAddOnsAvailableByOrganization>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassAddOnsAvailableByOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassAddOnsAvailableByOrganization[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdPageable(organizationId: string, req?: any): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    return this.http
      .get<
        RestCassAddOnsAvailableByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable(
    organizationId: string,
    entityType: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('entityType', entityType);
    return this.http
      .get<
        RestCassAddOnsAvailableByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-entity-type-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable(
    organizationId: string,
    entityType: string,
    entityId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('entityType', entityType);
    options = options.set('entityId', entityId);
    return this.http
      .get<
        RestCassAddOnsAvailableByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnIdPageable(
    organizationId: string,
    entityType: string,
    entityId: string,
    addOnId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('entityType', entityType);
    options = options.set('entityId', entityId);
    options = options.set('addOnId', addOnId);
    return this.http
      .get<
        RestCassAddOnsAvailableByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-and-composite-id-add-on-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
    organizationId: string,
    entityType: string,
    entityId: string,
    addOnId: string,
  ): Observable<EntityResponseType> {
    let options = new HttpParams();
    options = options.set('organizationId', organizationId);
    options = options.set('entityType', entityType);
    options = options.set('entityId', entityId);
    options = options.set('addOnId', addOnId);
    return this.http
      .get<RestCassAddOnsAvailableByOrganization>(
        `${this.resourceUrl}/find-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-and-composite-id-add-on-id`,
        { params: options, observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  delete(cassAddOnsAvailableByOrganization: ICassAddOnsAvailableByOrganization): Observable<HttpResponse<{}>> {
    const copy = this.convertValueFromClient(cassAddOnsAvailableByOrganization);
    return this.http.delete(
      `${this.resourceUrl}/${cassAddOnsAvailableByOrganization.compositeId.organizationId}/${cassAddOnsAvailableByOrganization.compositeId.entityType}/${cassAddOnsAvailableByOrganization.compositeId.entityId}/${cassAddOnsAvailableByOrganization.compositeId.addOnId}`,
      { observe: 'response' },
    );
  }

  getCassAddOnsAvailableByOrganizationIdentifier(
    cassAddOnsAvailableByOrganization: Pick<ICassAddOnsAvailableByOrganization, 'compositeId'>,
  ): ICassAddOnsAvailableByOrganizationId {
    return cassAddOnsAvailableByOrganization.compositeId;
  }
  compareCassAddOnsAvailableByOrganization(
    o1: Pick<ICassAddOnsAvailableByOrganization, 'compositeId'> | null,
    o2: Pick<ICassAddOnsAvailableByOrganization, 'compositeId'> | null,
  ): boolean {
    return o1 && o2
      ? this.getCassAddOnsAvailableByOrganizationIdentifier(o1) === this.getCassAddOnsAvailableByOrganizationIdentifier(o2)
      : o1 === o2;
  }
  addCassAddOnsAvailableByOrganizationToCollectionIfMissing<Type extends Pick<ICassAddOnsAvailableByOrganization, 'compositeId'>>(
    cassAddOnsAvailableByOrganizationCollection: Type[],
    ...cassAddOnsAvailableByOrganizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassAddOnsAvailableByOrganizations: Type[] = cassAddOnsAvailableByOrganizationsToCheck.filter(isPresent);
    if (cassAddOnsAvailableByOrganizations.length > 0) {
      const cassAddOnsAvailableByOrganizationCollectionIdentifiers = cassAddOnsAvailableByOrganizationCollection.map(
        cassAddOnsAvailableByOrganizationItem => this.getCassAddOnsAvailableByOrganizationIdentifier(cassAddOnsAvailableByOrganizationItem),
      );
      const cassAddOnsAvailableByOrganizationsToAdd = cassAddOnsAvailableByOrganizations.filter(cassAddOnsAvailableByOrganizationItem => {
        const cassAddOnsAvailableByOrganizationIdentifier = this.getCassAddOnsAvailableByOrganizationIdentifier(
          cassAddOnsAvailableByOrganizationItem,
        );
        if (cassAddOnsAvailableByOrganizationCollectionIdentifiers.includes(cassAddOnsAvailableByOrganizationIdentifier)) {
          return false;
        }
        cassAddOnsAvailableByOrganizationCollectionIdentifiers.push(cassAddOnsAvailableByOrganizationIdentifier);
        return true;
      });
      return [...cassAddOnsAvailableByOrganizationsToAdd, ...cassAddOnsAvailableByOrganizationCollection];
    }
    return cassAddOnsAvailableByOrganizationCollection;
  }

  protected convertValueFromClient<
    T extends ICassAddOnsAvailableByOrganization | NewCassAddOnsAvailableByOrganization | PartialUpdateCassAddOnsAvailableByOrganization,
  >(cassAddOnsAvailableByOrganization: T): RestOf<T> {
    return {
      ...cassAddOnsAvailableByOrganization,
      compositeId: {
        ...cassAddOnsAvailableByOrganization.compositeId,
      },
      /* eslint-disable @typescript-eslint/no-unnecessary-condition */
      addOnDetailsBigInt: cassAddOnsAvailableByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(Object.entries(cassAddOnsAvailableByOrganization.addOnDetailsBigInt).map(([k, v]) => [k, v?.valueOf()]))
        : {},
      /* eslint-enable @typescript-eslint/no-unnecessary-condition */
    } as RestOf<T>;
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestCassAddOnsAvailableByOrganization>,
  ): HttpResponse<ICassAddOnsAvailableByOrganization> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestCassAddOnsAvailableByOrganization[]>,
  ): HttpResponse<ICassAddOnsAvailableByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
