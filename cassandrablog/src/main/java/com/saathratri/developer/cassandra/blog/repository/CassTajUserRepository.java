package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassTajUser;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassTajUser entity.
 */
@Repository
public interface CassTajUserRepository extends CassandraRepository<CassTajUser, UUID> {}
