package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.service.dto.CassLandingPageByOrganizationDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassLandingPageByOrganization}.
 */
public interface CassLandingPageByOrganizationService {
    /**
     * Save a cassLandingPageByOrganization.
     *
     * @param cassLandingPageByOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    CassLandingPageByOrganizationDTO save(CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO);

    /**
     * Updates a cassLandingPageByOrganization.
     *
     * @param cassLandingPageByOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    CassLandingPageByOrganizationDTO update(CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO);

    /**
     * Partially updates a cassLandingPageByOrganization.
     *
     * @param cassLandingPageByOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassLandingPageByOrganizationDTO> partialUpdate(CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO);

    /**
     * Get all the cassLandingPageByOrganizations.
     *
     * @return the list of entities.
     */
    List<CassLandingPageByOrganizationDTO> findAll();

    /**
     * Get the "id" cassLandingPageByOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassLandingPageByOrganizationDTO> findOne(UUID organizationId);

    /**
     * Delete the "id" cassLandingPageByOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(UUID organizationId);

    /**
     * Get all the cassLandingPageByOrganizations with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassLandingPageByOrganizationDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);
}
