package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassSetEntityByOrganization;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassSetEntityByOrganization entity.
 */
@Repository
public interface CassSetEntityByOrganizationRepository extends CassandraRepository<CassSetEntityByOrganization, UUID> {}
