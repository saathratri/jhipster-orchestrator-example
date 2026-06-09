package com.saathratri.developer.psql.store.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.store.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PsqlProductDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlProductDTO.class);
        PsqlProductDTO psqlProductDTO1 = new PsqlProductDTO();
        psqlProductDTO1.setId(UUID.randomUUID());
        PsqlProductDTO psqlProductDTO2 = new PsqlProductDTO();
        assertThat(psqlProductDTO1).isNotEqualTo(psqlProductDTO2);
        psqlProductDTO2.setId(psqlProductDTO1.getId());
        assertThat(psqlProductDTO1).isEqualTo(psqlProductDTO2);
        psqlProductDTO2.setId(UUID.randomUUID());
        assertThat(psqlProductDTO1).isNotEqualTo(psqlProductDTO2);
        psqlProductDTO1.setId(null);
        assertThat(psqlProductDTO1).isNotEqualTo(psqlProductDTO2);
    }
}
