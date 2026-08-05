import { HttpClient, HttpParams, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassBlog, ICassBlogId, NewCassBlog } from '../cass-blog.model';
export type PartialUpdateCassBlog = Partial<ICassBlog> & Pick<ICassBlog, 'compositeId'>;

export type EntityResponseType = HttpResponse<ICassBlog>;
export type EntityArrayResponseType = HttpResponse<ICassBlog[]>;

@Injectable()
export class CassBlogsService {
  readonly cassBlogsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly cassBlogsResource = httpResource<ICassBlog[]>(() => {
    const params = this.cassBlogsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassBlog that have been fetched.
   */
  readonly cassBlogs = computed(() => (this.cassBlogsResource.hasValue() ? this.cassBlogsResource.value() : []));
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-blogs', 'cassandrablog');
}

@Injectable({ providedIn: 'root' })
export class CassBlogService extends CassBlogsService {
  protected readonly http = inject(HttpClient);

  create(cassBlog: NewCassBlog): Observable<EntityResponseType> {
    return this.http.post<ICassBlog>(this.resourceUrl, cassBlog, { observe: 'response' });
  }

  update(cassBlog: ICassBlog): Observable<EntityResponseType> {
    return this.http.put<ICassBlog>(`${this.resourceUrl}/${cassBlog.compositeId.category}/${cassBlog.compositeId.blogId}`, cassBlog, {
      observe: 'response',
    });
  }

  partialUpdate(cassBlog: PartialUpdateCassBlog): Observable<EntityResponseType> {
    return this.http.patch<ICassBlog>(`${this.resourceUrl}/${cassBlog.compositeId.category}/${cassBlog.compositeId.blogId}`, cassBlog, {
      observe: 'response',
    });
  }

  find(category: string, blogId: string): Observable<EntityResponseType> {
    const data = { category, blogId };
    return this.http.get<ICassBlog>(`${this.resourceUrl}/get`, { params: data, observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICassBlog[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICassBlog[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' });
  }

  findAllByCompositeIdCategoryPageable(category: string, req?: any): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('category', category);
    return this.http.get<ICassBlog[]>(`${this.resourceUrl}/find-all-by-composite-id-category-pageable`, {
      params: options,
      observe: 'response',
    });
  }

  findAllByCompositeIdCategoryAndCompositeIdBlogIdPageable(
    category: string,
    blogId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('category', category);
    options = options.set('blogId', blogId);
    return this.http.get<ICassBlog[]>(`${this.resourceUrl}/find-all-by-composite-id-category-and-composite-id-blog-id-pageable`, {
      params: options,
      observe: 'response',
    });
  }

  findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable(
    category: string,
    blogId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('category', category);
    options = options.set('blogId', blogId);
    return this.http.get<ICassBlog[]>(`${this.resourceUrl}/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-pageable`, {
      params: options,
      observe: 'response',
    });
  }

  findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable(
    category: string,
    blogId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('category', category);
    options = options.set('blogId', blogId);
    return this.http.get<ICassBlog[]>(
      `${this.resourceUrl}/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal-pageable`,
      { params: options, observe: 'response' },
    );
  }

  findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable(
    category: string,
    blogId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('category', category);
    options = options.set('blogId', blogId);
    return this.http.get<ICassBlog[]>(
      `${this.resourceUrl}/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-pageable`,
      { params: options, observe: 'response' },
    );
  }

  findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable(
    category: string,
    blogId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('category', category);
    options = options.set('blogId', blogId);
    return this.http.get<ICassBlog[]>(
      `${this.resourceUrl}/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal-pageable`,
      { params: options, observe: 'response' },
    );
  }

  findByCompositeIdCategoryAndCompositeIdBlogId(category: string, blogId: string): Observable<EntityResponseType> {
    let options = new HttpParams();
    options = options.set('category', category);
    options = options.set('blogId', blogId);
    return this.http.get<ICassBlog>(`${this.resourceUrl}/find-by-composite-id-category-and-composite-id-blog-id`, {
      params: options,
      observe: 'response',
    });
  }

  delete(cassBlog: ICassBlog): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${cassBlog.compositeId.category}/${cassBlog.compositeId.blogId}`, { observe: 'response' });
  }

  getCassBlogIdentifier(cassBlog: Pick<ICassBlog, 'compositeId'>): ICassBlogId {
    return cassBlog.compositeId;
  }
  compareCassBlog(o1: Pick<ICassBlog, 'compositeId'> | null, o2: Pick<ICassBlog, 'compositeId'> | null): boolean {
    return o1 && o2 ? this.getCassBlogIdentifier(o1) === this.getCassBlogIdentifier(o2) : o1 === o2;
  }
  addCassBlogToCollectionIfMissing<Type extends Pick<ICassBlog, 'compositeId'>>(
    cassBlogCollection: Type[],
    ...cassBlogsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassBlogs: Type[] = cassBlogsToCheck.filter(isPresent);
    if (cassBlogs.length > 0) {
      const cassBlogCollectionIdentifiers = cassBlogCollection.map(cassBlogItem => this.getCassBlogIdentifier(cassBlogItem));
      const cassBlogsToAdd = cassBlogs.filter(cassBlogItem => {
        const cassBlogIdentifier = this.getCassBlogIdentifier(cassBlogItem);
        if (cassBlogCollectionIdentifiers.includes(cassBlogIdentifier)) {
          return false;
        }
        cassBlogCollectionIdentifiers.push(cassBlogIdentifier);
        return true;
      });
      return [...cassBlogsToAdd, ...cassBlogCollection];
    }
    return cassBlogCollection;
  }
}
