package com.saathratri.developer.psql.blog.domain;

import static com.saathratri.developer.psql.blog.domain.PsqlBlogTestSamples.*;
import static com.saathratri.developer.psql.blog.domain.PsqlPostTestSamples.*;
import static com.saathratri.developer.psql.blog.domain.PsqlTajUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.blog.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PsqlBlogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlBlog.class);
        PsqlBlog psqlBlog1 = getPsqlBlogSample1();
        PsqlBlog psqlBlog2 = new PsqlBlog();
        assertThat(psqlBlog1).isNotEqualTo(psqlBlog2);

        psqlBlog2.setId(psqlBlog1.getId());
        assertThat(psqlBlog1).isEqualTo(psqlBlog2);

        psqlBlog2 = getPsqlBlogSample2();
        assertThat(psqlBlog1).isNotEqualTo(psqlBlog2);
    }

    @Test
    void tajUserTest() {
        PsqlBlog psqlBlog = getPsqlBlogRandomSampleGenerator();
        PsqlTajUser psqlTajUserBack = getPsqlTajUserRandomSampleGenerator();

        psqlBlog.setTajUser(psqlTajUserBack);
        assertThat(psqlBlog.getTajUser()).isEqualTo(psqlTajUserBack);

        psqlBlog.tajUser(null);
        assertThat(psqlBlog.getTajUser()).isNull();
    }

    @Test
    void postTest() {
        PsqlBlog psqlBlog = getPsqlBlogRandomSampleGenerator();
        PsqlPost psqlPostBack = getPsqlPostRandomSampleGenerator();

        psqlBlog.addPost(psqlPostBack);
        assertThat(psqlBlog.getPosts()).containsOnly(psqlPostBack);
        assertThat(psqlPostBack.getBlog()).isEqualTo(psqlBlog);

        psqlBlog.removePost(psqlPostBack);
        assertThat(psqlBlog.getPosts()).doesNotContain(psqlPostBack);
        assertThat(psqlPostBack.getBlog()).isNull();

        psqlBlog.posts(new HashSet<>(Set.of(psqlPostBack)));
        assertThat(psqlBlog.getPosts()).containsOnly(psqlPostBack);
        assertThat(psqlPostBack.getBlog()).isEqualTo(psqlBlog);

        psqlBlog.setPosts(new HashSet<>());
        assertThat(psqlBlog.getPosts()).doesNotContain(psqlPostBack);
        assertThat(psqlPostBack.getBlog()).isNull();
    }
}
