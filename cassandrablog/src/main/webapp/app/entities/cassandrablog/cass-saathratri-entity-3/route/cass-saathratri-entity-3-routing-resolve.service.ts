import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassSaathratriEntity3 } from '../cass-saathratri-entity-3.model';
import { CassSaathratriEntity3Service } from '../service/cass-saathratri-entity-3.service';

const cassSaathratriEntity3Resolve = (route: ActivatedRouteSnapshot): Observable<null | ICassSaathratriEntity3> => {
  const entityType = route.params.entityType;
  const createdTimeId = route.params.createdTimeId;

  if (entityType && createdTimeId) {
    return inject(CassSaathratriEntity3Service)
      .find(entityType, createdTimeId)
      .pipe(
        mergeMap((cassSaathratriEntity3: HttpResponse<ICassSaathratriEntity3>) => {
          if (cassSaathratriEntity3.body) {
            return of(cassSaathratriEntity3.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassSaathratriEntity3Resolve;
