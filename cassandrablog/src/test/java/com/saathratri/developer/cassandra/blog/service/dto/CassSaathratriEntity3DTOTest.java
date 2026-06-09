package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassSaathratriEntity3DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassSaathratriEntity3DTO.class);
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO1 = new CassSaathratriEntity3DTO();
        cassSaathratriEntity3DTO1.setCompositeId(new CassSaathratriEntity3DTOId(UUID.randomUUID().toString(), UUID.randomUUID()));
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO2 = new CassSaathratriEntity3DTO();
        assertThat(cassSaathratriEntity3DTO1).isNotEqualTo(cassSaathratriEntity3DTO2);
        cassSaathratriEntity3DTO2.setCompositeId(cassSaathratriEntity3DTO1.getCompositeId());
        assertThat(cassSaathratriEntity3DTO1).isEqualTo(cassSaathratriEntity3DTO2);
        cassSaathratriEntity3DTO2.setCompositeId(new CassSaathratriEntity3DTOId(UUID.randomUUID().toString(), UUID.randomUUID()));
        assertThat(cassSaathratriEntity3DTO1).isNotEqualTo(cassSaathratriEntity3DTO2);
        cassSaathratriEntity3DTO1.setCompositeId(null);
        assertThat(cassSaathratriEntity3DTO1).isNotEqualTo(cassSaathratriEntity3DTO2);
    }
}
