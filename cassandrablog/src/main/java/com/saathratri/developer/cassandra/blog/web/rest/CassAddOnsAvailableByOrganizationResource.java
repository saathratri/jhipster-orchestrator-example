package com.saathratri.developer.cassandra.blog.web.rest;

import com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganizationId;
import com.saathratri.developer.cassandra.blog.repository.CassAddOnsAvailableByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.CassAddOnsAvailableByOrganizationService;
import com.saathratri.developer.cassandra.blog.service.dto.CassAddOnsAvailableByOrganizationDTO;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganization}.
 */
@RestController
@RequestMapping("/api/cass-add-ons-available-by-organizations")
public class CassAddOnsAvailableByOrganizationResource {

    private static final Logger LOG = LoggerFactory.getLogger(CassAddOnsAvailableByOrganizationResource.class);

    private static final String ENTITY_NAME = "cassandrablogCassAddOnsAvailableByOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassAddOnsAvailableByOrganizationService cassAddOnsAvailableByOrganizationService;

    private final CassAddOnsAvailableByOrganizationRepository cassAddOnsAvailableByOrganizationRepository;

    public CassAddOnsAvailableByOrganizationResource(
        CassAddOnsAvailableByOrganizationService cassAddOnsAvailableByOrganizationService,
        CassAddOnsAvailableByOrganizationRepository cassAddOnsAvailableByOrganizationRepository
    ) {
        this.cassAddOnsAvailableByOrganizationService = cassAddOnsAvailableByOrganizationService;
        this.cassAddOnsAvailableByOrganizationRepository = cassAddOnsAvailableByOrganizationRepository;
    }

    /**
     * {@code POST  /cass-add-ons-available-by-organizations} : Create a new cassAddOnsAvailableByOrganization.
     *
     * @param cassAddOnsAvailableByOrganizationDTO the cassAddOnsAvailableByOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassAddOnsAvailableByOrganizationDTO, or with status {@code 400 (Bad Request)} if the cassAddOnsAvailableByOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassAddOnsAvailableByOrganizationDTO> createCassAddOnsAvailableByOrganization(
        @RequestBody CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save CassAddOnsAvailableByOrganization : {}", cassAddOnsAvailableByOrganizationDTO);

        // Composite Primary Key Code
        if (
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType() == null ||
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId() == null ||
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId() == null
        ) {
            throw new BadRequestAlertException(
                "A new cassAddOnsAvailableByOrganization cannot have an invalid ID",
                ENTITY_NAME,
                "idinvalid"
            );
        }

        cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationService.save(cassAddOnsAvailableByOrganizationDTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/cass-add-ons-available-by-organizations/" +
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId() +
                    "/" +
                    getUrlEncodedParameterValue(cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType()) +
                    "/" +
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId() +
                    "/" +
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().toString()
                )
            )
            .body(cassAddOnsAvailableByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /cass-add-ons-available-by-organizations/:organizationId/:entityType/:entityId/:addOnId} : Updates an existing cassAddOnsAvailableByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassAddOnsAvailableByOrganization to update.
     * @param entityType the Entity Type of the cassAddOnsAvailableByOrganization to update.
     * @param entityId the Entity Id of the cassAddOnsAvailableByOrganization to update.
     * @param addOnId the Add On Id of the cassAddOnsAvailableByOrganization to update.
     * @param cassAddOnsAvailableByOrganizationDTO the cassAddOnsAvailableByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassAddOnsAvailableByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the cassAddOnsAvailableByOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassAddOnsAvailableByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{organizationId}/{entityType}/{entityId}/{addOnId}")
    public ResponseEntity<CassAddOnsAvailableByOrganizationDTO> updateCassAddOnsAvailableByOrganization(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "entityId", required = true) final UUID entityId,
        @PathVariable(value = "addOnId", required = true) final UUID addOnId,
        @RequestBody CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update CassAddOnsAvailableByOrganization with parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}, cassAddOnsAvailableByOrganizationDTO: {}",
            organizationId,
            entityType,
            entityId,
            addOnId,
            cassAddOnsAvailableByOrganizationDTO
        );
        // Composite Primary Key Code
        if (
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType() == null ||
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId() == null ||
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(entityType, cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType()) ||
            !Objects.equals(entityId, cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId()) ||
            !Objects.equals(addOnId, cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassAddOnsAvailableByOrganizationRepository.existsById(
                new CassAddOnsAvailableByOrganizationId(
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationService.update(cassAddOnsAvailableByOrganizationDTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().toString()
                )
            )
            .body(cassAddOnsAvailableByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /cass-add-ons-available-by-organizations/:organizationId/:entityType/:entityId/:addOnId} : Partial updates given fields of an existing cassAddOnsAvailableByOrganization, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassAddOnsAvailableByOrganization to partially update.
     * @param entityType the Entity Type of the cassAddOnsAvailableByOrganization to partially update.
     * @param entityId the Entity Id of the cassAddOnsAvailableByOrganization to partially update.
     * @param addOnId the Add On Id of the cassAddOnsAvailableByOrganization to partially update.
     * @param cassAddOnsAvailableByOrganizationDTO the cassAddOnsAvailableByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassAddOnsAvailableByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the cassAddOnsAvailableByOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassAddOnsAvailableByOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassAddOnsAvailableByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(
        value = "/{organizationId}/{entityType}/{entityId}/{addOnId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<CassAddOnsAvailableByOrganizationDTO> partialUpdateCassAddOnsAvailableByOrganization(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "entityId", required = true) final UUID entityId,
        @PathVariable(value = "addOnId", required = true) final UUID addOnId,
        @RequestBody CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update CassAddOnsAvailableByOrganization with the parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}, cassAddOnsAvailableByOrganizationDTO: {}",
            organizationId,
            entityType,
            entityId,
            addOnId,
            cassAddOnsAvailableByOrganizationDTO
        );
        // Composite Primary Key Code
        if (
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType() == null ||
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId() == null ||
            cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(entityType, cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType()) ||
            !Objects.equals(entityId, cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId()) ||
            !Objects.equals(addOnId, cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassAddOnsAvailableByOrganizationRepository.existsById(
                new CassAddOnsAvailableByOrganizationId(
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassAddOnsAvailableByOrganizationDTO> result = cassAddOnsAvailableByOrganizationService.partialUpdate(
            cassAddOnsAvailableByOrganizationDTO
        );

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                cassAddOnsAvailableByOrganizationDTO.getCompositeId().toString()
            )
        );
    }

    /**
     * {@code GET  /cass-add-ons-available-by-organizations} : get all the cassAddOnsAvailableByOrganizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassAddOnsAvailableByOrganizations in body.
     */
    @GetMapping("")
    public List<CassAddOnsAvailableByOrganizationDTO> getAllCassAddOnsAvailableByOrganizations() {
        LOG.debug("REST request to get all CassAddOnsAvailableByOrganizations");
        return cassAddOnsAvailableByOrganizationService.findAll();
    }

    /**
     * {@code GET  /cass-add-ons-available-by-organizations/slice} : get cassAddOnsAvailableByOrganizations with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassAddOnsAvailableByOrganizations in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassAddOnsAvailableByOrganizationDTO>> getAllCassAddOnsAvailableByOrganizationsSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassAddOnsAvailableByOrganizations, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassAddOnsAvailableByOrganizationDTO> slice = cassAddOnsAvailableByOrganizationService.findAllSlice(cassandraPageRequest);
        List<CassAddOnsAvailableByOrganizationDTO> result = slice.getContent();

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
     * {@code GET  /:organizationId/:entityType/:entityId/:addOnId} : get the "organizationId" cassAddOnsAvailableByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassAddOnsAvailableByOrganization to retrieve.
     * @param entityType the Entity Type of the cassAddOnsAvailableByOrganization to retrieve.
     * @param entityId the Entity Id of the cassAddOnsAvailableByOrganization to retrieve.
     * @param addOnId the Add On Id of the cassAddOnsAvailableByOrganization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassAddOnsAvailableByOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<CassAddOnsAvailableByOrganizationDTO> getCassAddOnsAvailableByOrganization(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "entityId", required = true) final UUID entityId,
        @RequestParam(name = "addOnId", required = true) final UUID addOnId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get CassAddOnsAvailableByOrganization with parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}",
            organizationId,
            entityType,
            entityId,
            addOnId
        );
        // Composite Primary Key Code
        CassAddOnsAvailableByOrganizationId compositeId = new CassAddOnsAvailableByOrganizationId();
        compositeId.setOrganizationId(organizationId);
        compositeId.setEntityType(entityType);
        compositeId.setEntityId(entityId);
        compositeId.setAddOnId(addOnId);

        Optional<CassAddOnsAvailableByOrganizationDTO> cassAddOnsAvailableByOrganizationDTO =
            cassAddOnsAvailableByOrganizationService.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(cassAddOnsAvailableByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:organizationId/:entityType/:entityId/:addOnId} : delete the "compositeId" cassAddOnsAvailableByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassAddOnsAvailableByOrganization to delete.
     * @param entityType the Entity Type of the cassAddOnsAvailableByOrganization to delete.
     * @param entityId the Entity Id of the cassAddOnsAvailableByOrganization to delete.
     * @param addOnId the Add On Id of the cassAddOnsAvailableByOrganization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{organizationId}/{entityType}/{entityId}/{addOnId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteCassAddOnsAvailableByOrganization(
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "entityType", required = true) final String entityType,
        @PathVariable(value = "entityId", required = true) final UUID entityId,
        @PathVariable(value = "addOnId", required = true) final UUID addOnId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete CassAddOnsAvailableByOrganization with parameters organizationId: {}, entityType: {}, entityId: {}, addOnId: {}",
            organizationId,
            entityType,
            entityId,
            addOnId
        );
        // Composite Primary Key Code
        CassAddOnsAvailableByOrganizationId compositeId = new CassAddOnsAvailableByOrganizationId();
        compositeId.setOrganizationId(organizationId);
        compositeId.setEntityType(entityType);
        compositeId.setEntityId(entityId);
        compositeId.setAddOnId(addOnId);
        cassAddOnsAvailableByOrganizationService.delete(compositeId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, organizationId.toString()))
            .build();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-organization-id/:organizationId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsAvailableByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id")
    public List<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationId method for CassAddOnsAvailableByOrganizations with parameteres organizationId: {}",
            organizationId
        );
        return cassAddOnsAvailableByOrganizationService.findAllByCompositeIdOrganizationId(organizationId);
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-pageable/:organizationId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-pageable")
    public ResponseEntity<List<CassAddOnsAvailableByOrganizationDTO>> findAllByCompositeIdOrganizationIdPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsAvailableByOrganizations with parameters organizationId: {}, pagingState: {}, size: {}",
            organizationId,
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
                LOG.error("Invalid paging state for CassAddOnsAvailableByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsAvailableByOrganizationDTO> slice =
            cassAddOnsAvailableByOrganizationService.findAllByCompositeIdOrganizationIdPageable(organizationId, cassandraPageRequest);

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
                LOG.warn("Unable to resolve next paging state for CassAddOnsAvailableByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-entity-type/:organizationId/:entityType}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param entityType the Entity Type of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsAvailableByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-entity-type")
    public List<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityType method for CassAddOnsAvailableByOrganizations with parameteres organizationId: {}, entityType: {}",
            organizationId,
            entityType
        );
        return cassAddOnsAvailableByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdEntityType(
            organizationId,
            entityType
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-entity-type-pageable/:organizationId/:entityType} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param entityType the Entity Type of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-entity-type-pageable")
    public ResponseEntity<List<CassAddOnsAvailableByOrganizationDTO>> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsAvailableByOrganizations with parameters organizationId: {}, entityType: {}, pagingState: {}, size: {}",
            organizationId,
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
                LOG.error("Invalid paging state for CassAddOnsAvailableByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsAvailableByOrganizationDTO> slice =
            cassAddOnsAvailableByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable(
                organizationId,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsAvailableByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id/:organizationId/:entityType/:entityId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param entityType the Entity Type of the entity to retrieve.
     * @param entityId the Entity Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsAvailableByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id")
    public List<CassAddOnsAvailableByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "entityId", required = true) final UUID entityId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId method for CassAddOnsAvailableByOrganizations with parameteres organizationId: {}, entityType: {}, entityId: {}",
            organizationId,
            entityType,
            entityId
        );
        return cassAddOnsAvailableByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityId(
            organizationId,
            entityType,
            entityId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-pageable/:organizationId/:entityType/:entityId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param entityType the Entity Type of the entity to retrieve.
     * @param entityId the Entity Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-pageable")
    public ResponseEntity<
        List<CassAddOnsAvailableByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "entityId", required = true) final UUID entityId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsAvailableByOrganizations with parameters organizationId: {}, entityType: {}, entityId: {}, pagingState: {}, size: {}",
            organizationId,
            entityType,
            entityId,
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
                LOG.error("Invalid paging state for CassAddOnsAvailableByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsAvailableByOrganizationDTO> slice =
            cassAddOnsAvailableByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable(
                organizationId,
                entityType,
                entityId,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsAvailableByOrganizations", e);
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
     * {@code GET /find-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-and-composite-id-add-on-id/:organizationId/:entityType/:entityId/:addOnId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param entityType the Entity Type of the entity to retrieve.
     * @param entityId the Entity Id of the entity to retrieve.
     * @param addOnId the Add On Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsAvailableByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-and-composite-id-add-on-id")
    public Optional<
        CassAddOnsAvailableByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "entityType", required = true) final String entityType,
        @RequestParam(name = "entityId", required = true) final UUID entityId,
        @RequestParam(name = "addOnId", required = true) final UUID addOnId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId method for CassAddOnsAvailableByOrganizations with parameteres organizationId: {}, entityType: {}, entityId: {}, addOnId: {}",
            organizationId,
            entityType,
            entityId,
            addOnId
        );
        return cassAddOnsAvailableByOrganizationService.findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
            organizationId,
            entityType,
            entityId,
            addOnId
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
