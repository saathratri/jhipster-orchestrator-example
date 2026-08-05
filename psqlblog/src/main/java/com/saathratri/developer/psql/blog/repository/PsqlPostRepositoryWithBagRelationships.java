/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

package com.saathratri.developer.psql.blog.repository;

import com.saathratri.developer.psql.blog.domain.PsqlPost;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PsqlPostRepositoryWithBagRelationships {
    Optional<PsqlPost> fetchBagRelationships(Optional<PsqlPost> psqlPost);

    List<PsqlPost> fetchBagRelationships(List<PsqlPost> psqlPosts);

    Page<PsqlPost> fetchBagRelationships(Page<PsqlPost> psqlPosts);
}
