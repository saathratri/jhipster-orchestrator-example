package com.saathratri.developer.psql.blog.domain;

import static com.saathratri.developer.psql.blog.domain.PsqlTajUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PsqlTajUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlTajUser.class);
        PsqlTajUser psqlTajUser1 = getPsqlTajUserSample1();
        PsqlTajUser psqlTajUser2 = new PsqlTajUser();
        assertThat(psqlTajUser1).isNotEqualTo(psqlTajUser2);

        psqlTajUser2.setId(psqlTajUser1.getId());
        assertThat(psqlTajUser1).isEqualTo(psqlTajUser2);

        psqlTajUser2 = getPsqlTajUserSample2();
        assertThat(psqlTajUser1).isNotEqualTo(psqlTajUser2);
    }
}
