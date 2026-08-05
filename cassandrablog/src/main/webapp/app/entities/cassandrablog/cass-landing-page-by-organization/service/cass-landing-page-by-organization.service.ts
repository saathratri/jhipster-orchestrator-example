import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassLandingPageByOrganization, NewCassLandingPageByOrganization } from '../cass-landing-page-by-organization.model';
export type PartialUpdateCassLandingPageByOrganization = Partial<ICassLandingPageByOrganization> &
  Pick<ICassLandingPageByOrganization, 'organizationId'>;

type RestOf<T extends ICassLandingPageByOrganization | NewCassLandingPageByOrganization> = Omit<T, 'detailsBigInt'> & {
  detailsText?: Record<string, string> | null;
  detailsDecimal?: Record<string, number> | null;
  detailsBoolean?: Record<string, boolean> | null;
  detailsBigInt?: Record<string, dayjs.Dayjs> | null;
};

export type RestCassLandingPageByOrganization = RestOf<ICassLandingPageByOrganization>;

export type NewRestCassLandingPageByOrganization = RestOf<NewCassLandingPageByOrganization>;

export type PartialUpdateRestCassLandingPageByOrganization = RestOf<PartialUpdateCassLandingPageByOrganization>;

export type EntityResponseType = HttpResponse<ICassLandingPageByOrganization>;
export type EntityArrayResponseType = HttpResponse<ICassLandingPageByOrganization[]>;

@Injectable()
export class CassLandingPageByOrganizationsService {
  readonly cassLandingPageByOrganizationsParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly cassLandingPageByOrganizationsResource = httpResource<RestCassLandingPageByOrganization[]>(() => {
    const params = this.cassLandingPageByOrganizationsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassLandingPageByOrganization that have been fetched.
   */
  readonly cassLandingPageByOrganizations = computed(() =>
    (this.cassLandingPageByOrganizationsResource.hasValue() ? this.cassLandingPageByOrganizationsResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-landing-page-by-organizations', 'cassandrablog');

  protected convertValueFromServer(restCassLandingPageByOrganization: RestCassLandingPageByOrganization): ICassLandingPageByOrganization {
    return {
      ...restCassLandingPageByOrganization,
      detailsBigInt: restCassLandingPageByOrganization.detailsBigInt
        ? Object.fromEntries(Object.entries(restCassLandingPageByOrganization.detailsBigInt).map(([k, v]) => [k, dayjs(v)]))
        : {},
    };
  }
}

@Injectable({ providedIn: 'root' })
export class CassLandingPageByOrganizationService extends CassLandingPageByOrganizationsService {
  protected readonly http = inject(HttpClient);

  create(cassLandingPageByOrganization: NewCassLandingPageByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassLandingPageByOrganization);
    return this.http
      .post<RestCassLandingPageByOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cassLandingPageByOrganization: ICassLandingPageByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassLandingPageByOrganization);
    return this.http
      .put<RestCassLandingPageByOrganization>(
        `${this.resourceUrl}/${this.getCassLandingPageByOrganizationIdentifier(cassLandingPageByOrganization)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cassLandingPageByOrganization: PartialUpdateCassLandingPageByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassLandingPageByOrganization);
    return this.http
      .patch<RestCassLandingPageByOrganization>(
        `${this.resourceUrl}/${this.getCassLandingPageByOrganizationIdentifier(cassLandingPageByOrganization)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCassLandingPageByOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassLandingPageByOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassLandingPageByOrganization[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCassLandingPageByOrganizationIdentifier(
    cassLandingPageByOrganization: Pick<ICassLandingPageByOrganization, 'organizationId'>,
  ): string {
    return cassLandingPageByOrganization.organizationId;
  }

  compareCassLandingPageByOrganization(
    o1: Pick<ICassLandingPageByOrganization, 'organizationId'> | null,
    o2: Pick<ICassLandingPageByOrganization, 'organizationId'> | null,
  ): boolean {
    return o1 && o2
      ? this.getCassLandingPageByOrganizationIdentifier(o1) === this.getCassLandingPageByOrganizationIdentifier(o2)
      : o1 === o2;
  }

  addCassLandingPageByOrganizationToCollectionIfMissing<Type extends Pick<ICassLandingPageByOrganization, 'organizationId'>>(
    cassLandingPageByOrganizationCollection: Type[],
    ...cassLandingPageByOrganizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassLandingPageByOrganizations: Type[] = cassLandingPageByOrganizationsToCheck.filter(isPresent);
    if (cassLandingPageByOrganizations.length > 0) {
      const cassLandingPageByOrganizationCollectionIdentifiers = cassLandingPageByOrganizationCollection.map(
        cassLandingPageByOrganizationItem => this.getCassLandingPageByOrganizationIdentifier(cassLandingPageByOrganizationItem),
      );
      const cassLandingPageByOrganizationsToAdd = cassLandingPageByOrganizations.filter(cassLandingPageByOrganizationItem => {
        const cassLandingPageByOrganizationIdentifier = this.getCassLandingPageByOrganizationIdentifier(cassLandingPageByOrganizationItem);
        if (cassLandingPageByOrganizationCollectionIdentifiers.includes(cassLandingPageByOrganizationIdentifier)) {
          return false;
        }
        cassLandingPageByOrganizationCollectionIdentifiers.push(cassLandingPageByOrganizationIdentifier);
        return true;
      });
      return [...cassLandingPageByOrganizationsToAdd, ...cassLandingPageByOrganizationCollection];
    }
    return cassLandingPageByOrganizationCollection;
  }

  protected convertValueFromClient<
    T extends ICassLandingPageByOrganization | NewCassLandingPageByOrganization | PartialUpdateCassLandingPageByOrganization,
  >(cassLandingPageByOrganization: T): RestOf<T> {
    return {
      ...cassLandingPageByOrganization,
      /* eslint-disable @typescript-eslint/no-unnecessary-condition */
      detailsBigInt: cassLandingPageByOrganization.detailsBigInt
        ? Object.fromEntries(Object.entries(cassLandingPageByOrganization.detailsBigInt).map(([k, v]) => [k, v?.valueOf()]))
        : {},
      /* eslint-enable @typescript-eslint/no-unnecessary-condition */
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestCassLandingPageByOrganization>): HttpResponse<ICassLandingPageByOrganization> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestCassLandingPageByOrganization[]>,
  ): HttpResponse<ICassLandingPageByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
