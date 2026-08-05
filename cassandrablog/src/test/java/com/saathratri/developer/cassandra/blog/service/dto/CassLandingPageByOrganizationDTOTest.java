package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassLandingPageByOrganizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassLandingPageByOrganizationDTO.class);
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO1 = new CassLandingPageByOrganizationDTO();
        cassLandingPageByOrganizationDTO1.setOrganizationId(UUID.randomUUID());
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO2 = new CassLandingPageByOrganizationDTO();
        assertThat(cassLandingPageByOrganizationDTO1).isNotEqualTo(cassLandingPageByOrganizationDTO2);
        cassLandingPageByOrganizationDTO2.setOrganizationId(cassLandingPageByOrganizationDTO1.getOrganizationId());
        assertThat(cassLandingPageByOrganizationDTO1).isEqualTo(cassLandingPageByOrganizationDTO2);
        cassLandingPageByOrganizationDTO2.setOrganizationId(UUID.randomUUID());
        assertThat(cassLandingPageByOrganizationDTO1).isNotEqualTo(cassLandingPageByOrganizationDTO2);
        cassLandingPageByOrganizationDTO1.setOrganizationId(null);
        assertThat(cassLandingPageByOrganizationDTO1).isNotEqualTo(cassLandingPageByOrganizationDTO2);
    }
}
