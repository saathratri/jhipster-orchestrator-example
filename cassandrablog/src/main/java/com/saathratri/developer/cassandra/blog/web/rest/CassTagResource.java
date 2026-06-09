package com.saathratri.developer.cassandra.blog.web.rest;

import com.saathratri.developer.cassandra.blog.repository.CassTagRepository;
import com.saathratri.developer.cassandra.blog.service.CassTagService;
import com.saathratri.developer.cassandra.blog.service.dto.CassTagDTO;
import com.saathratri.developer.cassandra.blog.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassTag}.
 */
@RestController
@RequestMapping("/api/cass-tags")
public class CassTagResource {

    private static final Logger LOG = LoggerFactory.getLogger(CassTagResource.class);

    private static final String ENTITY_NAME = "cassandrablogCassTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassTagService cassTagService;

    private final CassTagRepository cassTagRepository;

    public CassTagResource(CassTagService cassTagService, CassTagRepository cassTagRepository) {
        this.cassTagService = cassTagService;
        this.cassTagRepository = cassTagRepository;
    }

    /**
     * {@code POST  /cass-tags} : Create a new cassTag.
     *
     * @param cassTagDTO the cassTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassTagDTO, or with status {@code 400 (Bad Request)} if the cassTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassTagDTO> createCassTag(@Valid @RequestBody CassTagDTO cassTagDTO) throws URISyntaxException {
        LOG.debug("REST request to save CassTag : {}", cassTagDTO);

        // Single-value Primary Key Code
        if (cassTagDTO.getId() == null) {
            throw new BadRequestAlertException("A new cassTag must have an ID", ENTITY_NAME, "idinvalid");
        }

        cassTagDTO = cassTagService.save(cassTagDTO);
        // Single-value Primary Key Code
        return ResponseEntity.created(new URI("/api/cass-tags/" + cassTagDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cassTagDTO.getId().toString()))
            .body(cassTagDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PUT  /cass-tags/:id} : Updates an existing cassTag.
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassTagDTO to save.
     * @param cassTagDTO the cassTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassTagDTO,
     * or with status {@code 400 (Bad Request)} if the cassTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PutMapping("/{id}")
    public ResponseEntity<CassTagDTO> updateCassTag(
        // Single-value Primary Key Code
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody CassTagDTO cassTagDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to update CassTag : {}, {}", id, cassTagDTO);
        // Single-value Primary Key Code
        if (cassTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(id, cassTagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassTagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassTagDTO = cassTagService.update(cassTagDTO);
        // Single-value Primary Key Code
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassTagDTO.getId().toString()))
            .body(cassTagDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PATCH  /cass-tags/:id} : Partial updates given fields of an existing cassTag, field will ignore if it is null
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassTagDTO to save.
     * @param cassTagDTO the cassTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassTagDTO,
     * or with status {@code 400 (Bad Request)} if the cassTagDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassTagDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CassTagDTO> partialUpdateCassTag(
        // Single-value Primary Key Code
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody CassTagDTO cassTagDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to partial update CassTag partially : {}, {}", id, cassTagDTO);
        // Single-value Primary Key Code
        if (cassTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(id, cassTagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassTagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassTagDTO> result = cassTagService.partialUpdate(cassTagDTO);

        // Single-value Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassTagDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cass-tags} : get all the cassTags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassTags in body.
     */
    @GetMapping("")
    public List<CassTagDTO> getAllCassTags() {
        LOG.debug("REST request to get all CassTags");
        return cassTagService.findAll();
    }

    /**
     * {@code GET  /cass-tags/slice} : get cassTags with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassTags in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassTagDTO>> getAllCassTagsSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassTags, pagingState: {}, size: {}", pagingState, size);

        // Build CassandraPageRequest from pagingState parameter
        org.springframework.data.domain.Pageable cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                java.nio.ByteBuffer pagingStateBuffer;
                try {
                    pagingStateBuffer = java.nio.ByteBuffer.wrap(java.util.Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    pagingStateBuffer = java.nio.ByteBuffer.wrap(java.util.Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(org.springframework.data.domain.PageRequest.of(0, size), pagingStateBuffer);
            } catch (Exception e) {
                LOG.warn("Invalid paging state, starting from beginning", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassTagDTO> slice = cassTagService.findAllSlice(cassandraPageRequest);
        List<CassTagDTO> result = slice.getContent();

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        boolean hasNext = slice.hasNext();
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            java.nio.ByteBuffer nextPagingState = null;
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            if (currentCassandraPageRequest.getPagingState() != null) {
                org.springframework.data.domain.Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            }
            if (nextPagingState != null) {
                byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
                nextPagingState.duplicate().get(pagingStateBytes);
                headers.add("X-Paging-State", java.util.Base64.getUrlEncoder().encodeToString(pagingStateBytes));
            }
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));
        headers.add("Access-Control-Expose-Headers", "X-Has-Next-Page, X-Paging-State, X-Total-Count");

        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * // Single-value Primary Key Code
     * {@code GET  /:id} : get the "id" cassTag.
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassTagDTO, or with status {@code 404 (Not Found)}.
     */
    // Single-value Primary Key Code
    @GetMapping("/{id}")
    // Single-value Primary Key Code
    public ResponseEntity<CassTagDTO> getCassTag(@PathVariable("id") UUID id) {
        // Single-value Primary Key Code
        LOG.debug("REST request to get CassTag : {}", id);

        Optional<CassTagDTO> cassTagDTO = cassTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cassTagDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code DELETE  /:id} : delete the "id" cassTag.
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Single-value Primary Key Code
    @DeleteMapping("/{id}")
    // Single-value Primary Key Code
    public ResponseEntity<Void> deleteCassTag(@PathVariable("id") UUID id) {
        // Single-value Primary Key Code
        LOG.debug("REST request to delete CassTag : {}", id);
        cassTagService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    // ==================== AI Text Search Endpoint ====================

    /**
     * {@code GET  /cass-tags/ai-search} : search for cassTags using AI-powered semantic similarity.
     *
     * @param query the text query to search for.
     * @param limit maximum number of results to return (default: 10).
     * @param fields optional comma-separated list of vector field names to search in.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matching entities in body.
     */
    @GetMapping("/ai-search")
    public ResponseEntity<List<CassTagDTO>> aiSearch(
        @RequestParam("query") String query,
        @RequestParam(value = "limit", defaultValue = "10") int limit,
        @RequestParam(value = "fields", required = false) String fields
    ) {
        LOG.debug("REST request to AI search CassTags for query: {}, limit: {}, fields: {}", query, limit, fields);
        List<String> fieldList = null;
        if (fields != null && !fields.isBlank()) {
            fieldList = java.util.Arrays.stream(fields.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(java.util.stream.Collectors.toList());
        }
        List<CassTagDTO> result = cassTagService.aiSearch(query, limit, fieldList);
        return ResponseEntity.ok().body(result);
    }

    private String getUrlEncodedParameterValue(String parameterValue) {
        String encodedValue = null;
        try {
            encodedValue = URLEncoder.encode(parameterValue, StandardCharsets.UTF_8);
            LOG.info("Encoded String '{}' is '{}'.", parameterValue, encodedValue);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return encodedValue;
    }
}
