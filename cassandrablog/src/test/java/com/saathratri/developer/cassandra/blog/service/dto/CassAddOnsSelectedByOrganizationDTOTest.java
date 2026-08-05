package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class CassAddOnsSelectedByOrganizationDTOTest {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassAddOnsSelectedByOrganizationDTO.class);
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO1 = new CassAddOnsSelectedByOrganizationDTO();
        cassAddOnsSelectedByOrganizationDTO1.setCompositeId(
            new CassAddOnsSelectedByOrganizationDTOId(
                UUID.randomUUID(),
                longCount.incrementAndGet(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO2 = new CassAddOnsSelectedByOrganizationDTO();
        assertThat(cassAddOnsSelectedByOrganizationDTO1).isNotEqualTo(cassAddOnsSelectedByOrganizationDTO2);
        cassAddOnsSelectedByOrganizationDTO2.setCompositeId(cassAddOnsSelectedByOrganizationDTO1.getCompositeId());
        assertThat(cassAddOnsSelectedByOrganizationDTO1).isEqualTo(cassAddOnsSelectedByOrganizationDTO2);
        cassAddOnsSelectedByOrganizationDTO2.setCompositeId(
            new CassAddOnsSelectedByOrganizationDTOId(
                UUID.randomUUID(),
                longCount.incrementAndGet(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );
        assertThat(cassAddOnsSelectedByOrganizationDTO1).isNotEqualTo(cassAddOnsSelectedByOrganizationDTO2);
        cassAddOnsSelectedByOrganizationDTO1.setCompositeId(null);
        assertThat(cassAddOnsSelectedByOrganizationDTO1).isNotEqualTo(cassAddOnsSelectedByOrganizationDTO2);
    }
}
