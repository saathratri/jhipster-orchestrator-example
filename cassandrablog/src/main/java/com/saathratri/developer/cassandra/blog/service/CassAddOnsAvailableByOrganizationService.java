package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganizationId;
import com.saathratri.developer.cassandra.blog.service.dto.CassAddOnsAvailableByOrganizationDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganization}.
 */
public interface CassAddOnsAvailableByOrganizationService {
    /**
     * Save a cassAddOnsAvailableByOrganization.
     *
     * @param cassAddOnsAvailableByOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    CassAddOnsAvailableByOrganizationDTO save(CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO);

    /**
     * Updates a cassAddOnsAvailableByOrganization.
     *
     * @param cassAddOnsAvailableByOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    CassAddOnsAvailableByOrganizationDTO update(CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO);

    /**
     * Partially updates a cassAddOnsAvailableByOrganization.
     *
     * @param cassAddOnsAvailableByOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassAddOnsAvailableByOrganizationDTO> partialUpdate(CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO);

    /**
     * Get all the cassAddOnsAvailableByOrganizations.
     *
     * @return the list of entities.
     */
    List<CassAddOnsAvailableByOrganizationDTO> findAll();

    /**
     * Get the "id" cassAddOnsAvailableByOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassAddOnsAvailableByOrganizationDTO> findOne(CassAddOnsAvailableByOrganizationId id);

    /**
     * Delete the "id" cassAddOnsAvailableByOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(CassAddOnsAvailableByOrganizationId id);

    /**
     * Get all the cassAddOnsAvailableByOrganizations with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassAddOnsAvailableByOrganizationDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);

    List<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationId(final UUID organizationId);
    Slice<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdPageable(final UUID organizationId, Pageable pageable);
    List<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        final UUID organizationId,
        final String entityType
    );
    Slice<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable(
        final UUID organizationId,
        final String entityType,
        Pageable pageable
    );
    List<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId
    );
    Slice<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        Pageable pageable
    );
    Optional<
        CassAddOnsAvailableByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        final UUID organizationId,
        final String entityType,
        final UUID entityId,
        final UUID addOnId
    );
}
