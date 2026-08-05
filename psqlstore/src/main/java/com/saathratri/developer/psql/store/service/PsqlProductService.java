package com.saathratri.developer.psql.store.service;

import com.saathratri.developer.psql.store.service.dto.PsqlProductDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.saathratri.developer.psql.store.domain.PsqlProduct}.
 */
public interface PsqlProductService {
    /**
     * Save a psqlProduct.
     *
     * @param psqlProductDTO the entity to save.
     * @return the persisted entity.
     */
    PsqlProductDTO save(PsqlProductDTO psqlProductDTO);

    /**
     * Updates a psqlProduct.
     *
     * @param psqlProductDTO the entity to update.
     * @return the persisted entity.
     */
    PsqlProductDTO update(PsqlProductDTO psqlProductDTO);

    /**
     * Partially updates a psqlProduct.
     *
     * @param psqlProductDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PsqlProductDTO> partialUpdate(PsqlProductDTO psqlProductDTO);

    /**
     * Get all the psqlProducts for saathratri orchestrator.
     *
     * @return the list of entities.
     */
    List<PsqlProductDTO> findAllForSaathratriOrchestrator();

    /**
     * Get all the psqlProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PsqlProductDTO> findAll(Pageable pageable);

    /**
     * Get the "id" psqlProduct.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PsqlProductDTO> findOne(UUID id);

    /**
     * Delete the "id" psqlProduct.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
