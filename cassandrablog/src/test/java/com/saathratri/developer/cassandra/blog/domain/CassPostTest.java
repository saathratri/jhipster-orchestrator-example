package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassPostTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CassPostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassPost.class);
        CassPost cassPost1 = getCassPostSample1();
        cassPost1.setCompositeId(new CassPostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));
        CassPost cassPost2 = new CassPost();
        assertThat(cassPost1).isNotEqualTo(cassPost2);

        cassPost2.setCompositeId(cassPost1.getCompositeId());
        assertThat(cassPost1).isEqualTo(cassPost2);
        cassPost2.setCompositeId(new CassPostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));
        cassPost2 = getCassPostSample2();
        assertThat(cassPost1).isNotEqualTo(cassPost2);
    }
}
