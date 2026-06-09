import { HttpClient, HttpParams, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassSaathratriEntity4, ICassSaathratriEntity4Id, NewCassSaathratriEntity4 } from '../cass-saathratri-entity-4.model';
export type PartialUpdateCassSaathratriEntity4 = Partial<ICassSaathratriEntity4> & Pick<ICassSaathratriEntity4, 'compositeId'>;

export type EntityResponseType = HttpResponse<ICassSaathratriEntity4>;
export type EntityArrayResponseType = HttpResponse<ICassSaathratriEntity4[]>;

@Injectable()
export class CassSaathratriEntity4sService {
  readonly cassSaathratriEntity4sParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly cassSaathratriEntity4sResource = httpResource<ICassSaathratriEntity4[]>(() => {
    const params = this.cassSaathratriEntity4sParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassSaathratriEntity4 that have been fetched.
   */
  readonly cassSaathratriEntity4s = computed(() =>
    this.cassSaathratriEntity4sResource.hasValue() ? this.cassSaathratriEntity4sResource.value() : [],
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-saathratri-entity-4-s', 'cassandrablog');
}

@Injectable({ providedIn: 'root' })
export class CassSaathratriEntity4Service extends CassSaathratriEntity4sService {
  protected readonly http = inject(HttpClient);

  create(cassSaathratriEntity4: NewCassSaathratriEntity4): Observable<EntityResponseType> {
    return this.http.post<ICassSaathratriEntity4>(this.resourceUrl, cassSaathratriEntity4, { observe: 'response' });
  }

  update(cassSaathratriEntity4: ICassSaathratriEntity4): Observable<EntityResponseType> {
    return this.http.put<ICassSaathratriEntity4>(
      `${this.resourceUrl}/${cassSaathratriEntity4.compositeId.organizationId}/${cassSaathratriEntity4.compositeId.attributeKey}`,
      cassSaathratriEntity4,
      { observe: 'response' },
    );
  }

  partialUpdate(cassSaathratriEntity4: PartialUpdateCassSaathratriEntity4): Observable<EntityResponseType> {
    return this.http.patch<ICassSaathratriEntity4>(
      `${this.resourceUrl}/${cassSaathratriEntity4.compositeId.organizationId}/${cassSaathratriEntity4.compositeId.attributeKey}`,
      cassSaathratriEntity4,
      { observe: 'response' },
    );
  }

  find(organizationId: string, attributeKey: string): Observable<EntityResponseType> {
    const data = { organizationId, attributeKey };
    return this.http.get<ICassSaathratriEntity4>(`${this.resourceUrl}/get`, { params: data, observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICassSaathratriEntity4[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICassSaathratriEntity4[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' });
  }

  findAllByCompositeIdOrganizationIdPageable(organizationId: string, req?: any): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    return this.http.get<ICassSaathratriEntity4[]>(`${this.resourceUrl}/find-all-by-composite-id-organization-id-pageable`, {
      params: options,
      observe: 'response',
    });
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdAttributeKeyPageable(
    organizationId: string,
    attributeKey: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', organizationId);
    options = options.set('attributeKey', attributeKey);
    return this.http.get<ICassSaathratriEntity4[]>(
      `${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-attribute-key-pageable`,
      { params: options, observe: 'response' },
    );
  }

  findByCompositeIdOrganizationIdAndCompositeIdAttributeKey(organizationId: string, attributeKey: string): Observable<EntityResponseType> {
    let options = new HttpParams();
    options = options.set('organizationId', organizationId);
    options = options.set('attributeKey', attributeKey);
    return this.http.get<ICassSaathratriEntity4>(
      `${this.resourceUrl}/find-by-composite-id-organization-id-and-composite-id-attribute-key`,
      { params: options, observe: 'response' },
    );
  }

  delete(cassSaathratriEntity4: ICassSaathratriEntity4): Observable<HttpResponse<{}>> {
    return this.http.delete(
      `${this.resourceUrl}/${cassSaathratriEntity4.compositeId.organizationId}/${cassSaathratriEntity4.compositeId.attributeKey}`,
      { observe: 'response' },
    );
  }

  getCassSaathratriEntity4Identifier(cassSaathratriEntity4: Pick<ICassSaathratriEntity4, 'compositeId'>): ICassSaathratriEntity4Id {
    return cassSaathratriEntity4.compositeId;
  }
  compareCassSaathratriEntity4(
    o1: Pick<ICassSaathratriEntity4, 'compositeId'> | null,
    o2: Pick<ICassSaathratriEntity4, 'compositeId'> | null,
  ): boolean {
    return o1 && o2 ? this.getCassSaathratriEntity4Identifier(o1) === this.getCassSaathratriEntity4Identifier(o2) : o1 === o2;
  }
  addCassSaathratriEntity4ToCollectionIfMissing<Type extends Pick<ICassSaathratriEntity4, 'compositeId'>>(
    cassSaathratriEntity4Collection: Type[],
    ...cassSaathratriEntity4sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassSaathratriEntity4s: Type[] = cassSaathratriEntity4sToCheck.filter(isPresent);
    if (cassSaathratriEntity4s.length > 0) {
      const cassSaathratriEntity4CollectionIdentifiers = cassSaathratriEntity4Collection.map(cassSaathratriEntity4Item =>
        this.getCassSaathratriEntity4Identifier(cassSaathratriEntity4Item),
      );
      const cassSaathratriEntity4sToAdd = cassSaathratriEntity4s.filter(cassSaathratriEntity4Item => {
        const cassSaathratriEntity4Identifier = this.getCassSaathratriEntity4Identifier(cassSaathratriEntity4Item);
        if (cassSaathratriEntity4CollectionIdentifiers.includes(cassSaathratriEntity4Identifier)) {
          return false;
        }
        cassSaathratriEntity4CollectionIdentifiers.push(cassSaathratriEntity4Identifier);
        return true;
      });
      return [...cassSaathratriEntity4sToAdd, ...cassSaathratriEntity4Collection];
    }
    return cassSaathratriEntity4Collection;
  }
}
