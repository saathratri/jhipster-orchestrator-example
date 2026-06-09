package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class CassSaathratriEntity2DTOTest {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassSaathratriEntity2DTO.class);
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO1 = new CassSaathratriEntity2DTO();
        cassSaathratriEntity2DTO1.setCompositeId(
            new CassSaathratriEntity2DTOId(UUID.randomUUID(), longCount.incrementAndGet(), longCount.incrementAndGet(), UUID.randomUUID())
        );
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO2 = new CassSaathratriEntity2DTO();
        assertThat(cassSaathratriEntity2DTO1).isNotEqualTo(cassSaathratriEntity2DTO2);
        cassSaathratriEntity2DTO2.setCompositeId(cassSaathratriEntity2DTO1.getCompositeId());
        assertThat(cassSaathratriEntity2DTO1).isEqualTo(cassSaathratriEntity2DTO2);
        cassSaathratriEntity2DTO2.setCompositeId(
            new CassSaathratriEntity2DTOId(UUID.randomUUID(), longCount.incrementAndGet(), longCount.incrementAndGet(), UUID.randomUUID())
        );
        assertThat(cassSaathratriEntity2DTO1).isNotEqualTo(cassSaathratriEntity2DTO2);
        cassSaathratriEntity2DTO1.setCompositeId(null);
        assertThat(cassSaathratriEntity2DTO1).isNotEqualTo(cassSaathratriEntity2DTO2);
    }
}
