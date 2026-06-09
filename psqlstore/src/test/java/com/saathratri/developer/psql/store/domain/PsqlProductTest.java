package com.saathratri.developer.psql.store.domain;

import static com.saathratri.developer.psql.store.domain.PsqlProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.store.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PsqlProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlProduct.class);
        PsqlProduct psqlProduct1 = getPsqlProductSample1();
        PsqlProduct psqlProduct2 = new PsqlProduct();
        assertThat(psqlProduct1).isNotEqualTo(psqlProduct2);

        psqlProduct2.setId(psqlProduct1.getId());
        assertThat(psqlProduct1).isEqualTo(psqlProduct2);

        psqlProduct2 = getPsqlProductSample2();
        assertThat(psqlProduct1).isNotEqualTo(psqlProduct2);
    }
}
