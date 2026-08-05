package com.saathratri.developer.psql.store.service.mapper;

import com.saathratri.developer.psql.store.domain.PsqlProduct;
import com.saathratri.developer.psql.store.service.dto.PsqlProductDTO;
import org.mapstruct.*;
import org.mapstruct.control.NoComplexMapping;

/**
 * Mapper for the entity {@link PsqlProduct} and its DTO {@link PsqlProductDTO}.
 *
 * Uses NoComplexMapping to prevent MapStruct from generating deep/complex mappings
 * that can cause infinite recursion with bidirectional entity relationships.
 * This limits MapStruct to direct field mappings only, requiring explicit @Mapping
 * annotations for any relationship mappings.
 */
@Mapper(componentModel = "spring", mappingControl = NoComplexMapping.class)
public interface PsqlProductMapper extends EntityMapper<PsqlProductDTO, PsqlProduct> {}
