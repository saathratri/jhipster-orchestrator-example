package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3Id;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity3DTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3}.
 */
public interface CassSaathratriEntity3Service {
    /**
     * Save a cassSaathratriEntity3.
     *
     * @param cassSaathratriEntity3DTO the entity to save.
     * @return the persisted entity.
     */
    CassSaathratriEntity3DTO save(CassSaathratriEntity3DTO cassSaathratriEntity3DTO);

    /**
     * Updates a cassSaathratriEntity3.
     *
     * @param cassSaathratriEntity3DTO the entity to update.
     * @return the persisted entity.
     */
    CassSaathratriEntity3DTO update(CassSaathratriEntity3DTO cassSaathratriEntity3DTO);

    /**
     * Partially updates a cassSaathratriEntity3.
     *
     * @param cassSaathratriEntity3DTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassSaathratriEntity3DTO> partialUpdate(CassSaathratriEntity3DTO cassSaathratriEntity3DTO);

    /**
     * Get all the cassSaathratriEntity3s.
     *
     * @return the list of entities.
     */
    List<CassSaathratriEntity3DTO> findAll();

    /**
     * Get the "id" cassSaathratriEntity3.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassSaathratriEntity3DTO> findOne(CassSaathratriEntity3Id id);

    /**
     * Delete the "id" cassSaathratriEntity3.
     *
     * @param id the id of the entity.
     */
    void delete(CassSaathratriEntity3Id id);

    /**
     * Get all the cassSaathratriEntity3s with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassSaathratriEntity3DTO> findAllSlice(org.springframework.data.domain.Pageable pageable);

    List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityType(final String entityType);
    Slice<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypePageable(final String entityType, Pageable pageable);
    Optional<CassSaathratriEntity3DTO> findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(
        final String entityType,
        final UUID createdTimeId
    );
    List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(
        final String entityType,
        final UUID createdTimeId
    );
    Slice<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(
        final String entityType,
        final UUID createdTimeId
    );
    Slice<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(
        final String entityType,
        final UUID createdTimeId
    );
    Slice<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    );
    List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(
        final String entityType,
        final UUID createdTimeId
    );
    Slice<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
        final String entityType,
        final UUID createdTimeId,
        Pageable pageable
    );
    CassSaathratriEntity3DTO findLatestByCompositeIdEntityType(final String entityType);
}
