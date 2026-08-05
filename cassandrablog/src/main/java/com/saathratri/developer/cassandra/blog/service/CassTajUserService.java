package com.saathratri.developer.cassandra.blog.service;

import com.saathratri.developer.cassandra.blog.service.dto.CassTajUserDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.blog.domain.CassTajUser}.
 */
public interface CassTajUserService {
    /**
     * Save a cassTajUser.
     *
     * @param cassTajUserDTO the entity to save.
     * @return the persisted entity.
     */
    CassTajUserDTO save(CassTajUserDTO cassTajUserDTO);

    /**
     * Updates a cassTajUser.
     *
     * @param cassTajUserDTO the entity to update.
     * @return the persisted entity.
     */
    CassTajUserDTO update(CassTajUserDTO cassTajUserDTO);

    /**
     * Partially updates a cassTajUser.
     *
     * @param cassTajUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassTajUserDTO> partialUpdate(CassTajUserDTO cassTajUserDTO);

    /**
     * Get all the cassTajUsers.
     *
     * @return the list of entities.
     */
    List<CassTajUserDTO> findAll();

    /**
     * Get the "id" cassTajUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassTajUserDTO> findOne(UUID id);

    /**
     * Delete the "id" cassTajUser.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);

    /**
     * Get all the cassTajUsers with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassTajUserDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);
}
