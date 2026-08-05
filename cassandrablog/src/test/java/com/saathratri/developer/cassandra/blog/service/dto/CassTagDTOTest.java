package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassTagDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassTagDTO.class);
        CassTagDTO cassTagDTO1 = new CassTagDTO();
        cassTagDTO1.setId(UUID.randomUUID());
        CassTagDTO cassTagDTO2 = new CassTagDTO();
        assertThat(cassTagDTO1).isNotEqualTo(cassTagDTO2);
        cassTagDTO2.setId(cassTagDTO1.getId());
        assertThat(cassTagDTO1).isEqualTo(cassTagDTO2);
        cassTagDTO2.setId(UUID.randomUUID());
        assertThat(cassTagDTO1).isNotEqualTo(cassTagDTO2);
        cassTagDTO1.setId(null);
        assertThat(cassTagDTO1).isNotEqualTo(cassTagDTO2);
    }
}
