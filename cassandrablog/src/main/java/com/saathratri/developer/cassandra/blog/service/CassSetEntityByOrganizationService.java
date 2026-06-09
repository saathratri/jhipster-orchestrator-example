package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.service.dto.CassSetEntityByOrganizationDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSetEntityByOrganization}.
 */
public interface CassSetEntityByOrganizationService {
    /**
     * Save a cassSetEntityByOrganization.
     *
     * @param cassSetEntityByOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    CassSetEntityByOrganizationDTO save(CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO);

    /**
     * Updates a cassSetEntityByOrganization.
     *
     * @param cassSetEntityByOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    CassSetEntityByOrganizationDTO update(CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO);

    /**
     * Partially updates a cassSetEntityByOrganization.
     *
     * @param cassSetEntityByOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassSetEntityByOrganizationDTO> partialUpdate(CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO);

    /**
     * Get all the cassSetEntityByOrganizations.
     *
     * @return the list of entities.
     */
    List<CassSetEntityByOrganizationDTO> findAll();

    /**
     * Get the "id" cassSetEntityByOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassSetEntityByOrganizationDTO> findOne(UUID organizationId);

    /**
     * Delete the "id" cassSetEntityByOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(UUID organizationId);

    /**
     * Get all the cassSetEntityByOrganizations with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassSetEntityByOrganizationDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);
}
