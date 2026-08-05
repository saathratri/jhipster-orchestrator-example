/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassTajUser } from '../cass-taj-user.model';
import { CassTajUserService } from '../service/cass-taj-user.service';

const cassTajUserResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassTajUser> => {
  const { id } = route.params;

  if (id) {
    return inject(CassTajUserService)
      .find(id)
      .pipe(
        mergeMap((cassTajUser: HttpResponse<ICassTajUser>) => {
          if (cassTajUser.body) {
            return of(cassTajUser.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassTajUserResolve;
