package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassTajUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassTajUserDTO.class);
        CassTajUserDTO cassTajUserDTO1 = new CassTajUserDTO();
        cassTajUserDTO1.setId(UUID.randomUUID());
        CassTajUserDTO cassTajUserDTO2 = new CassTajUserDTO();
        assertThat(cassTajUserDTO1).isNotEqualTo(cassTajUserDTO2);
        cassTajUserDTO2.setId(cassTajUserDTO1.getId());
        assertThat(cassTajUserDTO1).isEqualTo(cassTajUserDTO2);
        cassTajUserDTO2.setId(UUID.randomUUID());
        assertThat(cassTajUserDTO1).isNotEqualTo(cassTajUserDTO2);
        cassTajUserDTO1.setId(null);
        assertThat(cassTajUserDTO1).isNotEqualTo(cassTajUserDTO2);
    }
}
