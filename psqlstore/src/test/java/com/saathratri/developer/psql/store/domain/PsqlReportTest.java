package com.saathratri.developer.psql.store.domain;

import static com.saathratri.developer.psql.store.domain.PsqlProductTestSamples.*;
import static com.saathratri.developer.psql.store.domain.PsqlReportTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.store.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PsqlReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlReport.class);
        PsqlReport psqlReport1 = getPsqlReportSample1();
        PsqlReport psqlReport2 = new PsqlReport();
        assertThat(psqlReport1).isNotEqualTo(psqlReport2);

        psqlReport2.setId(psqlReport1.getId());
        assertThat(psqlReport1).isEqualTo(psqlReport2);

        psqlReport2 = getPsqlReportSample2();
        assertThat(psqlReport1).isNotEqualTo(psqlReport2);
    }

    @Test
    void productTest() {
        PsqlReport psqlReport = getPsqlReportRandomSampleGenerator();
        PsqlProduct psqlProductBack = getPsqlProductRandomSampleGenerator();

        psqlReport.setProduct(psqlProductBack);
        assertThat(psqlReport.getProduct()).isEqualTo(psqlProductBack);

        psqlReport.product(null);
        assertThat(psqlReport.getProduct()).isNull();
    }
}
