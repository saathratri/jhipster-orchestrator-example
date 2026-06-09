package com.saathratri.developer.psql.blog.service.mapper;

import static com.saathratri.developer.psql.blog.domain.PsqlTajUserAsserts.*;
import static com.saathratri.developer.psql.blog.domain.PsqlTajUserTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PsqlTajUserMapperTest {

    private PsqlTajUserMapper psqlTajUserMapper;

    @BeforeEach
    void setUp() {
        psqlTajUserMapper = new PsqlTajUserMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPsqlTajUserSample1();
        var actual = psqlTajUserMapper.toEntity(psqlTajUserMapper.toDto(expected));
        assertPsqlTajUserAllPropertiesEquals(expected, actual);
    }
}
