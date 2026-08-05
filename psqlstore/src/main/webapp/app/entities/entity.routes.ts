import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'psql-product',
    data: { pageTitle: 'psqlstoreApp.psqlstorePsqlProduct.home.title' },
    loadChildren: () => import('./psqlstore/psql-product/psql-product.routes'),
  },
  {
    path: 'psql-report',
    data: { pageTitle: 'psqlstoreApp.psqlstorePsqlReport.home.title' },
    loadChildren: () => import('./psqlstore/psql-report/psql-report.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
