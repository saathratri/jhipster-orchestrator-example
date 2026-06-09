import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassProduct } from '../cass-product.model';
import { CassProductService } from '../service/cass-product.service';

const cassProductResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassProduct> => {
  const id = route.params.id;

  if (id) {
    return inject(CassProductService)
      .find(id)
      .pipe(
        mergeMap((cassProduct: HttpResponse<ICassProduct>) => {
          if (cassProduct.body) {
            return of(cassProduct.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassProductResolve;
