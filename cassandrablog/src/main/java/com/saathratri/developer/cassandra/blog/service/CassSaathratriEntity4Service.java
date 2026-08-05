package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4Id;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity4DTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4}.
 */
public interface CassSaathratriEntity4Service {
    /**
     * Save a cassSaathratriEntity4.
     *
     * @param cassSaathratriEntity4DTO the entity to save.
     * @return the persisted entity.
     */
    CassSaathratriEntity4DTO save(CassSaathratriEntity4DTO cassSaathratriEntity4DTO);

    /**
     * Updates a cassSaathratriEntity4.
     *
     * @param cassSaathratriEntity4DTO the entity to update.
     * @return the persisted entity.
     */
    CassSaathratriEntity4DTO update(CassSaathratriEntity4DTO cassSaathratriEntity4DTO);

    /**
     * Partially updates a cassSaathratriEntity4.
     *
     * @param cassSaathratriEntity4DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassSaathratriEntity4DTO> partialUpdate(CassSaathratriEntity4DTO cassSaathratriEntity4DTO);

    /**
     * Get all the cassSaathratriEntity4s.
     *
     * @return the list of entities.
     */
    List<CassSaathratriEntity4DTO> findAll();

    /**
     * Get the "id" cassSaathratriEntity4.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassSaathratriEntity4DTO> findOne(CassSaathratriEntity4Id id);

    /**
     * Delete the "id" cassSaathratriEntity4.
     *
     * @param id the id of the entity.
     */
    void delete(CassSaathratriEntity4Id id);

    /**
     * Get all the cassSaathratriEntity4s with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassSaathratriEntity4DTO> findAllSlice(org.springframework.data.domain.Pageable pageable);

    List<CassSaathratriEntity4DTO> findAllByCompositeIdOrganizationId(final UUID organizationId);
    Slice<CassSaathratriEntity4DTO> findAllByCompositeIdOrganizationIdPageable(final UUID organizationId, Pageable pageable);
    Optional<CassSaathratriEntity4DTO> findByCompositeIdOrganizationIdAndCompositeIdAttributeKey(
        final UUID organizationId,
        final String attributeKey
    );
}
