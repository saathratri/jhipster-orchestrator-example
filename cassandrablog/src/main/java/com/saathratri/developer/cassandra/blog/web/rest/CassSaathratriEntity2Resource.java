package com.saathratri.developer.cassandra.blog.web.rest;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2Id;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntity2Repository;
import com.saathratri.developer.cassandra.blog.service.CassSaathratriEntity2Service;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity2DTO;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2}.
 */
@RestController
@RequestMapping("/api/cass-saathratri-entity-2-s")
public class CassSaathratriEntity2Resource {

    private static final Logger LOG = LoggerFactory.getLogger(CassSaathratriEntity2Resource.class);

    private static final String ENTITY_NAME = "cassandrablogCassSaathratriEntity2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassSaathratriEntity2Service cassSaathratriEntity2Service;

    private final CassSaathratriEntity2Repository cassSaathratriEntity2Repository;

    public CassSaathratriEntity2Resource(
        CassSaathratriEntity2Service cassSaathratriEntity2Service,
        CassSaathratriEntity2Repository cassSaathratriEntity2Repository
    ) {
        this.cassSaathratriEntity2Service = cassSaathratriEntity2Service;
        this.cassSaathratriEntity2Repository = cassSaathratriEntity2Repository;
    }

    /**
     * {@code POST  /cass-saathratri-entity-2-s} : Create a new cassSaathratriEntity2.
     *
     * @param cassSaathratriEntity2DTO the cassSaathratriEntity2DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassSaathratriEntity2DTO, or with status {@code 400 (Bad Request)} if the cassSaathratriEntity2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassSaathratriEntity2DTO> createCassSaathratriEntity2(
        @RequestBody CassSaathratriEntity2DTO cassSaathratriEntity2DTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save CassSaathratriEntity2 : {}", cassSaathratriEntity2DTO);

        // Composite Primary Key Code

        // Generate a TimeUUID for the Primary Key composite fields.

        cassSaathratriEntity2DTO.getCompositeId().setBlogId(Uuids.timeBased());

        if (
            cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId() == null ||
            cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded() == null ||
            cassSaathratriEntity2DTO.getCompositeId().getArrivalDate() == null ||
            cassSaathratriEntity2DTO.getCompositeId().getBlogId() == null
        ) {
            throw new BadRequestAlertException("A new cassSaathratriEntity2 cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        cassSaathratriEntity2DTO = cassSaathratriEntity2Service.save(cassSaathratriEntity2DTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/cass-saathratri-entity-2-s/" +
                    cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId() +
                    "/" +
                    cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded() +
                    "/" +
                    cassSaathratriEntity2DTO.getCompositeId().getArrivalDate() +
                    "/" +
                    cassSaathratriEntity2DTO.getCompositeId().getBlogId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassSaathratriEntity2DTO.getCompositeId().toString()
                )
            )
            .body(cassSaathratriEntity2DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /cass-saathratri-entity-2-s/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : Updates an existing cassSaathratriEntity2.
     *
     * // Composite Primary Key Code
     * @param entityTypeId the Entity Type Id of the cassSaathratriEntity2 to update.
     * @param yearOfDateAdded the Year Of Date Added of the cassSaathratriEntity2 to update.
     * @param arrivalDate the Arrival Date of the cassSaathratriEntity2 to update.
     * @param blogId the Blog Id of the cassSaathratriEntity2 to update.
     * @param cassSaathratriEntity2DTO the cassSaathratriEntity2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassSaathratriEntity2DTO,
     * or with status {@code 400 (Bad Request)} if the cassSaathratriEntity2DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassSaathratriEntity2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}")
    public ResponseEntity<CassSaathratriEntity2DTO> updateCassSaathratriEntity2(
        // Composite Primary Key Code
        @PathVariable(value = "entityTypeId", required = true) final UUID entityTypeId,
        @PathVariable(value = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "blogId", required = true) final UUID blogId,
        @RequestBody CassSaathratriEntity2DTO cassSaathratriEntity2DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update CassSaathratriEntity2 with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}, cassSaathratriEntity2DTO: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId,
            cassSaathratriEntity2DTO
        );
        // Composite Primary Key Code
        if (
            cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId() == null ||
            cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded() == null ||
            cassSaathratriEntity2DTO.getCompositeId().getArrivalDate() == null ||
            cassSaathratriEntity2DTO.getCompositeId().getBlogId() == null
        ) {
            throw new BadRequestAlertException("Invalid entityTypeId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(entityTypeId, cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId()) ||
            !Objects.equals(yearOfDateAdded, cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded()) ||
            !Objects.equals(arrivalDate, cassSaathratriEntity2DTO.getCompositeId().getArrivalDate()) ||
            !Objects.equals(blogId, cassSaathratriEntity2DTO.getCompositeId().getBlogId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassSaathratriEntity2Repository.existsById(
                new CassSaathratriEntity2Id(
                    cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    cassSaathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    cassSaathratriEntity2DTO.getCompositeId().getBlogId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassSaathratriEntity2DTO = cassSaathratriEntity2Service.update(cassSaathratriEntity2DTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassSaathratriEntity2DTO.getCompositeId().toString())
            )
            .body(cassSaathratriEntity2DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /cass-saathratri-entity-2-s/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : Partial updates given fields of an existing cassSaathratriEntity2, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param entityTypeId the Entity Type Id of the cassSaathratriEntity2 to partially update.
     * @param yearOfDateAdded the Year Of Date Added of the cassSaathratriEntity2 to partially update.
     * @param arrivalDate the Arrival Date of the cassSaathratriEntity2 to partially update.
     * @param blogId the Blog Id of the cassSaathratriEntity2 to partially update.
     * @param cassSaathratriEntity2DTO the cassSaathratriEntity2DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassSaathratriEntity2DTO,
     * or with status {@code 400 (Bad Request)} if the cassSaathratriEntity2DTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassSaathratriEntity2DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassSaathratriEntity2DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(
        value = "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<CassSaathratriEntity2DTO> partialUpdateCassSaathratriEntity2(
        // Composite Primary Key Code
        @PathVariable(value = "entityTypeId", required = true) final UUID entityTypeId,
        @PathVariable(value = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "blogId", required = true) final UUID blogId,
        @RequestBody CassSaathratriEntity2DTO cassSaathratriEntity2DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update CassSaathratriEntity2 with the parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}, cassSaathratriEntity2DTO: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId,
            cassSaathratriEntity2DTO
        );
        // Composite Primary Key Code
        if (
            cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId() == null ||
            cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded() == null ||
            cassSaathratriEntity2DTO.getCompositeId().getArrivalDate() == null ||
            cassSaathratriEntity2DTO.getCompositeId().getBlogId() == null
        ) {
            throw new BadRequestAlertException("Invalid entityTypeId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(entityTypeId, cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId()) ||
            !Objects.equals(yearOfDateAdded, cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded()) ||
            !Objects.equals(arrivalDate, cassSaathratriEntity2DTO.getCompositeId().getArrivalDate()) ||
            !Objects.equals(blogId, cassSaathratriEntity2DTO.getCompositeId().getBlogId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassSaathratriEntity2Repository.existsById(
                new CassSaathratriEntity2Id(
                    cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    cassSaathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    cassSaathratriEntity2DTO.getCompositeId().getBlogId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassSaathratriEntity2DTO> result = cassSaathratriEntity2Service.partialUpdate(cassSaathratriEntity2DTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassSaathratriEntity2DTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /cass-saathratri-entity-2-s} : get all the cassSaathratriEntity2s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassSaathratriEntity2s in body.
     */
    @GetMapping("")
    public List<CassSaathratriEntity2DTO> getAllCassSaathratriEntity2s() {
        LOG.debug("REST request to get all CassSaathratriEntity2s");
        return cassSaathratriEntity2Service.findAll();
    }

    /**
     * {@code GET  /cass-saathratri-entity-2-s/slice} : get cassSaathratriEntity2s with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassSaathratriEntity2s in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassSaathratriEntity2DTO>> getAllCassSaathratriEntity2sSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassSaathratriEntity2s, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassSaathratriEntity2DTO> slice = cassSaathratriEntity2Service.findAllSlice(cassandraPageRequest);
        List<CassSaathratriEntity2DTO> result = slice.getContent();

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
     * {@code GET  /:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : get the "entityTypeId" cassSaathratriEntity2.
     *
     * // Composite Primary Key Code
     * @param entityTypeId the Entity Type Id of the cassSaathratriEntity2 to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the cassSaathratriEntity2 to retrieve.
     * @param arrivalDate the Arrival Date of the cassSaathratriEntity2 to retrieve.
     * @param blogId the Blog Id of the cassSaathratriEntity2 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassSaathratriEntity2DTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<CassSaathratriEntity2DTO> getCassSaathratriEntity2(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get CassSaathratriEntity2 with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        // Composite Primary Key Code
        CassSaathratriEntity2Id compositeId = new CassSaathratriEntity2Id();
        compositeId.setEntityTypeId(entityTypeId);
        compositeId.setYearOfDateAdded(yearOfDateAdded);
        compositeId.setArrivalDate(arrivalDate);
        compositeId.setBlogId(blogId);

        Optional<CassSaathratriEntity2DTO> cassSaathratriEntity2DTO = cassSaathratriEntity2Service.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(cassSaathratriEntity2DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : delete the "compositeId" cassSaathratriEntity2.
     *
     * // Composite Primary Key Code
     * @param entityTypeId the Entity Type Id of the cassSaathratriEntity2 to delete.
     * @param yearOfDateAdded the Year Of Date Added of the cassSaathratriEntity2 to delete.
     * @param arrivalDate the Arrival Date of the cassSaathratriEntity2 to delete.
     * @param blogId the Blog Id of the cassSaathratriEntity2 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteCassSaathratriEntity2(
        @PathVariable(value = "entityTypeId", required = true) final UUID entityTypeId,
        @PathVariable(value = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete CassSaathratriEntity2 with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        // Composite Primary Key Code
        CassSaathratriEntity2Id compositeId = new CassSaathratriEntity2Id();
        compositeId.setEntityTypeId(entityTypeId);
        compositeId.setYearOfDateAdded(yearOfDateAdded);
        compositeId.setArrivalDate(arrivalDate);
        compositeId.setBlogId(blogId);
        cassSaathratriEntity2Service.delete(compositeId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, entityTypeId.toString()))
            .build();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-id/:entityTypeId}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id")
    public List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeId(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeId method for CassSaathratriEntity2s with parameteres entityTypeId: {}",
            entityTypeId
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeId(entityTypeId);
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-pageable/:entityTypeId} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-pageable")
    public ResponseEntity<List<CassSaathratriEntity2DTO>> findAllByCompositeIdEntityTypeIdPageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, pagingState: {}, size: {}",
            entityTypeId,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice = cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdPageable(
            entityTypeId,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added/:entityTypeId/:yearOfDateAdded}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added")
    public List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}",
            entityTypeId,
            yearOfDateAdded
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAdded(entityTypeId, yearOfDateAdded);
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-pageable/:entityTypeId/:yearOfDateAdded} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-pageable")
    public ResponseEntity<List<CassSaathratriEntity2DTO>> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedPageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, yearOfDateAdded: {}, pagingState: {}, size: {}",
            entityTypeId,
            yearOfDateAdded,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice =
            cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedPageable(
                entityTypeId,
                yearOfDateAdded,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date/:entityTypeId/:yearOfDateAdded/:arrivalDate}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date")
    public List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-pageable/:entityTypeId/:yearOfDateAdded/:arrivalDate} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-pageable")
    public ResponseEntity<
        List<CassSaathratriEntity2DTO>
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDatePageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, pagingState: {}, size: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice =
            cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDatePageable(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than/:entityTypeId/:yearOfDateAdded/:arrivalDate}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than")
    public List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThan(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-pageable/:entityTypeId/:yearOfDateAdded/:arrivalDate} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-pageable"
    )
    public ResponseEntity<
        List<CassSaathratriEntity2DTO>
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanPageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, pagingState: {}, size: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice =
            cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanPageable(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-equal/:entityTypeId/:yearOfDateAdded/:arrivalDate}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-equal"
    )
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqual(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-equal-pageable/:entityTypeId/:yearOfDateAdded/:arrivalDate} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-equal-pageable"
    )
    public ResponseEntity<
        List<CassSaathratriEntity2DTO>
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqualPageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, pagingState: {}, size: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice =
            cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqualPageable(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than/:entityTypeId/:yearOfDateAdded/:arrivalDate}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than")
    public List<CassSaathratriEntity2DTO> findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThan(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-pageable/:entityTypeId/:yearOfDateAdded/:arrivalDate} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-pageable"
    )
    public ResponseEntity<
        List<CassSaathratriEntity2DTO>
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanPageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, pagingState: {}, size: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice =
            cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanPageable(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-equal/:entityTypeId/:yearOfDateAdded/:arrivalDate}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-equal"
    )
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqual(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-equal-pageable/:entityTypeId/:yearOfDateAdded/:arrivalDate} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-equal-pageable"
    )
    public ResponseEntity<
        List<CassSaathratriEntity2DTO>
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqualPageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, pagingState: {}, size: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice =
            cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqualPageable(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id"
    )
    public Optional<
        CassSaathratriEntity2DTO
    > findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        return cassSaathratriEntity2Service.findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than"
    )
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThan(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-pageable/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-pageable"
    )
    public ResponseEntity<
        List<CassSaathratriEntity2DTO>
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanPageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}, pagingState: {}, size: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice =
            cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanPageable(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-equal/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-equal"
    )
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqual(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-equal-pageable/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-equal-pageable"
    )
    public ResponseEntity<
        List<CassSaathratriEntity2DTO>
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqualPageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}, pagingState: {}, size: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice =
            cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqualPageable(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than"
    )
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThan(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-pageable/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-pageable"
    )
    public ResponseEntity<
        List<CassSaathratriEntity2DTO>
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanPageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}, pagingState: {}, size: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice =
            cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanPageable(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-equal/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-equal"
    )
    public List<
        CassSaathratriEntity2DTO
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
        return cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqual(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-equal-pageable/:entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId} : get paginated entities by composite key.
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-equal-pageable"
    )
    public ResponseEntity<
        List<CassSaathratriEntity2DTO>
    > findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqualPageable(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity2s with parameters entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}, blogId: {}, pagingState: {}, size: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate,
            blogId,
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
                LOG.error("Invalid paging state for CassSaathratriEntity2s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity2DTO> slice =
            cassSaathratriEntity2Service.findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqualPageable(
                entityTypeId,
                yearOfDateAdded,
                arrivalDate,
                blogId,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity2s", e);
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
     * {@code GET /find-latest-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date/:entityTypeId/:yearOfDateAdded/:arrivalDate}
     *
     *
     * @param entityTypeId the Entity Type Id of the entity to retrieve.
     * @param yearOfDateAdded the Year Of Date Added of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-latest-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date")
    public CassSaathratriEntity2DTO findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
        @RequestParam(name = "entityTypeId", required = true) final UUID entityTypeId,
        @RequestParam(name = "yearOfDateAdded", required = true) final Long yearOfDateAdded,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate method for CassSaathratriEntity2s with parameteres entityTypeId: {}, yearOfDateAdded: {}, arrivalDate: {}",
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
        return cassSaathratriEntity2Service.findLatestByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDate(
            entityTypeId,
            yearOfDateAdded,
            arrivalDate
        );
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
