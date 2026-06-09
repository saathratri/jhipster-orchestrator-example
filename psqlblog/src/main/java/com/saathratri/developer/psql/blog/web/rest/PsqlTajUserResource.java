package com.saathratri.developer.psql.blog.web.rest;

import com.saathratri.developer.psql.blog.repository.PsqlTajUserRepository;
import com.saathratri.developer.psql.blog.service.PsqlTajUserService;
import com.saathratri.developer.psql.blog.service.dto.PsqlTajUserDTO;
import com.saathratri.developer.psql.blog.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.saathratri.developer.psql.blog.domain.PsqlTajUser}.
 */
@RestController
@RequestMapping("/api/psql-taj-users")
public class PsqlTajUserResource {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlTajUserResource.class);

    private static final String ENTITY_NAME = "psqlblogPsqlTajUser";

    @Value("${jhipster.clientApp.name:psqlblog}")
    private String applicationName;

    private final PsqlTajUserService psqlTajUserService;

    private final PsqlTajUserRepository psqlTajUserRepository;

    public PsqlTajUserResource(PsqlTajUserService psqlTajUserService, PsqlTajUserRepository psqlTajUserRepository) {
        this.psqlTajUserService = psqlTajUserService;
        this.psqlTajUserRepository = psqlTajUserRepository;
    }

    /**
     * {@code POST  /psql-taj-users} : Create a new psqlTajUser.
     *
     * @param psqlTajUserDTO the psqlTajUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new psqlTajUserDTO, or with status {@code 400 (Bad Request)} if the psqlTajUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PsqlTajUserDTO> createPsqlTajUser(@Valid @RequestBody PsqlTajUserDTO psqlTajUserDTO) throws URISyntaxException {
        LOG.debug("REST request to save PsqlTajUser : {}", psqlTajUserDTO);
        if (psqlTajUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new psqlTajUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        psqlTajUserDTO = psqlTajUserService.save(psqlTajUserDTO);
        return ResponseEntity.created(new URI("/api/psql-taj-users/" + psqlTajUserDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, psqlTajUserDTO.getId().toString()))
            .body(psqlTajUserDTO);
    }

    /**
     * {@code PUT  /psql-taj-users/:id} : Updates an existing psqlTajUser.
     *
     * @param id the id of the psqlTajUserDTO to save.
     * @param psqlTajUserDTO the psqlTajUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psqlTajUserDTO,
     * or with status {@code 400 (Bad Request)} if the psqlTajUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the psqlTajUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PsqlTajUserDTO> updatePsqlTajUser(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody PsqlTajUserDTO psqlTajUserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PsqlTajUser : {}, {}", id, psqlTajUserDTO);
        if (psqlTajUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psqlTajUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psqlTajUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        psqlTajUserDTO = psqlTajUserService.update(psqlTajUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psqlTajUserDTO.getId().toString()))
            .body(psqlTajUserDTO);
    }

    /**
     * {@code PATCH  /psql-taj-users/:id} : Partial updates given fields of an existing psqlTajUser, field will ignore if it is null
     *
     * @param id the id of the psqlTajUserDTO to save.
     * @param psqlTajUserDTO the psqlTajUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psqlTajUserDTO,
     * or with status {@code 400 (Bad Request)} if the psqlTajUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the psqlTajUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the psqlTajUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PsqlTajUserDTO> partialUpdatePsqlTajUser(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody PsqlTajUserDTO psqlTajUserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PsqlTajUser partially : {}, {}", id, psqlTajUserDTO);
        if (psqlTajUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psqlTajUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psqlTajUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PsqlTajUserDTO> result = psqlTajUserService.partialUpdate(psqlTajUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psqlTajUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /psql-taj-users} : get all the Psql Taj Users.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Psql Taj Users in body.
     */
    @GetMapping("")
    public List<PsqlTajUserDTO> getAllPsqlTajUsers() {
        LOG.debug("REST request to get all PsqlTajUsers");
        return psqlTajUserService.findAll();
    }

    /**
     * {@code GET  /saathratri-orchestrator} : get all the Psql Taj Users.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Psql Taj Users in body.
     */
    @GetMapping("/saathratri-orchestrator")
    public ResponseEntity<List<PsqlTajUserDTO>> getAllPsqlTajUsersForSaathratriOrchestrator() {
        LOG.debug("REST request to get all PsqlTajUsers for saathratri orchestrator");
        List<PsqlTajUserDTO> entityList;
        entityList = psqlTajUserService.findAllForSaathratriOrchestrator();
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /psql-taj-users/:id} : get the "id" psqlTajUser.
     *
     * @param id the id of the psqlTajUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the psqlTajUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PsqlTajUserDTO> getPsqlTajUser(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get PsqlTajUser : {}", id);
        Optional<PsqlTajUserDTO> psqlTajUserDTO = psqlTajUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(psqlTajUserDTO);
    }

    /**
     * {@code DELETE  /psql-taj-users/:id} : delete the "id" psqlTajUser.
     *
     * @param id the id of the psqlTajUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePsqlTajUser(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete PsqlTajUser : {}", id);
        psqlTajUserService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
