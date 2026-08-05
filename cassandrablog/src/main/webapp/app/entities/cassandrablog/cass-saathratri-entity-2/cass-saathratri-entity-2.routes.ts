import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassSaathratriEntity2Resolve from './route/cass-saathratri-entity-2-routing-resolve.service';

const cassSaathratriEntity2Route: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-saathratri-entity-2').then(m => m.CassSaathratriEntity2Component),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId/view',
    loadComponent: () => import('./detail/cass-saathratri-entity-2-detail').then(m => m.CassSaathratriEntity2DetailComponent),
    resolve: {
      cassSaathratriEntity2: CassSaathratriEntity2Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-saathratri-entity-2-update').then(m => m.CassSaathratriEntity2UpdateComponent),
    resolve: {
      cassSaathratriEntity2: CassSaathratriEntity2Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId/edit',
    loadComponent: () => import('./update/cass-saathratri-entity-2-update').then(m => m.CassSaathratriEntity2UpdateComponent),
    resolve: {
      cassSaathratriEntity2: CassSaathratriEntity2Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassSaathratriEntity2Route;
