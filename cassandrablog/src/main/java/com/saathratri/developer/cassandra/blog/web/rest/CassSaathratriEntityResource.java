package com.saathratri.developer.cassandra.blog.web.rest;

import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntityRepository;
import com.saathratri.developer.cassandra.blog.service.CassSaathratriEntityService;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntityDTO;
import com.saathratri.developer.cassandra.blog.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity}.
 */
@RestController
@RequestMapping("/api/cass-saathratri-entities")
public class CassSaathratriEntityResource {

    private static final Logger LOG = LoggerFactory.getLogger(CassSaathratriEntityResource.class);

    private static final String ENTITY_NAME = "cassandrablogCassSaathratriEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassSaathratriEntityService cassSaathratriEntityService;

    private final CassSaathratriEntityRepository cassSaathratriEntityRepository;

    public CassSaathratriEntityResource(
        CassSaathratriEntityService cassSaathratriEntityService,
        CassSaathratriEntityRepository cassSaathratriEntityRepository
    ) {
        this.cassSaathratriEntityService = cassSaathratriEntityService;
        this.cassSaathratriEntityRepository = cassSaathratriEntityRepository;
    }

    /**
     * {@code POST  /cass-saathratri-entities} : Create a new cassSaathratriEntity.
     *
     * @param cassSaathratriEntityDTO the cassSaathratriEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassSaathratriEntityDTO, or with status {@code 400 (Bad Request)} if the cassSaathratriEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassSaathratriEntityDTO> createCassSaathratriEntity(@RequestBody CassSaathratriEntityDTO cassSaathratriEntityDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CassSaathratriEntity : {}", cassSaathratriEntityDTO);

        // Single-value Primary Key Code
        if (cassSaathratriEntityDTO.getEntityId() == null) {
            throw new BadRequestAlertException("A new cassSaathratriEntity must have an ID", ENTITY_NAME, "idinvalid");
        }

        cassSaathratriEntityDTO = cassSaathratriEntityService.save(cassSaathratriEntityDTO);
        // Single-value Primary Key Code
        return ResponseEntity.created(new URI("/api/cass-saathratri-entities/" + cassSaathratriEntityDTO.getEntityId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cassSaathratriEntityDTO.getEntityId().toString())
            )
            .body(cassSaathratriEntityDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PUT  /cass-saathratri-entities/:entityId} : Updates an existing cassSaathratriEntity.
     *
     * // Single-value Primary Key Code
     * @param entityId the entityId of the cassSaathratriEntityDTO to save.
     * @param cassSaathratriEntityDTO the cassSaathratriEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassSaathratriEntityDTO,
     * or with status {@code 400 (Bad Request)} if the cassSaathratriEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassSaathratriEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PutMapping("/{entityId}")
    public ResponseEntity<CassSaathratriEntityDTO> updateCassSaathratriEntity(
        // Single-value Primary Key Code
        @PathVariable(value = "entityId", required = false) final UUID entityId,
        @RequestBody CassSaathratriEntityDTO cassSaathratriEntityDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to update CassSaathratriEntity : {}, {}", entityId, cassSaathratriEntityDTO);
        // Single-value Primary Key Code
        if (cassSaathratriEntityDTO.getEntityId() == null) {
            throw new BadRequestAlertException("Invalid entityId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(entityId, cassSaathratriEntityDTO.getEntityId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassSaathratriEntityRepository.existsById(entityId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassSaathratriEntityDTO = cassSaathratriEntityService.update(cassSaathratriEntityDTO);
        // Single-value Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassSaathratriEntityDTO.getEntityId().toString())
            )
            .body(cassSaathratriEntityDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PATCH  /cass-saathratri-entities/:entityId} : Partial updates given fields of an existing cassSaathratriEntity, field will ignore if it is null
     *
     * // Single-value Primary Key Code
     * @param entityId the entityId of the cassSaathratriEntityDTO to save.
     * @param cassSaathratriEntityDTO the cassSaathratriEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassSaathratriEntityDTO,
     * or with status {@code 400 (Bad Request)} if the cassSaathratriEntityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassSaathratriEntityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassSaathratriEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PatchMapping(value = "/{entityId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CassSaathratriEntityDTO> partialUpdateCassSaathratriEntity(
        // Single-value Primary Key Code
        @PathVariable(value = "entityId", required = false) final UUID entityId,
        @RequestBody CassSaathratriEntityDTO cassSaathratriEntityDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to partial update CassSaathratriEntity partially : {}, {}", entityId, cassSaathratriEntityDTO);
        // Single-value Primary Key Code
        if (cassSaathratriEntityDTO.getEntityId() == null) {
            throw new BadRequestAlertException("Invalid entityId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(entityId, cassSaathratriEntityDTO.getEntityId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassSaathratriEntityRepository.existsById(entityId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassSaathratriEntityDTO> result = cassSaathratriEntityService.partialUpdate(cassSaathratriEntityDTO);

        // Single-value Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassSaathratriEntityDTO.getEntityId().toString())
        );
    }

    /**
     * {@code GET  /cass-saathratri-entities} : get all the cassSaathratriEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassSaathratriEntities in body.
     */
    @GetMapping("")
    public List<CassSaathratriEntityDTO> getAllCassSaathratriEntities() {
        LOG.debug("REST request to get all CassSaathratriEntities");
        return cassSaathratriEntityService.findAll();
    }

    /**
     * {@code GET  /cass-saathratri-entities/slice} : get cassSaathratriEntities with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassSaathratriEntities in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassSaathratriEntityDTO>> getAllCassSaathratriEntitiesSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassSaathratriEntities, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassSaathratriEntityDTO> slice = cassSaathratriEntityService.findAllSlice(cassandraPageRequest);
        List<CassSaathratriEntityDTO> result = slice.getContent();

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
     * {@code GET  /:entityId} : get the "entityId" cassSaathratriEntity.
     *
     * // Single-value Primary Key Code
     * @param entityId the entityId of the cassSaathratriEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassSaathratriEntityDTO, or with status {@code 404 (Not Found)}.
     */
    // Single-value Primary Key Code
    @GetMapping("/{entityId}")
    // Single-value Primary Key Code
    public ResponseEntity<CassSaathratriEntityDTO> getCassSaathratriEntity(@PathVariable("entityId") UUID entityId) {
        // Single-value Primary Key Code
        LOG.debug("REST request to get CassSaathratriEntity : {}", entityId);

        Optional<CassSaathratriEntityDTO> cassSaathratriEntityDTO = cassSaathratriEntityService.findOne(entityId);
        return ResponseUtil.wrapOrNotFound(cassSaathratriEntityDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code DELETE  /:entityId} : delete the "entityId" cassSaathratriEntity.
     *
     * // Single-value Primary Key Code
     * @param entityId the entityId of the cassSaathratriEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Single-value Primary Key Code
    @DeleteMapping("/{entityId}")
    // Single-value Primary Key Code
    public ResponseEntity<Void> deleteCassSaathratriEntity(@PathVariable("entityId") UUID entityId) {
        // Single-value Primary Key Code
        LOG.debug("REST request to delete CassSaathratriEntity : {}", entityId);
        cassSaathratriEntityService.delete(entityId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, entityId.toString()))
            .build();
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
