import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassSaathratriEntity4 } from '../cass-saathratri-entity-4.model';
import { CassSaathratriEntity4Service } from '../service/cass-saathratri-entity-4.service';

const cassSaathratriEntity4Resolve = (route: ActivatedRouteSnapshot): Observable<null | ICassSaathratriEntity4> => {
  const { organizationId } = route.params;
  const { attributeKey } = route.params;

  if (organizationId && attributeKey) {
    return inject(CassSaathratriEntity4Service)
      .find(organizationId, attributeKey)
      .pipe(
        mergeMap((cassSaathratriEntity4: HttpResponse<ICassSaathratriEntity4>) => {
          if (cassSaathratriEntity4.body) {
            return of(cassSaathratriEntity4.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassSaathratriEntity4Resolve;
