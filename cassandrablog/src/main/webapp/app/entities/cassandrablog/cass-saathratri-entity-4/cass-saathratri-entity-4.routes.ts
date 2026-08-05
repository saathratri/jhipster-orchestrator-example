import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassSaathratriEntity4Resolve from './route/cass-saathratri-entity-4-routing-resolve.service';

const cassSaathratriEntity4Route: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-saathratri-entity-4').then(m => m.CassSaathratriEntity4Component),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:attributeKey/view',
    loadComponent: () => import('./detail/cass-saathratri-entity-4-detail').then(m => m.CassSaathratriEntity4DetailComponent),
    resolve: {
      cassSaathratriEntity4: CassSaathratriEntity4Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-saathratri-entity-4-update').then(m => m.CassSaathratriEntity4UpdateComponent),
    resolve: {
      cassSaathratriEntity4: CassSaathratriEntity4Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:attributeKey/edit',
    loadComponent: () => import('./update/cass-saathratri-entity-4-update').then(m => m.CassSaathratriEntity4UpdateComponent),
    resolve: {
      cassSaathratriEntity4: CassSaathratriEntity4Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassSaathratriEntity4Route;
