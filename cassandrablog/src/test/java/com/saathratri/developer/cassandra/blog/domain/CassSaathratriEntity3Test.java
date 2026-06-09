package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassSaathratriEntity3Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassSaathratriEntity3.class);
        CassSaathratriEntity3 cassSaathratriEntity31 = getCassSaathratriEntity3Sample1();
        cassSaathratriEntity31.setCompositeId(new CassSaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));
        CassSaathratriEntity3 cassSaathratriEntity32 = new CassSaathratriEntity3();
        assertThat(cassSaathratriEntity31).isNotEqualTo(cassSaathratriEntity32);

        cassSaathratriEntity32.setCompositeId(cassSaathratriEntity31.getCompositeId());
        assertThat(cassSaathratriEntity31).isEqualTo(cassSaathratriEntity32);
        cassSaathratriEntity32.setCompositeId(new CassSaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));
        cassSaathratriEntity32 = getCassSaathratriEntity3Sample2();
        assertThat(cassSaathratriEntity31).isNotEqualTo(cassSaathratriEntity32);
    }
}
