package com.saathratri.developer.cassandra.blog.web.rest;

import com.saathratri.developer.cassandra.blog.repository.CassSetEntityByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.CassSetEntityByOrganizationService;
import com.saathratri.developer.cassandra.blog.service.dto.CassSetEntityByOrganizationDTO;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSetEntityByOrganization}.
 */
@RestController
@RequestMapping("/api/cass-set-entity-by-organizations")
public class CassSetEntityByOrganizationResource {

    private static final Logger LOG = LoggerFactory.getLogger(CassSetEntityByOrganizationResource.class);

    private static final String ENTITY_NAME = "cassandrablogCassSetEntityByOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassSetEntityByOrganizationService cassSetEntityByOrganizationService;

    private final CassSetEntityByOrganizationRepository cassSetEntityByOrganizationRepository;

    public CassSetEntityByOrganizationResource(
        CassSetEntityByOrganizationService cassSetEntityByOrganizationService,
        CassSetEntityByOrganizationRepository cassSetEntityByOrganizationRepository
    ) {
        this.cassSetEntityByOrganizationService = cassSetEntityByOrganizationService;
        this.cassSetEntityByOrganizationRepository = cassSetEntityByOrganizationRepository;
    }

    /**
     * {@code POST  /cass-set-entity-by-organizations} : Create a new cassSetEntityByOrganization.
     *
     * @param cassSetEntityByOrganizationDTO the cassSetEntityByOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassSetEntityByOrganizationDTO, or with status {@code 400 (Bad Request)} if the cassSetEntityByOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassSetEntityByOrganizationDTO> createCassSetEntityByOrganization(
        @RequestBody CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save CassSetEntityByOrganization : {}", cassSetEntityByOrganizationDTO);

        // Single-value Primary Key Code
        if (cassSetEntityByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("A new cassSetEntityByOrganization must have an ID", ENTITY_NAME, "idinvalid");
        }

        cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationService.save(cassSetEntityByOrganizationDTO);
        // Single-value Primary Key Code
        return ResponseEntity.created(
            new URI("/api/cass-set-entity-by-organizations/" + cassSetEntityByOrganizationDTO.getOrganizationId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassSetEntityByOrganizationDTO.getOrganizationId().toString()
                )
            )
            .body(cassSetEntityByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PUT  /cass-set-entity-by-organizations/:organizationId} : Updates an existing cassSetEntityByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the cassSetEntityByOrganizationDTO to save.
     * @param cassSetEntityByOrganizationDTO the cassSetEntityByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassSetEntityByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the cassSetEntityByOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassSetEntityByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PutMapping("/{organizationId}")
    public ResponseEntity<CassSetEntityByOrganizationDTO> updateCassSetEntityByOrganization(
        // Single-value Primary Key Code
        @PathVariable(value = "organizationId", required = false) final UUID organizationId,
        @RequestBody CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to update CassSetEntityByOrganization : {}, {}", organizationId, cassSetEntityByOrganizationDTO);
        // Single-value Primary Key Code
        if (cassSetEntityByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(organizationId, cassSetEntityByOrganizationDTO.getOrganizationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassSetEntityByOrganizationRepository.existsById(organizationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationService.update(cassSetEntityByOrganizationDTO);
        // Single-value Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassSetEntityByOrganizationDTO.getOrganizationId().toString()
                )
            )
            .body(cassSetEntityByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PATCH  /cass-set-entity-by-organizations/:organizationId} : Partial updates given fields of an existing cassSetEntityByOrganization, field will ignore if it is null
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the cassSetEntityByOrganizationDTO to save.
     * @param cassSetEntityByOrganizationDTO the cassSetEntityByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassSetEntityByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the cassSetEntityByOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassSetEntityByOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassSetEntityByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PatchMapping(value = "/{organizationId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CassSetEntityByOrganizationDTO> partialUpdateCassSetEntityByOrganization(
        // Single-value Primary Key Code
        @PathVariable(value = "organizationId", required = false) final UUID organizationId,
        @RequestBody CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug(
            "REST request to partial update CassSetEntityByOrganization partially : {}, {}",
            organizationId,
            cassSetEntityByOrganizationDTO
        );
        // Single-value Primary Key Code
        if (cassSetEntityByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(organizationId, cassSetEntityByOrganizationDTO.getOrganizationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassSetEntityByOrganizationRepository.existsById(organizationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassSetEntityByOrganizationDTO> result = cassSetEntityByOrganizationService.partialUpdate(cassSetEntityByOrganizationDTO);

        // Single-value Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                cassSetEntityByOrganizationDTO.getOrganizationId().toString()
            )
        );
    }

    /**
     * {@code GET  /cass-set-entity-by-organizations} : get all the cassSetEntityByOrganizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassSetEntityByOrganizations in body.
     */
    @GetMapping("")
    public List<CassSetEntityByOrganizationDTO> getAllCassSetEntityByOrganizations() {
        LOG.debug("REST request to get all CassSetEntityByOrganizations");
        return cassSetEntityByOrganizationService.findAll();
    }

    /**
     * {@code GET  /cass-set-entity-by-organizations/slice} : get cassSetEntityByOrganizations with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassSetEntityByOrganizations in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassSetEntityByOrganizationDTO>> getAllCassSetEntityByOrganizationsSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassSetEntityByOrganizations, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassSetEntityByOrganizationDTO> slice = cassSetEntityByOrganizationService.findAllSlice(cassandraPageRequest);
        List<CassSetEntityByOrganizationDTO> result = slice.getContent();

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
     * {@code GET  /:organizationId} : get the "organizationId" cassSetEntityByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the cassSetEntityByOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassSetEntityByOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    // Single-value Primary Key Code
    @GetMapping("/{organizationId}")
    // Single-value Primary Key Code
    public ResponseEntity<CassSetEntityByOrganizationDTO> getCassSetEntityByOrganization(
        @PathVariable("organizationId") UUID organizationId
    ) {
        // Single-value Primary Key Code
        LOG.debug("REST request to get CassSetEntityByOrganization : {}", organizationId);

        Optional<CassSetEntityByOrganizationDTO> cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationService.findOne(
            organizationId
        );
        return ResponseUtil.wrapOrNotFound(cassSetEntityByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code DELETE  /:organizationId} : delete the "organizationId" cassSetEntityByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the cassSetEntityByOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Single-value Primary Key Code
    @DeleteMapping("/{organizationId}")
    // Single-value Primary Key Code
    public ResponseEntity<Void> deleteCassSetEntityByOrganization(@PathVariable("organizationId") UUID organizationId) {
        // Single-value Primary Key Code
        LOG.debug("REST request to delete CassSetEntityByOrganization : {}", organizationId);
        cassSetEntityByOrganizationService.delete(organizationId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, organizationId.toString()))
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
