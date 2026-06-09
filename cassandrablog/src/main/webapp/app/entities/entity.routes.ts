import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'cass-blog',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassBlog.home.title' },
    loadChildren: () => import('./cassandrablog/cass-blog/cass-blog.routes'),
  },
  {
    path: 'cass-post',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassPost.home.title' },
    loadChildren: () => import('./cassandrablog/cass-post/cass-post.routes'),
  },
  {
    path: 'cass-tag',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassTag.home.title' },
    loadChildren: () => import('./cassandrablog/cass-tag/cass-tag.routes'),
  },
  {
    path: 'cass-taj-user',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassTajUser.home.title' },
    loadChildren: () => import('./cassandrablog/cass-taj-user/cass-taj-user.routes'),
  },
  {
    path: 'cass-saathratri-entity',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassSaathratriEntity.home.title' },
    loadChildren: () => import('./cassandrablog/cass-saathratri-entity/cass-saathratri-entity.routes'),
  },
  {
    path: 'cass-saathratri-entity-2',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassSaathratriEntity2.home.title' },
    loadChildren: () => import('./cassandrablog/cass-saathratri-entity-2/cass-saathratri-entity-2.routes'),
  },
  {
    path: 'cass-saathratri-entity-3',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassSaathratriEntity3.home.title' },
    loadChildren: () => import('./cassandrablog/cass-saathratri-entity-3/cass-saathratri-entity-3.routes'),
  },
  {
    path: 'cass-saathratri-entity-4',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassSaathratriEntity4.home.title' },
    loadChildren: () => import('./cassandrablog/cass-saathratri-entity-4/cass-saathratri-entity-4.routes'),
  },
  {
    path: 'cass-add-ons-available-by-organization',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassAddOnsAvailableByOrganization.home.title' },
    loadChildren: () => import('./cassandrablog/cass-add-ons-available-by-organization/cass-add-ons-available-by-organization.routes'),
  },
  {
    path: 'cass-add-ons-selected-by-organization',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassAddOnsSelectedByOrganization.home.title' },
    loadChildren: () => import('./cassandrablog/cass-add-ons-selected-by-organization/cass-add-ons-selected-by-organization.routes'),
  },
  {
    path: 'cass-landing-page-by-organization',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassLandingPageByOrganization.home.title' },
    loadChildren: () => import('./cassandrablog/cass-landing-page-by-organization/cass-landing-page-by-organization.routes'),
  },
  {
    path: 'cass-set-entity-by-organization',
    data: { pageTitle: 'cassandrablogApp.cassandrablogCassSetEntityByOrganization.home.title' },
    loadChildren: () => import('./cassandrablog/cass-set-entity-by-organization/cass-set-entity-by-organization.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
