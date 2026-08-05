package com.saathratri.developer.cassandra.blog.repository;

import com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganization;
import com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganizationId;
import java.util.List;
import java.util.Optional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the CassAddOnsSelectedByOrganization entity.
 */
@Repository
public interface CassAddOnsSelectedByOrganizationRepository
    extends CassandraRepository<CassAddOnsSelectedByOrganization, CassAddOnsSelectedByOrganizationId>
{
    List<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationId(final UUID organizationId);
    Slice<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationId(final UUID organizationId, Pageable pageable);
    List<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
    Slice<CassAddOnsSelectedByOrganization> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        Pageable pageable
    );
    Optional<
        CassAddOnsSelectedByOrganization
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    List<
        CassAddOnsSelectedByOrganization
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        CassAddOnsSelectedByOrganization
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<
        CassAddOnsSelectedByOrganization
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        CassAddOnsSelectedByOrganization
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<
        CassAddOnsSelectedByOrganization
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        CassAddOnsSelectedByOrganization
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<
        CassAddOnsSelectedByOrganization
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        CassAddOnsSelectedByOrganization
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );

    @Query(
        "SELECT * FROM cass_add_ons_selected_by_organization WHERE organization_id = ?0 AND arrival_date = ?1 AND account_number = ?2 LIMIT 1"
    )
    Optional<CassAddOnsSelectedByOrganization> findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
}
