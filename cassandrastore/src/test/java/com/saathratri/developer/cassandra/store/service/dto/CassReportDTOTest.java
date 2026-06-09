package com.saathratri.developer.cassandra.store.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.store.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassReportDTO.class);
        CassReportDTO cassReportDTO1 = new CassReportDTO();
        cassReportDTO1.setId(UUID.randomUUID());
        CassReportDTO cassReportDTO2 = new CassReportDTO();
        assertThat(cassReportDTO1).isNotEqualTo(cassReportDTO2);
        cassReportDTO2.setId(cassReportDTO1.getId());
        assertThat(cassReportDTO1).isEqualTo(cassReportDTO2);
        cassReportDTO2.setId(UUID.randomUUID());
        assertThat(cassReportDTO1).isNotEqualTo(cassReportDTO2);
        cassReportDTO1.setId(null);
        assertThat(cassReportDTO1).isNotEqualTo(cassReportDTO2);
    }
}
