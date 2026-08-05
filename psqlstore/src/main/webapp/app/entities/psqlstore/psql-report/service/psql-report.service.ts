import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { IPsqlReport, NewPsqlReport } from '../psql-report.model';

export type PartialUpdatePsqlReport = Partial<IPsqlReport> & Pick<IPsqlReport, 'id'>;

type RestOf<T extends IPsqlReport | NewPsqlReport> = Omit<T, 'createDate'> & {
  createDate?: string | null;
};

export type RestPsqlReport = RestOf<IPsqlReport>;

export type NewRestPsqlReport = RestOf<NewPsqlReport>;

export type PartialUpdateRestPsqlReport = RestOf<PartialUpdatePsqlReport>;

@Injectable()
export class PsqlReportsService {
  readonly psqlReportsParams = signal<Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined>(
    undefined,
  );
  readonly psqlReportsResource = httpResource<RestPsqlReport[]>(() => {
    const params = this.psqlReportsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of psqlReport that have been fetched. It is updated when the psqlReportsResource emits a new value.
   * In case of error while fetching the psqlReports, the signal is set to an empty array.
   */
  readonly psqlReports = computed(() =>
    (this.psqlReportsResource.hasValue() ? this.psqlReportsResource.value() : []).map(item => this.convertValueFromServer(item)),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/psql-reports', 'psqlstore');

  protected convertValueFromServer(restPsqlReport: RestPsqlReport): IPsqlReport {
    return {
      ...restPsqlReport,
      createDate: restPsqlReport.createDate ? dayjs(restPsqlReport.createDate) : undefined,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class PsqlReportService extends PsqlReportsService {
  protected readonly http = inject(HttpClient);

  create(psqlReport: NewPsqlReport): Observable<IPsqlReport> {
    const copy = this.convertValueFromClient(psqlReport);
    return this.http.post<RestPsqlReport>(this.resourceUrl, copy).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(psqlReport: IPsqlReport): Observable<IPsqlReport> {
    const copy = this.convertValueFromClient(psqlReport);
    return this.http
      .put<RestPsqlReport>(`${this.resourceUrl}/${encodeURIComponent(this.getPsqlReportIdentifier(psqlReport))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(psqlReport: PartialUpdatePsqlReport): Observable<IPsqlReport> {
    const copy = this.convertValueFromClient(psqlReport);
    return this.http
      .patch<RestPsqlReport>(`${this.resourceUrl}/${encodeURIComponent(this.getPsqlReportIdentifier(psqlReport))}`, copy)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<IPsqlReport> {
    return this.http
      .get<RestPsqlReport>(`${this.resourceUrl}/${encodeURIComponent(id)}`)
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IPsqlReport[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPsqlReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => res.clone({ body: this.convertResponseArrayFromServer(res.body!) })));
  }

  delete(id: string): Observable<undefined> {
    return this.http.delete<undefined>(`${this.resourceUrl}/${encodeURIComponent(id)}`);
  }

  getPsqlReportIdentifier(psqlReport: Pick<IPsqlReport, 'id'>): string {
    return psqlReport.id;
  }

  comparePsqlReport(o1: Pick<IPsqlReport, 'id'> | null, o2: Pick<IPsqlReport, 'id'> | null): boolean {
    return o1 && o2 ? this.getPsqlReportIdentifier(o1) === this.getPsqlReportIdentifier(o2) : o1 === o2;
  }

  addPsqlReportToCollectionIfMissing<Type extends Pick<IPsqlReport, 'id'>>(
    psqlReportCollection: Type[],
    ...psqlReportsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const psqlReports: Type[] = psqlReportsToCheck.filter(isPresent);
    if (psqlReports.length > 0) {
      const psqlReportCollectionIdentifiers = psqlReportCollection.map(psqlReportItem => this.getPsqlReportIdentifier(psqlReportItem));
      const psqlReportsToAdd = psqlReports.filter(psqlReportItem => {
        const psqlReportIdentifier = this.getPsqlReportIdentifier(psqlReportItem);
        if (psqlReportCollectionIdentifiers.includes(psqlReportIdentifier)) {
          return false;
        }
        psqlReportCollectionIdentifiers.push(psqlReportIdentifier);
        return true;
      });
      return [...psqlReportsToAdd, ...psqlReportCollection];
    }
    return psqlReportCollection;
  }

  protected convertValueFromClient<T extends IPsqlReport | NewPsqlReport | PartialUpdatePsqlReport>(psqlReport: T): RestOf<T> {
    return {
      ...psqlReport,
      createDate: psqlReport.createDate?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: RestPsqlReport): IPsqlReport {
    return this.convertValueFromServer(res);
  }

  protected convertResponseArrayFromServer(res: RestPsqlReport[]): IPsqlReport[] {
    return res.map(item => this.convertValueFromServer(item));
  }
}
