/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

export interface ICassSetEntityByOrganization {
  organizationId: string;
  tags?: Set<string> | null;
}

export type NewCassSetEntityByOrganization = Omit<ICassSetEntityByOrganization, 'organizationId'> & { organizationId: string };
