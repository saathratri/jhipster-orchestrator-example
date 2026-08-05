import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassPostResolve from './route/cass-post-routing-resolve.service';

const cassPostRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-post').then(m => m.CassPostComponent),
    data: {
      defaultSort: `createdDate,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':createdDate/:addedDateTime/:postId/view',
    loadComponent: () => import('./detail/cass-post-detail').then(m => m.CassPostDetailComponent),
    resolve: {
      cassPost: CassPostResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-post-update').then(m => m.CassPostUpdateComponent),
    resolve: {
      cassPost: CassPostResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':createdDate/:addedDateTime/:postId/edit',
    loadComponent: () => import('./update/cass-post-update').then(m => m.CassPostUpdateComponent),
    resolve: {
      cassPost: CassPostResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassPostRoute;
