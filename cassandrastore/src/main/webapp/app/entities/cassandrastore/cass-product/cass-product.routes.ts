import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassProductResolve from './route/cass-product-routing-resolve.service';

const cassProductRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-product').then(m => m.CassProductComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/cass-product-detail').then(m => m.CassProductDetailComponent),
    resolve: {
      cassProduct: CassProductResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-product-update').then(m => m.CassProductUpdateComponent),
    resolve: {
      cassProduct: CassProductResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/cass-product-update').then(m => m.CassProductUpdateComponent),
    resolve: {
      cassProduct: CassProductResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassProductRoute;
