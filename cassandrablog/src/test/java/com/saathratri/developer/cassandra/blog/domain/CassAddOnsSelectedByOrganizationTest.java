package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganizationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassAddOnsSelectedByOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassAddOnsSelectedByOrganization.class);
        CassAddOnsSelectedByOrganization cassAddOnsSelectedByOrganization1 = getCassAddOnsSelectedByOrganizationSample1();
        cassAddOnsSelectedByOrganization1.setCompositeId(
            new CassAddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );
        CassAddOnsSelectedByOrganization cassAddOnsSelectedByOrganization2 = new CassAddOnsSelectedByOrganization();
        assertThat(cassAddOnsSelectedByOrganization1).isNotEqualTo(cassAddOnsSelectedByOrganization2);

        cassAddOnsSelectedByOrganization2.setCompositeId(cassAddOnsSelectedByOrganization1.getCompositeId());
        assertThat(cassAddOnsSelectedByOrganization1).isEqualTo(cassAddOnsSelectedByOrganization2);
        cassAddOnsSelectedByOrganization2.setCompositeId(
            new CassAddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );
        cassAddOnsSelectedByOrganization2 = getCassAddOnsSelectedByOrganizationSample2();
        assertThat(cassAddOnsSelectedByOrganization1).isNotEqualTo(cassAddOnsSelectedByOrganization2);
    }
}
