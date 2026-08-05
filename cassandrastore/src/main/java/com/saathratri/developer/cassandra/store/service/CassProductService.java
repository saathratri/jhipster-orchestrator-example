package com.saathratri.developer.cassandra.store.service;

import com.saathratri.developer.cassandra.store.service.dto.CassProductDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.store.domain.CassProduct}.
 */
public interface CassProductService {
    /**
     * Save a cassProduct.
     *
     * @param cassProductDTO the entity to save.
     * @return the persisted entity.
     */
    CassProductDTO save(CassProductDTO cassProductDTO);

    /**
     * Updates a cassProduct.
     *
     * @param cassProductDTO the entity to update.
     * @return the persisted entity.
     */
    CassProductDTO update(CassProductDTO cassProductDTO);

    /**
     * Partially updates a cassProduct.
     *
     * @param cassProductDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassProductDTO> partialUpdate(CassProductDTO cassProductDTO);

    /**
     * Get all the cassProducts.
     *
     * @return the list of entities.
     */
    List<CassProductDTO> findAll();

    /**
     * Get the "id" cassProduct.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassProductDTO> findOne(UUID id);

    /**
     * Delete the "id" cassProduct.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);

    /**
     * Get all the cassProducts with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassProductDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);
}
