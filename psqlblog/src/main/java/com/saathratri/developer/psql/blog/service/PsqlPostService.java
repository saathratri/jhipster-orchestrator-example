package com.saathratri.developer.psql.blog.service;

import com.saathratri.developer.psql.blog.service.dto.PsqlPostDTO;
import java.util.List;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.saathratri.developer.psql.blog.domain.PsqlPost}.
 */
public interface PsqlPostService {
    /**
     * Save a psqlPost.
     *
     * @param psqlPostDTO the entity to save.
     * @return the persisted entity.
     */
    PsqlPostDTO save(PsqlPostDTO psqlPostDTO);

    /**
     * Updates a psqlPost.
     *
     * @param psqlPostDTO the entity to update.
     * @return the persisted entity.
     */
    PsqlPostDTO update(PsqlPostDTO psqlPostDTO);

    /**
     * Partially updates a psqlPost.
     *
     * @param psqlPostDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PsqlPostDTO> partialUpdate(PsqlPostDTO psqlPostDTO);

    /**
     * Get all the psqlPosts for saathratri orchestrator.
     *
     * @return the list of entities.
     */
    List<PsqlPostDTO> findAllForSaathratriOrchestrator();

    /**
     * Get the "id" psqlPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PsqlPostDTO> findOneWithEagerRelationships(UUID id);

    /**
     * Get all the psqlPosts.
     * @return the list of entities.
     */
    List<PsqlPostDTO> findAllWithEagerRelationships();

    /**
     * Get all the psqlPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PsqlPostDTO> findAll(Pageable pageable);

    /**
     * Get all the psqlPosts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PsqlPostDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" psqlPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PsqlPostDTO> findOne(UUID id);

    /**
     * Delete the "id" psqlPost.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
