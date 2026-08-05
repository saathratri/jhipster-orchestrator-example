package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganization;
import com.saathratri.developer.cassandra.blog.service.dto.CassAddOnsAvailableByOrganizationDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassAddOnsAvailableByOrganization} and its DTO {@link CassAddOnsAvailableByOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassAddOnsAvailableByOrganizationMapper
    extends EntityMapper<CassAddOnsAvailableByOrganizationDTO, CassAddOnsAvailableByOrganization>
{
    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.entityType", source = "compositeId.entityType")
    @Mapping(target = "compositeId.entityId", source = "compositeId.entityId")
    @Mapping(target = "compositeId.addOnId", source = "compositeId.addOnId")
    CassAddOnsAvailableByOrganization toEntity(CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO);

    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.entityType", source = "compositeId.entityType")
    @Mapping(target = "compositeId.entityId", source = "compositeId.entityId")
    @Mapping(target = "compositeId.addOnId", source = "compositeId.addOnId")
    CassAddOnsAvailableByOrganizationDTO toDto(CassAddOnsAvailableByOrganization cassAddOnsAvailableByOrganization);

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
