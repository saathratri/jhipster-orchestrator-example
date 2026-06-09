package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.service.dto.CassTagDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassTag}.
 */
public interface CassTagService {
    /**
     * Save a cassTag.
     *
     * @param cassTagDTO the entity to save.
     * @return the persisted entity.
     */
    CassTagDTO save(CassTagDTO cassTagDTO);

    /**
     * Updates a cassTag.
     *
     * @param cassTagDTO the entity to update.
     * @return the persisted entity.
     */
    CassTagDTO update(CassTagDTO cassTagDTO);

    /**
     * Partially updates a cassTag.
     *
     * @param cassTagDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassTagDTO> partialUpdate(CassTagDTO cassTagDTO);

    /**
     * Get all the cassTags.
     *
     * @return the list of entities.
     */
    List<CassTagDTO> findAll();

    /**
     * Get the "id" cassTag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassTagDTO> findOne(UUID id);

    /**
     * Delete the "id" cassTag.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);

    /**
     * Get all the cassTags with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassTagDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);

    // ==================== AI Text Search ====================

    /**
     * Search for cassTags using AI-powered semantic similarity.
     *
     * @param query the text query to search for
     * @param limit maximum number of results to return
     * @param fields optional list of vector field names to search in (null or empty searches all)
     * @return list of semantically similar cassTags
     */
    List<CassTagDTO> aiSearch(String query, int limit, List<String> fields);

    /**
     * Find cassTags similar to the given embedding vector using nameEmbedding.
     */
    List<CassTagDTO> findSimilarByNameEmbedding(com.datastax.oss.driver.api.core.data.CqlVector<Float> embedding, int limit);
    /**
     * Find cassTags similar to the given embedding vector using descriptionEmbedding.
     */
    List<CassTagDTO> findSimilarByDescriptionEmbedding(com.datastax.oss.driver.api.core.data.CqlVector<Float> embedding, int limit);
}
