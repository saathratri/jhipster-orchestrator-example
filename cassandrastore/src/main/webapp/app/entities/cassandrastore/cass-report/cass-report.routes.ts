import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import CassReportResolve from './route/cass-report-routing-resolve.service';

const cassReportRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cass-report').then(m => m.CassReportComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/cass-report-detail').then(m => m.CassReportDetailComponent),
    resolve: {
      cassReport: CassReportResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cass-report-update').then(m => m.CassReportUpdateComponent),
    resolve: {
      cassReport: CassReportResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/cass-report-update').then(m => m.CassReportUpdateComponent),
    resolve: {
      cassReport: CassReportResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cassReportRoute;
