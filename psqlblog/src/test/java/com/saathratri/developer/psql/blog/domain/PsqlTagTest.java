package com.saathratri.developer.psql.blog.domain;

import static com.saathratri.developer.psql.blog.domain.PsqlPostTestSamples.*;
import static com.saathratri.developer.psql.blog.domain.PsqlTagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.saathratri.developer.psql.blog.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PsqlTagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PsqlTag.class);
        PsqlTag psqlTag1 = getPsqlTagSample1();
        PsqlTag psqlTag2 = new PsqlTag();
        assertThat(psqlTag1).isNotEqualTo(psqlTag2);

        psqlTag2.setId(psqlTag1.getId());
        assertThat(psqlTag1).isEqualTo(psqlTag2);

        psqlTag2 = getPsqlTagSample2();
        assertThat(psqlTag1).isNotEqualTo(psqlTag2);
    }

    @Test
    void postTest() {
        PsqlTag psqlTag = getPsqlTagRandomSampleGenerator();
        PsqlPost psqlPostBack = getPsqlPostRandomSampleGenerator();

        psqlTag.addPost(psqlPostBack);
        assertThat(psqlTag.getPosts()).containsOnly(psqlPostBack);
        assertThat(psqlPostBack.getTags()).containsOnly(psqlTag);

        psqlTag.removePost(psqlPostBack);
        assertThat(psqlTag.getPosts()).doesNotContain(psqlPostBack);
        assertThat(psqlPostBack.getTags()).doesNotContain(psqlTag);

        psqlTag.posts(new HashSet<>(Set.of(psqlPostBack)));
        assertThat(psqlTag.getPosts()).containsOnly(psqlPostBack);
        assertThat(psqlPostBack.getTags()).containsOnly(psqlTag);

        psqlTag.setPosts(new HashSet<>());
        assertThat(psqlTag.getPosts()).doesNotContain(psqlPostBack);
        assertThat(psqlPostBack.getTags()).doesNotContain(psqlTag);
    }
}
