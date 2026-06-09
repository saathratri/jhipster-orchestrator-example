package com.saathratri.developer.psql.store.service.mapper;

import static com.saathratri.developer.psql.store.domain.PsqlReportAsserts.*;
import static com.saathratri.developer.psql.store.domain.PsqlReportTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PsqlReportMapperTest {

    private PsqlReportMapper psqlReportMapper;

    @BeforeEach
    void setUp() {
        psqlReportMapper = new PsqlReportMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPsqlReportSample1();
        var actual = psqlReportMapper.toEntity(psqlReportMapper.toDto(expected));
        assertPsqlReportAllPropertiesEquals(expected, actual);
    }
}
