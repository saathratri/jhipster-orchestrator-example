package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassLandingPageByOrganization;
import com.saathratri.developer.cassandra.blog.service.dto.CassLandingPageByOrganizationDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassLandingPageByOrganization} and its DTO {@link CassLandingPageByOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassLandingPageByOrganizationMapper extends EntityMapper<CassLandingPageByOrganizationDTO, CassLandingPageByOrganization> {
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
