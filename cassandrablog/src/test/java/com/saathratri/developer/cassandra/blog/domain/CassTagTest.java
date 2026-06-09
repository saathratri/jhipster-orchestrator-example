package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassTagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CassTagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassTag.class);
        CassTag cassTag1 = getCassTagSample1();
        CassTag cassTag2 = new CassTag();
        assertThat(cassTag1).isNotEqualTo(cassTag2);

        cassTag2.setId(cassTag1.getId());
        assertThat(cassTag1).isEqualTo(cassTag2);
        cassTag2 = getCassTagSample2();
        assertThat(cassTag1).isNotEqualTo(cassTag2);
    }
}
