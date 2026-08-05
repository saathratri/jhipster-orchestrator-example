import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassSetEntityByOrganizationResolve from './route/cass-set-entity-by-organization-routing-resolve.service';

const cassSetEntityByOrganizationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-set-entity-by-organization').then(m => m.CassSetEntityByOrganizationComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/view',
    loadComponent: () => import('./detail/cass-set-entity-by-organization-detail').then(m => m.CassSetEntityByOrganizationDetailComponent),
    resolve: {
      cassSetEntityByOrganization: CassSetEntityByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-set-entity-by-organization-update').then(m => m.CassSetEntityByOrganizationUpdateComponent),
    resolve: {
      cassSetEntityByOrganization: CassSetEntityByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/edit',
    loadComponent: () => import('./update/cass-set-entity-by-organization-update').then(m => m.CassSetEntityByOrganizationUpdateComponent),
    resolve: {
      cassSetEntityByOrganization: CassSetEntityByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassSetEntityByOrganizationRoute;
