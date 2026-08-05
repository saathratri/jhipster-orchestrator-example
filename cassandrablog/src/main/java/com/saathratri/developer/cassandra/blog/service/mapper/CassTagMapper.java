package com.saathratri.developer.cassandra.blog.service.mapper;

import com.datastax.oss.driver.api.core.data.CqlVector;
import com.saathratri.developer.cassandra.blog.domain.CassTag;
import com.saathratri.developer.cassandra.blog.service.dto.CassTagDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassTag} and its DTO {@link CassTagDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassTagMapper extends EntityMapper<CassTagDTO, CassTag> {
    @Mapping(target = "nameEmbedding", ignore = true)
    @Mapping(target = "descriptionEmbedding", ignore = true)
    CassTag toEntity(CassTagDTO cassTagDTO);

    @Mapping(target = "nameEmbedding", ignore = true)
    @Mapping(target = "descriptionEmbedding", ignore = true)
    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget CassTag entity, CassTagDTO dto);

    default Set<String> map(String value) {
        Set<String> theSet = new TreeSet<String>();
        if (value != null) {
            theSet.add(value);
        }
        return theSet;
    }

    default String map(Set<String> value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value.iterator().next();
    }

    default List<Float> mapCqlVectorToList(CqlVector<Float> vector) {
        if (vector == null) {
            return null;
        }
        List<Float> list = new ArrayList<>();
        for (Float f : vector) {
            list.add(f);
        }
        return list;
    }
}
