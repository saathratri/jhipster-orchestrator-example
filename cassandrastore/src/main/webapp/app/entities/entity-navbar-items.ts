/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import NavbarItem from 'app/layouts/navbar/navbar-item.model';

export const EntityNavbarItems: NavbarItem[] = [
  {
    name: 'CassProduct',
    route: '/cassandrastore/cass-product',
    translationKey: 'global.menu.entities.cassandrastoreCassProduct',
  },
  {
    name: 'CassReport',
    route: '/cassandrastore/cass-report',
    translationKey: 'global.menu.entities.cassandrastoreCassReport',
  },
  /* jhipster-needle-add-entity-navbar - JHipster will add entity navbar items here */
];
