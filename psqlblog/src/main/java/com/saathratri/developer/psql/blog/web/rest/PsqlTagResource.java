package com.saathratri.developer.psql.blog.web.rest;

import com.saathratri.developer.psql.blog.repository.PsqlTagRepository;
import com.saathratri.developer.psql.blog.service.PsqlTagService;
import com.saathratri.developer.psql.blog.service.dto.PsqlTagDTO;
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
 * REST controller for managing {@link com.saathratri.developer.psql.blog.domain.PsqlTag}.
 */
@RestController
@RequestMapping("/api/psql-tags")
public class PsqlTagResource {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlTagResource.class);

    private static final String ENTITY_NAME = "psqlblogPsqlTag";

    @Value("${jhipster.clientApp.name:psqlblog}")
    private String applicationName;

    private final PsqlTagService psqlTagService;

    private final PsqlTagRepository psqlTagRepository;

    public PsqlTagResource(PsqlTagService psqlTagService, PsqlTagRepository psqlTagRepository) {
        this.psqlTagService = psqlTagService;
        this.psqlTagRepository = psqlTagRepository;
    }

    /**
     * {@code POST  /psql-tags} : Create a new psqlTag.
     *
     * @param psqlTagDTO the psqlTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new psqlTagDTO, or with status {@code 400 (Bad Request)} if the psqlTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PsqlTagDTO> createPsqlTag(@Valid @RequestBody PsqlTagDTO psqlTagDTO) throws URISyntaxException {
        LOG.debug("REST request to save PsqlTag : {}", psqlTagDTO);
        if (psqlTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new psqlTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        psqlTagDTO = psqlTagService.save(psqlTagDTO);
        return ResponseEntity.created(new URI("/api/psql-tags/" + psqlTagDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, psqlTagDTO.getId().toString()))
            .body(psqlTagDTO);
    }

    /**
     * {@code PUT  /psql-tags/:id} : Updates an existing psqlTag.
     *
     * @param id the id of the psqlTagDTO to save.
     * @param psqlTagDTO the psqlTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psqlTagDTO,
     * or with status {@code 400 (Bad Request)} if the psqlTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the psqlTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PsqlTagDTO> updatePsqlTag(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody PsqlTagDTO psqlTagDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PsqlTag : {}, {}", id, psqlTagDTO);
        if (psqlTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psqlTagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psqlTagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        psqlTagDTO = psqlTagService.update(psqlTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psqlTagDTO.getId().toString()))
            .body(psqlTagDTO);
    }

    /**
     * {@code PATCH  /psql-tags/:id} : Partial updates given fields of an existing psqlTag, field will ignore if it is null
     *
     * @param id the id of the psqlTagDTO to save.
     * @param psqlTagDTO the psqlTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psqlTagDTO,
     * or with status {@code 400 (Bad Request)} if the psqlTagDTO is not valid,
     * or with status {@code 404 (Not Found)} if the psqlTagDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the psqlTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PsqlTagDTO> partialUpdatePsqlTag(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody PsqlTagDTO psqlTagDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PsqlTag partially : {}, {}", id, psqlTagDTO);
        if (psqlTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psqlTagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psqlTagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PsqlTagDTO> result = psqlTagService.partialUpdate(psqlTagDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psqlTagDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /psql-tags} : get all the Psql Tags.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Psql Tags in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PsqlTagDTO>> getAllPsqlTags(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of PsqlTags");
        Page<PsqlTagDTO> page = psqlTagService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /saathratri-orchestrator} : get all the Psql Tags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Psql Tags in body.
     */
    @GetMapping("/saathratri-orchestrator")
    public ResponseEntity<List<PsqlTagDTO>> getAllPsqlTagsForSaathratriOrchestrator() {
        LOG.debug("REST request to get all PsqlTags for saathratri orchestrator");
        List<PsqlTagDTO> entityList;
        entityList = psqlTagService.findAllForSaathratriOrchestrator();
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /psql-tags/:id} : get the "id" psqlTag.
     *
     * @param id the id of the psqlTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the psqlTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PsqlTagDTO> getPsqlTag(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get PsqlTag : {}", id);
        Optional<PsqlTagDTO> psqlTagDTO = psqlTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(psqlTagDTO);
    }

    /**
     * {@code DELETE  /psql-tags/:id} : delete the "id" psqlTag.
     *
     * @param id the id of the psqlTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePsqlTag(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete PsqlTag : {}", id);
        psqlTagService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    // ==================== AI Text Search Endpoint ====================

    /**
     * {@code GET  /psql-tags/ai-search} : search for Psql Tags using AI-powered semantic similarity.
     * Converts the text query to an embedding and searches across all vector fields,
     * or only the specified fields if the 'fields' parameter is provided.
     *
     * @param query the text query to search for.
     * @param limit maximum number of results to return (default: 10).
     * @param fields optional comma-separated list of vector field names to search in.
     *               If not provided or empty, all vector fields are searched.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matching Psql Tags in body.
     */
    @GetMapping("/ai-search")
    public ResponseEntity<List<PsqlTagDTO>> aiSearch(
        @RequestParam("query") String query,
        @RequestParam(value = "limit", defaultValue = "10") int limit,
        @RequestParam(value = "fields", required = false) String fields
    ) {
        LOG.debug("REST request to AI search PsqlTags for query: {}, limit: {}, fields: {}", query, limit, fields);
        List<String> fieldList = null;
        if (fields != null && !fields.isBlank()) {
            fieldList = java.util.Arrays.stream(fields.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(java.util.stream.Collectors.toList());
        }
        List<PsqlTagDTO> result = psqlTagService.aiSearch(query, limit, fieldList);
        return ResponseEntity.ok().body(result);
    }

    // ==================== Vector Similarity Search Endpoints ====================

    /**
     * {@code POST  /psql-tags/vector-search/nameEmbedding} : search for Psql Tags similar to the given embedding.
     *
     * @param embedding the query embedding vector as a JSON array string (e.g., "[0.1, 0.2, ...]")
     * @param limit maximum number of results to return (default: 10)
     * @return the list of similar Psql Tags ordered by similarity.
     */
    @PostMapping("/vector-search/nameEmbedding")
    public ResponseEntity<List<PsqlTagDTO>> searchSimilarByNameEmbedding(
        @RequestBody String embedding,
        @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        LOG.debug("REST request to search PsqlTags similar by nameEmbedding, limit: {}", limit);
        List<PsqlTagDTO> result = psqlTagService.findSimilarByNameEmbedding(embedding, limit);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST  /psql-tags/vector-search/nameEmbedding/threshold} : search for Psql Tags similar to the given embedding with distance threshold.
     *
     * @param embedding the query embedding vector as a JSON array string (e.g., "[0.1, 0.2, ...]")
     * @param maxDistance maximum cosine distance (0 = identical, 2 = opposite). Default: 0.5
     * @param limit maximum number of results to return (default: 10)
     * @return the list of similar Psql Tags ordered by similarity.
     */
    @PostMapping("/vector-search/nameEmbedding/threshold")
    public ResponseEntity<List<PsqlTagDTO>> searchSimilarByNameEmbeddingWithThreshold(
        @RequestBody String embedding,
        @RequestParam(value = "maxDistance", defaultValue = "0.5") double maxDistance,
        @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        LOG.debug(
            "REST request to search PsqlTags similar by nameEmbedding with threshold, maxDistance: {}, limit: {}",
            maxDistance,
            limit
        );
        List<PsqlTagDTO> result = psqlTagService.findSimilarByNameEmbeddingWithThreshold(embedding, maxDistance, limit);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST  /psql-tags/vector-search/descriptionEmbedding} : search for Psql Tags similar to the given embedding.
     *
     * @param embedding the query embedding vector as a JSON array string (e.g., "[0.1, 0.2, ...]")
     * @param limit maximum number of results to return (default: 10)
     * @return the list of similar Psql Tags ordered by similarity.
     */
    @PostMapping("/vector-search/descriptionEmbedding")
    public ResponseEntity<List<PsqlTagDTO>> searchSimilarByDescriptionEmbedding(
        @RequestBody String embedding,
        @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        LOG.debug("REST request to search PsqlTags similar by descriptionEmbedding, limit: {}", limit);
        List<PsqlTagDTO> result = psqlTagService.findSimilarByDescriptionEmbedding(embedding, limit);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST  /psql-tags/vector-search/descriptionEmbedding/threshold} : search for Psql Tags similar to the given embedding with distance threshold.
     *
     * @param embedding the query embedding vector as a JSON array string (e.g., "[0.1, 0.2, ...]")
     * @param maxDistance maximum cosine distance (0 = identical, 2 = opposite). Default: 0.5
     * @param limit maximum number of results to return (default: 10)
     * @return the list of similar Psql Tags ordered by similarity.
     */
    @PostMapping("/vector-search/descriptionEmbedding/threshold")
    public ResponseEntity<List<PsqlTagDTO>> searchSimilarByDescriptionEmbeddingWithThreshold(
        @RequestBody String embedding,
        @RequestParam(value = "maxDistance", defaultValue = "0.5") double maxDistance,
        @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        LOG.debug(
            "REST request to search PsqlTags similar by descriptionEmbedding with threshold, maxDistance: {}, limit: {}",
            maxDistance,
            limit
        );
        List<PsqlTagDTO> result = psqlTagService.findSimilarByDescriptionEmbeddingWithThreshold(embedding, maxDistance, limit);
        return ResponseEntity.ok().body(result);
    }
}
