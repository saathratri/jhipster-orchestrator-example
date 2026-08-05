import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'cass-product',
    data: { pageTitle: 'cassandrastoreApp.cassandrastoreCassProduct.home.title' },
    loadChildren: () => import('./cassandrastore/cass-product/cass-product.routes'),
  },
  {
    path: 'cass-report',
    data: { pageTitle: 'cassandrastoreApp.cassandrastoreCassReport.home.title' },
    loadChildren: () => import('./cassandrastore/cass-report/cass-report.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
