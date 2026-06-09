package com.saathratri.developer.cassandra.blog.web.rest;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3Id;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntity3Repository;
import com.saathratri.developer.cassandra.blog.service.CassSaathratriEntity3Service;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity3DTO;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3}.
 */
@RestController
@RequestMapping("/api/cass-saathratri-entity-3-s")
public class CassSaathratriEntity3Resource {

    private static final Logger LOG = LoggerFactory.getLogger(CassSaathratriEntity3Resource.class);

    private static final String ENTITY_NAME = "cassandrablogCassSaathratriEntity3";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassSaathratriEntity3Service cassSaathratriEntity3Service;

    private final CassSaathratriEntity3Repository cassSaathratriEntity3Repository;

    public CassSaathratriEntity3Resource(
        CassSaathratriEntity3Service cassSaathratriEntity3Service,
        CassSaathratriEntity3Repository cassSaathratriEntity3Repository
    ) {
        this.cassSaathratriEntity3Service = cassSaathratriEntity3Service;
        this.cassSaathratriEntity3Repository = cassSaathratriEntity3Repository;
    }

    /**
     * {@code POST  /cass-saathratri-entity-3-s} : Create a new cassSaathratriEntity3.
     *
     * @param cassSaathratriEntity3DTO the cassSaathratriEntity3DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassSaathratriEntity3DTO, or with status {@code 400 (Bad Request)} if the cassSaathratriEntity3 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassSaathratriEntity3DTO> createCassSaathratriEntity3(
        @RequestBody CassSaathratriEntity3DTO cassSaathratriEntity3DTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save CassSaathratriEntity3 : {}", cassSaathratriEntity3DTO);

        // Generate a TimeUUID for the Primary Key composite fields.

        cassSaathratriEntity3DTO.getCompositeId().setCreatedTimeId(Uuids.timeBased());

        // Composite Primary Key Code
        if (
            cassSaathratriEntity3DTO.getCompositeId().getEntityType() == null ||
            cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("A new cassSaathratriEntity3 cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        cassSaathratriEntity3DTO = cassSaathratriEntity3Service.save(cassSaathratriEntity3DTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/cass-saathratri-entity-3-s/" +
                    getUrlEncodedParameterValue(cassSaathratriEntity3DTO.getCompositeId().getEntityType()) +
                    "/" +
                    cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassSaathratriEntity3DTO.getCompositeId().toString()
                )
            )
            .body(cassSaathratriEntity3DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /cass-saathratri-entity-3-s/:entityType/:createdTimeId} : Updates an existing cassSaathratriEntity3.
     *
     * // Composite Primary Key Code
     * @param entityType the Entity Type of the cassSaathratriEntity3 to update.
     * @param createdTimeId the Created Time Id of the cassSaathratriEntity3 to update.
     * @param cassSaathratriEntity3DTO the cassSaathratriEntity3DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassSaathratriEntity3DTO,
     * or with status {@code 400 (Bad Request)} if the cassSaathratriEntity3DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassSaathratriEntity3DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{entityType}/{createdTimeId}")
    public ResponseEntity<CassSaathratriEntity3DTO> updateCassSaathratriEntity3(
        // Composite Primary Key Code
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestBody CassSaathratriEntity3DTO cassSaathratriEntity3DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update CassSaathratriEntity3 with parameters entityType: {}, createdTimeId: {}, cassSaathratriEntity3DTO: {}",
            entityType,
            createdTimeId,
            cassSaathratriEntity3DTO
        );
        // Composite Primary Key Code
        if (
            cassSaathratriEntity3DTO.getCompositeId().getEntityType() == null ||
            cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("Invalid entityType", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(entityType, cassSaathratriEntity3DTO.getCompositeId().getEntityType()) ||
            !Objects.equals(createdTimeId, cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassSaathratriEntity3Repository.existsById(
                new CassSaathratriEntity3Id(
                    cassSaathratriEntity3DTO.getCompositeId().getEntityType(),
                    cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassSaathratriEntity3DTO = cassSaathratriEntity3Service.update(cassSaathratriEntity3DTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassSaathratriEntity3DTO.getCompositeId().toString())
            )
            .body(cassSaathratriEntity3DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /cass-saathratri-entity-3-s/:entityType/:createdTimeId} : Partial updates given fields of an existing cassSaathratriEntity3, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param entityType the Entity Type of the cassSaathratriEntity3 to partially update.
     * @param createdTimeId the Created Time Id of the cassSaathratriEntity3 to partially update.
     * @param cassSaathratriEntity3DTO the cassSaathratriEntity3DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassSaathratriEntity3DTO,
     * or with status {@code 400 (Bad Request)} if the cassSaathratriEntity3DTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassSaathratriEntity3DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassSaathratriEntity3DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(value = "/{entityType}/{createdTimeId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CassSaathratriEntity3DTO> partialUpdateCassSaathratriEntity3(
        // Composite Primary Key Code
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestBody CassSaathratriEntity3DTO cassSaathratriEntity3DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update CassSaathratriEntity3 with the parameters entityType: {}, createdTimeId: {}, cassSaathratriEntity3DTO: {}",
            entityType,
            createdTimeId,
            cassSaathratriEntity3DTO
        );
        // Composite Primary Key Code
        if (
            cassSaathratriEntity3DTO.getCompositeId().getEntityType() == null ||
            cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("Invalid entityType", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(entityType, cassSaathratriEntity3DTO.getCompositeId().getEntityType()) ||
            !Objects.equals(createdTimeId, cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassSaathratriEntity3Repository.existsById(
                new CassSaathratriEntity3Id(
                    cassSaathratriEntity3DTO.getCompositeId().getEntityType(),
                    cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassSaathratriEntity3DTO> result = cassSaathratriEntity3Service.partialUpdate(cassSaathratriEntity3DTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassSaathratriEntity3DTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /cass-saathratri-entity-3-s} : get all the cassSaathratriEntity3s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassSaathratriEntity3s in body.
     */
    @GetMapping("")
    public List<CassSaathratriEntity3DTO> getAllCassSaathratriEntity3s() {
        LOG.debug("REST request to get all CassSaathratriEntity3s");
        return cassSaathratriEntity3Service.findAll();
    }

    /**
     * {@code GET  /cass-saathratri-entity-3-s/slice} : get cassSaathratriEntity3s with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassSaathratriEntity3s in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassSaathratriEntity3DTO>> getAllCassSaathratriEntity3sSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassSaathratriEntity3s, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassSaathratriEntity3DTO> slice = cassSaathratriEntity3Service.findAllSlice(cassandraPageRequest);
        List<CassSaathratriEntity3DTO> result = slice.getContent();

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
     * // Composite Primary Key Code
     * {@code GET  /:entityType/:createdTimeId} : get the "entityType" cassSaathratriEntity3.
     *
     * // Composite Primary Key Code
     * @param entityType the Entity Type of the cassSaathratriEntity3 to retrieve.
     * @param createdTimeId the Created Time Id of the cassSaathratriEntity3 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassSaathratriEntity3DTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<CassSaathratriEntity3DTO> getCassSaathratriEntity3(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug("REST request to get CassSaathratriEntity3 with parameters entityType: {}, createdTimeId: {}", entityType, createdTimeId);
        // Composite Primary Key Code
        CassSaathratriEntity3Id compositeId = new CassSaathratriEntity3Id();
        compositeId.setEntityType(entityType);
        compositeId.setCreatedTimeId(createdTimeId);

        Optional<CassSaathratriEntity3DTO> cassSaathratriEntity3DTO = cassSaathratriEntity3Service.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(cassSaathratriEntity3DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:entityType/:createdTimeId} : delete the "compositeId" cassSaathratriEntity3.
     *
     * // Composite Primary Key Code
     * @param entityType the Entity Type of the cassSaathratriEntity3 to delete.
     * @param createdTimeId the Created Time Id of the cassSaathratriEntity3 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{entityType}/{createdTimeId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteCassSaathratriEntity3(
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete CassSaathratriEntity3 with parameters entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        // Composite Primary Key Code
        CassSaathratriEntity3Id compositeId = new CassSaathratriEntity3Id();
        compositeId.setEntityType(entityType);
        compositeId.setCreatedTimeId(createdTimeId);
        cassSaathratriEntity3Service.delete(compositeId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, entityType))
            .build();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type/:entityType}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type")
    public List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityType(
        @RequestParam(name = "entityType", required = true) final String entityType
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityType method for CassSaathratriEntity3s with parameteres entityType: {}",
            entityType
        );
        return cassSaathratriEntity3Service.findAllByCompositeIdEntityType(entityType);
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-pageable/:entityType} : get paginated entities by composite key.
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-pageable")
    public ResponseEntity<List<CassSaathratriEntity3DTO>> findAllByCompositeIdEntityTypePageable(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity3s with parameters entityType: {}, pagingState: {}, size: {}",
            entityType,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for CassSaathratriEntity3s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity3DTO> slice = cassSaathratriEntity3Service.findAllByCompositeIdEntityTypePageable(
            entityType,
            cassandraPageRequest
        );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity3s", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-by-composite-id-entity-type-and-composite-id-created-time-id/:entityType/:createdTimeId}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-composite-id-entity-type-and-composite-id-created-time-id")
    public Optional<CassSaathratriEntity3DTO> findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId method for CassSaathratriEntity3s with parameteres entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        return cassSaathratriEntity3Service.findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId(entityType, createdTimeId);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than/:entityType/:createdTimeId}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than")
    public List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan method for CassSaathratriEntity3s with parameteres entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        return cassSaathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThan(entityType, createdTimeId);
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-pageable/:entityType/:createdTimeId} : get paginated entities by composite key.
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-pageable")
    public ResponseEntity<List<CassSaathratriEntity3DTO>> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity3s with parameters entityType: {}, createdTimeId: {}, pagingState: {}, size: {}",
            entityType,
            createdTimeId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for CassSaathratriEntity3s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity3DTO> slice =
            cassSaathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable(
                entityType,
                createdTimeId,
                cassandraPageRequest
            );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity3s", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal/:entityType/:createdTimeId}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal")
    public List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual method for CassSaathratriEntity3s with parameteres entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        return cassSaathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqual(
            entityType,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal-pageable/:entityType/:createdTimeId} : get paginated entities by composite key.
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal-pageable")
    public ResponseEntity<List<CassSaathratriEntity3DTO>> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity3s with parameters entityType: {}, createdTimeId: {}, pagingState: {}, size: {}",
            entityType,
            createdTimeId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for CassSaathratriEntity3s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity3DTO> slice =
            cassSaathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable(
                entityType,
                createdTimeId,
                cassandraPageRequest
            );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity3s", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than/:entityType/:createdTimeId}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than")
    public List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan method for CassSaathratriEntity3s with parameteres entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        return cassSaathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThan(entityType, createdTimeId);
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-pageable/:entityType/:createdTimeId} : get paginated entities by composite key.
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-pageable")
    public ResponseEntity<List<CassSaathratriEntity3DTO>> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity3s with parameters entityType: {}, createdTimeId: {}, pagingState: {}, size: {}",
            entityType,
            createdTimeId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for CassSaathratriEntity3s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity3DTO> slice =
            cassSaathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable(
                entityType,
                createdTimeId,
                cassandraPageRequest
            );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity3s", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal/:entityType/:createdTimeId}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal")
    public List<CassSaathratriEntity3DTO> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual method for CassSaathratriEntity3s with parameteres entityType: {}, createdTimeId: {}",
            entityType,
            createdTimeId
        );
        return cassSaathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqual(
            entityType,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal-pageable/:entityType/:createdTimeId} : get paginated entities by composite key.
     *
     * @param entityType the Entity Type of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal-pageable")
    public ResponseEntity<List<CassSaathratriEntity3DTO>> findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity3s with parameters entityType: {}, createdTimeId: {}, pagingState: {}, size: {}",
            entityType,
            createdTimeId,
            pagingState,
            size
        );

        // Build CassandraPageRequest from pagingState parameter
        CassandraPageRequest cassandraPageRequest;
        if (pagingState == null || pagingState.isEmpty()) {
            cassandraPageRequest = CassandraPageRequest.first(size);
        } else {
            try {
                ByteBuffer pagingStateBuffer;
                try {
                    // Try URL-safe Base64 decoding first
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getUrlDecoder().decode(pagingState));
                } catch (IllegalArgumentException e) {
                    // Fall back to standard Base64 decoding
                    pagingStateBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(pagingState));
                }
                cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, size), pagingStateBuffer);
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid paging state for CassSaathratriEntity3s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity3DTO> slice =
            cassSaathratriEntity3Service.findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
                entityType,
                createdTimeId,
                cassandraPageRequest
            );

        // Generate Slice pagination headers (Cassandra cursor-based pagination)
        HttpHeaders headers = new HttpHeaders();

        boolean hasNext = slice.hasNext();
        headers.add("X-Page-Size", String.valueOf(slice.getSize()));

        // Extract paging state from current pageable (populated after query execution)
        ByteBuffer nextPagingState = null;
        if (hasNext && slice.getPageable() instanceof CassandraPageRequest) {
            CassandraPageRequest currentCassandraPageRequest = (CassandraPageRequest) slice.getPageable();
            nextPagingState = currentCassandraPageRequest.getPagingState();
        }
        if (hasNext && nextPagingState == null) {
            try {
                Pageable nextPageable = slice.nextPageable();
                if (nextPageable instanceof CassandraPageRequest) {
                    nextPagingState = ((CassandraPageRequest) nextPageable).getPagingState();
                }
            } catch (IllegalStateException e) {
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity3s", e);
            }
        }
        hasNext = hasNext && nextPagingState != null;
        if (nextPagingState != null) {
            byte[] pagingStateBytes = new byte[nextPagingState.remaining()];
            nextPagingState.duplicate().get(pagingStateBytes);
            headers.add("X-Paging-State", Base64.getUrlEncoder().encodeToString(pagingStateBytes));
        }
        headers.add("X-Has-Next-Page", String.valueOf(hasNext));

        return ResponseEntity.ok().headers(headers).body(slice.getContent());
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-latest-by-composite-id-entity-type/:entityType}
     *
     *
     * @param entityType the Entity Type of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity3, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-latest-by-composite-id-entity-type")
    public CassSaathratriEntity3DTO findLatestByCompositeIdEntityType(
        @RequestParam(name = "entityType", required = true) final String entityType
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findLatestByCompositeIdEntityType method for CassSaathratriEntity3s with parameteres entityType: {}",
            entityType
        );
        return cassSaathratriEntity3Service.findLatestByCompositeIdEntityType(entityType);
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
