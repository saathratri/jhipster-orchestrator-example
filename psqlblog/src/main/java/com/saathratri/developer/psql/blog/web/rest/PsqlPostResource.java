package com.saathratri.developer.psql.blog.web.rest;

import com.saathratri.developer.psql.blog.repository.PsqlPostRepository;
import com.saathratri.developer.psql.blog.service.PsqlPostService;
import com.saathratri.developer.psql.blog.service.dto.PsqlPostDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.saathratri.developer.psql.blog.domain.PsqlPost}.
 */
@RestController
@RequestMapping("/api/psql-posts")
public class PsqlPostResource {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlPostResource.class);

    private static final String ENTITY_NAME = "psqlblogPsqlPost";

    @Value("${jhipster.clientApp.name:psqlblog}")
    private String applicationName;

    private final PsqlPostService psqlPostService;

    private final PsqlPostRepository psqlPostRepository;

    public PsqlPostResource(PsqlPostService psqlPostService, PsqlPostRepository psqlPostRepository) {
        this.psqlPostService = psqlPostService;
        this.psqlPostRepository = psqlPostRepository;
    }

    /**
     * {@code POST  /psql-posts} : Create a new psqlPost.
     *
     * @param psqlPostDTO the psqlPostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new psqlPostDTO, or with status {@code 400 (Bad Request)} if the psqlPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PsqlPostDTO> createPsqlPost(@Valid @RequestBody PsqlPostDTO psqlPostDTO) throws URISyntaxException {
        LOG.debug("REST request to save PsqlPost : {}", psqlPostDTO);
        if (psqlPostDTO.getId() != null) {
            throw new BadRequestAlertException("A new psqlPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        psqlPostDTO = psqlPostService.save(psqlPostDTO);
        return ResponseEntity.created(new URI("/api/psql-posts/" + psqlPostDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, psqlPostDTO.getId().toString()))
            .body(psqlPostDTO);
    }

    /**
     * {@code PUT  /psql-posts/:id} : Updates an existing psqlPost.
     *
     * @param id the id of the psqlPostDTO to save.
     * @param psqlPostDTO the psqlPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psqlPostDTO,
     * or with status {@code 400 (Bad Request)} if the psqlPostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the psqlPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PsqlPostDTO> updatePsqlPost(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody PsqlPostDTO psqlPostDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PsqlPost : {}, {}", id, psqlPostDTO);
        if (psqlPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psqlPostDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psqlPostRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        psqlPostDTO = psqlPostService.update(psqlPostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psqlPostDTO.getId().toString()))
            .body(psqlPostDTO);
    }

    /**
     * {@code PATCH  /psql-posts/:id} : Partial updates given fields of an existing psqlPost, field will ignore if it is null
     *
     * @param id the id of the psqlPostDTO to save.
     * @param psqlPostDTO the psqlPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psqlPostDTO,
     * or with status {@code 400 (Bad Request)} if the psqlPostDTO is not valid,
     * or with status {@code 404 (Not Found)} if the psqlPostDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the psqlPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PsqlPostDTO> partialUpdatePsqlPost(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody PsqlPostDTO psqlPostDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PsqlPost partially : {}, {}", id, psqlPostDTO);
        if (psqlPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psqlPostDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psqlPostRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PsqlPostDTO> result = psqlPostService.partialUpdate(psqlPostDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psqlPostDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /psql-posts} : get all the Psql Posts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Psql Posts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PsqlPostDTO>> getAllPsqlPosts(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of PsqlPosts");
        Page<PsqlPostDTO> page;
        if (eagerload) {
            page = psqlPostService.findAllWithEagerRelationships(pageable);
        } else {
            page = psqlPostService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /saathratri-orchestrator} : get all the Psql Posts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Psql Posts in body.
     */
    @GetMapping("/saathratri-orchestrator")
    public ResponseEntity<List<PsqlPostDTO>> getAllPsqlPostsForSaathratriOrchestrator() {
        LOG.debug("REST request to get all PsqlPosts for saathratri orchestrator");
        List<PsqlPostDTO> entityList;
        entityList = psqlPostService.findAllForSaathratriOrchestrator();
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /psql-posts/:id} : get the "id" psqlPost.
     *
     * @param id the id of the psqlPostDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the psqlPostDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PsqlPostDTO> getPsqlPost(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get PsqlPost : {}", id);
        Optional<PsqlPostDTO> psqlPostDTO = psqlPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(psqlPostDTO);
    }

    /**
     * {@code DELETE  /psql-posts/:id} : delete the "id" psqlPost.
     *
     * @param id the id of the psqlPostDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePsqlPost(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete PsqlPost : {}", id);
        psqlPostService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
