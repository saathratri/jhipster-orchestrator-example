package com.saathratri.developer.psql.blog.service.mapper;

import com.saathratri.developer.psql.blog.domain.PsqlTag;
import com.saathratri.developer.psql.blog.service.dto.PsqlTagDTO;
import org.mapstruct.*;
import org.mapstruct.control.NoComplexMapping;

/**
 * Mapper for the entity {@link PsqlTag} and its DTO {@link PsqlTagDTO}.
 *
 * Uses NoComplexMapping to prevent MapStruct from generating deep/complex mappings
 * that can cause infinite recursion with bidirectional entity relationships.
 * This limits MapStruct to direct field mappings only, requiring explicit @Mapping
 * annotations for any relationship mappings.
 */
@Mapper(componentModel = "spring", mappingControl = NoComplexMapping.class)
public interface PsqlTagMapper extends EntityMapper<PsqlTagDTO, PsqlTag> {
    PsqlTagDTO toDto(PsqlTag s);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "nameEmbedding", ignore = true)
    @Mapping(target = "descriptionEmbedding", ignore = true)
    void partialUpdate(@MappingTarget PsqlTag entity, PsqlTagDTO dto);

    @Mapping(target = "nameEmbedding", ignore = true)
    @Mapping(target = "descriptionEmbedding", ignore = true)
    PsqlTag toEntity(PsqlTagDTO psqlTagDTO);

    default String map(byte[] value) {
        return value == null ? null : new String(value);
    }

    default byte[] map(String value) {
        return value == null ? null : value.getBytes();
    }

    default java.util.List<Float> mapFloatArrayToList(float[] array) {
        if (array == null) {
            return null;
        }
        java.util.List<Float> list = new java.util.ArrayList<>(array.length);
        for (float f : array) {
            list.add(f);
        }
        return list;
    }
}
