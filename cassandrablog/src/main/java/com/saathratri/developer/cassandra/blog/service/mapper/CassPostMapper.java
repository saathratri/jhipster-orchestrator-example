package com.saathratri.developer.cassandra.blog.service.mapper;

import com.saathratri.developer.cassandra.blog.domain.CassPost;
import com.saathratri.developer.cassandra.blog.service.dto.CassPostDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassPost} and its DTO {@link CassPostDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassPostMapper extends EntityMapper<CassPostDTO, CassPost> {
    @Mapping(target = "compositeId.createdDate", source = "compositeId.createdDate")
    @Mapping(target = "compositeId.addedDateTime", source = "compositeId.addedDateTime")
    @Mapping(target = "compositeId.postId", source = "compositeId.postId")
    CassPost toEntity(CassPostDTO cassPostDTO);

    @Mapping(target = "compositeId.createdDate", source = "compositeId.createdDate")
    @Mapping(target = "compositeId.addedDateTime", source = "compositeId.addedDateTime")
    @Mapping(target = "compositeId.postId", source = "compositeId.postId")
    CassPostDTO toDto(CassPost cassPost);

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
