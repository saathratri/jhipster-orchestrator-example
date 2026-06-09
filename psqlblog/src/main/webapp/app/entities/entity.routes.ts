import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'psql-blog',
    data: { pageTitle: 'psqlblogApp.psqlblogPsqlBlog.home.title' },
    loadChildren: () => import('./psqlblog/psql-blog/psql-blog.routes'),
  },
  {
    path: 'psql-post',
    data: { pageTitle: 'psqlblogApp.psqlblogPsqlPost.home.title' },
    loadChildren: () => import('./psqlblog/psql-post/psql-post.routes'),
  },
  {
    path: 'psql-tag',
    data: { pageTitle: 'psqlblogApp.psqlblogPsqlTag.home.title' },
    loadChildren: () => import('./psqlblog/psql-tag/psql-tag.routes'),
  },
  {
    path: 'psql-taj-user',
    data: { pageTitle: 'psqlblogApp.psqlblogPsqlTajUser.home.title' },
    loadChildren: () => import('./psqlblog/psql-taj-user/psql-taj-user.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
