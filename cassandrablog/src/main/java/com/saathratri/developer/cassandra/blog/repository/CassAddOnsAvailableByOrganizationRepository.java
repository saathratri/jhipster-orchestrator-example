package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganization;
import com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganizationId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassAddOnsAvailableByOrganization entity.
 */
@Repository
public interface CassAddOnsAvailableByOrganizationRepository
    extends CassandraRepository<CassAddOnsAvailableByOrganization, CassAddOnsAvailableByOrganizationId>
{
    @org.springframework.data.cassandra.repository.AllowFiltering
    List<CassAddOnsAvailableByOrganization> findAllByCompositeIdOrganizationId(final UUID organizationId);

    @org.springframework.data.cassandra.repository.AllowFiltering
    Slice<CassAddOnsAvailableByOrganization> findAllByCompositeIdOrganizationId(final UUID organizationId, Pageable pageable);

    @org.springframework.data.cassandra.repository.AllowFiltering
    List<CassAddOnsAvailableByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType
    );

    @org.springframework.data.cassandra.repository.AllowFiltering
    Slice<CassAddOnsAvailableByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType,
        Pageable pageable
    );

    List<CassAddOnsAvailableByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId
    );
    Slice<CassAddOnsAvailableByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        Pageable pageable
    );
    Optional<
        CassAddOnsAvailableByOrganization
    > findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        final UUID addOnId
    );
}
