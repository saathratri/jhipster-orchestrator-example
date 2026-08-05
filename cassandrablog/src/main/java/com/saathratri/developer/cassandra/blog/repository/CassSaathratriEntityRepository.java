package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassSaathratriEntity entity.
 */
@Repository
public interface CassSaathratriEntityRepository extends CassandraRepository<CassSaathratriEntity, UUID> {}
