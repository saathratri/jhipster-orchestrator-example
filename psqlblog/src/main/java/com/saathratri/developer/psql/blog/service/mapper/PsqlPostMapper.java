package com.saathratri.developer.psql.blog.service.mapper;

import com.saathratri.developer.psql.blog.domain.PsqlPost;
import com.saathratri.developer.psql.blog.service.dto.PsqlPostDTO;
import org.mapstruct.*;
import org.mapstruct.control.NoComplexMapping;

/**
 * Mapper for the entity {@link PsqlPost} and its DTO {@link PsqlPostDTO}.
 *
 * Uses NoComplexMapping to prevent MapStruct from generating deep/complex mappings
 * that can cause infinite recursion with bidirectional entity relationships.
 * This limits MapStruct to direct field mappings only, requiring explicit @Mapping
 * annotations for any relationship mappings.
 */
@Mapper(componentModel = "spring", mappingControl = NoComplexMapping.class)
public interface PsqlPostMapper extends EntityMapper<PsqlPostDTO, PsqlPost> {
    default String map(byte[] value) {
        return value == null ? null : new String(value);
    }

    default byte[] map(String value) {
        return value == null ? null : value.getBytes();
    }
}
