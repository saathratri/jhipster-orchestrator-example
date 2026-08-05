package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.domain.CassBlogId;
import com.saathratri.developer.cassandra.blog.service.dto.CassBlogDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassBlog}.
 */
public interface CassBlogService {
    /**
     * Save a cassBlog.
     *
     * @param cassBlogDTO the entity to save.
     * @return the persisted entity.
     */
    CassBlogDTO save(CassBlogDTO cassBlogDTO);

    /**
     * Updates a cassBlog.
     *
     * @param cassBlogDTO the entity to update.
     * @return the persisted entity.
     */
    CassBlogDTO update(CassBlogDTO cassBlogDTO);

    /**
     * Partially updates a cassBlog.
     *
     * @param cassBlogDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassBlogDTO> partialUpdate(CassBlogDTO cassBlogDTO);

    /**
     * Get all the cassBlogs.
     *
     * @return the list of entities.
     */
    List<CassBlogDTO> findAll();

    /**
     * Get the "id" cassBlog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassBlogDTO> findOne(CassBlogId id);

    /**
     * Delete the "id" cassBlog.
     *
     * @param id the id of the entity.
     */
    void delete(CassBlogId id);

    /**
     * Get all the cassBlogs with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassBlogDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);

    List<CassBlogDTO> findAllByCompositeIdCategory(final String category);
    Slice<CassBlogDTO> findAllByCompositeIdCategoryPageable(final String category, Pageable pageable);
    Optional<CassBlogDTO> findByCompositeIdCategoryAndCompositeIdBlogId(final String category, final UUID blogId);
    List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(final String category, final UUID blogId);
    Slice<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    );
    List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(final String category, final UUID blogId);
    Slice<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    );
    List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(final String category, final UUID blogId);
    Slice<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    );
    List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(final String category, final UUID blogId);
    Slice<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable(
        final String category,
        final UUID blogId,
        Pageable pageable
    );
    CassBlogDTO findLatestByCompositeIdCategory(final String category);
}
