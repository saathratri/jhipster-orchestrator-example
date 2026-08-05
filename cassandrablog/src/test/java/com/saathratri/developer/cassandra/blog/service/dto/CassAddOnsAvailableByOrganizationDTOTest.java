package com.saathratri.developer.cassandra.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassAddOnsAvailableByOrganizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassAddOnsAvailableByOrganizationDTO.class);
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO1 = new CassAddOnsAvailableByOrganizationDTO();
        cassAddOnsAvailableByOrganizationDTO1.setCompositeId(
            new CassAddOnsAvailableByOrganizationDTOId(
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                UUID.randomUUID(),
                UUID.randomUUID()
            )
        );
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO2 = new CassAddOnsAvailableByOrganizationDTO();
        assertThat(cassAddOnsAvailableByOrganizationDTO1).isNotEqualTo(cassAddOnsAvailableByOrganizationDTO2);
        cassAddOnsAvailableByOrganizationDTO2.setCompositeId(cassAddOnsAvailableByOrganizationDTO1.getCompositeId());
        assertThat(cassAddOnsAvailableByOrganizationDTO1).isEqualTo(cassAddOnsAvailableByOrganizationDTO2);
        cassAddOnsAvailableByOrganizationDTO2.setCompositeId(
            new CassAddOnsAvailableByOrganizationDTOId(
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                UUID.randomUUID(),
                UUID.randomUUID()
            )
        );
        assertThat(cassAddOnsAvailableByOrganizationDTO1).isNotEqualTo(cassAddOnsAvailableByOrganizationDTO2);
        cassAddOnsAvailableByOrganizationDTO1.setCompositeId(null);
        assertThat(cassAddOnsAvailableByOrganizationDTO1).isNotEqualTo(cassAddOnsAvailableByOrganizationDTO2);
    }
}
