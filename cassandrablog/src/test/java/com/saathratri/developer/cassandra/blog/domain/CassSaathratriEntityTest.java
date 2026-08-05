/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CassSaathratriEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassSaathratriEntity.class);
        CassSaathratriEntity cassSaathratriEntity1 = getCassSaathratriEntitySample1();
        CassSaathratriEntity cassSaathratriEntity2 = new CassSaathratriEntity();
        assertThat(cassSaathratriEntity1).isNotEqualTo(cassSaathratriEntity2);

        cassSaathratriEntity2.setEntityId(cassSaathratriEntity1.getEntityId());
        assertThat(cassSaathratriEntity1).isEqualTo(cassSaathratriEntity2);
        cassSaathratriEntity2 = getCassSaathratriEntitySample2();
        assertThat(cassSaathratriEntity1).isNotEqualTo(cassSaathratriEntity2);
    }
}
