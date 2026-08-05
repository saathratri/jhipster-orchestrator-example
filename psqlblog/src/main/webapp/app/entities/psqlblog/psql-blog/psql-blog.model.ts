/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { IPsqlTajUser } from 'app/entities/psqlblog/psql-taj-user/psql-taj-user.model';

export interface IPsqlBlog {
  id: string;
  name?: string | null;
  handle?: string | null;

  tajUser?: Pick<IPsqlTajUser, 'id' | 'login'> | null;
}

export type NewPsqlBlog = Omit<IPsqlBlog, 'id'> & { id: null };
