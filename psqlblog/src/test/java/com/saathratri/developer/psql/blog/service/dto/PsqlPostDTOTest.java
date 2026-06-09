package com.saathratri.developer.psql.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PsqlPostDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlPostDTO.class);
        PsqlPostDTO psqlPostDTO1 = new PsqlPostDTO();
        psqlPostDTO1.setId(UUID.randomUUID());
        PsqlPostDTO psqlPostDTO2 = new PsqlPostDTO();
        assertThat(psqlPostDTO1).isNotEqualTo(psqlPostDTO2);
        psqlPostDTO2.setId(psqlPostDTO1.getId());
        assertThat(psqlPostDTO1).isEqualTo(psqlPostDTO2);
        psqlPostDTO2.setId(UUID.randomUUID());
        assertThat(psqlPostDTO1).isNotEqualTo(psqlPostDTO2);
        psqlPostDTO1.setId(null);
        assertThat(psqlPostDTO1).isNotEqualTo(psqlPostDTO2);
    }
}
