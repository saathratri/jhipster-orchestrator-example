package com.saathratri.developer.cassandra.store.service.mapper;

import com.saathratri.developer.cassandra.store.domain.CassProduct;
import com.saathratri.developer.cassandra.store.service.dto.CassProductDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassProduct} and its DTO {@link CassProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassProductMapper extends EntityMapper<CassProductDTO, CassProduct> {
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
