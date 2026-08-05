import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import PsqlTagResolve from './route/psql-tag-routing-resolve.service';

const psqlTagRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/psql-tag').then(m => m.PsqlTag),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/psql-tag-detail').then(m => m.PsqlTagDetail),
    resolve: {
      psqlTag: PsqlTagResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/psql-tag-update').then(m => m.PsqlTagUpdate),
    resolve: {
      psqlTag: PsqlTagResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/psql-tag-update').then(m => m.PsqlTagUpdate),
    resolve: {
      psqlTag: PsqlTagResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default psqlTagRoute;
