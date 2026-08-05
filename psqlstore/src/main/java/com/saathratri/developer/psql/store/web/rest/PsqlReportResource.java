package com.saathratri.developer.psql.store.web.rest;

import com.saathratri.developer.psql.store.repository.PsqlReportRepository;
import com.saathratri.developer.psql.store.service.PsqlReportService;
import com.saathratri.developer.psql.store.service.dto.PsqlReportDTO;
import com.saathratri.developer.psql.store.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.saathratri.developer.psql.store.domain.PsqlReport}.
 */
@RestController
@RequestMapping("/api/psql-reports")
public class PsqlReportResource {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlReportResource.class);

    private static final String ENTITY_NAME = "psqlstorePsqlReport";

    @Value("${jhipster.clientApp.name:psqlstore}")
    private String applicationName;

    private final PsqlReportService psqlReportService;

    private final PsqlReportRepository psqlReportRepository;

    public PsqlReportResource(PsqlReportService psqlReportService, PsqlReportRepository psqlReportRepository) {
        this.psqlReportService = psqlReportService;
        this.psqlReportRepository = psqlReportRepository;
    }

    /**
     * {@code POST  /psql-reports} : Create a new psqlReport.
     *
     * @param psqlReportDTO the psqlReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new psqlReportDTO, or with status {@code 400 (Bad Request)} if the psqlReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PsqlReportDTO> createPsqlReport(@Valid @RequestBody PsqlReportDTO psqlReportDTO) throws URISyntaxException {
        LOG.debug("REST request to save PsqlReport : {}", psqlReportDTO);
        if (psqlReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new psqlReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        psqlReportDTO = psqlReportService.save(psqlReportDTO);
        return ResponseEntity.created(new URI("/api/psql-reports/" + psqlReportDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, psqlReportDTO.getId().toString()))
            .body(psqlReportDTO);
    }

    /**
     * {@code PUT  /psql-reports/:id} : Updates an existing psqlReport.
     *
     * @param id the id of the psqlReportDTO to save.
     * @param psqlReportDTO the psqlReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psqlReportDTO,
     * or with status {@code 400 (Bad Request)} if the psqlReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the psqlReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PsqlReportDTO> updatePsqlReport(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody PsqlReportDTO psqlReportDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PsqlReport : {}, {}", id, psqlReportDTO);
        if (psqlReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psqlReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psqlReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        psqlReportDTO = psqlReportService.update(psqlReportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psqlReportDTO.getId().toString()))
            .body(psqlReportDTO);
    }

    /**
     * {@code PATCH  /psql-reports/:id} : Partial updates given fields of an existing psqlReport, field will ignore if it is null
     *
     * @param id the id of the psqlReportDTO to save.
     * @param psqlReportDTO the psqlReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psqlReportDTO,
     * or with status {@code 400 (Bad Request)} if the psqlReportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the psqlReportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the psqlReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PsqlReportDTO> partialUpdatePsqlReport(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody PsqlReportDTO psqlReportDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PsqlReport partially : {}, {}", id, psqlReportDTO);
        if (psqlReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psqlReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psqlReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PsqlReportDTO> result = psqlReportService.partialUpdate(psqlReportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psqlReportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /psql-reports} : get all the Psql Reports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Psql Reports in body.
     */
    @GetMapping("")
    public List<PsqlReportDTO> getAllPsqlReports() {
        LOG.debug("REST request to get all PsqlReports");
        return psqlReportService.findAll();
    }

    /**
     * {@code GET  /saathratri-orchestrator} : get all the Psql Reports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Psql Reports in body.
     */
    @GetMapping("/saathratri-orchestrator")
    public ResponseEntity<List<PsqlReportDTO>> getAllPsqlReportsForSaathratriOrchestrator() {
        LOG.debug("REST request to get all PsqlReports for saathratri orchestrator");
        List<PsqlReportDTO> entityList;
        entityList = psqlReportService.findAllForSaathratriOrchestrator();
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /psql-reports/:id} : get the "id" psqlReport.
     *
     * @param id the id of the psqlReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the psqlReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PsqlReportDTO> getPsqlReport(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get PsqlReport : {}", id);
        Optional<PsqlReportDTO> psqlReportDTO = psqlReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(psqlReportDTO);
    }

    /**
     * {@code DELETE  /psql-reports/:id} : delete the "id" psqlReport.
     *
     * @param id the id of the psqlReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePsqlReport(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete PsqlReport : {}", id);
        psqlReportService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
