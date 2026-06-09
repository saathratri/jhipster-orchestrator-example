package com.saathratri.developer.cassandra.store.repository;

import com.saathratri.developer.cassandra.store.domain.CassReport;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassReport entity.
 */
@Repository
public interface CassReportRepository extends CassandraRepository<CassReport, UUID> {}
