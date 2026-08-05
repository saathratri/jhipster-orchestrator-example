import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassTagResolve from './route/cass-tag-routing-resolve.service';

const cassTagRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-tag').then(m => m.CassTagComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/cass-tag-detail').then(m => m.CassTagDetailComponent),
    resolve: {
      cassTag: CassTagResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-tag-update').then(m => m.CassTagUpdateComponent),
    resolve: {
      cassTag: CassTagResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/cass-tag-update').then(m => m.CassTagUpdateComponent),
    resolve: {
      cassTag: CassTagResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassTagRoute;
