package com.saathratri.developer.psql.blog.service.mapper;

import com.saathratri.developer.psql.blog.domain.PsqlBlog;
import com.saathratri.developer.psql.blog.service.dto.PsqlBlogDTO;
import org.mapstruct.*;
import org.mapstruct.control.NoComplexMapping;

/**
 * Mapper for the entity {@link PsqlBlog} and its DTO {@link PsqlBlogDTO}.
 *
 * Uses NoComplexMapping to prevent MapStruct from generating deep/complex mappings
 * that can cause infinite recursion with bidirectional entity relationships.
 * This limits MapStruct to direct field mappings only, requiring explicit @Mapping
 * annotations for any relationship mappings.
 */
@Mapper(componentModel = "spring", mappingControl = NoComplexMapping.class)
public interface PsqlBlogMapper extends EntityMapper<PsqlBlogDTO, PsqlBlog> {
    default String map(byte[] value) {
        return value == null ? null : new String(value);
    }

    default byte[] map(String value) {
        return value == null ? null : value.getBytes();
    }
}
