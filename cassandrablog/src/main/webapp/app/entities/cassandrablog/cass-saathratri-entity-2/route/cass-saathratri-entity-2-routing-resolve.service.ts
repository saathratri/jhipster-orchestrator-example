import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassSaathratriEntity2 } from '../cass-saathratri-entity-2.model';
import { CassSaathratriEntity2Service } from '../service/cass-saathratri-entity-2.service';

const cassSaathratriEntity2Resolve = (route: ActivatedRouteSnapshot): Observable<null | ICassSaathratriEntity2> => {
  const entityTypeId = route.params.entityTypeId;
  const yearOfDateAdded = route.params.yearOfDateAdded;
  const arrivalDate = route.params.arrivalDate;
  const blogId = route.params.blogId;

  if (entityTypeId && yearOfDateAdded && arrivalDate && blogId) {
    return inject(CassSaathratriEntity2Service)
      .find(entityTypeId, yearOfDateAdded, arrivalDate, blogId)
      .pipe(
        mergeMap((cassSaathratriEntity2: HttpResponse<ICassSaathratriEntity2>) => {
          if (cassSaathratriEntity2.body) {
            return of(cassSaathratriEntity2.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassSaathratriEntity2Resolve;
