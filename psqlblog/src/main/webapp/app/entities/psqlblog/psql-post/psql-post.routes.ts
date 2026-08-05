import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import PsqlPostResolve from './route/psql-post-routing-resolve.service';

const psqlPostRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/psql-post').then(m => m.PsqlPost),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/psql-post-detail').then(m => m.PsqlPostDetail),
    resolve: {
      psqlPost: PsqlPostResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/psql-post-update').then(m => m.PsqlPostUpdate),
    resolve: {
      psqlPost: PsqlPostResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/psql-post-update').then(m => m.PsqlPostUpdate),
    resolve: {
      psqlPost: PsqlPostResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default psqlPostRoute;
