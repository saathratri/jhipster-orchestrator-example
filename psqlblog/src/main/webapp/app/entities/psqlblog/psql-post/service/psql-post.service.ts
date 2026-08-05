import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IPsqlPost, NewPsqlPost } from '../psql-post.model';

export type PartialUpdatePsqlPost = Partial<IPsqlPost> & Pick<IPsqlPost, 'id'>;

type RestOf<T extends IPsqlPost | NewPsqlPost> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestPsqlPost = RestOf<IPsqlPost>;

export type NewRestPsqlPost = RestOf<NewPsqlPost>;

export type PartialUpdateRestPsqlPost = RestOf<PartialUpdatePsqlPost>;

@Injectable()
export class PsqlPostsService {
  readonly psqlPostsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly psqlPostsResource = httpResource<RestPsqlPost[]>(() => {
    const params = this.psqlPostsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of psqlPost that have been fetched. It is updated when the psqlPostsResource emits a new value.
   * In case of error while fetching the psqlPosts, the signal is set to an empty array.
   */
  readonly psqlPosts = computed(() =>
    (this.psqlPostsResource.hasValue() ? this.psqlPostsResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/psql-posts', 'psqlblog');

  protected convertValueFromServer(restPsqlPost: RestPsqlPost): IPsqlPost {
    return {
      ...restPsqlPost,
      date: restPsqlPost.date ? dayjs(restPsqlPost.date) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class PsqlPostService extends PsqlPostsService {
  protected readonly http = inject(HttpClient);

  create(psqlPost: NewPsqlPost): Observable<IPsqlPost> {
    const copy = this.convertValueFromClient(psqlPost);
    return this.http.post<RestPsqlPost>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(psqlPost: IPsqlPost): Observable<IPsqlPost> {
    const copy = this.convertValueFromClient(psqlPost);
    return this.http
      .put<RestPsqlPost>(`${this.resourceUrl}/${encodeURIComponent(this.getPsqlPostIdentifier(psqlPost))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(psqlPost: PartialUpdatePsqlPost): Observable<IPsqlPost> {
    const copy = this.convertValueFromClient(psqlPost);
    return this.http
      .patch<RestPsqlPost>(`${this.resourceUrl}/${encodeURIComponent(this.getPsqlPostIdentifier(psqlPost))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<IPsqlPost> {
    return this.http
      .get<RestPsqlPost>(`${this.resourceUrl}/${encodeURIComponent(id)}`)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IPsqlPost[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPsqlPost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: string): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getPsqlPostIdentifier(psqlPost: Pick<IPsqlPost, 'id'>): string {
    return psqlPost.id;
  }

  comparePsqlPost(o1: Pick<IPsqlPost, 'id'> | null, o2: Pick<IPsqlPost, 'id'> | null): boolean {
    return o1 && o2 ? this.getPsqlPostIdentifier(o1) === this.getPsqlPostIdentifier(o2) : o1 === o2;
  }

  addPsqlPostToCollectionIfMissing<Type extends Pick<IPsqlPost, 'id'>>(
    psqlPostCollection: Type[],
    ...psqlPostsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const psqlPosts: Type[] = psqlPostsToCheck.filter(isPresent);
    if (psqlPosts.length > 0) {
      const psqlPostCollectionIdentifiers = psqlPostCollection.map(psqlPostItem => this.getPsqlPostIdentifier(psqlPostItem));
      const psqlPostsToAdd = psqlPosts.filter(psqlPostItem => {
        const psqlPostIdentifier = this.getPsqlPostIdentifier(psqlPostItem);
        if (psqlPostCollectionIdentifiers.includes(psqlPostIdentifier)) {
          return false;
        }
        psqlPostCollectionIdentifiers.push(psqlPostIdentifier);
        return true;
      });
      return [...psqlPostsToAdd, ...psqlPostCollection];
    }
    return psqlPostCollection;
  }

  protected convertValueFromClient<T extends IPsqlPost | NewPsqlPost | PartialUpdatePsqlPost>(psqlPost: T): RestOf<T> {
    return {
      ...psqlPost,
      date: psqlPost.date?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestPsqlPost): IPsqlPost {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestPsqlPost[]): IPsqlPost[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
