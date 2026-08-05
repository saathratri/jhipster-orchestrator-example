import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ICassReport, NewCassReport } from '../cass-report.model';
export type PartialUpdateCassReport = Partial<ICassReport> & Pick<ICassReport, 'id'>;

type RestOf<T extends ICassReport | NewCassReport> = Omit<T, 'createDate'> & {
  createDate?: number | null;
};

export type RestCassReport = RestOf<ICassReport>;

export type NewRestCassReport = RestOf<NewCassReport>;

export type PartialUpdateRestCassReport = RestOf<PartialUpdateCassReport>;

export type EntityResponseType = HttpResponse<ICassReport>;
export type EntityArrayResponseType = HttpResponse<ICassReport[]>;

@Injectable()
export class CassReportsService {
  readonly cassReportsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly cassReportsResource = httpResource<RestCassReport[]>(() => {
    const params = this.cassReportsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of cassReport that have been fetched.
   */
  readonly cassReports = computed(() =>
    (this.cassReportsResource.hasValue() ? this.cassReportsResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/cass-reports', 'cassandrastore');

  protected convertValueFromServer(restCassReport: RestCassReport): ICassReport {
    return {
      ...restCassReport,
      createDate: restCassReport.createDate ? dayjs(restCassReport.createDate) : null,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class CassReportService extends CassReportsService {
  protected readonly http = inject(HttpClient);

  create(cassReport: NewCassReport): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassReport);
    return this.http
      .post<RestCassReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cassReport: ICassReport): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassReport);
    return this.http
      .put<RestCassReport>(`${this.resourceUrl}/${this.getCassReportIdentifier(cassReport)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cassReport: PartialUpdateCassReport): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(cassReport);
    return this.http
      .patch<RestCassReport>(`${this.resourceUrl}/${this.getCassReportIdentifier(cassReport)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCassReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCassReport[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCassReportIdentifier(cassReport: Pick<ICassReport, 'id'>): string {
    return cassReport.id;
  }

  compareCassReport(o1: Pick<ICassReport, 'id'> | null, o2: Pick<ICassReport, 'id'> | null): boolean {
    return o1 && o2 ? this.getCassReportIdentifier(o1) === this.getCassReportIdentifier(o2) : o1 === o2;
  }

  addCassReportToCollectionIfMissing<Type extends Pick<ICassReport, 'id'>>(
    cassReportCollection: Type[],
    ...cassReportsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cassReports: Type[] = cassReportsToCheck.filter(isPresent);
    if (cassReports.length > 0) {
      const cassReportCollectionIdentifiers = cassReportCollection.map(cassReportItem => this.getCassReportIdentifier(cassReportItem));
      const cassReportsToAdd = cassReports.filter(cassReportItem => {
        const cassReportIdentifier = this.getCassReportIdentifier(cassReportItem);
        if (cassReportCollectionIdentifiers.includes(cassReportIdentifier)) {
          return false;
        }
        cassReportCollectionIdentifiers.push(cassReportIdentifier);
        return true;
      });
      return [...cassReportsToAdd, ...cassReportCollection];
    }
    return cassReportCollection;
  }

  protected convertValueFromClient<T extends ICassReport | NewCassReport | PartialUpdateCassReport>(cassReport: T): RestOf<T> {
    return {
      ...cassReport,
      createDate: cassReport.createDate ? cassReport.createDate.valueOf() : null,
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestCassReport>): HttpResponse<ICassReport> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCassReport[]>): HttpResponse<ICassReport[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
