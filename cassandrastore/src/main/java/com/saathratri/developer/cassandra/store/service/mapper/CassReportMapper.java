package com.saathratri.developer.cassandra.store.service.mapper;

import com.saathratri.developer.cassandra.store.domain.CassReport;
import com.saathratri.developer.cassandra.store.service.dto.CassReportDTO;
import java.util.Set;
import java.util.TreeSet;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CassReport} and its DTO {@link CassReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface CassReportMapper extends EntityMapper<CassReportDTO, CassReport> {
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
