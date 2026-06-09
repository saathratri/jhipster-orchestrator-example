package com.saathratri.developer.cassandra.store.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.store.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassProductDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassProductDTO.class);
        CassProductDTO cassProductDTO1 = new CassProductDTO();
        cassProductDTO1.setId(UUID.randomUUID());
        CassProductDTO cassProductDTO2 = new CassProductDTO();
        assertThat(cassProductDTO1).isNotEqualTo(cassProductDTO2);
        cassProductDTO2.setId(cassProductDTO1.getId());
        assertThat(cassProductDTO1).isEqualTo(cassProductDTO2);
        cassProductDTO2.setId(UUID.randomUUID());
        assertThat(cassProductDTO1).isNotEqualTo(cassProductDTO2);
        cassProductDTO1.setId(null);
        assertThat(cassProductDTO1).isNotEqualTo(cassProductDTO2);
    }
}
