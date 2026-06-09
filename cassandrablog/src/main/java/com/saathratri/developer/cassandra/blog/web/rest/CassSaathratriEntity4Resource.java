package com.saathratri.developer.cassandra.blog.web.rest;

import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4Id;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntity4Repository;
import com.saathratri.developer.cassandra.blog.service.CassSaathratriEntity4Service;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity4DTO;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4}.
 */
@RestController
@RequestMapping("/api/cass-saathratri-entity-4-s")
public class CassSaathratriEntity4Resource {

    private static final Logger LOG = LoggerFactory.getLogger(CassSaathratriEntity4Resource.class);

    private static final String ENTITY_NAME = "cassandrablogCassSaathratriEntity4";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassSaathratriEntity4Service cassSaathratriEntity4Service;

    private final CassSaathratriEntity4Repository cassSaathratriEntity4Repository;

    public CassSaathratriEntity4Resource(
        CassSaathratriEntity4Service cassSaathratriEntity4Service,
        CassSaathratriEntity4Repository cassSaathratriEntity4Repository
    ) {
        this.cassSaathratriEntity4Service = cassSaathratriEntity4Service;
        this.cassSaathratriEntity4Repository = cassSaathratriEntity4Repository;
    }

    /**
     * {@code POST  /cass-saathratri-entity-4-s} : Create a new cassSaathratriEntity4.
     *
     * @param cassSaathratriEntity4DTO the cassSaathratriEntity4DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassSaathratriEntity4DTO, or with status {@code 400 (Bad Request)} if the cassSaathratriEntity4 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassSaathratriEntity4DTO> createCassSaathratriEntity4(
        @RequestBody CassSaathratriEntity4DTO cassSaathratriEntity4DTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save CassSaathratriEntity4 : {}", cassSaathratriEntity4DTO);

        // Composite Primary Key Code
        if (
            cassSaathratriEntity4DTO.getCompositeId().getOrganizationId() == null ||
            cassSaathratriEntity4DTO.getCompositeId().getAttributeKey() == null
        ) {
            throw new BadRequestAlertException("A new cassSaathratriEntity4 cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        cassSaathratriEntity4DTO = cassSaathratriEntity4Service.save(cassSaathratriEntity4DTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/cass-saathratri-entity-4-s/" +
                    cassSaathratriEntity4DTO.getCompositeId().getOrganizationId() +
                    "/" +
                    getUrlEncodedParameterValue(cassSaathratriEntity4DTO.getCompositeId().getAttributeKey())
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    cassSaathratriEntity4DTO.getCompositeId().toString()
                )
            )
            .body(cassSaathratriEntity4DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /cass-saathratri-entity-4-s/:organizationId/:attributeKey} : Updates an existing cassSaathratriEntity4.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassSaathratriEntity4 to update.
     * @param attributeKey the Attribute Key of the cassSaathratriEntity4 to update.
     * @param cassSaathratriEntity4DTO the cassSaathratriEntity4DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassSaathratriEntity4DTO,
     * or with status {@code 400 (Bad Request)} if the cassSaathratriEntity4DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassSaathratriEntity4DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{organizationId}/{attributeKey}")
    public ResponseEntity<CassSaathratriEntity4DTO> updateCassSaathratriEntity4(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "attributeKey", required = true) final String attributeKey,
        @RequestBody CassSaathratriEntity4DTO cassSaathratriEntity4DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update CassSaathratriEntity4 with parameters organizationId: {}, attributeKey: {}, cassSaathratriEntity4DTO: {}",
            organizationId,
            attributeKey,
            cassSaathratriEntity4DTO
        );
        // Composite Primary Key Code
        if (
            cassSaathratriEntity4DTO.getCompositeId().getOrganizationId() == null ||
            cassSaathratriEntity4DTO.getCompositeId().getAttributeKey() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, cassSaathratriEntity4DTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(attributeKey, cassSaathratriEntity4DTO.getCompositeId().getAttributeKey())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassSaathratriEntity4Repository.existsById(
                new CassSaathratriEntity4Id(
                    cassSaathratriEntity4DTO.getCompositeId().getOrganizationId(),
                    cassSaathratriEntity4DTO.getCompositeId().getAttributeKey()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassSaathratriEntity4DTO = cassSaathratriEntity4Service.update(cassSaathratriEntity4DTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassSaathratriEntity4DTO.getCompositeId().toString())
            )
            .body(cassSaathratriEntity4DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /cass-saathratri-entity-4-s/:organizationId/:attributeKey} : Partial updates given fields of an existing cassSaathratriEntity4, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassSaathratriEntity4 to partially update.
     * @param attributeKey the Attribute Key of the cassSaathratriEntity4 to partially update.
     * @param cassSaathratriEntity4DTO the cassSaathratriEntity4DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassSaathratriEntity4DTO,
     * or with status {@code 400 (Bad Request)} if the cassSaathratriEntity4DTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassSaathratriEntity4DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassSaathratriEntity4DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(value = "/{organizationId}/{attributeKey}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CassSaathratriEntity4DTO> partialUpdateCassSaathratriEntity4(
        // Composite Primary Key Code
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "attributeKey", required = true) final String attributeKey,
        @RequestBody CassSaathratriEntity4DTO cassSaathratriEntity4DTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update CassSaathratriEntity4 with the parameters organizationId: {}, attributeKey: {}, cassSaathratriEntity4DTO: {}",
            organizationId,
            attributeKey,
            cassSaathratriEntity4DTO
        );
        // Composite Primary Key Code
        if (
            cassSaathratriEntity4DTO.getCompositeId().getOrganizationId() == null ||
            cassSaathratriEntity4DTO.getCompositeId().getAttributeKey() == null
        ) {
            throw new BadRequestAlertException("Invalid organizationId", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(organizationId, cassSaathratriEntity4DTO.getCompositeId().getOrganizationId()) ||
            !Objects.equals(attributeKey, cassSaathratriEntity4DTO.getCompositeId().getAttributeKey())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassSaathratriEntity4Repository.existsById(
                new CassSaathratriEntity4Id(
                    cassSaathratriEntity4DTO.getCompositeId().getOrganizationId(),
                    cassSaathratriEntity4DTO.getCompositeId().getAttributeKey()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassSaathratriEntity4DTO> result = cassSaathratriEntity4Service.partialUpdate(cassSaathratriEntity4DTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassSaathratriEntity4DTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /cass-saathratri-entity-4-s} : get all the cassSaathratriEntity4s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassSaathratriEntity4s in body.
     */
    @GetMapping("")
    public List<CassSaathratriEntity4DTO> getAllCassSaathratriEntity4s() {
        LOG.debug("REST request to get all CassSaathratriEntity4s");
        return cassSaathratriEntity4Service.findAll();
    }

    /**
     * {@code GET  /cass-saathratri-entity-4-s/slice} : get cassSaathratriEntity4s with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassSaathratriEntity4s in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassSaathratriEntity4DTO>> getAllCassSaathratriEntity4sSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassSaathratriEntity4s, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassSaathratriEntity4DTO> slice = cassSaathratriEntity4Service.findAllSlice(cassandraPageRequest);
        List<CassSaathratriEntity4DTO> result = slice.getContent();

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
     * {@code GET  /:organizationId/:attributeKey} : get the "organizationId" cassSaathratriEntity4.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassSaathratriEntity4 to retrieve.
     * @param attributeKey the Attribute Key of the cassSaathratriEntity4 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassSaathratriEntity4DTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<CassSaathratriEntity4DTO> getCassSaathratriEntity4(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "attributeKey", required = true) final String attributeKey
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get CassSaathratriEntity4 with parameters organizationId: {}, attributeKey: {}",
            organizationId,
            attributeKey
        );
        // Composite Primary Key Code
        CassSaathratriEntity4Id compositeId = new CassSaathratriEntity4Id();
        compositeId.setOrganizationId(organizationId);
        compositeId.setAttributeKey(attributeKey);

        Optional<CassSaathratriEntity4DTO> cassSaathratriEntity4DTO = cassSaathratriEntity4Service.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(cassSaathratriEntity4DTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:organizationId/:attributeKey} : delete the "compositeId" cassSaathratriEntity4.
     *
     * // Composite Primary Key Code
     * @param organizationId the Organization Id of the cassSaathratriEntity4 to delete.
     * @param attributeKey the Attribute Key of the cassSaathratriEntity4 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{organizationId}/{attributeKey}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteCassSaathratriEntity4(
        @PathVariable(value = "organizationId", required = true) final UUID organizationId,
        @PathVariable(value = "attributeKey", required = true) final String attributeKey
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete CassSaathratriEntity4 with parameters organizationId: {}, attributeKey: {}",
            organizationId,
            attributeKey
        );
        // Composite Primary Key Code
        CassSaathratriEntity4Id compositeId = new CassSaathratriEntity4Id();
        compositeId.setOrganizationId(organizationId);
        compositeId.setAttributeKey(attributeKey);
        cassSaathratriEntity4Service.delete(compositeId);
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity4, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-organization-id")
    public List<CassSaathratriEntity4DTO> findAllByCompositeIdOrganizationId(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdOrganizationId method for CassSaathratriEntity4s with parameteres organizationId: {}",
            organizationId
        );
        return cassSaathratriEntity4Service.findAllByCompositeIdOrganizationId(organizationId);
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
    public ResponseEntity<List<CassSaathratriEntity4DTO>> findAllByCompositeIdOrganizationIdPageable(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassSaathratriEntity4s with parameters organizationId: {}, pagingState: {}, size: {}",
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
                LOG.error("Invalid paging state for CassSaathratriEntity4s", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassSaathratriEntity4DTO> slice = cassSaathratriEntity4Service.findAllByCompositeIdOrganizationIdPageable(
            organizationId,
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
                LOG.warn("Unable to resolve next paging state for CassSaathratriEntity4s", e);
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
     * {@code GET /find-by-composite-id-organization-id-and-composite-id-attribute-key/:organizationId/:attributeKey}
     *
     *
     * @param organizationId the Organization Id of the entity to retrieve.
     * @param attributeKey the Attribute Key of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassSaathratriEntity4, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-composite-id-organization-id-and-composite-id-attribute-key")
    public Optional<CassSaathratriEntity4DTO> findByCompositeIdOrganizationIdAndCompositeIdAttributeKey(
        @RequestParam(name = "organizationId", required = true) final UUID organizationId,
        @RequestParam(name = "attributeKey", required = true) final String attributeKey
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdOrganizationIdAndCompositeIdAttributeKey method for CassSaathratriEntity4s with parameteres organizationId: {}, attributeKey: {}",
            organizationId,
            attributeKey
        );
        return cassSaathratriEntity4Service.findByCompositeIdOrganizationIdAndCompositeIdAttributeKey(organizationId, attributeKey);
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
