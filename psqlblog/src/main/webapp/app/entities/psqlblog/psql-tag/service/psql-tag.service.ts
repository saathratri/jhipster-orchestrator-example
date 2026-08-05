import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IPsqlTag, NewPsqlTag } from '../psql-tag.model';

export type PartialUpdatePsqlTag = Partial<IPsqlTag> & Pick<IPsqlTag, 'id'>;

@Injectable()
export class PsqlTagsService {
  readonly psqlTagsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly psqlTagsResource = httpResource<IPsqlTag[]>(() => {
    const params = this.psqlTagsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of psqlTag that have been fetched. It is updated when the psqlTagsResource emits a new value.
   * In case of error while fetching the psqlTags, the signal is set to an empty array.
   */
  readonly psqlTags = computed(() => (this.psqlTagsResource.hasValue() ? this.psqlTagsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/psql-tags', 'psqlblog');
}

@Injectable({ providedIn: 'root' })
export class PsqlTagService extends PsqlTagsService {
  protected readonly http = inject(HttpClient);

  create(psqlTag: NewPsqlTag): Observable<IPsqlTag> {
    return this.http.post<IPsqlTag>(this.resourceUrl, psqlTag);
  }

  update(psqlTag: IPsqlTag): Observable<IPsqlTag> {
    return this.http.put<IPsqlTag>(`${this.resourceUrl}/${encodeURIComponent(this.getPsqlTagIdentifier(psqlTag))}`, psqlTag);
  }

  partialUpdate(psqlTag: PartialUpdatePsqlTag): Observable<IPsqlTag> {
    return this.http.patch<IPsqlTag>(`${this.resourceUrl}/${encodeURIComponent(this.getPsqlTagIdentifier(psqlTag))}`, psqlTag);
  }

  find(id: string): Observable<IPsqlTag> {
    return this.http.get<IPsqlTag>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<IPsqlTag[]>> {
    const options = createRequestOption(req);
    return this.http.get<IPsqlTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getPsqlTagIdentifier(psqlTag: Pick<IPsqlTag, 'id'>): string {
    return psqlTag.id;
  }

  comparePsqlTag(o1: Pick<IPsqlTag, 'id'> | null, o2: Pick<IPsqlTag, 'id'> | null): boolean {
    return o1 && o2 ? this.getPsqlTagIdentifier(o1) === this.getPsqlTagIdentifier(o2) : o1 === o2;
  }

  addPsqlTagToCollectionIfMissing<Type extends Pick<IPsqlTag, 'id'>>(
    psqlTagCollection: Type[],
    ...psqlTagsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const psqlTags: Type[] = psqlTagsToCheck.filter(isPresent);
    if (psqlTags.length > 0) {
      const psqlTagCollectionIdentifiers = psqlTagCollection.map(psqlTagItem => this.getPsqlTagIdentifier(psqlTagItem));
      const psqlTagsToAdd = psqlTags.filter(psqlTagItem => {
        const psqlTagIdentifier = this.getPsqlTagIdentifier(psqlTagItem);
        if (psqlTagCollectionIdentifiers.includes(psqlTagIdentifier)) {
          return false;
        }
        psqlTagCollectionIdentifiers.push(psqlTagIdentifier);
        return true;
      });
      return [...psqlTagsToAdd, ...psqlTagCollection];
    }
    return psqlTagCollection;
  }

  aiSearch(query: string, limit: number, fields?: string[]): Observable<IPsqlTag[]> {
    const params: Record<string, string | string[]> = { query, limit: String(limit) };
    if (fields && fields.length > 0) {
      params.fields = fields.join(',');
    }
    return this.http.get<IPsqlTag[]>(`${this.resourceUrl}/ai-search`, {
      params,
    });
  }
}
