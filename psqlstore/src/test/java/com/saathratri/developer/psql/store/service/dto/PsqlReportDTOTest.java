package com.saathratri.developer.psql.store.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.store.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PsqlReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlReportDTO.class);
        PsqlReportDTO psqlReportDTO1 = new PsqlReportDTO();
        psqlReportDTO1.setId(UUID.randomUUID());
        PsqlReportDTO psqlReportDTO2 = new PsqlReportDTO();
        assertThat(psqlReportDTO1).isNotEqualTo(psqlReportDTO2);
        psqlReportDTO2.setId(psqlReportDTO1.getId());
        assertThat(psqlReportDTO1).isEqualTo(psqlReportDTO2);
        psqlReportDTO2.setId(UUID.randomUUID());
        assertThat(psqlReportDTO1).isNotEqualTo(psqlReportDTO2);
        psqlReportDTO1.setId(null);
        assertThat(psqlReportDTO1).isNotEqualTo(psqlReportDTO2);
    }
}
