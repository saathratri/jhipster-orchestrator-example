import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassTajUserResolve from './route/cass-taj-user-routing-resolve.service';

const cassTajUserRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-taj-user').then(m => m.CassTajUserComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/cass-taj-user-detail').then(m => m.CassTajUserDetailComponent),
    resolve: {
      cassTajUser: CassTajUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-taj-user-update').then(m => m.CassTajUserUpdateComponent),
    resolve: {
      cassTajUser: CassTajUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/cass-taj-user-update').then(m => m.CassTajUserUpdateComponent),
    resolve: {
      cassTajUser: CassTajUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassTajUserRoute;
