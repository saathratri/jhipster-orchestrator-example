package com.saathratri.developer.cassandra.store.domain;

import static com.saathratri.developer.cassandra.store.domain.CassProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.store.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CassProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassProduct.class);
        CassProduct cassProduct1 = getCassProductSample1();
        CassProduct cassProduct2 = new CassProduct();
        assertThat(cassProduct1).isNotEqualTo(cassProduct2);

        cassProduct2.setId(cassProduct1.getId());
        assertThat(cassProduct1).isEqualTo(cassProduct2);
        cassProduct2 = getCassProductSample2();
        assertThat(cassProduct1).isNotEqualTo(cassProduct2);
    }
}
