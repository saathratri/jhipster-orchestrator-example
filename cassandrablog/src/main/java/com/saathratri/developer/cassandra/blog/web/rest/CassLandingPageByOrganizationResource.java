package com.saathratri.developer.cassandra.blog.web.rest;

import com.saathratri.developer.cassandra.blog.repository.CassLandingPageByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.CassLandingPageByOrganizationService;
import com.saathratri.developer.cassandra.blog.service.dto.CassLandingPageByOrganizationDTO;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassLandingPageByOrganization}.
 */
@RestController
@RequestMapping("/api/cass-landing-page-by-organizations")
public class CassLandingPageByOrganizationResource {

    private static final Logger LOG = LoggerFactory.getLogger(CassLandingPageByOrganizationResource.class);

    private static final String ENTITY_NAME = "cassandrablogCassLandingPageByOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassLandingPageByOrganizationService cassLandingPageByOrganizationService;

    private final CassLandingPageByOrganizationRepository cassLandingPageByOrganizationRepository;

    public CassLandingPageByOrganizationResource(
        CassLandingPageByOrganizationService cassLandingPageByOrganizationService,
        CassLandingPageByOrganizationRepository cassLandingPageByOrganizationRepository
    ) {
        this.cassLandingPageByOrganizationService = cassLandingPageByOrganizationService;
        this.cassLandingPageByOrganizationRepository = cassLandingPageByOrganizationRepository;
    }

    /**
     * {@code POST  /cass-landing-page-by-organizations} : Create a new cassLandingPageByOrganization.
     *
     * @param cassLandingPageByOrganizationDTO the cassLandingPageByOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassLandingPageByOrganizationDTO, or with status {@code 400 (Bad Request)} if the cassLandingPageByOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassLandingPageByOrganizationDTO> createCassLandingPageByOrganization(
        @RequestBody CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save CassLandingPageByOrganization : {}", cassLandingPageByOrganizationDTO);

        // Single-value Primary Key Code
        if (cassLandingPageByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("A new cassLandingPageByOrganization must have an ID", ENTITY_NAME, "idinvalid");
        }

        cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationService.save(cassLandingPageByOrganizationDTO);
        // Single-value Primary Key Code
        return ResponseEntity.created(
            new URI("/api/cass-landing-page-by-organizations/" + cassLandingPageByOrganizationDTO.getOrganizationId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassLandingPageByOrganizationDTO.getOrganizationId().toString()
                )
            )
            .body(cassLandingPageByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PUT  /cass-landing-page-by-organizations/:organizationId} : Updates an existing cassLandingPageByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the cassLandingPageByOrganizationDTO to save.
     * @param cassLandingPageByOrganizationDTO the cassLandingPageByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassLandingPageByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the cassLandingPageByOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassLandingPageByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PutMapping("/{organizationId}")
    public ResponseEntity<CassLandingPageByOrganizationDTO> updateCassLandingPageByOrganization(
        // Single-value Primary Key Code
        @PathVariable(value = "organizationId", required = false) final UUID organizationId,
        @RequestBody CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to update CassLandingPageByOrganization : {}, {}", organizationId, cassLandingPageByOrganizationDTO);
        // Single-value Primary Key Code
        if (cassLandingPageByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(organizationId, cassLandingPageByOrganizationDTO.getOrganizationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassLandingPageByOrganizationRepository.existsById(organizationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationService.update(cassLandingPageByOrganizationDTO);
        // Single-value Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassLandingPageByOrganizationDTO.getOrganizationId().toString()
                )
            )
            .body(cassLandingPageByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PATCH  /cass-landing-page-by-organizations/:organizationId} : Partial updates given fields of an existing cassLandingPageByOrganization, field will ignore if it is null
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the cassLandingPageByOrganizationDTO to save.
     * @param cassLandingPageByOrganizationDTO the cassLandingPageByOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassLandingPageByOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the cassLandingPageByOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassLandingPageByOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassLandingPageByOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PatchMapping(value = "/{organizationId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CassLandingPageByOrganizationDTO> partialUpdateCassLandingPageByOrganization(
        // Single-value Primary Key Code
        @PathVariable(value = "organizationId", required = false) final UUID organizationId,
        @RequestBody CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug(
            "REST request to partial update CassLandingPageByOrganization partially : {}, {}",
            organizationId,
            cassLandingPageByOrganizationDTO
        );
        // Single-value Primary Key Code
        if (cassLandingPageByOrganizationDTO.getOrganizationId() == null) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(organizationId, cassLandingPageByOrganizationDTO.getOrganizationId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassLandingPageByOrganizationRepository.existsById(organizationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassLandingPageByOrganizationDTO> result = cassLandingPageByOrganizationService.partialUpdate(
            cassLandingPageByOrganizationDTO
        );

        // Single-value Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                cassLandingPageByOrganizationDTO.getOrganizationId().toString()
            )
        );
    }

    /**
     * {@code GET  /cass-landing-page-by-organizations} : get all the cassLandingPageByOrganizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassLandingPageByOrganizations in body.
     */
    @GetMapping("")
    public List<CassLandingPageByOrganizationDTO> getAllCassLandingPageByOrganizations() {
        LOG.debug("REST request to get all CassLandingPageByOrganizations");
        return cassLandingPageByOrganizationService.findAll();
    }

    /**
     * {@code GET  /cass-landing-page-by-organizations/slice} : get cassLandingPageByOrganizations with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassLandingPageByOrganizations in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassLandingPageByOrganizationDTO>> getAllCassLandingPageByOrganizationsSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassLandingPageByOrganizations, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassLandingPageByOrganizationDTO> slice = cassLandingPageByOrganizationService.findAllSlice(cassandraPageRequest);
        List<CassLandingPageByOrganizationDTO> result = slice.getContent();

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
     * {@code GET  /:organizationId} : get the "organizationId" cassLandingPageByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the cassLandingPageByOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassLandingPageByOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    // Single-value Primary Key Code
    @GetMapping("/{organizationId}")
    // Single-value Primary Key Code
    public ResponseEntity<CassLandingPageByOrganizationDTO> getCassLandingPageByOrganization(
        @PathVariable("organizationId") UUID organizationId
    ) {
        // Single-value Primary Key Code
        LOG.debug("REST request to get CassLandingPageByOrganization : {}", organizationId);

        Optional<CassLandingPageByOrganizationDTO> cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationService.findOne(
            organizationId
        );
        return ResponseUtil.wrapOrNotFound(cassLandingPageByOrganizationDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code DELETE  /:organizationId} : delete the "organizationId" cassLandingPageByOrganization.
     *
     * // Single-value Primary Key Code
     * @param organizationId the organizationId of the cassLandingPageByOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Single-value Primary Key Code
    @DeleteMapping("/{organizationId}")
    // Single-value Primary Key Code
    public ResponseEntity<Void> deleteCassLandingPageByOrganization(@PathVariable("organizationId") UUID organizationId) {
        // Single-value Primary Key Code
        LOG.debug("REST request to delete CassLandingPageByOrganization : {}", organizationId);
        cassLandingPageByOrganizationService.delete(organizationId);
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
