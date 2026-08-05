/*
 * Copyright (c) 2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

package com.saathratri.developer.psql.store.service.mapper;

import static com.saathratri.developer.psql.store.domain.PsqlProductAsserts.*;
import static com.saathratri.developer.psql.store.domain.PsqlProductTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PsqlProductMapperTest {

    private PsqlProductMapper psqlProductMapper;

    @BeforeEach
    void setUp() {
        psqlProductMapper = new PsqlProductMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPsqlProductSample1();
        var actual = psqlProductMapper.toEntity(psqlProductMapper.toDto(expected));
        assertPsqlProductAllPropertiesEquals(expected, actual);
    }
}
