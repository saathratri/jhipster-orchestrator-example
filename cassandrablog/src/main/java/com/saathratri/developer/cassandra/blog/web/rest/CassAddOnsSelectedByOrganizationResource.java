package com.saathratri.developer.cassandra.blog.web.rest;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganizationId;
import com.saathratri.developer.cassandra.blog.repository.CassAddOnsSelectedByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.CassAddOnsSelectedByOrganizationService;
import com.saathratri.developer.cassandra.blog.service.dto.CassAddOnsSelectedByOrganizationDTO;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganization}.
 */
@RestController
@RequestMapping("/api/cass-add-ons-selected-by-organizations")
public class CassAddOnsSelectedByOrganizationResource {

    private static final Logger LOG = LoggerFactory.getLogger(CassAddOnsSelectedByOrganizationResource.class);

    private static final String ENTITY_NAME = "cassandrablogCassAddOnsSelectedByOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassAddOnsSelectedByOrganizationService cassAddOnsSelectedByOrganizationService;

    private final CassAddOnsSelectedByOrganizationRepository cassAddOnsSelectedByOrganizationRepository;

    public CassAddOnsSelectedByOrganizationResource(
        CassAddOnsSelectedByOrganizationService cassAddOnsSelectedByOrganizationService,
        CassAddOnsSelectedByOrganizationRepository cassAddOnsSelectedByOrganizationRepository
    ) {
        this.cassAddOnsSelectedByOrganizationService = cassAddOnsSelectedByOrganizationService;
        this.cassAddOnsSelectedByOrganizationRepository = cassAddOnsSelectedByOrganizationRepository;
    }

    /**
     * {@code POST  /cass-add-ons-selected-by-organizations} : Create a new cassAddOnsSelectedByOrganization.
     *
     * @param cassAddOnsSelectedByOrganizationDTO the cassAddOnsSelectedByOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassAddOnsSelectedByOrganizationDTO, or with status {@code 400 (Bad Request)} if the cassAddOnsSelectedByOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassAddOnsSelectedByOrganizationDTO> createCassAddOnsSelectedByOrganization(
        @RequestBody CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save CassAddOnsSelectedByOrganization : {}", cassAddOnsSelectedByOrganizationDTO);

        // Composite Primary Key Code

        // Generate a TimeUUID for the Primary Key composite fields.

        cassAddOnsSelectedByOrganizationDTO.getCompositeId().setCreatedTimeId(Uuids.timeBased());

        if (
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate() == null ||
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber() == null ||
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException(
                "A new cassAddOnsSelectedByOrganization cannot have an invalid ID",
                ENTITY_NAME,
                "idinvalid"
            );
        }

        cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationService.save(cassAddOnsSelectedByOrganizationDTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/cass-add-ons-selected-by-organizations/" +
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId() +
                    "/" +
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate() +
                    "/" +
                    getUrlEncodedParameterValue(cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber()) +
                    "/" +
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().toString()
                )
            )
            .body(cassAddOnsSelectedByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /cass-add-ons-selected-by-organizations/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : Updates an existing cassAddOnsSelectedByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassAddOnsSelectedByOrganization to update.
     * @param arrivalDate the Arrival Date of the cassAddOnsSelectedByOrganization to update.
     * @param accountNumber the Account Number of the cassAddOnsSelectedByOrganization to update.
     * @param createdTimeId the Created Time Id of the cassAddOnsSelectedByOrganization to update.
     * @param cassAddOnsSelectedByOrganizationDTO the cassAddOnsSelectedByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassAddOnsSelectedByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the cassAddOnsSelectedByOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassAddOnsSelectedByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}")
    public ResponseEntity<CassAddOnsSelectedByOrganizationDTO> updateCassAddOnsSelectedByOrganization(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "accountNumber", required = true) final String accountNumber,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestBody CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update CassAddOnsSelectedByOrganization with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, cassAddOnsSelectedByOrganizationDTO: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId,
            cassAddOnsSelectedByOrganizationDTO
        );
        // Composite Primary Key Code
        if (
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate() == null ||
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber() == null ||
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(arrivalDate, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate()) ||
            !Objects.equals(accountNumber, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber()) ||
            !Objects.equals(createdTimeId, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassAddOnsSelectedByOrganizationRepository.existsById(
                new CassAddOnsSelectedByOrganizationId(
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationService.update(cassAddOnsSelectedByOrganizationDTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().toString()
                )
            )
            .body(cassAddOnsSelectedByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /cass-add-ons-selected-by-organizations/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : Partial updates given fields of an existing cassAddOnsSelectedByOrganization, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassAddOnsSelectedByOrganization to partially update.
     * @param arrivalDate the Arrival Date of the cassAddOnsSelectedByOrganization to partially update.
     * @param accountNumber the Account Number of the cassAddOnsSelectedByOrganization to partially update.
     * @param createdTimeId the Created Time Id of the cassAddOnsSelectedByOrganization to partially update.
     * @param cassAddOnsSelectedByOrganizationDTO the cassAddOnsSelectedByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassAddOnsSelectedByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the cassAddOnsSelectedByOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassAddOnsSelectedByOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassAddOnsSelectedByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(
        value = "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<CassAddOnsSelectedByOrganizationDTO> partialUpdateCassAddOnsSelectedByOrganization(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "accountNumber", required = true) final String accountNumber,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestBody CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update CassAddOnsSelectedByOrganization with the parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, cassAddOnsSelectedByOrganizationDTO: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId,
            cassAddOnsSelectedByOrganizationDTO
        );
        // Composite Primary Key Code
        if (
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId() == null ||
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate() == null ||
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber() == null ||
            cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(arrivalDate, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate()) ||
            !Objects.equals(accountNumber, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber()) ||
            !Objects.equals(createdTimeId, cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassAddOnsSelectedByOrganizationRepository.existsById(
                new CassAddOnsSelectedByOrganizationId(
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassAddOnsSelectedByOrganizationDTO> result = cassAddOnsSelectedByOrganizationService.partialUpdate(
            cassAddOnsSelectedByOrganizationDTO
        );

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                cassAddOnsSelectedByOrganizationDTO.getCompositeId().toString()
            )
        );
    }

    /**
     * {@code GET  /cass-add-ons-selected-by-organizations} : get all the cassAddOnsSelectedByOrganizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassAddOnsSelectedByOrganizations in body.
     */
    @GetMapping("")
    public List<CassAddOnsSelectedByOrganizationDTO> getAllCassAddOnsSelectedByOrganizations() {
        LOG.debug("REST request to get all CassAddOnsSelectedByOrganizations");
        return cassAddOnsSelectedByOrganizationService.findAll();
    }

    /**
     * {@code GET  /cass-add-ons-selected-by-organizations/slice} : get cassAddOnsSelectedByOrganizations with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassAddOnsSelectedByOrganizations in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassAddOnsSelectedByOrganizationDTO>> getAllCassAddOnsSelectedByOrganizationsSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassAddOnsSelectedByOrganizations, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassAddOnsSelectedByOrganizationDTO> slice = cassAddOnsSelectedByOrganizationService.findAllSlice(cassandraPageRequest);
        List<CassAddOnsSelectedByOrganizationDTO> result = slice.getContent();

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
     * {@code GET  /:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get the "organizationId" cassAddOnsSelectedByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassAddOnsSelectedByOrganization to retrieve.
     * @param arrivalDate the Arrival Date of the cassAddOnsSelectedByOrganization to retrieve.
     * @param accountNumber the Account Number of the cassAddOnsSelectedByOrganization to retrieve.
     * @param createdTimeId the Created Time Id of the cassAddOnsSelectedByOrganization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassAddOnsSelectedByOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<CassAddOnsSelectedByOrganizationDTO> getCassAddOnsSelectedByOrganization(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get CassAddOnsSelectedByOrganization with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        // Composite Primary Key Code
        CassAddOnsSelectedByOrganizationId compositeId = new CassAddOnsSelectedByOrganizationId();
        compositeId.setOrganizationId(organizationId);
        compositeId.setArrivalDate(arrivalDate);
        compositeId.setAccountNumber(accountNumber);
        compositeId.setCreatedTimeId(createdTimeId);

        Optional<CassAddOnsSelectedByOrganizationDTO> cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationService.findOne(
            compositeId
        );
        return ResponseUtil.wrapOrNotFound(cassAddOnsSelectedByOrganizationDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : delete the "compositeId" cassAddOnsSelectedByOrganization.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassAddOnsSelectedByOrganization to delete.
     * @param arrivalDate the Arrival Date of the cassAddOnsSelectedByOrganization to delete.
     * @param accountNumber the Account Number of the cassAddOnsSelectedByOrganization to delete.
     * @param createdTimeId the Created Time Id of the cassAddOnsSelectedByOrganization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteCassAddOnsSelectedByOrganization(
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "arrivalDate", required = true) final Long arrivalDate,
        @PathVariable(value = "accountNumber", required = true) final String accountNumber,
        @PathVariable(value = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete CassAddOnsSelectedByOrganization with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        // Composite Primary Key Code
        CassAddOnsSelectedByOrganizationId compositeId = new CassAddOnsSelectedByOrganizationId();
        compositeId.setOrganizationId(organizationId);
        compositeId.setArrivalDate(arrivalDate);
        compositeId.setAccountNumber(accountNumber);
        compositeId.setCreatedTimeId(createdTimeId);
        cassAddOnsSelectedByOrganizationService.delete(compositeId);
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id")
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationId method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}",
            organizationId
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationId(organizationId);
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
    public ResponseEntity<List<CassAddOnsSelectedByOrganizationDTO>> findAllByCompositeIdOrganizationIdPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, pagingState: {}, size: {}",
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdPageable(organizationId, cassandraPageRequest);

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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date")
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDate(
            organizationId,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-pageable/:organizationId/:arrivalDate} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-pageable")
    public ResponseEntity<List<CassAddOnsSelectedByOrganizationDTO>> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, pagingState: {}, size: {}",
            organizationId,
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable(
                organizationId,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than")
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThan(
            organizationId,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-pageable/:organizationId/:arrivalDate} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-pageable")
    public ResponseEntity<
        List<CassAddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, pagingState: {}, size: {}",
            organizationId,
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable(
                organizationId,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal")
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqual(
            organizationId,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal-pageable/:organizationId/:arrivalDate} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal-pageable")
    public ResponseEntity<
        List<CassAddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, pagingState: {}, size: {}",
            organizationId,
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable(
                organizationId,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than")
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThan(
            organizationId,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-pageable/:organizationId/:arrivalDate} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-pageable")
    public ResponseEntity<
        List<CassAddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, pagingState: {}, size: {}",
            organizationId,
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable(
                organizationId,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal/:organizationId/:arrivalDate}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal")
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}",
            organizationId,
            arrivalDate
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqual(
            organizationId,
            arrivalDate
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal-pageable/:organizationId/:arrivalDate} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal-pageable")
    public ResponseEntity<
        List<CassAddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, pagingState: {}, size: {}",
            organizationId,
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable(
                organizationId,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number/:organizationId/:arrivalDate/:accountNumber}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number")
    public List<CassAddOnsSelectedByOrganizationDTO> findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}",
            organizationId,
            arrivalDate,
            accountNumber
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
            organizationId,
            arrivalDate,
            accountNumber
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-pageable/:organizationId/:arrivalDate/:accountNumber} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-pageable")
    public ResponseEntity<
        List<CassAddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
            accountNumber,
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
                organizationId,
                arrivalDate,
                accountNumber,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id/:organizationId/:arrivalDate/:accountNumber/:createdTimeId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id"
    )
    public Optional<
        CassAddOnsSelectedByOrganizationDTO
    > findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return cassAddOnsSelectedByOrganizationService.findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than/:organizationId/:arrivalDate/:accountNumber/:createdTimeId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than"
    )
    public List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThan(
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-pageable/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-pageable"
    )
    public ResponseEntity<
        List<CassAddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
            accountNumber,
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
                organizationId,
                arrivalDate,
                accountNumber,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal/:organizationId/:arrivalDate/:accountNumber/:createdTimeId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal"
    )
    public List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqual(
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal-pageable/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal-pageable"
    )
    public ResponseEntity<
        List<CassAddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
            accountNumber,
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
                organizationId,
                arrivalDate,
                accountNumber,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than/:organizationId/:arrivalDate/:accountNumber/:createdTimeId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than"
    )
    public List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThan(
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-pageable/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-pageable"
    )
    public ResponseEntity<
        List<CassAddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
            accountNumber,
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
                organizationId,
                arrivalDate,
                accountNumber,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal/:organizationId/:arrivalDate/:accountNumber/:createdTimeId}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal"
    )
    public List<
        CassAddOnsSelectedByOrganizationDTO
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}",
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
        return cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqual(
            organizationId,
            arrivalDate,
            accountNumber,
            createdTimeId
        );
    }

    /**
     * {@code GET /find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal-pageable/:organizationId/:arrivalDate/:accountNumber/:createdTimeId} : get paginated entities by composite key.
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     * @param createdTimeId the Created Time Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping(
        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal-pageable"
    )
    public ResponseEntity<
        List<CassAddOnsSelectedByOrganizationDTO>
    > findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber,
        @RequestParam(name = "createdTimeId", required = true) final UUID createdTimeId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassAddOnsSelectedByOrganizations with parameters organizationId: {}, arrivalDate: {}, accountNumber: {}, createdTimeId: {}, pagingState: {}, size: {}",
            organizationId,
            arrivalDate,
            accountNumber,
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
                LOG.error("Invalid paging state for CassAddOnsSelectedByOrganizations", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassAddOnsSelectedByOrganizationDTO> slice =
            cassAddOnsSelectedByOrganizationService.findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
                organizationId,
                arrivalDate,
                accountNumber,
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
                LOG.warn("Unable to resolve next paging state for CassAddOnsSelectedByOrganizations", e);
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
     * {@code GET /find-latest-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number/:organizationId/:arrivalDate/:accountNumber}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param arrivalDate the Arrival Date of the entity to retrieve.
     * @param accountNumber the Account Number of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassAddOnsSelectedByOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-latest-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number")
    public CassAddOnsSelectedByOrganizationDTO findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "arrivalDate", required = true) final Long arrivalDate,
        @RequestParam(name = "accountNumber", required = true) final String accountNumber
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber method for CassAddOnsSelectedByOrganizations with parameteres organizationId: {}, arrivalDate: {}, accountNumber: {}",
            organizationId,
            arrivalDate,
            accountNumber
        );
        return cassAddOnsSelectedByOrganizationService.findLatestByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumber(
            organizationId,
            arrivalDate,
            accountNumber
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
