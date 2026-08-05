package com.saathratri.developer.psql.blog.service.mapper;

import static com.saathratri.developer.psql.blog.domain.PsqlBlogAsserts.*;
import static com.saathratri.developer.psql.blog.domain.PsqlBlogTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PsqlBlogMapperTest {

    private PsqlBlogMapper psqlBlogMapper;

    @BeforeEach
    void setUp() {
        psqlBlogMapper = new PsqlBlogMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPsqlBlogSample1();
        var actual = psqlBlogMapper.toEntity(psqlBlogMapper.toDto(expected));
        assertPsqlBlogAllPropertiesEquals(expected, actual);
    }
}
