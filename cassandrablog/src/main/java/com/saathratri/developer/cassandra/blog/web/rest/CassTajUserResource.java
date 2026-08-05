package com.saathratri.developer.cassandra.blog.web.rest;

import com.saathratri.developer.cassandra.blog.repository.CassTajUserRepository;
import com.saathratri.developer.cassandra.blog.service.CassTajUserService;
import com.saathratri.developer.cassandra.blog.service.dto.CassTajUserDTO;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassTajUser}.
 */
@RestController
@RequestMapping("/api/cass-taj-users")
public class CassTajUserResource {

    private static final Logger LOG = LoggerFactory.getLogger(CassTajUserResource.class);

    private static final String ENTITY_NAME = "cassandrablogCassTajUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassTajUserService cassTajUserService;

    private final CassTajUserRepository cassTajUserRepository;

    public CassTajUserResource(CassTajUserService cassTajUserService, CassTajUserRepository cassTajUserRepository) {
        this.cassTajUserService = cassTajUserService;
        this.cassTajUserRepository = cassTajUserRepository;
    }

    /**
     * {@code POST  /cass-taj-users} : Create a new cassTajUser.
     *
     * @param cassTajUserDTO the cassTajUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassTajUserDTO, or with status {@code 400 (Bad Request)} if the cassTajUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassTajUserDTO> createCassTajUser(@Valid @RequestBody CassTajUserDTO cassTajUserDTO) throws URISyntaxException {
        LOG.debug("REST request to save CassTajUser : {}", cassTajUserDTO);

        // Single-value Primary Key Code
        if (cassTajUserDTO.getId() == null) {
            throw new BadRequestAlertException("A new cassTajUser must have an ID", ENTITY_NAME, "idinvalid");
        }

        cassTajUserDTO = cassTajUserService.save(cassTajUserDTO);
        // Single-value Primary Key Code
        return ResponseEntity.created(new URI("/api/cass-taj-users/" + cassTajUserDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cassTajUserDTO.getId().toString()))
            .body(cassTajUserDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PUT  /cass-taj-users/:id} : Updates an existing cassTajUser.
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassTajUserDTO to save.
     * @param cassTajUserDTO the cassTajUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassTajUserDTO,
     * or with status {@code 400 (Bad Request)} if the cassTajUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassTajUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PutMapping("/{id}")
    public ResponseEntity<CassTajUserDTO> updateCassTajUser(
        // Single-value Primary Key Code
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody CassTajUserDTO cassTajUserDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to update CassTajUser : {}, {}", id, cassTajUserDTO);
        // Single-value Primary Key Code
        if (cassTajUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(id, cassTajUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassTajUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassTajUserDTO = cassTajUserService.update(cassTajUserDTO);
        // Single-value Primary Key Code
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassTajUserDTO.getId().toString()))
            .body(cassTajUserDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PATCH  /cass-taj-users/:id} : Partial updates given fields of an existing cassTajUser, field will ignore if it is null
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassTajUserDTO to save.
     * @param cassTajUserDTO the cassTajUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassTajUserDTO,
     * or with status {@code 400 (Bad Request)} if the cassTajUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassTajUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassTajUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CassTajUserDTO> partialUpdateCassTajUser(
        // Single-value Primary Key Code
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody CassTajUserDTO cassTajUserDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to partial update CassTajUser partially : {}, {}", id, cassTajUserDTO);
        // Single-value Primary Key Code
        if (cassTajUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(id, cassTajUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassTajUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassTajUserDTO> result = cassTajUserService.partialUpdate(cassTajUserDTO);

        // Single-value Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassTajUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cass-taj-users} : get all the cassTajUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassTajUsers in body.
     */
    @GetMapping("")
    public List<CassTajUserDTO> getAllCassTajUsers() {
        LOG.debug("REST request to get all CassTajUsers");
        return cassTajUserService.findAll();
    }

    /**
     * {@code GET  /cass-taj-users/slice} : get cassTajUsers with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassTajUsers in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassTajUserDTO>> getAllCassTajUsersSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassTajUsers, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassTajUserDTO> slice = cassTajUserService.findAllSlice(cassandraPageRequest);
        List<CassTajUserDTO> result = slice.getContent();

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
     * {@code GET  /:id} : get the "id" cassTajUser.
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassTajUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassTajUserDTO, or with status {@code 404 (Not Found)}.
     */
    // Single-value Primary Key Code
    @GetMapping("/{id}")
    // Single-value Primary Key Code
    public ResponseEntity<CassTajUserDTO> getCassTajUser(@PathVariable("id") UUID id) {
        // Single-value Primary Key Code
        LOG.debug("REST request to get CassTajUser : {}", id);

        Optional<CassTajUserDTO> cassTajUserDTO = cassTajUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cassTajUserDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code DELETE  /:id} : delete the "id" cassTajUser.
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassTajUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Single-value Primary Key Code
    @DeleteMapping("/{id}")
    // Single-value Primary Key Code
    public ResponseEntity<Void> deleteCassTajUser(@PathVariable("id") UUID id) {
        // Single-value Primary Key Code
        LOG.debug("REST request to delete CassTajUser : {}", id);
        cassTajUserService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
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
