package com.saathratri.developer.psql.blog.repository;

import com.saathratri.developer.psql.blog.domain.PsqlTag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PsqlTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PsqlTagRepository extends JpaRepository<PsqlTag, UUID> {
    // ==================== Vector Similarity Search Methods ====================
    // These methods use pgvector for semantic similarity search using cosine distance

    /**
     * Find psqlTags similar to the given embedding vector using nameEmbedding.
     * Uses pgvector cosine distance operator (<=>) for similarity search.
     *
     * @param embedding the query embedding vector (must match dimension 1536)
     * @param limit maximum number of results to return
     * @return list of psqlTags ordered by similarity (most similar first)
     */
    @Query(value = "SELECT * FROM psql_tag ORDER BY name_embedding <=> cast(:embedding as vector) LIMIT :limit", nativeQuery = true)
    List<PsqlTag> findSimilarByNameEmbedding(@Param("embedding") String embedding, @Param("limit") int limit);

    /**
     * Find psqlTags similar to the given embedding vector using nameEmbedding with minimum similarity threshold.
     * Uses pgvector cosine distance operator (<=>) for similarity search.
     *
     * @param embedding the query embedding vector (must match dimension 1536)
     * @param maxDistance maximum cosine distance (0 = identical, 2 = opposite)
     * @param limit maximum number of results to return
     * @return list of psqlTags ordered by similarity (most similar first)
     */
    @Query(
        value = "SELECT * FROM psql_tag WHERE name_embedding <=> cast(:embedding as vector) < :maxDistance ORDER BY name_embedding <=> cast(:embedding as vector) LIMIT :limit",
        nativeQuery = true
    )
    List<PsqlTag> findSimilarByNameEmbeddingWithThreshold(
        @Param("embedding") String embedding,
        @Param("maxDistance") double maxDistance,
        @Param("limit") int limit
    );

    /**
     * Find psqlTags similar to the given embedding vector using descriptionEmbedding.
     * Uses pgvector cosine distance operator (<=>) for similarity search.
     *
     * @param embedding the query embedding vector (must match dimension 1536)
     * @param limit maximum number of results to return
     * @return list of psqlTags ordered by similarity (most similar first)
     */
    @Query(value = "SELECT * FROM psql_tag ORDER BY description_embedding <=> cast(:embedding as vector) LIMIT :limit", nativeQuery = true)
    List<PsqlTag> findSimilarByDescriptionEmbedding(@Param("embedding") String embedding, @Param("limit") int limit);

    /**
     * Find psqlTags similar to the given embedding vector using descriptionEmbedding with minimum similarity threshold.
     * Uses pgvector cosine distance operator (<=>) for similarity search.
     *
     * @param embedding the query embedding vector (must match dimension 1536)
     * @param maxDistance maximum cosine distance (0 = identical, 2 = opposite)
     * @param limit maximum number of results to return
     * @return list of psqlTags ordered by similarity (most similar first)
     */
    @Query(
        value = "SELECT * FROM psql_tag WHERE description_embedding <=> cast(:embedding as vector) < :maxDistance ORDER BY description_embedding <=> cast(:embedding as vector) LIMIT :limit",
        nativeQuery = true
    )
    List<PsqlTag> findSimilarByDescriptionEmbeddingWithThreshold(
        @Param("embedding") String embedding,
        @Param("maxDistance") double maxDistance,
        @Param("limit") int limit
    );
}
