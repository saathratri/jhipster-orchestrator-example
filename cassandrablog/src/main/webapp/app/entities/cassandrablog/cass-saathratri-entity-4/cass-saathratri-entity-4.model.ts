/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

export interface ICassSaathratriEntity4 {
  compositeId: ICassSaathratriEntity4Id;
  attributeValue?: string | null;
}
export interface ICassSaathratriEntity4Id {
  organizationId: string | null;
  attributeKey: string | null;
}

export type NewCassSaathratriEntity4 = Omit<ICassSaathratriEntity4, 'compositeId'> & { compositeId: ICassSaathratriEntity4Id };
