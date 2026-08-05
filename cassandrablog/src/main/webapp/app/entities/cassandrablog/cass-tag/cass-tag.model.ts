/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

export interface ICassTag {
  id: string;
  name?: string | null;
  description?: string | null;
  nameEmbedding?: number[] | null;
  descriptionEmbedding?: number[] | null;
}

export type NewCassTag = Omit<ICassTag, 'id'> & { id: string };
