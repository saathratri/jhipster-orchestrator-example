package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.domain.CassPostId;
import com.saathratri.developer.cassandra.blog.service.dto.CassPostDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassPost}.
 */
public interface CassPostService {
    /**
     * Save a cassPost.
     *
     * @param cassPostDTO the entity to save.
     * @return the persisted entity.
     */
    CassPostDTO save(CassPostDTO cassPostDTO);

    /**
     * Updates a cassPost.
     *
     * @param cassPostDTO the entity to update.
     * @return the persisted entity.
     */
    CassPostDTO update(CassPostDTO cassPostDTO);

    /**
     * Partially updates a cassPost.
     *
     * @param cassPostDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassPostDTO> partialUpdate(CassPostDTO cassPostDTO);

    /**
     * Get all the cassPosts.
     *
     * @return the list of entities.
     */
    List<CassPostDTO> findAll();

    /**
     * Get the "id" cassPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassPostDTO> findOne(CassPostId id);

    /**
     * Delete the "id" cassPost.
     *
     * @param id the id of the entity.
     */
    void delete(CassPostId id);

    /**
     * Get all the cassPosts with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassPostDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);

    List<CassPostDTO> findAllByCompositeIdCreatedDate(final Long createdDate);
    Slice<CassPostDTO> findAllByCompositeIdCreatedDatePageable(final Long createdDate, Pageable pageable);
    List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(final Long createdDate, final Long addedDateTime);
    Slice<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(final Long createdDate, final Long addedDateTime);
    Slice<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(
        final Long createdDate,
        final Long addedDateTime
    );
    Slice<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(
        final Long createdDate,
        final Long addedDateTime
    );
    Slice<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(
        final Long createdDate,
        final Long addedDateTime
    );
    Slice<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable(
        final Long createdDate,
        final Long addedDateTime,
        Pageable pageable
    );
    Optional<CassPostDTO> findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
        final Long createdDate,
        final Long addedDateTime,
        final UUID postId
    );
}
