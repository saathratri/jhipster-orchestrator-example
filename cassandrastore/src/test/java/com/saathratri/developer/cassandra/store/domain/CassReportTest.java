package com.saathratri.developer.cassandra.store.domain;

import static com.saathratri.developer.cassandra.store.domain.CassReportTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.store.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CassReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassReport.class);
        CassReport cassReport1 = getCassReportSample1();
        CassReport cassReport2 = new CassReport();
        assertThat(cassReport1).isNotEqualTo(cassReport2);

        cassReport2.setId(cassReport1.getId());
        assertThat(cassReport1).isEqualTo(cassReport2);
        cassReport2 = getCassReportSample2();
        assertThat(cassReport1).isNotEqualTo(cassReport2);
    }
}
