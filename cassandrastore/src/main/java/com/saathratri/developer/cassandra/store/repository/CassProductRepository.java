package com.saathratri.developer.cassandra.store.repository;

import com.saathratri.developer.cassandra.store.domain.CassProduct;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassProduct entity.
 */
@Repository
public interface CassProductRepository extends CassandraRepository<CassProduct, UUID> {}
