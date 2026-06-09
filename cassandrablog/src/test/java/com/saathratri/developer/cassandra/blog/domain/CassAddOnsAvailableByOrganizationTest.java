package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganizationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassAddOnsAvailableByOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassAddOnsAvailableByOrganization.class);
        CassAddOnsAvailableByOrganization cassAddOnsAvailableByOrganization1 = getCassAddOnsAvailableByOrganizationSample1();
        cassAddOnsAvailableByOrganization1.setCompositeId(
            new CassAddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        CassAddOnsAvailableByOrganization cassAddOnsAvailableByOrganization2 = new CassAddOnsAvailableByOrganization();
        assertThat(cassAddOnsAvailableByOrganization1).isNotEqualTo(cassAddOnsAvailableByOrganization2);

        cassAddOnsAvailableByOrganization2.setCompositeId(cassAddOnsAvailableByOrganization1.getCompositeId());
        assertThat(cassAddOnsAvailableByOrganization1).isEqualTo(cassAddOnsAvailableByOrganization2);
        cassAddOnsAvailableByOrganization2.setCompositeId(
            new CassAddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        cassAddOnsAvailableByOrganization2 = getCassAddOnsAvailableByOrganizationSample2();
        assertThat(cassAddOnsAvailableByOrganization1).isNotEqualTo(cassAddOnsAvailableByOrganization2);
    }
}
