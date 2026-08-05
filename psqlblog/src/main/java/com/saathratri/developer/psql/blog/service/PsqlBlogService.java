package com.saathratri.developer.psql.blog.service;

import com.saathratri.developer.psql.blog.service.dto.PsqlBlogDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.saathratri.developer.psql.blog.domain.PsqlBlog}.
 */
public interface PsqlBlogService {
    /**
     * Save a psqlBlog.
     *
     * @param psqlBlogDTO the entity to save.
     * @return the persisted entity.
     */
    PsqlBlogDTO save(PsqlBlogDTO psqlBlogDTO);

    /**
     * Updates a psqlBlog.
     *
     * @param psqlBlogDTO the entity to update.
     * @return the persisted entity.
     */
    PsqlBlogDTO update(PsqlBlogDTO psqlBlogDTO);

    /**
     * Partially updates a psqlBlog.
     *
     * @param psqlBlogDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PsqlBlogDTO> partialUpdate(PsqlBlogDTO psqlBlogDTO);

    /**
     * Get all the psqlBlogs for saathratri orchestrator.
     *
     * @return the list of entities.
     */
    List<PsqlBlogDTO> findAllForSaathratriOrchestrator();

    /**
     * Get the "id" psqlBlog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PsqlBlogDTO> findOneWithEagerRelationships(UUID id);

    /**
     * Get all the psqlBlogs.
     * @return the list of entities.
     */
    List<PsqlBlogDTO> findAllWithEagerRelationships();

    /**
     * Get all the psqlBlogs.
     *
     * @return the list of entities.
     */
    List<PsqlBlogDTO> findAll();

    /**
     * Get all the psqlBlogs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PsqlBlogDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" psqlBlog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PsqlBlogDTO> findOne(UUID id);

    /**
     * Delete the "id" psqlBlog.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
