import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassAddOnsSelectedByOrganization } from '../cass-add-ons-selected-by-organization.model';
import { CassAddOnsSelectedByOrganizationService } from '../service/cass-add-ons-selected-by-organization.service';

const cassAddOnsSelectedByOrganizationResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassAddOnsSelectedByOrganization> => {
  const { organizationId } = route.params;
  const { arrivalDate } = route.params;
  const { accountNumber } = route.params;
  const { createdTimeId } = route.params;

  if (organizationId && arrivalDate && accountNumber && createdTimeId) {
    return inject(CassAddOnsSelectedByOrganizationService)
      .find(organizationId, arrivalDate, accountNumber, createdTimeId)
      .pipe(
        mergeMap((cassAddOnsSelectedByOrganization: HttpResponse<ICassAddOnsSelectedByOrganization>) => {
          if (cassAddOnsSelectedByOrganization.body) {
            return of(cassAddOnsSelectedByOrganization.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassAddOnsSelectedByOrganizationResolve;
