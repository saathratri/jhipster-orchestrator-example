/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassLandingPageByOrganizationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CassLandingPageByOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassLandingPageByOrganization.class);
        CassLandingPageByOrganization cassLandingPageByOrganization1 = getCassLandingPageByOrganizationSample1();
        CassLandingPageByOrganization cassLandingPageByOrganization2 = new CassLandingPageByOrganization();
        assertThat(cassLandingPageByOrganization1).isNotEqualTo(cassLandingPageByOrganization2);

        cassLandingPageByOrganization2.setOrganizationId(cassLandingPageByOrganization1.getOrganizationId());
        assertThat(cassLandingPageByOrganization1).isEqualTo(cassLandingPageByOrganization2);
        cassLandingPageByOrganization2 = getCassLandingPageByOrganizationSample2();
        assertThat(cassLandingPageByOrganization1).isNotEqualTo(cassLandingPageByOrganization2);
    }
}
