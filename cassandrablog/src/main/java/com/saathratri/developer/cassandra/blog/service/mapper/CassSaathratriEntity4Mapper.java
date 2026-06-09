package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity4DTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassSaathratriEntity4} and its DTO {@link CassSaathratriEntity4DTO}.
 */
@Mapper(componentModel = "spring")
public interface CassSaathratriEntity4Mapper extends EntityMapper<CassSaathratriEntity4DTO, CassSaathratriEntity4> {
    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.attributeKey", source = "compositeId.attributeKey")
    CassSaathratriEntity4 toEntity(CassSaathratriEntity4DTO cassSaathratriEntity4DTO);

    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.attributeKey", source = "compositeId.attributeKey")
    CassSaathratriEntity4DTO toDto(CassSaathratriEntity4 cassSaathratriEntity4);

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
