import NavbarItem from 'app/layouts/navbar/navbar-item.model';

export const EntityNavbarItems: NavbarItem[] = [
  {
    name: 'PsqlBlog',
    route: '/psqlblog/psql-blog',
    translationKey: 'global.menu.entities.psqlblogPsqlBlog',
  },
  {
    name: 'PsqlPost',
    route: '/psqlblog/psql-post',
    translationKey: 'global.menu.entities.psqlblogPsqlPost',
  },
  {
    name: 'PsqlTag',
    route: '/psqlblog/psql-tag',
    translationKey: 'global.menu.entities.psqlblogPsqlTag',
  },
  {
    name: 'PsqlTajUser',
    route: '/psqlblog/psql-taj-user',
    translationKey: 'global.menu.entities.psqlblogPsqlTajUser',
  },
  /* jhipster-needle-add-entity-navbar - JHipster will add entity navbar items here */
];
