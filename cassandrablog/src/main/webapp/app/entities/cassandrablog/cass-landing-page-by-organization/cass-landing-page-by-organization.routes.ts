import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassLandingPageByOrganizationResolve from './route/cass-landing-page-by-organization-routing-resolve.service';

const cassLandingPageByOrganizationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-landing-page-by-organization').then(m => m.CassLandingPageByOrganizationComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/view',
    loadComponent: () =>
      import('./detail/cass-landing-page-by-organization-detail').then(m => m.CassLandingPageByOrganizationDetailComponent),
    resolve: {
      cassLandingPageByOrganization: CassLandingPageByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/cass-landing-page-by-organization-update').then(m => m.CassLandingPageByOrganizationUpdateComponent),
    resolve: {
      cassLandingPageByOrganization: CassLandingPageByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/edit',
    loadComponent: () =>
      import('./update/cass-landing-page-by-organization-update').then(m => m.CassLandingPageByOrganizationUpdateComponent),
    resolve: {
      cassLandingPageByOrganization: CassLandingPageByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassLandingPageByOrganizationRoute;
