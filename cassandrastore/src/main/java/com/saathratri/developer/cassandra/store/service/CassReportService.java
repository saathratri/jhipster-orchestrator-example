package com.saathratri.developer.cassandra.store.service;

import com.saathratri.developer.cassandra.store.service.dto.CassReportDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * Service Interface for managing {@link com.saathratri.developer.cassandra.store.domain.CassReport}.
 */
public interface CassReportService {
    /**
     * Save a cassReport.
     *
     * @param cassReportDTO the entity to save.
     * @return the persisted entity.
     */
    CassReportDTO save(CassReportDTO cassReportDTO);

    /**
     * Updates a cassReport.
     *
     * @param cassReportDTO the entity to update.
     * @return the persisted entity.
     */
    CassReportDTO update(CassReportDTO cassReportDTO);

    /**
     * Partially updates a cassReport.
     *
     * @param cassReportDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CassReportDTO> partialUpdate(CassReportDTO cassReportDTO);

    /**
     * Get all the cassReports.
     *
     * @return the list of entities.
     */
    List<CassReportDTO> findAll();

    /**
     * Get the "id" cassReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CassReportDTO> findOne(UUID id);

    /**
     * Delete the "id" cassReport.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);

    /**
     * Get all the cassReports with Cassandra cursor-based pagination.
     *
     * @param pageable the pagination information.
     * @return the slice of entities.
     */
    Slice<CassReportDTO> findAllSlice(org.springframework.data.domain.Pageable pageable);
}
