package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassTajUser;
import com.saathratri.developer.cassandra.blog.service.dto.CassTajUserDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassTajUser} and its DTO {@link CassTajUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassTajUserMapper extends EntityMapper<CassTajUserDTO, CassTajUser> {
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
