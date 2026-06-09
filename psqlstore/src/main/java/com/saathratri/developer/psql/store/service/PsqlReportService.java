package com.saathratri.developer.psql.store.service;

import com.saathratri.developer.psql.store.service.dto.PsqlReportDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.saathratri.developer.psql.store.domain.PsqlReport}.
 */
public interface PsqlReportService {
    /**
     * Save a psqlReport.
     *
     * @param psqlReportDTO the entity to save.
     * @return the persisted entity.
     */
    PsqlReportDTO save(PsqlReportDTO psqlReportDTO);

    /**
     * Updates a psqlReport.
     *
     * @param psqlReportDTO the entity to update.
     * @return the persisted entity.
     */
    PsqlReportDTO update(PsqlReportDTO psqlReportDTO);

    /**
     * Partially updates a psqlReport.
     *
     * @param psqlReportDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PsqlReportDTO> partialUpdate(PsqlReportDTO psqlReportDTO);

    /**
     * Get all the psqlReports for saathratri orchestrator.
     *
     * @return the list of entities.
     */
    List<PsqlReportDTO> findAllForSaathratriOrchestrator();

    /**
     * Get all the psqlReports.
     *
     * @return the list of entities.
     */
    List<PsqlReportDTO> findAll();

    /**
     * Get the "id" psqlReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PsqlReportDTO> findOne(UUID id);

    /**
     * Delete the "id" psqlReport.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
