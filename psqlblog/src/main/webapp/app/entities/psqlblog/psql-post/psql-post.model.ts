/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import dayjs from 'dayjs/esm';

import { IPsqlBlog } from 'app/entities/psqlblog/psql-blog/psql-blog.model';
import { IPsqlTag } from 'app/entities/psqlblog/psql-tag/psql-tag.model';

export interface IPsqlPost {
  id: string;
  title?: string | null;
  content?: string | null;
  date?: dayjs.Dayjs | null;

  blog?: Pick<IPsqlBlog, 'id' | 'name' | 'handle'> | null;

  tags?: Pick<IPsqlTag, 'id' | 'name' | 'name'>[] | null;
}

export type NewPsqlPost = Omit<IPsqlPost, 'id'> & { id: null };
