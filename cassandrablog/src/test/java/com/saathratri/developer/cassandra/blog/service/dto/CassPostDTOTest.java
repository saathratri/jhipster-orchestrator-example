package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

class CassPostDTOTest {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassPostDTO.class);
        CassPostDTO cassPostDTO1 = new CassPostDTO();
        cassPostDTO1.setCompositeId(new CassPostDTOId(longCount.incrementAndGet(), longCount.incrementAndGet(), UUID.randomUUID()));
        CassPostDTO cassPostDTO2 = new CassPostDTO();
        assertThat(cassPostDTO1).isNotEqualTo(cassPostDTO2);
        cassPostDTO2.setCompositeId(cassPostDTO1.getCompositeId());
        assertThat(cassPostDTO1).isEqualTo(cassPostDTO2);
        cassPostDTO2.setCompositeId(new CassPostDTOId(longCount.incrementAndGet(), longCount.incrementAndGet(), UUID.randomUUID()));
        assertThat(cassPostDTO1).isNotEqualTo(cassPostDTO2);
        cassPostDTO1.setCompositeId(null);
        assertThat(cassPostDTO1).isNotEqualTo(cassPostDTO2);
    }
}
