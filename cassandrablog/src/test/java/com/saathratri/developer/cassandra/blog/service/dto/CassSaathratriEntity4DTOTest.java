package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassSaathratriEntity4DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassSaathratriEntity4DTO.class);
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO1 = new CassSaathratriEntity4DTO();
        cassSaathratriEntity4DTO1.setCompositeId(new CassSaathratriEntity4DTOId(UUID.randomUUID(), UUID.randomUUID().toString()));
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO2 = new CassSaathratriEntity4DTO();
        assertThat(cassSaathratriEntity4DTO1).isNotEqualTo(cassSaathratriEntity4DTO2);
        cassSaathratriEntity4DTO2.setCompositeId(cassSaathratriEntity4DTO1.getCompositeId());
        assertThat(cassSaathratriEntity4DTO1).isEqualTo(cassSaathratriEntity4DTO2);
        cassSaathratriEntity4DTO2.setCompositeId(new CassSaathratriEntity4DTOId(UUID.randomUUID(), UUID.randomUUID().toString()));
        assertThat(cassSaathratriEntity4DTO1).isNotEqualTo(cassSaathratriEntity4DTO2);
        cassSaathratriEntity4DTO1.setCompositeId(null);
        assertThat(cassSaathratriEntity4DTO1).isNotEqualTo(cassSaathratriEntity4DTO2);
    }
}
