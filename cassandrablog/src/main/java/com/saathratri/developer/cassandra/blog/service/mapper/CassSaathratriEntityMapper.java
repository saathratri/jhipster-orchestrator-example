package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntityDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassSaathratriEntity} and its DTO {@link CassSaathratriEntityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassSaathratriEntityMapper extends EntityMapper<CassSaathratriEntityDTO, CassSaathratriEntity> {
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
