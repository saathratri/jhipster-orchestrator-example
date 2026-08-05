package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassSaathratriEntityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassSaathratriEntityDTO.class);
        CassSaathratriEntityDTO cassSaathratriEntityDTO1 = new CassSaathratriEntityDTO();
        cassSaathratriEntityDTO1.setEntityId(UUID.randomUUID());
        CassSaathratriEntityDTO cassSaathratriEntityDTO2 = new CassSaathratriEntityDTO();
        assertThat(cassSaathratriEntityDTO1).isNotEqualTo(cassSaathratriEntityDTO2);
        cassSaathratriEntityDTO2.setEntityId(cassSaathratriEntityDTO1.getEntityId());
        assertThat(cassSaathratriEntityDTO1).isEqualTo(cassSaathratriEntityDTO2);
        cassSaathratriEntityDTO2.setEntityId(UUID.randomUUID());
        assertThat(cassSaathratriEntityDTO1).isNotEqualTo(cassSaathratriEntityDTO2);
        cassSaathratriEntityDTO1.setEntityId(null);
        assertThat(cassSaathratriEntityDTO1).isNotEqualTo(cassSaathratriEntityDTO2);
    }
}
