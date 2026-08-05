package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganizationId;
import com.saathratri.developer.cassandra.blog.service.dto.CassAddOnsSelectedByOrganizationDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganization}.
 */
public interface CassAddOnsSelectedByOrganizationService {
    /**
     * Save a cassAddOnsSelectedByOrganization.
     *
     * @param cassAddOnsSelectedByOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    CassAddOnsSelectedByOrganizationDTO save(CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO);

    /**
     * Updates a cassAddOnsSelectedByOrganization.
     *
     * @param cassAddOnsSelectedByOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    CassAddOnsSelectedByOrganizationDTO update(CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO);

    /**
     * Partially updates a cassAddOnsSelectedByOrganization.
     *
     * @param cassAddOnsSelectedByOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassAddOnsSelectedByOrganizationDTO> partialUpdate(CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO);

    /**
     * Get all the cassAddOnsSelectedByOrganizations.
     *
     * @return the list of entities.
     */
    List<CassAddOnsSelectedByOrganizationDTO> findAll();

    /**
     * Get the "id" cassAddOnsSelectedByOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassAddOnsSelectedByOrganizationDTO> findOne(CassAddOnsSelectedByOrganizationId id);

    /**
     * Delete the "id" cassAddOnsSelectedByOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(CassAddOnsSelectedByOrganizationId id);

    /**
     * Get all the cassAddOnsSelectedByOrganizations with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassAddOnsSelectedByOrganizationDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);

    List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationId(final UUID organizationId);
    Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdPageable(final UUID organizationId, Pageable pageable);
    List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID organizationId,
        final Long arrivalDate
    );
    Slice<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
    Slice<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        Pageable pageable
    );
    Optional<
        CassAddOnsSelectedByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId
    );
    Slice<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber,
        final UUID createdTimeId,
        Pageable pageable
    );
    CassAddOnsSelectedByOrganizationDTO findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        final UUID organizationId,
        final Long arrivalDate,
        final String accountNumber
    );
}
