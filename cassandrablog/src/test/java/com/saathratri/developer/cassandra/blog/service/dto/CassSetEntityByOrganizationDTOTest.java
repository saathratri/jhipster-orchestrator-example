package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassSetEntityByOrganizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassSetEntityByOrganizationDTO.class);
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO1 = new CassSetEntityByOrganizationDTO();
        cassSetEntityByOrganizationDTO1.setOrganizationId(UUID.randomUUID());
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO2 = new CassSetEntityByOrganizationDTO();
        assertThat(cassSetEntityByOrganizationDTO1).isNotEqualTo(cassSetEntityByOrganizationDTO2);
        cassSetEntityByOrganizationDTO2.setOrganizationId(cassSetEntityByOrganizationDTO1.getOrganizationId());
        assertThat(cassSetEntityByOrganizationDTO1).isEqualTo(cassSetEntityByOrganizationDTO2);
        cassSetEntityByOrganizationDTO2.setOrganizationId(UUID.randomUUID());
        assertThat(cassSetEntityByOrganizationDTO1).isNotEqualTo(cassSetEntityByOrganizationDTO2);
        cassSetEntityByOrganizationDTO1.setOrganizationId(null);
        assertThat(cassSetEntityByOrganizationDTO1).isNotEqualTo(cassSetEntityByOrganizationDTO2);
    }
}
