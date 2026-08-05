/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import dayjs from 'dayjs/esm';

import { IPsqlProduct } from 'app/entities/psqlstore/psql-product/psql-product.model';

export interface IPsqlReport {
  id: string;
  fileName?: string | null;
  fileExtension?: string | null;
  createDate?: dayjs.Dayjs | null;
  file?: string | null;
  fileContentType?: string | null;
  approved?: boolean | null;

  product?: Pick<IPsqlProduct, 'id'> | null;
}

export type NewPsqlReport = Omit<IPsqlReport, 'id'> & { id: null };
