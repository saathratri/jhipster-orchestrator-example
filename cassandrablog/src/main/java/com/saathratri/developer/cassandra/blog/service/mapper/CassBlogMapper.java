package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassBlog;
import com.saathratri.developer.cassandra.blog.service.dto.CassBlogDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassBlog} and its DTO {@link CassBlogDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassBlogMapper extends EntityMapper<CassBlogDTO, CassBlog> {
    @Mapping(target = "compositeId.category", source = "compositeId.category")
    @Mapping(target = "compositeId.blogId", source = "compositeId.blogId")
    CassBlog toEntity(CassBlogDTO cassBlogDTO);

    @Mapping(target = "compositeId.category", source = "compositeId.category")
    @Mapping(target = "compositeId.blogId", source = "compositeId.blogId")
    CassBlogDTO toDto(CassBlog cassBlog);

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
