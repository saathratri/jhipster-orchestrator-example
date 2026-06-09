package com.saathratri.developer.psql.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.blog.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PsqlBlogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlBlogDTO.class);
        PsqlBlogDTO psqlBlogDTO1 = new PsqlBlogDTO();
        psqlBlogDTO1.setId(UUID.randomUUID());
        PsqlBlogDTO psqlBlogDTO2 = new PsqlBlogDTO();
        assertThat(psqlBlogDTO1).isNotEqualTo(psqlBlogDTO2);
        psqlBlogDTO2.setId(psqlBlogDTO1.getId());
        assertThat(psqlBlogDTO1).isEqualTo(psqlBlogDTO2);
        psqlBlogDTO2.setId(UUID.randomUUID());
        assertThat(psqlBlogDTO1).isNotEqualTo(psqlBlogDTO2);
        psqlBlogDTO1.setId(null);
        assertThat(psqlBlogDTO1).isNotEqualTo(psqlBlogDTO2);
    }
}
