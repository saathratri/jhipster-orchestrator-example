package com.saathratri.developer.psql.blog.domain;

import static com.saathratri.developer.psql.blog.domain.PsqlBlogTestSamples.*;
import static com.saathratri.developer.psql.blog.domain.PsqlPostTestSamples.*;
import static com.saathratri.developer.psql.blog.domain.PsqlTagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.blog.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PsqlPostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlPost.class);
        PsqlPost psqlPost1 = getPsqlPostSample1();
        PsqlPost psqlPost2 = new PsqlPost();
        assertThat(psqlPost1).isNotEqualTo(psqlPost2);

        psqlPost2.setId(psqlPost1.getId());
        assertThat(psqlPost1).isEqualTo(psqlPost2);

        psqlPost2 = getPsqlPostSample2();
        assertThat(psqlPost1).isNotEqualTo(psqlPost2);
    }

    @Test
    void blogTest() {
        PsqlPost psqlPost = getPsqlPostRandomSampleGenerator();
        PsqlBlog psqlBlogBack = getPsqlBlogRandomSampleGenerator();

        psqlPost.setBlog(psqlBlogBack);
        assertThat(psqlPost.getBlog()).isEqualTo(psqlBlogBack);

        psqlPost.blog(null);
        assertThat(psqlPost.getBlog()).isNull();
    }

    @Test
    void tagTest() {
        PsqlPost psqlPost = getPsqlPostRandomSampleGenerator();
        PsqlTag psqlTagBack = getPsqlTagRandomSampleGenerator();

        psqlPost.addTag(psqlTagBack);
        assertThat(psqlPost.getTags()).containsOnly(psqlTagBack);

        psqlPost.removeTag(psqlTagBack);
        assertThat(psqlPost.getTags()).doesNotContain(psqlTagBack);

        psqlPost.tags(new HashSet<>(Set.of(psqlTagBack)));
        assertThat(psqlPost.getTags()).containsOnly(psqlTagBack);

        psqlPost.setTags(new HashSet<>());
        assertThat(psqlPost.getTags()).doesNotContain(psqlTagBack);
    }
}
