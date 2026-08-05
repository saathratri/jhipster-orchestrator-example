package com.saathratri.developer.cassandra.store.web.rest;

import com.saathratri.developer.cassandra.store.repository.CassProductRepository;
import com.saathratri.developer.cassandra.store.service.CassProductService;
import com.saathratri.developer.cassandra.store.service.dto.CassProductDTO;
import com.saathratri.developer.cassandra.store.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.store.domain.CassProduct}.
 */
@RestController
@RequestMapping("/api/cass-products")
public class CassProductResource {

    private static final Logger LOG = LoggerFactory.getLogger(CassProductResource.class);

    private static final String ENTITY_NAME = "cassandrastoreCassProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassProductService cassProductService;

    private final CassProductRepository cassProductRepository;

    public CassProductResource(CassProductService cassProductService, CassProductRepository cassProductRepository) {
        this.cassProductService = cassProductService;
        this.cassProductRepository = cassProductRepository;
    }

    /**
     * {@code POST  /cass-products} : Create a new cassProduct.
     *
     * @param cassProductDTO the cassProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassProductDTO, or with status {@code 400 (Bad Request)} if the cassProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassProductDTO> createCassProduct(@Valid @RequestBody CassProductDTO cassProductDTO) throws URISyntaxException {
        LOG.debug("REST request to save CassProduct : {}", cassProductDTO);

        // Single-value Primary Key Code
        if (cassProductDTO.getId() == null) {
            throw new BadRequestAlertException("A new cassProduct must have an ID", ENTITY_NAME, "idinvalid");
        }

        cassProductDTO = cassProductService.save(cassProductDTO);
        // Single-value Primary Key Code
        return ResponseEntity.created(new URI("/api/cass-products/" + cassProductDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cassProductDTO.getId().toString()))
            .body(cassProductDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PUT  /cass-products/:id} : Updates an existing cassProduct.
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassProductDTO to save.
     * @param cassProductDTO the cassProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassProductDTO,
     * or with status {@code 400 (Bad Request)} if the cassProductDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PutMapping("/{id}")
    public ResponseEntity<CassProductDTO> updateCassProduct(
        // Single-value Primary Key Code
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody CassProductDTO cassProductDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to update CassProduct : {}, {}", id, cassProductDTO);
        // Single-value Primary Key Code
        if (cassProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(id, cassProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassProductDTO = cassProductService.update(cassProductDTO);
        // Single-value Primary Key Code
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassProductDTO.getId().toString()))
            .body(cassProductDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code PATCH  /cass-products/:id} : Partial updates given fields of an existing cassProduct, field will ignore if it is null
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassProductDTO to save.
     * @param cassProductDTO the cassProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassProductDTO,
     * or with status {@code 400 (Bad Request)} if the cassProductDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassProductDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Single-value Primary Key Code
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CassProductDTO> partialUpdateCassProduct(
        // Single-value Primary Key Code
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody CassProductDTO cassProductDTO
    ) throws URISyntaxException {
        // Single-value Primary Key Code
        LOG.debug("REST request to partial update CassProduct partially : {}, {}", id, cassProductDTO);
        // Single-value Primary Key Code
        if (cassProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // Single-value Primary Key Code
        if (!Objects.equals(id, cassProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Single-value Primary Key Code
        if (!cassProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassProductDTO> result = cassProductService.partialUpdate(cassProductDTO);

        // Single-value Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassProductDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cass-products} : get all the cassProducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassProducts in body.
     */
    @GetMapping("")
    public List<CassProductDTO> getAllCassProducts() {
        LOG.debug("REST request to get all CassProducts");
        return cassProductService.findAll();
    }

    /**
     * {@code GET  /cass-products/slice} : get cassProducts with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassProducts in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassProductDTO>> getAllCassProductsSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassProducts, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassProductDTO> slice = cassProductService.findAllSlice(cassandraPageRequest);
        List<CassProductDTO> result = slice.getContent();

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
     * {@code GET  /:id} : get the "id" cassProduct.
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassProductDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassProductDTO, or with status {@code 404 (Not Found)}.
     */
    // Single-value Primary Key Code
    @GetMapping("/{id}")
    // Single-value Primary Key Code
    public ResponseEntity<CassProductDTO> getCassProduct(@PathVariable("id") UUID id) {
        // Single-value Primary Key Code
        LOG.debug("REST request to get CassProduct : {}", id);

        Optional<CassProductDTO> cassProductDTO = cassProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cassProductDTO);
    }

    /**
     * // Single-value Primary Key Code
     * {@code DELETE  /:id} : delete the "id" cassProduct.
     *
     * // Single-value Primary Key Code
     * @param id the id of the cassProductDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Single-value Primary Key Code
    @DeleteMapping("/{id}")
    // Single-value Primary Key Code
    public ResponseEntity<Void> deleteCassProduct(@PathVariable("id") UUID id) {
        // Single-value Primary Key Code
        LOG.debug("REST request to delete CassProduct : {}", id);
        cassProductService.delete(id);
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
