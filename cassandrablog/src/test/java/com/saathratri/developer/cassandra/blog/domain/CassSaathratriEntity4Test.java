package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassSaathratriEntity4Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassSaathratriEntity4.class);
        CassSaathratriEntity4 cassSaathratriEntity41 = getCassSaathratriEntity4Sample1();
        cassSaathratriEntity41.setCompositeId(new CassSaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));
        CassSaathratriEntity4 cassSaathratriEntity42 = new CassSaathratriEntity4();
        assertThat(cassSaathratriEntity41).isNotEqualTo(cassSaathratriEntity42);

        cassSaathratriEntity42.setCompositeId(cassSaathratriEntity41.getCompositeId());
        assertThat(cassSaathratriEntity41).isEqualTo(cassSaathratriEntity42);
        cassSaathratriEntity42.setCompositeId(new CassSaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));
        cassSaathratriEntity42 = getCassSaathratriEntity4Sample2();
        assertThat(cassSaathratriEntity41).isNotEqualTo(cassSaathratriEntity42);
    }
}
