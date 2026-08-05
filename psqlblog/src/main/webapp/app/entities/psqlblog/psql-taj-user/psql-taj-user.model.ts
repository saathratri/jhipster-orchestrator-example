/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

export interface IPsqlTajUser {
  id: string;
  login?: string | null;
}

export type NewPsqlTajUser = Omit<IPsqlTajUser, 'id'> & { id: null };
