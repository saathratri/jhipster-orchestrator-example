package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassBlogTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassBlogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassBlog.class);
        CassBlog cassBlog1 = getCassBlogSample1();
        cassBlog1.setCompositeId(new CassBlogId(UUID.randomUUID().toString(), UUID.randomUUID()));
        CassBlog cassBlog2 = new CassBlog();
        assertThat(cassBlog1).isNotEqualTo(cassBlog2);

        cassBlog2.setCompositeId(cassBlog1.getCompositeId());
        assertThat(cassBlog1).isEqualTo(cassBlog2);
        cassBlog2.setCompositeId(new CassBlogId(UUID.randomUUID().toString(), UUID.randomUUID()));
        cassBlog2 = getCassBlogSample2();
        assertThat(cassBlog1).isNotEqualTo(cassBlog2);
    }
}
