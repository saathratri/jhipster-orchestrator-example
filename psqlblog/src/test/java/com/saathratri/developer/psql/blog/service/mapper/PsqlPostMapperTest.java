package com.saathratri.developer.psql.blog.service.mapper;

import static com.saathratri.developer.psql.blog.domain.PsqlPostAsserts.*;
import static com.saathratri.developer.psql.blog.domain.PsqlPostTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PsqlPostMapperTest {

    private PsqlPostMapper psqlPostMapper;

    @BeforeEach
    void setUp() {
        psqlPostMapper = new PsqlPostMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPsqlPostSample1();
        var actual = psqlPostMapper.toEntity(psqlPostMapper.toDto(expected));
        assertPsqlPostAllPropertiesEquals(expected, actual);
    }
}
