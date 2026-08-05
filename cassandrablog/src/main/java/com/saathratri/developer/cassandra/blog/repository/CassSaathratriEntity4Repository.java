package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4Id;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassSaathratriEntity4 entity.
 */
@Repository
public interface CassSaathratriEntity4Repository extends CassandraRepository<CassSaathratriEntity4, CassSaathratriEntity4Id> {
    List<CassSaathratriEntity4> findAllByCompositeIdOrganizationId(final UUID organizationId);
    Slice<CassSaathratriEntity4> findAllByCompositeIdOrganizationId(final UUID organizationId, Pageable pageable);
    Optional<CassSaathratriEntity4> findByCompositeIdOrganizationIdAndCompositeIdAttributeKey(
        final UUID organizationId,
        final String attributeKey
    );
}
