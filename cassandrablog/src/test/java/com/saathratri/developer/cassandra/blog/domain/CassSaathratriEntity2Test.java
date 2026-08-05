package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassSaathratriEntity2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassSaathratriEntity2.class);
        CassSaathratriEntity2 cassSaathratriEntity21 = getCassSaathratriEntity2Sample1();
        cassSaathratriEntity21.setCompositeId(
            new CassSaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );
        CassSaathratriEntity2 cassSaathratriEntity22 = new CassSaathratriEntity2();
        assertThat(cassSaathratriEntity21).isNotEqualTo(cassSaathratriEntity22);

        cassSaathratriEntity22.setCompositeId(cassSaathratriEntity21.getCompositeId());
        assertThat(cassSaathratriEntity21).isEqualTo(cassSaathratriEntity22);
        cassSaathratriEntity22.setCompositeId(
            new CassSaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );
        cassSaathratriEntity22 = getCassSaathratriEntity2Sample2();
        assertThat(cassSaathratriEntity21).isNotEqualTo(cassSaathratriEntity22);
    }
}
