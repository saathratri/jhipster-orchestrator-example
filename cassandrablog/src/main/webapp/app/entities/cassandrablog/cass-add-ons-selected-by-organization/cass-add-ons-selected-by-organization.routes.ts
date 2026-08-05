import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassAddOnsSelectedByOrganizationResolve from './route/cass-add-ons-selected-by-organization-routing-resolve.service';

const cassAddOnsSelectedByOrganizationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-add-ons-selected-by-organization').then(m => m.CassAddOnsSelectedByOrganizationComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:arrivalDate/:accountNumber/:createdTimeId/view',
    loadComponent: () =>
      import('./detail/cass-add-ons-selected-by-organization-detail').then(m => m.CassAddOnsSelectedByOrganizationDetailComponent),
    resolve: {
      cassAddOnsSelectedByOrganization: CassAddOnsSelectedByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/cass-add-ons-selected-by-organization-update').then(m => m.CassAddOnsSelectedByOrganizationUpdateComponent),
    resolve: {
      cassAddOnsSelectedByOrganization: CassAddOnsSelectedByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:arrivalDate/:accountNumber/:createdTimeId/edit',
    loadComponent: () =>
      import('./update/cass-add-ons-selected-by-organization-update').then(m => m.CassAddOnsSelectedByOrganizationUpdateComponent),
    resolve: {
      cassAddOnsSelectedByOrganization: CassAddOnsSelectedByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassAddOnsSelectedByOrganizationRoute;
