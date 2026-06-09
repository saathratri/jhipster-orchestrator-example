import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassAddOnsAvailableByOrganizationResolve from './route/cass-add-ons-available-by-organization-routing-resolve.service';

const cassAddOnsAvailableByOrganizationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-add-ons-available-by-organization').then(m => m.CassAddOnsAvailableByOrganizationComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:entityType/:entityId/:addOnId/view',
    loadComponent: () =>
      import('./detail/cass-add-ons-available-by-organization-detail').then(m => m.CassAddOnsAvailableByOrganizationDetailComponent),
    resolve: {
      cassAddOnsAvailableByOrganization: CassAddOnsAvailableByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/cass-add-ons-available-by-organization-update').then(m => m.CassAddOnsAvailableByOrganizationUpdateComponent),
    resolve: {
      cassAddOnsAvailableByOrganization: CassAddOnsAvailableByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:entityType/:entityId/:addOnId/edit',
    loadComponent: () =>
      import('./update/cass-add-ons-available-by-organization-update').then(m => m.CassAddOnsAvailableByOrganizationUpdateComponent),
    resolve: {
      cassAddOnsAvailableByOrganization: CassAddOnsAvailableByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassAddOnsAvailableByOrganizationRoute;
