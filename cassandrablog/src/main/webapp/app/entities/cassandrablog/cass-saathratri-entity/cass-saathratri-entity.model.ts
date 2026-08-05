/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

export interface ICassSaathratriEntity {
  entityId: string;
  entityName?: string | null;
  entityDescription?: string | null;
  entityCost?: number | null;
  createdId?: string | null;
  createdTimeId?: string | null;
}

export type NewCassSaathratriEntity = Omit<ICassSaathratriEntity, 'entityId'> & { entityId: string };
