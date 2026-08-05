import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassReport } from '../cass-report.model';
import { CassReportService } from '../service/cass-report.service';

const cassReportResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassReport> => {
  const { id } = route.params;

  if (id) {
    return inject(CassReportService)
      .find(id)
      .pipe(
        mergeMap((cassReport: HttpResponse<ICassReport>) => {
          if (cassReport.body) {
            return of(cassReport.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassReportResolve;
