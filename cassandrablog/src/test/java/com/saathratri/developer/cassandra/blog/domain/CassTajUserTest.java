package com.saathratri.developer.cassandra.blog.domain;

import static com.saathratri.developer.cassandra.blog.domain.CassTajUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.cassandra.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CassTajUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CassTajUser.class);
        CassTajUser cassTajUser1 = getCassTajUserSample1();
        CassTajUser cassTajUser2 = new CassTajUser();
        assertThat(cassTajUser1).isNotEqualTo(cassTajUser2);

        cassTajUser2.setId(cassTajUser1.getId());
        assertThat(cassTajUser1).isEqualTo(cassTajUser2);
        cassTajUser2 = getCassTajUserSample2();
        assertThat(cassTajUser1).isNotEqualTo(cassTajUser2);
    }
}
