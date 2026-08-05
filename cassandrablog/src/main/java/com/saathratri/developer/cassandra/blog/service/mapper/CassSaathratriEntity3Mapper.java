package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity3DTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassSaathratriEntity3} and its DTO {@link CassSaathratriEntity3DTO}.
 */
@Mapper(componentModel = "spring")
public interface CassSaathratriEntity3Mapper extends EntityMapper<CassSaathratriEntity3DTO, CassSaathratriEntity3> {
    @Mapping(target = "compositeId.entityType", source = "compositeId.entityType")
    @Mapping(target = "compositeId.createdTimeId", source = "compositeId.createdTimeId")
    CassSaathratriEntity3 toEntity(CassSaathratriEntity3DTO cassSaathratriEntity3DTO);

    @Mapping(target = "compositeId.entityType", source = "compositeId.entityType")
    @Mapping(target = "compositeId.createdTimeId", source = "compositeId.createdTimeId")
    CassSaathratriEntity3DTO toDto(CassSaathratriEntity3 cassSaathratriEntity3);

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
}
