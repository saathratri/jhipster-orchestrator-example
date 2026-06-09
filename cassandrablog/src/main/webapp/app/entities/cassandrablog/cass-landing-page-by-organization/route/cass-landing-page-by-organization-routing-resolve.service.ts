import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICassLandingPageByOrganization } from '../cass-landing-page-by-organization.model';
import { CassLandingPageByOrganizationService } from '../service/cass-landing-page-by-organization.service';

const cassLandingPageByOrganizationResolve = (route: ActivatedRouteSnapshot): Observable<null | ICassLandingPageByOrganization> => {
  const id = route.params.organizationId;

  if (id) {
    return inject(CassLandingPageByOrganizationService)
      .find(id)
      .pipe(
        mergeMap((cassLandingPageByOrganization: HttpResponse<ICassLandingPageByOrganization>) => {
          if (cassLandingPageByOrganization.body) {
            return of(cassLandingPageByOrganization.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cassLandingPageByOrganizationResolve;
