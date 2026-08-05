import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IPsqlTajUser, NewPsqlTajUser } from '../psql-taj-user.model';

export type PartialUpdatePsqlTajUser = Partial<IPsqlTajUser> & Pick<IPsqlTajUser, 'id'>;

@Injectable()
export class PsqlTajUsersService {
  readonly psqlTajUsersParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly psqlTajUsersResource = httpResource<IPsqlTajUser[]>(() => {
    const params = this.psqlTajUsersParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of psqlTajUser that have been fetched. It is updated when the psqlTajUsersResource emits a new value.
   * In case of error while fetching the psqlTajUsers, the signal is set to an empty array.
   */
  readonly psqlTajUsers = computed(() => (this.psqlTajUsersResource.hasValue() ? this.psqlTajUsersResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/psql-taj-users', 'psqlblog');
}

@Injectable({ providedIn: 'root' })
export class PsqlTajUserService extends PsqlTajUsersService {
  protected readonly http = inject(HttpClient);

  create(psqlTajUser: NewPsqlTajUser): Observable<IPsqlTajUser> {
    return this.http.post<IPsqlTajUser>(this.resourceUrl, psqlTajUser);
  }

  update(psqlTajUser: IPsqlTajUser): Observable<IPsqlTajUser> {
    return this.http.put<IPsqlTajUser>(
      `${this.resourceUrl}/${encodeURIComponent(this.getPsqlTajUserIdentifier(psqlTajUser))}`,
      psqlTajUser,
    );
  }

  partialUpdate(psqlTajUser: PartialUpdatePsqlTajUser): Observable<IPsqlTajUser> {
    return this.http.patch<IPsqlTajUser>(
      `${this.resourceUrl}/${encodeURIComponent(this.getPsqlTajUserIdentifier(psqlTajUser))}`,
      psqlTajUser,
    );
  }

  find(id: string): Observable<IPsqlTajUser> {
    return this.http.get<IPsqlTajUser>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<IPsqlTajUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IPsqlTajUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getPsqlTajUserIdentifier(psqlTajUser: Pick<IPsqlTajUser, 'id'>): string {
    return psqlTajUser.id;
  }

  comparePsqlTajUser(o1: Pick<IPsqlTajUser, 'id'> | null, o2: Pick<IPsqlTajUser, 'id'> | null): boolean {
    return o1 && o2 ? this.getPsqlTajUserIdentifier(o1) === this.getPsqlTajUserIdentifier(o2) : o1 === o2;
  }

  addPsqlTajUserToCollectionIfMissing<Type extends Pick<IPsqlTajUser, 'id'>>(
    psqlTajUserCollection: Type[],
    ...psqlTajUsersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const psqlTajUsers: Type[] = psqlTajUsersToCheck.filter(isPresent);
    if (psqlTajUsers.length > 0) {
      const psqlTajUserCollectionIdentifiers = psqlTajUserCollection.map(psqlTajUserItem => this.getPsqlTajUserIdentifier(psqlTajUserItem));
      const psqlTajUsersToAdd = psqlTajUsers.filter(psqlTajUserItem => {
        const psqlTajUserIdentifier = this.getPsqlTajUserIdentifier(psqlTajUserItem);
        if (psqlTajUserCollectionIdentifiers.includes(psqlTajUserIdentifier)) {
          return false;
        }
        psqlTajUserCollectionIdentifiers.push(psqlTajUserIdentifier);
        return true;
      });
      return [...psqlTajUsersToAdd, ...psqlTajUserCollection];
    }
    return psqlTajUserCollection;
  }
}
