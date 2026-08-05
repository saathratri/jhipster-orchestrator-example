import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassSaathratriEntity } from '../cass-saathratri-entity.model';
import { CassSaathratriEntityService } from '../service/cass-saathratri-entity.service';

const cassSaathratriEntityResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassSaathratriEntity> => {
  const id = route.params.entityId;

  if (id) {
    return inject(CassSaathratriEntityService)
      .find(id)
      .pipe(
        mergeMap((cassSaathratriEntity: HttpResponse<ICassSaathratriEntity>) => {
          if (cassSaathratriEntity.body) {
            return of(cassSaathratriEntity.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassSaathratriEntityResolve;
