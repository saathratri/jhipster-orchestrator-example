package com.saathratri.developer.psql.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PsqlTajUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlTajUserDTO.class);
        PsqlTajUserDTO psqlTajUserDTO1 = new PsqlTajUserDTO();
        psqlTajUserDTO1.setId(UUID.randomUUID());
        PsqlTajUserDTO psqlTajUserDTO2 = new PsqlTajUserDTO();
        assertThat(psqlTajUserDTO1).isNotEqualTo(psqlTajUserDTO2);
        psqlTajUserDTO2.setId(psqlTajUserDTO1.getId());
        assertThat(psqlTajUserDTO1).isEqualTo(psqlTajUserDTO2);
        psqlTajUserDTO2.setId(UUID.randomUUID());
        assertThat(psqlTajUserDTO1).isNotEqualTo(psqlTajUserDTO2);
        psqlTajUserDTO1.setId(null);
        assertThat(psqlTajUserDTO1).isNotEqualTo(psqlTajUserDTO2);
    }
}
