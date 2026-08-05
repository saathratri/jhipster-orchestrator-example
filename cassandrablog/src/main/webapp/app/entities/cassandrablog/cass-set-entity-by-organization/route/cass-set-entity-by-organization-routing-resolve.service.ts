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

import { ICassSetEntityByOrganization } from '../cass-set-entity-by-organization.model';
import { CassSetEntityByOrganizationService } from '../service/cass-set-entity-by-organization.service';

const cassSetEntityByOrganizationResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassSetEntityByOrganization> => {
  const id = route.params.organizationId;

  if (id) {
    return inject(CassSetEntityByOrganizationService)
      .find(id)
      .pipe(
        mergeMap((cassSetEntityByOrganization: HttpResponse<ICassSetEntityByOrganization>) => {
          if (cassSetEntityByOrganization.body) {
            return of(cassSetEntityByOrganization.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassSetEntityByOrganizationResolve;
