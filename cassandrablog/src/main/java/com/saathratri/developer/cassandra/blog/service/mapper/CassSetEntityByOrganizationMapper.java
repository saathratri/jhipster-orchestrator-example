package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassSetEntityByOrganization;
import com.saathratri.developer.cassandra.blog.service.dto.CassSetEntityByOrganizationDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassSetEntityByOrganization} and its DTO {@link CassSetEntityByOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassSetEntityByOrganizationMapper extends EntityMapper<CassSetEntityByOrganizationDTO, CassSetEntityByOrganization> {
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
