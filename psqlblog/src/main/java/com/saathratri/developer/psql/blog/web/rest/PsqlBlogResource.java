package com.saathratri.developer.psql.blog.web.rest;

import com.saathratri.developer.psql.blog.repository.PsqlBlogRepository;
import com.saathratri.developer.psql.blog.service.PsqlBlogService;
import com.saathratri.developer.psql.blog.service.dto.PsqlBlogDTO;
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
 * REST controller for managing {@link com.saathratri.developer.psql.blog.domain.PsqlBlog}.
 */
@RestController
@RequestMapping("/api/psql-blogs")
public class PsqlBlogResource {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlBlogResource.class);

    private static final String ENTITY_NAME = "psqlblogPsqlBlog";

    @Value("${jhipster.clientApp.name:psqlblog}")
    private String applicationName;

    private final PsqlBlogService psqlBlogService;

    private final PsqlBlogRepository psqlBlogRepository;

    public PsqlBlogResource(PsqlBlogService psqlBlogService, PsqlBlogRepository psqlBlogRepository) {
        this.psqlBlogService = psqlBlogService;
        this.psqlBlogRepository = psqlBlogRepository;
    }

    /**
     * {@code POST  /psql-blogs} : Create a new psqlBlog.
     *
     * @param psqlBlogDTO the psqlBlogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new psqlBlogDTO, or with status {@code 400 (Bad Request)} if the psqlBlog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PsqlBlogDTO> createPsqlBlog(@Valid @RequestBody PsqlBlogDTO psqlBlogDTO) throws URISyntaxException {
        LOG.debug("REST request to save PsqlBlog : {}", psqlBlogDTO);
        if (psqlBlogDTO.getId() != null) {
            throw new BadRequestAlertException("A new psqlBlog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        psqlBlogDTO = psqlBlogService.save(psqlBlogDTO);
        return ResponseEntity.created(new URI("/api/psql-blogs/" + psqlBlogDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, psqlBlogDTO.getId().toString()))
            .body(psqlBlogDTO);
    }

    /**
     * {@code PUT  /psql-blogs/:id} : Updates an existing psqlBlog.
     *
     * @param id the id of the psqlBlogDTO to save.
     * @param psqlBlogDTO the psqlBlogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psqlBlogDTO,
     * or with status {@code 400 (Bad Request)} if the psqlBlogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the psqlBlogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PsqlBlogDTO> updatePsqlBlog(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody PsqlBlogDTO psqlBlogDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PsqlBlog : {}, {}", id, psqlBlogDTO);
        if (psqlBlogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psqlBlogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psqlBlogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        psqlBlogDTO = psqlBlogService.update(psqlBlogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psqlBlogDTO.getId().toString()))
            .body(psqlBlogDTO);
    }

    /**
     * {@code PATCH  /psql-blogs/:id} : Partial updates given fields of an existing psqlBlog, field will ignore if it is null
     *
     * @param id the id of the psqlBlogDTO to save.
     * @param psqlBlogDTO the psqlBlogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psqlBlogDTO,
     * or with status {@code 400 (Bad Request)} if the psqlBlogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the psqlBlogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the psqlBlogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PsqlBlogDTO> partialUpdatePsqlBlog(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody PsqlBlogDTO psqlBlogDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PsqlBlog partially : {}, {}", id, psqlBlogDTO);
        if (psqlBlogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psqlBlogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psqlBlogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PsqlBlogDTO> result = psqlBlogService.partialUpdate(psqlBlogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psqlBlogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /psql-blogs} : get all the Psql Blogs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Psql Blogs in body.
     */
    @GetMapping("")
    public List<PsqlBlogDTO> getAllPsqlBlogs(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        LOG.debug("REST request to get all PsqlBlogs");
        return psqlBlogService.findAll();
    }

    /**
     * {@code GET  /saathratri-orchestrator} : get all the Psql Blogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Psql Blogs in body.
     */
    @GetMapping("/saathratri-orchestrator")
    public ResponseEntity<List<PsqlBlogDTO>> getAllPsqlBlogsForSaathratriOrchestrator() {
        LOG.debug("REST request to get all PsqlBlogs for saathratri orchestrator");
        List<PsqlBlogDTO> entityList;
        entityList = psqlBlogService.findAllForSaathratriOrchestrator();
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /psql-blogs/:id} : get the "id" psqlBlog.
     *
     * @param id the id of the psqlBlogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the psqlBlogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PsqlBlogDTO> getPsqlBlog(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get PsqlBlog : {}", id);
        Optional<PsqlBlogDTO> psqlBlogDTO = psqlBlogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(psqlBlogDTO);
    }

    /**
     * {@code DELETE  /psql-blogs/:id} : delete the "id" psqlBlog.
     *
     * @param id the id of the psqlBlogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePsqlBlog(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete PsqlBlog : {}", id);
        psqlBlogService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
