import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassSaathratriEntity3Resolve from './route/cass-saathratri-entity-3-routing-resolve.service';

const cassSaathratriEntity3Route: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-saathratri-entity-3').then(m => m.CassSaathratriEntity3Component),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityType/:createdTimeId/view',
    loadComponent: () => import('./detail/cass-saathratri-entity-3-detail').then(m => m.CassSaathratriEntity3DetailComponent),
    resolve: {
      cassSaathratriEntity3: CassSaathratriEntity3Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-saathratri-entity-3-update').then(m => m.CassSaathratriEntity3UpdateComponent),
    resolve: {
      cassSaathratriEntity3: CassSaathratriEntity3Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityType/:createdTimeId/edit',
    loadComponent: () => import('./update/cass-saathratri-entity-3-update').then(m => m.CassSaathratriEntity3UpdateComponent),
    resolve: {
      cassSaathratriEntity3: CassSaathratriEntity3Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassSaathratriEntity3Route;
