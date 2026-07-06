import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassTag } from '../cass-tag.model';
import { CassTagService } from '../service/cass-tag.service';

const cassTagResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassTag> => {
  const { id } = route.params;

  if (id) {
    return inject(CassTagService)
      .find(id)
      .pipe(
        mergeMap((cassTag: HttpResponse<ICassTag>) => {
          if (cassTag.body) {
            return of(cassTag.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassTagResolve;
