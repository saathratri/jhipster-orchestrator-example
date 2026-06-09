package com.saathratri.developer.psql.blog.service.mapper;

import static com.saathratri.developer.psql.blog.domain.PsqlTagAsserts.*;
import static com.saathratri.developer.psql.blog.domain.PsqlTagTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PsqlTagMapperTest {

    private PsqlTagMapper psqlTagMapper;

    @BeforeEach
    void setUp() {
        psqlTagMapper = new PsqlTagMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPsqlTagSample1();
        var actual = psqlTagMapper.toEntity(psqlTagMapper.toDto(expected));
        assertPsqlTagAllPropertiesEquals(expected, actual);
    }
}
