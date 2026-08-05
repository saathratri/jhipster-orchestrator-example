import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import PsqlProductResolve from './route/psql-product-routing-resolve.service';

const psqlProductRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/psql-product').then(m => m.PsqlProduct),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/psql-product-detail').then(m => m.PsqlProductDetail),
    resolve: {
      psqlProduct: PsqlProductResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/psql-product-update').then(m => m.PsqlProductUpdate),
    resolve: {
      psqlProduct: PsqlProductResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/psql-product-update').then(m => m.PsqlProductUpdate),
    resolve: {
      psqlProduct: PsqlProductResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default psqlProductRoute;
