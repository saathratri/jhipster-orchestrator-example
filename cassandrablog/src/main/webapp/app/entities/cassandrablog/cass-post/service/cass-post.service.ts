import { HttpClient, HttpParams, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassPost, ICassPostId, NewCassPost } from '../cass-post.model';
export type PartialUpdateCassPost = Partial<ICassPost> & Pick<ICassPost, 'compositeId'>;

type RestOf<T extends ICassPost | NewCassPost> = Omit<T, 'createdDate' | 'addedDateTime' | 'publishedDateTime' | 'sentDate'> & {
  compositeId: {
    createdDate?: number | null;
    addedDateTime?: number | null;
  };
  publishedDateTime?: number | null;
  sentDate?: number | null;
};

export type RestCassPost = RestOf<ICassPost>;

export type NewRestCassPost = RestOf<NewCassPost>;

export type PartialUpdateRestCassPost = RestOf<PartialUpdateCassPost>;

export type EntityResponseType = HttpResponse<ICassPost>;
export type EntityArrayResponseType = HttpResponse<ICassPost[]>;

@Injectable()
export class CassPostsService {
  readonly cassPostsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly cassPostsResource = httpResource<RestCassPost[]>(() => {
    const params = this.cassPostsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassPost that have been fetched.
   */
  readonly cassPosts = computed(() =>
    (this.cassPostsResource.hasValue() ? this.cassPostsResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-posts', 'cassandrablog');

  protected convertValueFromServer(restCassPost: RestCassPost): ICassPost {
    return {
      ...restCassPost,
      compositeId: {
        createdDate: restCassPost.compositeId.createdDate ? dayjs(restCassPost.compositeId.createdDate) : null,
        addedDateTime: restCassPost.compositeId.addedDateTime ? dayjs(restCassPost.compositeId.addedDateTime) : null,

        postId: restCassPost.compositeId.postId,
      },
      publishedDateTime: restCassPost.publishedDateTime ? dayjs(restCassPost.publishedDateTime) : null,
      sentDate: restCassPost.sentDate ? dayjs(restCassPost.sentDate) : null,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class CassPostService extends CassPostsService {
  protected readonly http = inject(HttpClient);

  create(cassPost: NewCassPost): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassPost);
    return this.http
      .post<RestCassPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cassPost: ICassPost): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassPost);
    return this.http
      .put<RestCassPost>(
        `${this.resourceUrl}/${copy.compositeId.createdDate}/${copy.compositeId.addedDateTime}/${cassPost.compositeId.postId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cassPost: PartialUpdateCassPost): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassPost);
    return this.http
      .patch<RestCassPost>(
        `${this.resourceUrl}/${copy.compositeId.createdDate}/${copy.compositeId.addedDateTime}/${cassPost.compositeId.postId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(createdDate: number, addedDateTime: number, postId: string): Observable<EntityResponseType> {
    const data = { createdDate, addedDateTime, postId };
    return this.http
      .get<RestCassPost>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassPost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassPost[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDatePageable(createdDate: number, req?: any): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', createdDate);
    return this.http
      .get<RestCassPost[]>(`${this.resourceUrl}/find-all-by-composite-id-created-date-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable(
    createdDate: number,
    addedDateTime: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', createdDate);
    options = options.set('addedDateTime', addedDateTime);
    return this.http
      .get<
        RestCassPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostIdPageable(
    createdDate: number,
    addedDateTime: number,
    postId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', createdDate);
    options = options.set('addedDateTime', addedDateTime);
    options = options.set('postId', postId);
    return this.http
      .get<
        RestCassPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-and-composite-id-post-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable(
    createdDate: number,
    addedDateTime: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', createdDate);
    options = options.set('addedDateTime', addedDateTime);
    return this.http
      .get<
        RestCassPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable(
    createdDate: number,
    addedDateTime: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', createdDate);
    options = options.set('addedDateTime', addedDateTime);
    return this.http
      .get<
        RestCassPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable(
    createdDate: number,
    addedDateTime: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', createdDate);
    options = options.set('addedDateTime', addedDateTime);
    return this.http
      .get<
        RestCassPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable(
    createdDate: number,
    addedDateTime: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('createdDate', createdDate);
    options = options.set('addedDateTime', addedDateTime);
    return this.http
      .get<
        RestCassPost[]
      >(`${this.resourceUrl}/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
    createdDate: number,
    addedDateTime: number,
    postId: string,
  ): Observable<EntityResponseType> {
    let options = new HttpParams();
    options = options.set('createdDate', createdDate);
    options = options.set('addedDateTime', addedDateTime);
    options = options.set('postId', postId);
    return this.http
      .get<RestCassPost>(
        `${this.resourceUrl}/find-by-composite-id-created-date-and-composite-id-added-date-time-and-composite-id-post-id`,
        { params: options, observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  delete(cassPost: ICassPost): Observable<HttpResponse<{}>> {
    const copy = this.convertValueFromClient(cassPost);
    return this.http.delete(
      `${this.resourceUrl}/${copy.compositeId.createdDate}/${copy.compositeId.addedDateTime}/${cassPost.compositeId.postId}`,
      { observe: 'response' },
    );
  }

  getCassPostIdentifier(cassPost: Pick<ICassPost, 'compositeId'>): ICassPostId {
    return cassPost.compositeId;
  }
  compareCassPost(o1: Pick<ICassPost, 'compositeId'> | null, o2: Pick<ICassPost, 'compositeId'> | null): boolean {
    return o1 && o2 ? this.getCassPostIdentifier(o1) === this.getCassPostIdentifier(o2) : o1 === o2;
  }
  addCassPostToCollectionIfMissing<Type extends Pick<ICassPost, 'compositeId'>>(
    cassPostCollection: Type[],
    ...cassPostsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassPosts: Type[] = cassPostsToCheck.filter(isPresent);
    if (cassPosts.length > 0) {
      const cassPostCollectionIdentifiers = cassPostCollection.map(cassPostItem => this.getCassPostIdentifier(cassPostItem));
      const cassPostsToAdd = cassPosts.filter(cassPostItem => {
        const cassPostIdentifier = this.getCassPostIdentifier(cassPostItem);
        if (cassPostCollectionIdentifiers.includes(cassPostIdentifier)) {
          return false;
        }
        cassPostCollectionIdentifiers.push(cassPostIdentifier);
        return true;
      });
      return [...cassPostsToAdd, ...cassPostCollection];
    }
    return cassPostCollection;
  }

  protected convertValueFromClient<T extends ICassPost | NewCassPost | PartialUpdateCassPost>(cassPost: T): RestOf<T> {
    return {
      ...cassPost,
      compositeId: {
        ...cassPost.compositeId,
        createdDate: cassPost.compositeId.createdDate ? cassPost.compositeId.createdDate.valueOf() : null,

        addedDateTime: cassPost.compositeId.addedDateTime ? cassPost.compositeId.addedDateTime.valueOf() : null,
      },
      publishedDateTime: cassPost.publishedDateTime ? cassPost.publishedDateTime.valueOf() : null,
      sentDate: cassPost.sentDate ? cassPost.sentDate.valueOf() : null,
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestCassPost>): HttpResponse<ICassPost> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCassPost[]>): HttpResponse<ICassPost[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
