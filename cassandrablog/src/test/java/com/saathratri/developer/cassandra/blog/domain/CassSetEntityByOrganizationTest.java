package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassSetEntityByOrganizationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CassSetEntityByOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassSetEntityByOrganization.class);
        CassSetEntityByOrganization cassSetEntityByOrganization1 = getCassSetEntityByOrganizationSample1();
        CassSetEntityByOrganization cassSetEntityByOrganization2 = new CassSetEntityByOrganization();
        assertThat(cassSetEntityByOrganization1).isNotEqualTo(cassSetEntityByOrganization2);

        cassSetEntityByOrganization2.setOrganizationId(cassSetEntityByOrganization1.getOrganizationId());
        assertThat(cassSetEntityByOrganization1).isEqualTo(cassSetEntityByOrganization2);
        cassSetEntityByOrganization2 = getCassSetEntityByOrganizationSample2();
        assertThat(cassSetEntityByOrganization1).isNotEqualTo(cassSetEntityByOrganization2);
    }
}
