import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassBlogResolve from './route/cass-blog-routing-resolve.service';

const cassBlogRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-blog').then(m => m.CassBlogComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':category/:blogId/view',
    loadComponent: () => import('./detail/cass-blog-detail').then(m => m.CassBlogDetailComponent),
    resolve: {
      cassBlog: CassBlogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-blog-update').then(m => m.CassBlogUpdateComponent),
    resolve: {
      cassBlog: CassBlogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':category/:blogId/edit',
    loadComponent: () => import('./update/cass-blog-update').then(m => m.CassBlogUpdateComponent),
    resolve: {
      cassBlog: CassBlogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassBlogRoute;
