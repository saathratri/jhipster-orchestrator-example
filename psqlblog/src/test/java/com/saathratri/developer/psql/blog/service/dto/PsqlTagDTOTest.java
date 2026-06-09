package com.saathratri.developer.psql.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PsqlTagDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlTagDTO.class);
        PsqlTagDTO psqlTagDTO1 = new PsqlTagDTO();
        psqlTagDTO1.setId(UUID.randomUUID());
        PsqlTagDTO psqlTagDTO2 = new PsqlTagDTO();
        assertThat(psqlTagDTO1).isNotEqualTo(psqlTagDTO2);
        psqlTagDTO2.setId(psqlTagDTO1.getId());
        assertThat(psqlTagDTO1).isEqualTo(psqlTagDTO2);
        psqlTagDTO2.setId(UUID.randomUUID());
        assertThat(psqlTagDTO1).isNotEqualTo(psqlTagDTO2);
        psqlTagDTO1.setId(null);
        assertThat(psqlTagDTO1).isNotEqualTo(psqlTagDTO2);
    }
}
