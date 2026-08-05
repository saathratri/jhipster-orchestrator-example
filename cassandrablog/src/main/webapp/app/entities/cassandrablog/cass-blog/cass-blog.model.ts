/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

export interface ICassBlog {
  compositeId: ICassBlogId;
  handle?: string | null;
  content?: string | null;
}
export interface ICassBlogId {
  category: string | null;
  blogId: string | null;
}

export type NewCassBlog = Omit<ICassBlog, 'compositeId'> & { compositeId: ICassBlogId };
