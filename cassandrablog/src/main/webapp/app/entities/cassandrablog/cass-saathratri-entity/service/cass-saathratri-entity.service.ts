import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassSaathratriEntity, NewCassSaathratriEntity } from '../cass-saathratri-entity.model';
export type PartialUpdateCassSaathratriEntity = Partial<ICassSaathratriEntity> & Pick<ICassSaathratriEntity, 'entityId'>;

export type EntityResponseType = HttpResponse<ICassSaathratriEntity>;
export type EntityArrayResponseType = HttpResponse<ICassSaathratriEntity[]>;

@Injectable()
export class CassSaathratriEntitiesService {
  readonly cassSaathratriEntitiesParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly cassSaathratriEntitiesResource = httpResource<ICassSaathratriEntity[]>(() => {
    const params = this.cassSaathratriEntitiesParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassSaathratriEntity that have been fetched.
   */
  readonly cassSaathratriEntities = computed(() =>
    this.cassSaathratriEntitiesResource.hasValue() ? this.cassSaathratriEntitiesResource.value() : [],
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-saathratri-entities', 'cassandrablog');
}

@Injectable({ providedIn: 'root' })
export class CassSaathratriEntityService extends CassSaathratriEntitiesService {
  protected readonly http = inject(HttpClient);

  create(cassSaathratriEntity: NewCassSaathratriEntity): Observable<EntityResponseType> {
    return this.http.post<ICassSaathratriEntity>(this.resourceUrl, cassSaathratriEntity, { observe: 'response' });
  }

  update(cassSaathratriEntity: ICassSaathratriEntity): Observable<EntityResponseType> {
    return this.http.put<ICassSaathratriEntity>(
      `${this.resourceUrl}/${this.getCassSaathratriEntityIdentifier(cassSaathratriEntity)}`,
      cassSaathratriEntity,
      { observe: 'response' },
    );
  }

  partialUpdate(cassSaathratriEntity: PartialUpdateCassSaathratriEntity): Observable<EntityResponseType> {
    return this.http.patch<ICassSaathratriEntity>(
      `${this.resourceUrl}/${this.getCassSaathratriEntityIdentifier(cassSaathratriEntity)}`,
      cassSaathratriEntity,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICassSaathratriEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICassSaathratriEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICassSaathratriEntity[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCassSaathratriEntityIdentifier(cassSaathratriEntity: Pick<ICassSaathratriEntity, 'entityId'>): string {
    return cassSaathratriEntity.entityId;
  }

  compareCassSaathratriEntity(
    o1: Pick<ICassSaathratriEntity, 'entityId'> | null,
    o2: Pick<ICassSaathratriEntity, 'entityId'> | null,
  ): boolean {
    return o1 && o2 ? this.getCassSaathratriEntityIdentifier(o1) === this.getCassSaathratriEntityIdentifier(o2) : o1 === o2;
  }

  addCassSaathratriEntityToCollectionIfMissing<Type extends Pick<ICassSaathratriEntity, 'entityId'>>(
    cassSaathratriEntityCollection: Type[],
    ...cassSaathratriEntitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassSaathratriEntities: Type[] = cassSaathratriEntitiesToCheck.filter(isPresent);
    if (cassSaathratriEntities.length > 0) {
      const cassSaathratriEntityCollectionIdentifiers = cassSaathratriEntityCollection.map(cassSaathratriEntityItem =>
        this.getCassSaathratriEntityIdentifier(cassSaathratriEntityItem),
      );
      const cassSaathratriEntitiesToAdd = cassSaathratriEntities.filter(cassSaathratriEntityItem => {
        const cassSaathratriEntityIdentifier = this.getCassSaathratriEntityIdentifier(cassSaathratriEntityItem);
        if (cassSaathratriEntityCollectionIdentifiers.includes(cassSaathratriEntityIdentifier)) {
          return false;
        }
        cassSaathratriEntityCollectionIdentifiers.push(cassSaathratriEntityIdentifier);
        return true;
      });
      return [...cassSaathratriEntitiesToAdd, ...cassSaathratriEntityCollection];
    }
    return cassSaathratriEntityCollection;
  }
}
