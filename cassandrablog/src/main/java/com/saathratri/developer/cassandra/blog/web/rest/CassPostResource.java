package com.saathratri.developer.cassandra.blog.web.rest;

import com.saathratri.developer.cassandra.blog.domain.CassPostId;
import com.saathratri.developer.cassandra.blog.repository.CassPostRepository;
import com.saathratri.developer.cassandra.blog.service.CassPostService;
import com.saathratri.developer.cassandra.blog.service.dto.CassPostDTO;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassPost}.
 */
@RestController
@RequestMapping("/api/cass-posts")
public class CassPostResource {

    private static final Logger LOG = LoggerFactory.getLogger(CassPostResource.class);

    private static final String ENTITY_NAME = "cassandrablogCassPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassPostService cassPostService;

    private final CassPostRepository cassPostRepository;

    public CassPostResource(CassPostService cassPostService, CassPostRepository cassPostRepository) {
        this.cassPostService = cassPostService;
        this.cassPostRepository = cassPostRepository;
    }

    /**
     * {@code POST  /cass-posts} : Create a new cassPost.
     *
     * @param cassPostDTO the cassPostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassPostDTO, or with status {@code 400 (Bad Request)} if the cassPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassPostDTO> createCassPost(@Valid @RequestBody CassPostDTO cassPostDTO) throws URISyntaxException {
        LOG.debug("REST request to save CassPost : {}", cassPostDTO);

        // Composite Primary Key Code
        if (
            cassPostDTO.getCompositeId().getCreatedDate() == null ||
            cassPostDTO.getCompositeId().getAddedDateTime() == null ||
            cassPostDTO.getCompositeId().getPostId() == null
        ) {
            throw new BadRequestAlertException("A new cassPost cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        cassPostDTO = cassPostService.save(cassPostDTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/cass-posts/" +
                    cassPostDTO.getCompositeId().getCreatedDate() +
                    "/" +
                    cassPostDTO.getCompositeId().getAddedDateTime() +
                    "/" +
                    cassPostDTO.getCompositeId().getPostId()
            )
        )
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cassPostDTO.getCompositeId().toString()))
            .body(cassPostDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /cass-posts/:createdDate/:addedDateTime/:postId} : Updates an existing cassPost.
     *
     * // Composite Primary Key Code
     * @param createdDate the Created Date of the cassPost to update.
     * @param addedDateTime the Added Date Time of the cassPost to update.
     * @param postId the Post Id of the cassPost to update.
     * @param cassPostDTO the cassPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassPostDTO,
     * or with status {@code 400 (Bad Request)} if the cassPostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{createdDate}/{addedDateTime}/{postId}")
    public ResponseEntity<CassPostDTO> updateCassPost(
        // Composite Primary Key Code
        @PathVariable(value = "createdDate", required = true) final Long createdDate,
        @PathVariable(value = "addedDateTime", required = true) final Long addedDateTime,
        @PathVariable(value = "postId", required = true) final UUID postId,
        @Valid @RequestBody CassPostDTO cassPostDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update CassPost with parameters createdDate: {}, addedDateTime: {}, postId: {}, cassPostDTO: {}",
            createdDate,
            addedDateTime,
            postId,
            cassPostDTO
        );
        // Composite Primary Key Code
        if (
            cassPostDTO.getCompositeId().getCreatedDate() == null ||
            cassPostDTO.getCompositeId().getAddedDateTime() == null ||
            cassPostDTO.getCompositeId().getPostId() == null
        ) {
            throw new BadRequestAlertException("Invalid createdDate", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(createdDate, cassPostDTO.getCompositeId().getCreatedDate()) ||
            !Objects.equals(addedDateTime, cassPostDTO.getCompositeId().getAddedDateTime()) ||
            !Objects.equals(postId, cassPostDTO.getCompositeId().getPostId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassPostRepository.existsById(
                new CassPostId(
                    cassPostDTO.getCompositeId().getCreatedDate(),
                    cassPostDTO.getCompositeId().getAddedDateTime(),
                    cassPostDTO.getCompositeId().getPostId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassPostDTO = cassPostService.update(cassPostDTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassPostDTO.getCompositeId().toString()))
            .body(cassPostDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /cass-posts/:createdDate/:addedDateTime/:postId} : Partial updates given fields of an existing cassPost, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param createdDate the Created Date of the cassPost to partially update.
     * @param addedDateTime the Added Date Time of the cassPost to partially update.
     * @param postId the Post Id of the cassPost to partially update.
     * @param cassPostDTO the cassPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassPostDTO,
     * or with status {@code 400 (Bad Request)} if the cassPostDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassPostDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(value = "/{createdDate}/{addedDateTime}/{postId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CassPostDTO> partialUpdateCassPost(
        // Composite Primary Key Code
        @PathVariable(value = "createdDate", required = true) final Long createdDate,
        @PathVariable(value = "addedDateTime", required = true) final Long addedDateTime,
        @PathVariable(value = "postId", required = true) final UUID postId,
        @NotNull @RequestBody CassPostDTO cassPostDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update CassPost with the parameters createdDate: {}, addedDateTime: {}, postId: {}, cassPostDTO: {}",
            createdDate,
            addedDateTime,
            postId,
            cassPostDTO
        );
        // Composite Primary Key Code
        if (
            cassPostDTO.getCompositeId().getCreatedDate() == null ||
            cassPostDTO.getCompositeId().getAddedDateTime() == null ||
            cassPostDTO.getCompositeId().getPostId() == null
        ) {
            throw new BadRequestAlertException("Invalid createdDate", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(createdDate, cassPostDTO.getCompositeId().getCreatedDate()) ||
            !Objects.equals(addedDateTime, cassPostDTO.getCompositeId().getAddedDateTime()) ||
            !Objects.equals(postId, cassPostDTO.getCompositeId().getPostId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassPostRepository.existsById(
                new CassPostId(
                    cassPostDTO.getCompositeId().getCreatedDate(),
                    cassPostDTO.getCompositeId().getAddedDateTime(),
                    cassPostDTO.getCompositeId().getPostId()
                )
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassPostDTO> result = cassPostService.partialUpdate(cassPostDTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassPostDTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /cass-posts} : get all the cassPosts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassPosts in body.
     */
    @GetMapping("")
    public List<CassPostDTO> getAllCassPosts() {
        LOG.debug("REST request to get all CassPosts");
        return cassPostService.findAll();
    }

    /**
     * {@code GET  /cass-posts/slice} : get cassPosts with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassPosts in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassPostDTO>> getAllCassPostsSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassPosts, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassPostDTO> slice = cassPostService.findAllSlice(cassandraPageRequest);
        List<CassPostDTO> result = slice.getContent();

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
     * {@code GET  /:createdDate/:addedDateTime/:postId} : get the "createdDate" cassPost.
     *
     * // Composite Primary Key Code
     * @param createdDate the Created Date of the cassPost to retrieve.
     * @param addedDateTime the Added Date Time of the cassPost to retrieve.
     * @param postId the Post Id of the cassPost to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassPostDTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<CassPostDTO> getCassPost(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime,
        @RequestParam(name = "postId", required = true) final UUID postId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to get CassPost with parameters createdDate: {}, addedDateTime: {}, postId: {}",
            createdDate,
            addedDateTime,
            postId
        );
        // Composite Primary Key Code
        CassPostId compositeId = new CassPostId();
        compositeId.setCreatedDate(createdDate);
        compositeId.setAddedDateTime(addedDateTime);
        compositeId.setPostId(postId);

        Optional<CassPostDTO> cassPostDTO = cassPostService.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(cassPostDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:createdDate/:addedDateTime/:postId} : delete the "compositeId" cassPost.
     *
     * // Composite Primary Key Code
     * @param createdDate the Created Date of the cassPost to delete.
     * @param addedDateTime the Added Date Time of the cassPost to delete.
     * @param postId the Post Id of the cassPost to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{createdDate}/{addedDateTime}/{postId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteCassPost(
        @PathVariable(value = "createdDate", required = true) final Long createdDate,
        @PathVariable(value = "addedDateTime", required = true) final Long addedDateTime,
        @PathVariable(value = "postId", required = true) final UUID postId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to delete CassPost with parameters createdDate: {}, addedDateTime: {}, postId: {}",
            createdDate,
            addedDateTime,
            postId
        );
        // Composite Primary Key Code
        CassPostId compositeId = new CassPostId();
        compositeId.setCreatedDate(createdDate);
        compositeId.setAddedDateTime(addedDateTime);
        compositeId.setPostId(postId);
        cassPostService.delete(compositeId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, createdDate.toString()))
            .build();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-created-date/:createdDate}
     *
     *
     * @param createdDate the Created Date of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-created-date")
    public List<CassPostDTO> findAllByCompositeIdCreatedDate(@RequestParam(name = "createdDate", required = true) final Long createdDate) {
        // Composite Primary Key Code
        LOG.debug("REST request to findAllByCompositeIdCreatedDate method for CassPosts with parameteres createdDate: {}", createdDate);
        return cassPostService.findAllByCompositeIdCreatedDate(createdDate);
    }

    /**
     * {@code GET /find-all-by-composite-id-created-date-pageable/:createdDate} : get paginated entities by composite key.
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-created-date-pageable")
    public ResponseEntity<List<CassPostDTO>> findAllByCompositeIdCreatedDatePageable(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassPosts with parameters createdDate: {}, pagingState: {}, size: {}",
            createdDate,
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
                LOG.error("Invalid paging state for CassPosts", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassPostDTO> slice = cassPostService.findAllByCompositeIdCreatedDatePageable(createdDate, cassandraPageRequest);

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
                LOG.warn("Unable to resolve next paging state for CassPosts", e);
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
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time/:createdDate/:addedDateTime}
     *
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time")
    public List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime method for CassPosts with parameteres createdDate: {}, addedDateTime: {}",
            createdDate,
            addedDateTime
        );
        return cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTime(createdDate, addedDateTime);
    }

    /**
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-pageable/:createdDate/:addedDateTime} : get paginated entities by composite key.
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-pageable")
    public ResponseEntity<List<CassPostDTO>> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassPosts with parameters createdDate: {}, addedDateTime: {}, pagingState: {}, size: {}",
            createdDate,
            addedDateTime,
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
                LOG.error("Invalid paging state for CassPosts", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassPostDTO> slice = cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimePageable(
            createdDate,
            addedDateTime,
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
                LOG.warn("Unable to resolve next paging state for CassPosts", e);
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
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than/:createdDate/:addedDateTime}
     *
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than")
    public List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan method for CassPosts with parameteres createdDate: {}, addedDateTime: {}",
            createdDate,
            addedDateTime
        );
        return cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThan(createdDate, addedDateTime);
    }

    /**
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-pageable/:createdDate/:addedDateTime} : get paginated entities by composite key.
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-pageable")
    public ResponseEntity<List<CassPostDTO>> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassPosts with parameters createdDate: {}, addedDateTime: {}, pagingState: {}, size: {}",
            createdDate,
            addedDateTime,
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
                LOG.error("Invalid paging state for CassPosts", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassPostDTO> slice = cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanPageable(
            createdDate,
            addedDateTime,
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
                LOG.warn("Unable to resolve next paging state for CassPosts", e);
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
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-equal/:createdDate/:addedDateTime}
     *
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-equal")
    public List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual method for CassPosts with parameteres createdDate: {}, addedDateTime: {}",
            createdDate,
            addedDateTime
        );
        return cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqual(createdDate, addedDateTime);
    }

    /**
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-equal-pageable/:createdDate/:addedDateTime} : get paginated entities by composite key.
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-equal-pageable")
    public ResponseEntity<List<CassPostDTO>> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassPosts with parameters createdDate: {}, addedDateTime: {}, pagingState: {}, size: {}",
            createdDate,
            addedDateTime,
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
                LOG.error("Invalid paging state for CassPosts", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassPostDTO> slice = cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeLessThanEqualPageable(
            createdDate,
            addedDateTime,
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
                LOG.warn("Unable to resolve next paging state for CassPosts", e);
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
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than/:createdDate/:addedDateTime}
     *
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than")
    public List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan method for CassPosts with parameteres createdDate: {}, addedDateTime: {}",
            createdDate,
            addedDateTime
        );
        return cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThan(createdDate, addedDateTime);
    }

    /**
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-pageable/:createdDate/:addedDateTime} : get paginated entities by composite key.
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-pageable")
    public ResponseEntity<List<CassPostDTO>> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassPosts with parameters createdDate: {}, addedDateTime: {}, pagingState: {}, size: {}",
            createdDate,
            addedDateTime,
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
                LOG.error("Invalid paging state for CassPosts", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassPostDTO> slice = cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanPageable(
            createdDate,
            addedDateTime,
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
                LOG.warn("Unable to resolve next paging state for CassPosts", e);
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
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-equal/:createdDate/:addedDateTime}
     *
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-equal")
    public List<CassPostDTO> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual method for CassPosts with parameteres createdDate: {}, addedDateTime: {}",
            createdDate,
            addedDateTime
        );
        return cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqual(createdDate, addedDateTime);
    }

    /**
     * {@code GET /find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-equal-pageable/:createdDate/:addedDateTime} : get paginated entities by composite key.
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-equal-pageable")
    public ResponseEntity<List<CassPostDTO>> findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassPosts with parameters createdDate: {}, addedDateTime: {}, pagingState: {}, size: {}",
            createdDate,
            addedDateTime,
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
                LOG.error("Invalid paging state for CassPosts", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassPostDTO> slice = cassPostService.findAllByCompositeIdCreatedDateAndCompositeIdAddedDateTimeGreaterThanEqualPageable(
            createdDate,
            addedDateTime,
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
                LOG.warn("Unable to resolve next paging state for CassPosts", e);
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
     * {@code GET /find-by-composite-id-created-date-and-composite-id-added-date-time-and-composite-id-post-id/:createdDate/:addedDateTime/:postId}
     *
     *
     * @param createdDate the Created Date of the entity to retrieve.
     * @param addedDateTime the Added Date Time of the entity to retrieve.
     * @param postId the Post Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-composite-id-created-date-and-composite-id-added-date-time-and-composite-id-post-id")
    public Optional<CassPostDTO> findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
        @RequestParam(name = "createdDate", required = true) final Long createdDate,
        @RequestParam(name = "addedDateTime", required = true) final Long addedDateTime,
        @RequestParam(name = "postId", required = true) final UUID postId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId method for CassPosts with parameteres createdDate: {}, addedDateTime: {}, postId: {}",
            createdDate,
            addedDateTime,
            postId
        );
        return cassPostService.findByCompositeIdCreatedDateAndCompositeIdAddedDateTimeAndCompositeIdPostId(
            createdDate,
            addedDateTime,
            postId
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
