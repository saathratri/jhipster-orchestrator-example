package com.saathratri.developer.psql.store.service.mapper;

import com.saathratri.developer.psql.store.domain.PsqlReport;
import com.saathratri.developer.psql.store.service.dto.PsqlReportDTO;
import org.mapstruct.*;
import org.mapstruct.control.NoComplexMapping;

/**
 * Mapper for the entity {@link PsqlReport} and its DTO {@link PsqlReportDTO}.
 *
 * Uses NoComplexMapping to prevent MapStruct from generating deep/complex mappings
 * that can cause infinite recursion with bidirectional entity relationships.
 * This limits MapStruct to direct field mappings only, requiring explicit @Mapping
 * annotations for any relationship mappings.
 */
@Mapper(componentModel = "spring", mappingControl = NoComplexMapping.class)
public interface PsqlReportMapper extends EntityMapper<PsqlReportDTO, PsqlReport> {}
