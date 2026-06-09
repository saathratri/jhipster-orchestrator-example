package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2Id;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity2DTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2}.
 */
public interface CassSaathratriEntity2Service {
    /**
     * Save a cassSaathratriEntity2.
     *
     * @param cassSaathratriEntity2DTO the entity to save.
     * @return the persisted entity.
     */
    CassSaathratriEntity2DTO save(CassSaathratriEntity2DTO cassSaathratriEntity2DTO);

    /**
     * Updates a cassSaathratriEntity2.
     *
     * @param cassSaathratriEntity2DTO the entity to update.
     * @return the persisted entity.
     */
    CassSaathratriEntity2DTO update(CassSaathratriEntity2DTO cassSaathratriEntity2DTO);

    /**
     * Partially updates a cassSaathratriEntity2.
     *
     * @param cassSaathratriEntity2DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassSaathratriEntity2DTO> partialUpdate(CassSaathratriEntity2DTO cassSaathratriEntity2DTO);

    /**
     * Get all the cassSaathratriEntity2s.
     *
     * @return the list of entities.
     */
    List<CassSaathratriEntity2DTO> findAll();

    /**
     * Get the "id" cassSaathratriEntity2.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassSaathratriEntity2DTO> findOne(CassSaathratriEntity2Id id);

    /**
     * Delete the "id" cassSaathratriEntity2.
     *
     * @param id the id of the entity.
     */
    void delete(CassSaathratriEntity2Id id);

    /**
     * Get all the cassSaathratriEntity2s with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassSaathratriEntity2DTO> findAllSlice(org.springframework.data.domain.Pageable pageable);

    List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeId(final UUID entityTypeId);
    Slice<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdPageable(final UUID entityTypeId, Pageable pageable);
    List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(
        final UUID entityTypeId,
        final Long yearOfDateAdded
    );
    Slice<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        Pageable pageable
    );
    List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDatePageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqualPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
    Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqualPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        Pageable pageable
    );
    Optional<
        CassSaathratriEntity2DTO
    > findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );
    List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqualPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );
    List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );
    List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId
    );
    Slice<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqualPageable(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate,
        final UUID blogId,
        Pageable pageable
    );
    CassSaathratriEntity2DTO findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        final UUID entityTypeId,
        final Long yearOfDateAdded,
        final Long arrivalDate
    );
}
