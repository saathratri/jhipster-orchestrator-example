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

import { ICassAddOnsAvailableByOrganization } from '../cass-add-ons-available-by-organization.model';
import { CassAddOnsAvailableByOrganizationService } from '../service/cass-add-ons-available-by-organization.service';

const cassAddOnsAvailableByOrganizationResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassAddOnsAvailableByOrganization> => {
  const { organizationId } = route.params;
  const { entityType } = route.params;
  const { entityId } = route.params;
  const { addOnId } = route.params;

  if (organizationId && entityType && entityId && addOnId) {
    return inject(CassAddOnsAvailableByOrganizationService)
      .find(organizationId, entityType, entityId, addOnId)
      .pipe(
        mergeMap((cassAddOnsAvailableByOrganization: HttpResponse<ICassAddOnsAvailableByOrganization>) => {
          if (cassAddOnsAvailableByOrganization.body) {
            return of(cassAddOnsAvailableByOrganization.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassAddOnsAvailableByOrganizationResolve;
