import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassSaathratriEntityResolve from './route/cass-saathratri-entity-routing-resolve.service';

const cassSaathratriEntityRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-saathratri-entity').then(m => m.CassSaathratriEntityComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityId/view',
    loadComponent: () => import('./detail/cass-saathratri-entity-detail').then(m => m.CassSaathratriEntityDetailComponent),
    resolve: {
      cassSaathratriEntity: CassSaathratriEntityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-saathratri-entity-update').then(m => m.CassSaathratriEntityUpdateComponent),
    resolve: {
      cassSaathratriEntity: CassSaathratriEntityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityId/edit',
    loadComponent: () => import('./update/cass-saathratri-entity-update').then(m => m.CassSaathratriEntityUpdateComponent),
    resolve: {
      cassSaathratriEntity: CassSaathratriEntityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassSaathratriEntityRoute;
