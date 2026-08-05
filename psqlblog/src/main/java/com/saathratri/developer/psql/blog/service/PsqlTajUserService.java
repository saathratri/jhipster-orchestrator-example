package com.saathratri.developer.psql.blog.service;

import com.saathratri.developer.psql.blog.service.dto.PsqlTajUserDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.psql.blog.domain.PsqlTajUser}.
 */
public interface PsqlTajUserService {
    /**
     * Save a psqlTajUser.
     *
     * @param psqlTajUserDTO the entity to save.
     * @return the persisted entity.
     */
    PsqlTajUserDTO save(PsqlTajUserDTO psqlTajUserDTO);

    /**
     * Updates a psqlTajUser.
     *
     * @param psqlTajUserDTO the entity to update.
     * @return the persisted entity.
     */
    PsqlTajUserDTO update(PsqlTajUserDTO psqlTajUserDTO);

    /**
     * Partially updates a psqlTajUser.
     *
     * @param psqlTajUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PsqlTajUserDTO> partialUpdate(PsqlTajUserDTO psqlTajUserDTO);

    /**
     * Get all the psqlTajUsers for saathratri orchestrator.
     *
     * @return the list of entities.
     */
    List<PsqlTajUserDTO> findAllForSaathratriOrchestrator();

    /**
     * Get all the psqlTajUsers.
     *
     * @return the list of entities.
     */
    List<PsqlTajUserDTO> findAll();

    /**
     * Get the "id" psqlTajUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PsqlTajUserDTO> findOne(UUID id);

    /**
     * Delete the "id" psqlTajUser.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
