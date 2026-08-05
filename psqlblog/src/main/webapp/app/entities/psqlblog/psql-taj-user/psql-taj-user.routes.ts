import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import PsqlTajUserResolve from './route/psql-taj-user-routing-resolve.service';

const psqlTajUserRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/psql-taj-user').then(m => m.PsqlTajUser),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/psql-taj-user-detail').then(m => m.PsqlTajUserDetail),
    resolve: {
      psqlTajUser: PsqlTajUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/psql-taj-user-update').then(m => m.PsqlTajUserUpdate),
    resolve: {
      psqlTajUser: PsqlTajUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/psql-taj-user-update').then(m => m.PsqlTajUserUpdate),
    resolve: {
      psqlTajUser: PsqlTajUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default psqlTajUserRoute;
