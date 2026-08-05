package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganization;
import com.saathratri.developer.cassandra.blog.service.dto.CassAddOnsSelectedByOrganizationDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassAddOnsSelectedByOrganization} and its DTO {@link CassAddOnsSelectedByOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassAddOnsSelectedByOrganizationMapper
    extends EntityMapper<CassAddOnsSelectedByOrganizationDTO, CassAddOnsSelectedByOrganization>
{
    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.arrivalDate", source = "compositeId.arrivalDate")
    @Mapping(target = "compositeId.accountNumber", source = "compositeId.accountNumber")
    @Mapping(target = "compositeId.createdTimeId", source = "compositeId.createdTimeId")
    CassAddOnsSelectedByOrganization toEntity(CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO);

    @Mapping(target = "compositeId.organizationId", source = "compositeId.organizationId")
    @Mapping(target = "compositeId.arrivalDate", source = "compositeId.arrivalDate")
    @Mapping(target = "compositeId.accountNumber", source = "compositeId.accountNumber")
    @Mapping(target = "compositeId.createdTimeId", source = "compositeId.createdTimeId")
    CassAddOnsSelectedByOrganizationDTO toDto(CassAddOnsSelectedByOrganization cassAddOnsSelectedByOrganization);

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
