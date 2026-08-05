import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassSetEntityByOrganization, NewCassSetEntityByOrganization } from '../cass-set-entity-by-organization.model';
export type PartialUpdateCassSetEntityByOrganization = Partial<ICassSetEntityByOrganization> &
  Pick<ICassSetEntityByOrganization, 'organizationId'>;

type RestOf<T extends ICassSetEntityByOrganization | NewCassSetEntityByOrganization> = Omit<T, 'tags'> & {
  tags?: Set<string> | null;
};

export type RestCassSetEntityByOrganization = RestOf<ICassSetEntityByOrganization>;

export type NewRestCassSetEntityByOrganization = RestOf<NewCassSetEntityByOrganization>;

export type PartialUpdateRestCassSetEntityByOrganization = RestOf<PartialUpdateCassSetEntityByOrganization>;

export type EntityResponseType = HttpResponse<ICassSetEntityByOrganization>;
export type EntityArrayResponseType = HttpResponse<ICassSetEntityByOrganization[]>;

@Injectable()
export class CassSetEntityByOrganizationsService {
  readonly cassSetEntityByOrganizationsParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly cassSetEntityByOrganizationsResource = httpResource<RestCassSetEntityByOrganization[]>(() => {
    const params = this.cassSetEntityByOrganizationsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassSetEntityByOrganization that have been fetched.
   */
  readonly cassSetEntityByOrganizations = computed(() =>
    (this.cassSetEntityByOrganizationsResource.hasValue() ? this.cassSetEntityByOrganizationsResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-set-entity-by-organizations', 'cassandrablog');

  protected convertValueFromServer(restCassSetEntityByOrganization: RestCassSetEntityByOrganization): ICassSetEntityByOrganization {
    return {
      ...restCassSetEntityByOrganization,
      tags: restCassSetEntityByOrganization.tags ? new Set(restCassSetEntityByOrganization.tags) : new Set<string>(),
    };
  }
}

@Injectable({ providedIn: 'root' })
export class CassSetEntityByOrganizationService extends CassSetEntityByOrganizationsService {
  protected readonly http = inject(HttpClient);

  create(cassSetEntityByOrganization: NewCassSetEntityByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassSetEntityByOrganization);
    return this.http
      .post<RestCassSetEntityByOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cassSetEntityByOrganization: ICassSetEntityByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassSetEntityByOrganization);
    return this.http
      .put<RestCassSetEntityByOrganization>(
        `${this.resourceUrl}/${this.getCassSetEntityByOrganizationIdentifier(cassSetEntityByOrganization)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cassSetEntityByOrganization: PartialUpdateCassSetEntityByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassSetEntityByOrganization);
    return this.http
      .patch<RestCassSetEntityByOrganization>(
        `${this.resourceUrl}/${this.getCassSetEntityByOrganizationIdentifier(cassSetEntityByOrganization)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCassSetEntityByOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassSetEntityByOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassSetEntityByOrganization[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCassSetEntityByOrganizationIdentifier(cassSetEntityByOrganization: Pick<ICassSetEntityByOrganization, 'organizationId'>): string {
    return cassSetEntityByOrganization.organizationId;
  }

  compareCassSetEntityByOrganization(
    o1: Pick<ICassSetEntityByOrganization, 'organizationId'> | null,
    o2: Pick<ICassSetEntityByOrganization, 'organizationId'> | null,
  ): boolean {
    return o1 && o2 ? this.getCassSetEntityByOrganizationIdentifier(o1) === this.getCassSetEntityByOrganizationIdentifier(o2) : o1 === o2;
  }

  addCassSetEntityByOrganizationToCollectionIfMissing<Type extends Pick<ICassSetEntityByOrganization, 'organizationId'>>(
    cassSetEntityByOrganizationCollection: Type[],
    ...cassSetEntityByOrganizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassSetEntityByOrganizations: Type[] = cassSetEntityByOrganizationsToCheck.filter(isPresent);
    if (cassSetEntityByOrganizations.length > 0) {
      const cassSetEntityByOrganizationCollectionIdentifiers = cassSetEntityByOrganizationCollection.map(cassSetEntityByOrganizationItem =>
        this.getCassSetEntityByOrganizationIdentifier(cassSetEntityByOrganizationItem),
      );
      const cassSetEntityByOrganizationsToAdd = cassSetEntityByOrganizations.filter(cassSetEntityByOrganizationItem => {
        const cassSetEntityByOrganizationIdentifier = this.getCassSetEntityByOrganizationIdentifier(cassSetEntityByOrganizationItem);
        if (cassSetEntityByOrganizationCollectionIdentifiers.includes(cassSetEntityByOrganizationIdentifier)) {
          return false;
        }
        cassSetEntityByOrganizationCollectionIdentifiers.push(cassSetEntityByOrganizationIdentifier);
        return true;
      });
      return [...cassSetEntityByOrganizationsToAdd, ...cassSetEntityByOrganizationCollection];
    }
    return cassSetEntityByOrganizationCollection;
  }

  protected convertValueFromClient<
    T extends ICassSetEntityByOrganization | NewCassSetEntityByOrganization | PartialUpdateCassSetEntityByOrganization,
  >(cassSetEntityByOrganization: T): RestOf<T> {
    return {
      ...cassSetEntityByOrganization,
      tags: cassSetEntityByOrganization.tags ? Array.from(cassSetEntityByOrganization.tags) : [],
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestCassSetEntityByOrganization>): HttpResponse<ICassSetEntityByOrganization> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestCassSetEntityByOrganization[]>,
  ): HttpResponse<ICassSetEntityByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
