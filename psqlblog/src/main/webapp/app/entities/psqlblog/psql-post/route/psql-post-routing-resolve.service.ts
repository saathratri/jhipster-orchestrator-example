import { HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { IPsqlPost } from '../psql-post.model';
import { PsqlPostService } from '../service/psql-post.service';

const psqlPostResolve = (route: ActivatedRouteSnapshot): Observable<null | IPsqlPost> => {
  const id = route.params.id;
  if (id) {
    const router = inject(Router);
    const service = inject(PsqlPostService);
    return service.find(id).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 404) {
          router.navigate(['404']);
        } else {
          router.navigate(['error']);
        }
        return EMPTY;
      }),
    );
  }

  return of(null);
};

export default psqlPostResolve;
