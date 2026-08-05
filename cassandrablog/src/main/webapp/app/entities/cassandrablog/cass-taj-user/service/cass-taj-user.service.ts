import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassTajUser, NewCassTajUser } from '../cass-taj-user.model';
export type PartialUpdateCassTajUser = Partial<ICassTajUser> & Pick<ICassTajUser, 'id'>;

export type EntityResponseType = HttpResponse<ICassTajUser>;
export type EntityArrayResponseType = HttpResponse<ICassTajUser[]>;

@Injectable()
export class CassTajUsersService {
  readonly cassTajUsersParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly cassTajUsersResource = httpResource<ICassTajUser[]>(() => {
    const params = this.cassTajUsersParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassTajUser that have been fetched.
   */
  readonly cassTajUsers = computed(() => (this.cassTajUsersResource.hasValue() ? this.cassTajUsersResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-taj-users', 'cassandrablog');
}

@Injectable({ providedIn: 'root' })
export class CassTajUserService extends CassTajUsersService {
  protected readonly http = inject(HttpClient);

  create(cassTajUser: NewCassTajUser): Observable<EntityResponseType> {
    return this.http.post<ICassTajUser>(this.resourceUrl, cassTajUser, { observe: 'response' });
  }

  update(cassTajUser: ICassTajUser): Observable<EntityResponseType> {
    return this.http.put<ICassTajUser>(`${this.resourceUrl}/${this.getCassTajUserIdentifier(cassTajUser)}`, cassTajUser, {
      observe: 'response',
    });
  }

  partialUpdate(cassTajUser: PartialUpdateCassTajUser): Observable<EntityResponseType> {
    return this.http.patch<ICassTajUser>(`${this.resourceUrl}/${this.getCassTajUserIdentifier(cassTajUser)}`, cassTajUser, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICassTajUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICassTajUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICassTajUser[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCassTajUserIdentifier(cassTajUser: Pick<ICassTajUser, 'id'>): string {
    return cassTajUser.id;
  }

  compareCassTajUser(o1: Pick<ICassTajUser, 'id'> | null, o2: Pick<ICassTajUser, 'id'> | null): boolean {
    return o1 && o2 ? this.getCassTajUserIdentifier(o1) === this.getCassTajUserIdentifier(o2) : o1 === o2;
  }

  addCassTajUserToCollectionIfMissing<Type extends Pick<ICassTajUser, 'id'>>(
    cassTajUserCollection: Type[],
    ...cassTajUsersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassTajUsers: Type[] = cassTajUsersToCheck.filter(isPresent);
    if (cassTajUsers.length > 0) {
      const cassTajUserCollectionIdentifiers = cassTajUserCollection.map(cassTajUserItem => this.getCassTajUserIdentifier(cassTajUserItem));
      const cassTajUsersToAdd = cassTajUsers.filter(cassTajUserItem => {
        const cassTajUserIdentifier = this.getCassTajUserIdentifier(cassTajUserItem);
        if (cassTajUserCollectionIdentifiers.includes(cassTajUserIdentifier)) {
          return false;
        }
        cassTajUserCollectionIdentifiers.push(cassTajUserIdentifier);
        return true;
      });
      return [...cassTajUsersToAdd, ...cassTajUserCollection];
    }
    return cassTajUserCollection;
  }
}
