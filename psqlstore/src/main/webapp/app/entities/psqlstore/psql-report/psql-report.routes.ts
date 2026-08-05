/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import PsqlReportResolve from './route/psql-report-routing-resolve.service';

const psqlReportRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/psql-report').then(m => m.PsqlReport),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/psql-report-detail').then(m => m.PsqlReportDetail),
    resolve: {
      psqlReport: PsqlReportResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/psql-report-update').then(m => m.PsqlReportUpdate),
    resolve: {
      psqlReport: PsqlReportResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/psql-report-update').then(m => m.PsqlReportUpdate),
    resolve: {
      psqlReport: PsqlReportResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default psqlReportRoute;
