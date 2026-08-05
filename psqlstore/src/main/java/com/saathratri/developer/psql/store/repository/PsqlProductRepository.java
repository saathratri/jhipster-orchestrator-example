/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

package com.saathratri.developer.psql.store.repository;

import com.saathratri.developer.psql.store.domain.PsqlProduct;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PsqlProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PsqlProductRepository extends JpaRepository<PsqlProduct, UUID> {}
