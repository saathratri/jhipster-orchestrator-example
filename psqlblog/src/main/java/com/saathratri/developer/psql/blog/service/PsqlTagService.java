package com.saathratri.developer.psql.blog.service;

import com.saathratri.developer.psql.blog.service.dto.PsqlTagDTO;
import java.util.List;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.saathratri.developer.psql.blog.domain.PsqlTag}.
 */
public interface PsqlTagService {
    /**
     * Save a psqlTag.
     *
     * @param psqlTagDTO the entity to save.
     * @return the persisted entity.
     */
    PsqlTagDTO save(PsqlTagDTO psqlTagDTO);

    /**
     * Updates a psqlTag.
     *
     * @param psqlTagDTO the entity to update.
     * @return the persisted entity.
     */
    PsqlTagDTO update(PsqlTagDTO psqlTagDTO);

    /**
     * Partially updates a psqlTag.
     *
     * @param psqlTagDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PsqlTagDTO> partialUpdate(PsqlTagDTO psqlTagDTO);

    /**
     * Get all the psqlTags for saathratri orchestrator.
     *
     * @return the list of entities.
     */
    List<PsqlTagDTO> findAllForSaathratriOrchestrator();

    /**
     * Get all the psqlTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PsqlTagDTO> findAll(Pageable pageable);

    /**
     * Get the "id" psqlTag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PsqlTagDTO> findOne(UUID id);

    /**
     * Delete the "id" psqlTag.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);

    // ==================== AI Text Search ====================

    /**
     * Search for psqlTags using AI-powered semantic similarity.
     * Converts the text query to an embedding and searches across all vector fields,
     * or only the specified fields if the 'fields' parameter is provided.
     *
     * @param query the text query to search for
     * @param limit maximum number of results to return
     * @param fields optional list of vector field names to search in (null or empty searches all)
     * @return list of semantically similar psqlTags
     */
    List<PsqlTagDTO> aiSearch(String query, int limit, List<String> fields);

    // ==================== Vector Similarity Search Methods ====================

    /**
     * Find psqlTags similar to the given embedding vector using nameEmbedding.
     *
     * @param embedding the query embedding vector as a formatted string "[0.1, 0.2, ...]"
     * @param limit maximum number of results to return
     * @return list of psqlTags ordered by similarity (most similar first)
     */
    List<PsqlTagDTO> findSimilarByNameEmbedding(String embedding, int limit);

    /**
     * Find psqlTags similar to the given embedding vector using nameEmbedding with threshold.
     *
     * @param embedding the query embedding vector as a formatted string "[0.1, 0.2, ...]"
     * @param maxDistance maximum cosine distance (0 = identical, 2 = opposite)
     * @param limit maximum number of results to return
     * @return list of psqlTags ordered by similarity (most similar first)
     */
    List<PsqlTagDTO> findSimilarByNameEmbeddingWithThreshold(String embedding, double maxDistance, int limit);

    /**
     * Find psqlTags similar to the given embedding vector using descriptionEmbedding.
     *
     * @param embedding the query embedding vector as a formatted string "[0.1, 0.2, ...]"
     * @param limit maximum number of results to return
     * @return list of psqlTags ordered by similarity (most similar first)
     */
    List<PsqlTagDTO> findSimilarByDescriptionEmbedding(String embedding, int limit);

    /**
     * Find psqlTags similar to the given embedding vector using descriptionEmbedding with threshold.
     *
     * @param embedding the query embedding vector as a formatted string "[0.1, 0.2, ...]"
     * @param maxDistance maximum cosine distance (0 = identical, 2 = opposite)
     * @param limit maximum number of results to return
     * @return list of psqlTags ordered by similarity (most similar first)
     */
    List<PsqlTagDTO> findSimilarByDescriptionEmbeddingWithThreshold(String embedding, double maxDistance, int limit);
}
