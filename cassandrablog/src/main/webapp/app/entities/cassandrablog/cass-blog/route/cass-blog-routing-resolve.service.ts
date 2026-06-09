import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassBlog } from '../cass-blog.model';
import { CassBlogService } from '../service/cass-blog.service';

const cassBlogResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassBlog> => {
  const category = route.params.category;
  const blogId = route.params.blogId;

  if (category && blogId) {
    return inject(CassBlogService)
      .find(category, blogId)
      .pipe(
        mergeMap((cassBlog: HttpResponse<ICassBlog>) => {
          if (cassBlog.body) {
            return of(cassBlog.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassBlogResolve;
