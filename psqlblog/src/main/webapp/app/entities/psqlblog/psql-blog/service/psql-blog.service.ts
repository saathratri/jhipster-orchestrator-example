import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IPsqlBlog, NewPsqlBlog } from '../psql-blog.model';

export type PartialUpdatePsqlBlog = Partial<IPsqlBlog> & Pick<IPsqlBlog, 'id'>;

@Injectable()
export class PsqlBlogsService {
  readonly psqlBlogsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly psqlBlogsResource = httpResource<IPsqlBlog[]>(() => {
    const params = this.psqlBlogsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of psqlBlog that have been fetched. It is updated when the psqlBlogsResource emits a new value.
   * In case of error while fetching the psqlBlogs, the signal is set to an empty array.
   */
  readonly psqlBlogs = computed(() => (this.psqlBlogsResource.hasValue() ? this.psqlBlogsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/psql-blogs', 'psqlblog');
}

@Injectable({ providedIn: 'root' })
export class PsqlBlogService extends PsqlBlogsService {
  protected readonly http = inject(HttpClient);

  create(psqlBlog: NewPsqlBlog): Observable<IPsqlBlog> {
    return this.http.post<IPsqlBlog>(this.resourceUrl, psqlBlog);
  }

  update(psqlBlog: IPsqlBlog): Observable<IPsqlBlog> {
    return this.http.put<IPsqlBlog>(`${this.resourceUrl}/${encodeURIComponent(this.getPsqlBlogIdentifier(psqlBlog))}`, psqlBlog);
  }

  partialUpdate(psqlBlog: PartialUpdatePsqlBlog): Observable<IPsqlBlog> {
    return this.http.patch<IPsqlBlog>(`${this.resourceUrl}/${encodeURIComponent(this.getPsqlBlogIdentifier(psqlBlog))}`, psqlBlog);
  }

  find(id: string): Observable<IPsqlBlog> {
    return this.http.get<IPsqlBlog>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  query(req?: any): Observable<HttpResponse<IPsqlBlog[]>> {
    const options = createRequestOption(req);
    return this.http.get<IPsqlBlog[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getPsqlBlogIdentifier(psqlBlog: Pick<IPsqlBlog, 'id'>): string {
    return psqlBlog.id;
  }

  comparePsqlBlog(o1: Pick<IPsqlBlog, 'id'> | null, o2: Pick<IPsqlBlog, 'id'> | null): boolean {
    return o1 && o2 ? this.getPsqlBlogIdentifier(o1) === this.getPsqlBlogIdentifier(o2) : o1 === o2;
  }

  addPsqlBlogToCollectionIfMissing<Type extends Pick<IPsqlBlog, 'id'>>(
    psqlBlogCollection: Type[],
    ...psqlBlogsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const psqlBlogs: Type[] = psqlBlogsToCheck.filter(isPresent);
    if (psqlBlogs.length > 0) {
      const psqlBlogCollectionIdentifiers = psqlBlogCollection.map(psqlBlogItem => this.getPsqlBlogIdentifier(psqlBlogItem));
      const psqlBlogsToAdd = psqlBlogs.filter(psqlBlogItem => {
        const psqlBlogIdentifier = this.getPsqlBlogIdentifier(psqlBlogItem);
        if (psqlBlogCollectionIdentifiers.includes(psqlBlogIdentifier)) {
          return false;
        }
        psqlBlogCollectionIdentifiers.push(psqlBlogIdentifier);
        return true;
      });
      return [...psqlBlogsToAdd, ...psqlBlogCollection];
    }
    return psqlBlogCollection;
  }
}
