package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassBlogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassBlogDTO.class);
        CassBlogDTO cassBlogDTO1 = new CassBlogDTO();
        cassBlogDTO1.setCompositeId(new CassBlogDTOId(UUID.randomUUID().toString(), UUID.randomUUID()));
        CassBlogDTO cassBlogDTO2 = new CassBlogDTO();
        assertThat(cassBlogDTO1).isNotEqualTo(cassBlogDTO2);
        cassBlogDTO2.setCompositeId(cassBlogDTO1.getCompositeId());
        assertThat(cassBlogDTO1).isEqualTo(cassBlogDTO2);
        cassBlogDTO2.setCompositeId(new CassBlogDTOId(UUID.randomUUID().toString(), UUID.randomUUID()));
        assertThat(cassBlogDTO1).isNotEqualTo(cassBlogDTO2);
        cassBlogDTO1.setCompositeId(null);
        assertThat(cassBlogDTO1).isNotEqualTo(cassBlogDTO2);
    }
}
