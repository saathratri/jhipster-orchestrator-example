import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassTag, NewCassTag } from '../cass-tag.model';
export type PartialUpdateCassTag = Partial<ICassTag> & Pick<ICassTag, 'id'>;

export type EntityResponseType = HttpResponse<ICassTag>;
export type EntityArrayResponseType = HttpResponse<ICassTag[]>;

@Injectable()
export class CassTagsService {
  readonly cassTagsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly cassTagsResource = httpResource<ICassTag[]>(() => {
    const params = this.cassTagsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassTag that have been fetched.
   */
  readonly cassTags = computed(() => (this.cassTagsResource.hasValue() ? this.cassTagsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-tags', 'cassandrablog');
}

@Injectable({ providedIn: 'root' })
export class CassTagService extends CassTagsService {
  protected readonly http = inject(HttpClient);

  create(cassTag: NewCassTag): Observable<EntityResponseType> {
    return this.http.post<ICassTag>(this.resourceUrl, cassTag, { observe: 'response' });
  }

  update(cassTag: ICassTag): Observable<EntityResponseType> {
    return this.http.put<ICassTag>(`${this.resourceUrl}/${this.getCassTagIdentifier(cassTag)}`, cassTag, { observe: 'response' });
  }

  partialUpdate(cassTag: PartialUpdateCassTag): Observable<EntityResponseType> {
    return this.http.patch<ICassTag>(`${this.resourceUrl}/${this.getCassTagIdentifier(cassTag)}`, cassTag, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICassTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICassTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICassTag[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCassTagIdentifier(cassTag: Pick<ICassTag, 'id'>): string {
    return cassTag.id;
  }

  compareCassTag(o1: Pick<ICassTag, 'id'> | null, o2: Pick<ICassTag, 'id'> | null): boolean {
    return o1 && o2 ? this.getCassTagIdentifier(o1) === this.getCassTagIdentifier(o2) : o1 === o2;
  }

  addCassTagToCollectionIfMissing<Type extends Pick<ICassTag, 'id'>>(
    cassTagCollection: Type[],
    ...cassTagsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassTags: Type[] = cassTagsToCheck.filter(isPresent);
    if (cassTags.length > 0) {
      const cassTagCollectionIdentifiers = cassTagCollection.map(cassTagItem => this.getCassTagIdentifier(cassTagItem));
      const cassTagsToAdd = cassTags.filter(cassTagItem => {
        const cassTagIdentifier = this.getCassTagIdentifier(cassTagItem);
        if (cassTagCollectionIdentifiers.includes(cassTagIdentifier)) {
          return false;
        }
        cassTagCollectionIdentifiers.push(cassTagIdentifier);
        return true;
      });
      return [...cassTagsToAdd, ...cassTagCollection];
    }
    return cassTagCollection;
  }

  aiSearch(query: string, limit: number, fields?: string[]): Observable<ICassTag[]> {
    const params: Record<string, string | string[]> = { query, limit: String(limit) };
    if (fields && fields.length > 0) {
      params.fields = fields.join(',');
    }
    return this.http.get<ICassTag[]>(`${this.resourceUrl}/ai-search`, {
      params,
    });
  }
}
