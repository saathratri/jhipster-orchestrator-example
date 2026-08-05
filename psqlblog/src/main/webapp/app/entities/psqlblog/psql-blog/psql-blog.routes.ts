import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import PsqlBlogResolve from './route/psql-blog-routing-resolve.service';

const psqlBlogRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/psql-blog').then(m => m.PsqlBlog),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/psql-blog-detail').then(m => m.PsqlBlogDetail),
    resolve: {
      psqlBlog: PsqlBlogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/psql-blog-update').then(m => m.PsqlBlogUpdate),
    resolve: {
      psqlBlog: PsqlBlogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/psql-blog-update').then(m => m.PsqlBlogUpdate),
    resolve: {
      psqlBlog: PsqlBlogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default psqlBlogRoute;
