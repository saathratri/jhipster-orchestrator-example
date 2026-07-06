import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassPost } from '../cass-post.model';
import { CassPostService } from '../service/cass-post.service';

const cassPostResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassPost> => {
  const { createdDate } = route.params;
  const { addedDateTime } = route.params;
  const { postId } = route.params;

  if (createdDate && addedDateTime && postId) {
    return inject(CassPostService)
      .find(createdDate, addedDateTime, postId)
      .pipe(
        mergeMap((cassPost: HttpResponse<ICassPost>) => {
          if (cassPost.body) {
            return of(cassPost.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassPostResolve;
