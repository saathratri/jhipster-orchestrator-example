package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntityDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity}.
 */
public interface CassSaathratriEntityService {
    /**
     * Save a cassSaathratriEntity.
     *
     * @param cassSaathratriEntityDTO the entity to save.
     * @return the persisted entity.
     */
    CassSaathratriEntityDTO save(CassSaathratriEntityDTO cassSaathratriEntityDTO);

    /**
     * Updates a cassSaathratriEntity.
     *
     * @param cassSaathratriEntityDTO the entity to update.
     * @return the persisted entity.
     */
    CassSaathratriEntityDTO update(CassSaathratriEntityDTO cassSaathratriEntityDTO);

    /**
     * Partially updates a cassSaathratriEntity.
     *
     * @param cassSaathratriEntityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassSaathratriEntityDTO> partialUpdate(CassSaathratriEntityDTO cassSaathratriEntityDTO);

    /**
     * Get all the cassSaathratriEntities.
     *
     * @return the list of entities.
     */
    List<CassSaathratriEntityDTO> findAll();

    /**
     * Get the "id" cassSaathratriEntity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassSaathratriEntityDTO> findOne(UUID entityId);

    /**
     * Delete the "id" cassSaathratriEntity.
     *
     * @param id the id of the entity.
     */
    void delete(UUID entityId);

    /**
     * Get all the cassSaathratriEntities with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassSaathratriEntityDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);
}
