package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassLandingPageByOrganization;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassLandingPageByOrganization entity.
 */
@Repository
public interface CassLandingPageByOrganizationRepository extends CassandraRepository<CassLandingPageByOrganization, UUID> {}
