package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity2DTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassSaathratriEntity2} and its DTO {@link CassSaathratriEntity2DTO}.
 */
@Mapper(componentModel = "spring")
public interface CassSaathratriEntity2Mapper extends EntityMapper<CassSaathratriEntity2DTO, CassSaathratriEntity2> {
    @Mapping(target = "compositeId.entityTypeId", source = "compositeId.entityTypeId")
    @Mapping(target = "compositeId.yearOfDateAdded", source = "compositeId.yearOfDateAdded")
    @Mapping(target = "compositeId.arrivalDate", source = "compositeId.arrivalDate")
    @Mapping(target = "compositeId.blogId", source = "compositeId.blogId")
    CassSaathratriEntity2 toEntity(CassSaathratriEntity2DTO cassSaathratriEntity2DTO);

    @Mapping(target = "compositeId.entityTypeId", source = "compositeId.entityTypeId")
    @Mapping(target = "compositeId.yearOfDateAdded", source = "compositeId.yearOfDateAdded")
    @Mapping(target = "compositeId.arrivalDate", source = "compositeId.arrivalDate")
    @Mapping(target = "compositeId.blogId", source = "compositeId.blogId")
    CassSaathratriEntity2DTO toDto(CassSaathratriEntity2 cassSaathratriEntity2);

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
