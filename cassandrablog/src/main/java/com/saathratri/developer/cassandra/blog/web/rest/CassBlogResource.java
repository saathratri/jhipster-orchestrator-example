package com.saathratri.developer.cassandra.blog.web.rest;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.saathratri.developer.cassandra.blog.domain.CassBlogId;
import com.saathratri.developer.cassandra.blog.repository.CassBlogRepository;
import com.saathratri.developer.cassandra.blog.service.CassBlogService;
import com.saathratri.developer.cassandra.blog.service.dto.CassBlogDTO;
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
 * REST controller for managing {@link com.saathratri.developer.cassandra.blog.domain.CassBlog}.
 */
@RestController
@RequestMapping("/api/cass-blogs")
public class CassBlogResource {

    private static final Logger LOG = LoggerFactory.getLogger(CassBlogResource.class);

    private static final String ENTITY_NAME = "cassandrablogCassBlog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CassBlogService cassBlogService;

    private final CassBlogRepository cassBlogRepository;

    public CassBlogResource(CassBlogService cassBlogService, CassBlogRepository cassBlogRepository) {
        this.cassBlogService = cassBlogService;
        this.cassBlogRepository = cassBlogRepository;
    }

    /**
     * {@code POST  /cass-blogs} : Create a new cassBlog.
     *
     * @param cassBlogDTO the cassBlogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cassBlogDTO, or with status {@code 400 (Bad Request)} if the cassBlog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CassBlogDTO> createCassBlog(@Valid @RequestBody CassBlogDTO cassBlogDTO) throws URISyntaxException {
        LOG.debug("REST request to save CassBlog : {}", cassBlogDTO);

        // Generate a TimeUUID for the Primary Key composite fields.

        cassBlogDTO.getCompositeId().setBlogId(Uuids.timeBased());

        // Composite Primary Key Code
        if (cassBlogDTO.getCompositeId().getCategory() == null || cassBlogDTO.getCompositeId().getBlogId() == null) {
            throw new BadRequestAlertException("A new cassBlog cannot have an invalid ID", ENTITY_NAME, "idinvalid");
        }

        cassBlogDTO = cassBlogService.save(cassBlogDTO);
        // Composite Primary Key Code
        return ResponseEntity.created(
            new URI(
                "/api/cass-blogs/" +
                    getUrlEncodedParameterValue(cassBlogDTO.getCompositeId().getCategory()) +
                    "/" +
                    cassBlogDTO.getCompositeId().getBlogId()
            )
        )
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cassBlogDTO.getCompositeId().toString()))
            .body(cassBlogDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PUT  /cass-blogs/:category/:blogId} : Updates an existing cassBlog.
     *
     * // Composite Primary Key Code
     * @param category the Category of the cassBlog to update.
     * @param blogId the Blog Id of the cassBlog to update.
     * @param cassBlogDTO the cassBlogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassBlogDTO,
     * or with status {@code 400 (Bad Request)} if the cassBlogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cassBlogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PutMapping("/{category}/{blogId}")
    public ResponseEntity<CassBlogDTO> updateCassBlog(
        // Composite Primary Key Code
        @PathVariable(value = "category", required = true) final String category,
        @PathVariable(value = "blogId", required = true) final UUID blogId,
        @Valid @RequestBody CassBlogDTO cassBlogDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to update CassBlog with parameters category: {}, blogId: {}, cassBlogDTO: {}",
            category,
            blogId,
            cassBlogDTO
        );
        // Composite Primary Key Code
        if (cassBlogDTO.getCompositeId().getCategory() == null || cassBlogDTO.getCompositeId().getBlogId() == null) {
            throw new BadRequestAlertException("Invalid category", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(category, cassBlogDTO.getCompositeId().getCategory()) ||
            !Objects.equals(blogId, cassBlogDTO.getCompositeId().getBlogId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassBlogRepository.existsById(
                new CassBlogId(cassBlogDTO.getCompositeId().getCategory(), cassBlogDTO.getCompositeId().getBlogId())
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cassBlogDTO = cassBlogService.update(cassBlogDTO);
        // Composite Primary Key Code
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassBlogDTO.getCompositeId().toString()))
            .body(cassBlogDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code PATCH  /cass-blogs/:category/:blogId} : Partial updates given fields of an existing cassBlog, field will ignore if it is null
     *
     * // Composite Primary Key Code
     * @param category the Category of the cassBlog to partially update.
     * @param blogId the Blog Id of the cassBlog to partially update.
     * @param cassBlogDTO the cassBlogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cassBlogDTO,
     * or with status {@code 400 (Bad Request)} if the cassBlogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cassBlogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cassBlogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // Composite Primary Key Code
    @PatchMapping(value = "/{category}/{blogId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CassBlogDTO> partialUpdateCassBlog(
        // Composite Primary Key Code
        @PathVariable(value = "category", required = true) final String category,
        @PathVariable(value = "blogId", required = true) final UUID blogId,
        @NotNull @RequestBody CassBlogDTO cassBlogDTO
    ) throws URISyntaxException {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to partially update CassBlog with the parameters category: {}, blogId: {}, cassBlogDTO: {}",
            category,
            blogId,
            cassBlogDTO
        );
        // Composite Primary Key Code
        if (cassBlogDTO.getCompositeId().getCategory() == null || cassBlogDTO.getCompositeId().getBlogId() == null) {
            throw new BadRequestAlertException("Invalid category", ENTITY_NAME, "idnull");
        }
        // Composite Primary Key Code
        if (
            !Objects.equals(category, cassBlogDTO.getCompositeId().getCategory()) ||
            !Objects.equals(blogId, cassBlogDTO.getCompositeId().getBlogId())
        ) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        // Composite Primary Key Code
        if (
            !cassBlogRepository.existsById(
                new CassBlogId(cassBlogDTO.getCompositeId().getCategory(), cassBlogDTO.getCompositeId().getBlogId())
            )
        ) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CassBlogDTO> result = cassBlogService.partialUpdate(cassBlogDTO);

        // Composite Primary Key Code
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cassBlogDTO.getCompositeId().toString())
        );
    }

    /**
     * {@code GET  /cass-blogs} : get all the cassBlogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassBlogs in body.
     */
    @GetMapping("")
    public List<CassBlogDTO> getAllCassBlogs() {
        LOG.debug("REST request to get all CassBlogs");
        return cassBlogService.findAll();
    }

    /**
     * {@code GET  /cass-blogs/slice} : get cassBlogs with Cassandra cursor-based pagination.
     *
     * @param pagingState the Cassandra paging state for cursor-based pagination.
     * @param size the page size.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cassBlogs in body.
     */
    @GetMapping("/slice")
    public ResponseEntity<List<CassBlogDTO>> getAllCassBlogsSlice(
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug("REST request to get a slice of CassBlogs, pagingState: {}, size: {}", pagingState, size);

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

        Slice<CassBlogDTO> slice = cassBlogService.findAllSlice(cassandraPageRequest);
        List<CassBlogDTO> result = slice.getContent();

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
     * {@code GET  /:category/:blogId} : get the "category" cassBlog.
     *
     * // Composite Primary Key Code
     * @param category the Category of the cassBlog to retrieve.
     * @param blogId the Blog Id of the cassBlog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cassBlogDTO, or with status {@code 404 (Not Found)}.
     */
    // Composite Primary Key Code
    @GetMapping("/get")
    // Composite Primary Key Code
    public ResponseEntity<CassBlogDTO> getCassBlog(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug("REST request to get CassBlog with parameters category: {}, blogId: {}", category, blogId);
        // Composite Primary Key Code
        CassBlogId compositeId = new CassBlogId();
        compositeId.setCategory(category);
        compositeId.setBlogId(blogId);

        Optional<CassBlogDTO> cassBlogDTO = cassBlogService.findOne(compositeId);
        return ResponseUtil.wrapOrNotFound(cassBlogDTO);
    }

    /**
     * // Composite Primary Key Code
     * {@code DELETE  /:category/:blogId} : delete the "compositeId" cassBlog.
     *
     * // Composite Primary Key Code
     * @param category the Category of the cassBlog to delete.
     * @param blogId the Blog Id of the cassBlog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    // Composite Primary Key Code
    @DeleteMapping("/{category}/{blogId}")
    // Composite Primary Key Code
    public ResponseEntity<Void> deleteCassBlog(
        @PathVariable(value = "category", required = true) final String category,
        @PathVariable(value = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug("REST request to delete CassBlog with parameters category: {}, blogId: {}", category, blogId);
        // Composite Primary Key Code
        CassBlogId compositeId = new CassBlogId();
        compositeId.setCategory(category);
        compositeId.setBlogId(blogId);
        cassBlogService.delete(compositeId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, category))
            .build();
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-category/:category}
     *
     *
     * @param category the Category of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassBlog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-category")
    public List<CassBlogDTO> findAllByCompositeIdCategory(@RequestParam(name = "category", required = true) final String category) {
        // Composite Primary Key Code
        LOG.debug("REST request to findAllByCompositeIdCategory method for CassBlogs with parameteres category: {}", category);
        return cassBlogService.findAllByCompositeIdCategory(category);
    }

    /**
     * {@code GET /find-all-by-composite-id-category-pageable/:category} : get paginated entities by composite key.
     *
     * @param category the Category of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-category-pageable")
    public ResponseEntity<List<CassBlogDTO>> findAllByCompositeIdCategoryPageable(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassBlogs with parameters category: {}, pagingState: {}, size: {}",
            category,
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
                LOG.error("Invalid paging state for CassBlogs", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassBlogDTO> slice = cassBlogService.findAllByCompositeIdCategoryPageable(category, cassandraPageRequest);

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
                LOG.warn("Unable to resolve next paging state for CassBlogs", e);
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
     * {@code GET /find-by-composite-id-category-and-composite-id-blog-id/:category/:blogId}
     *
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassBlog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-composite-id-category-and-composite-id-blog-id")
    public Optional<CassBlogDTO> findByCompositeIdCategoryAndCompositeIdBlogId(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findByCompositeIdCategoryAndCompositeIdBlogId method for CassBlogs with parameteres category: {}, blogId: {}",
            category,
            blogId
        );
        return cassBlogService.findByCompositeIdCategoryAndCompositeIdBlogId(category, blogId);
    }

    /**
     * // Composite Primary Key Code
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-less-than/:category/:blogId}
     *
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassBlog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-less-than")
    public List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan method for CassBlogs with parameteres category: {}, blogId: {}",
            category,
            blogId
        );
        return cassBlogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThan(category, blogId);
    }

    /**
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-less-than-pageable/:category/:blogId} : get paginated entities by composite key.
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-pageable")
    public ResponseEntity<List<CassBlogDTO>> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassBlogs with parameters category: {}, blogId: {}, pagingState: {}, size: {}",
            category,
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
                LOG.error("Invalid paging state for CassBlogs", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassBlogDTO> slice = cassBlogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable(
            category,
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
                LOG.warn("Unable to resolve next paging state for CassBlogs", e);
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
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal/:category/:blogId}
     *
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassBlog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal")
    public List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual method for CassBlogs with parameteres category: {}, blogId: {}",
            category,
            blogId
        );
        return cassBlogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqual(category, blogId);
    }

    /**
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal-pageable/:category/:blogId} : get paginated entities by composite key.
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal-pageable")
    public ResponseEntity<List<CassBlogDTO>> findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassBlogs with parameters category: {}, blogId: {}, pagingState: {}, size: {}",
            category,
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
                LOG.error("Invalid paging state for CassBlogs", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassBlogDTO> slice = cassBlogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable(
            category,
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
                LOG.warn("Unable to resolve next paging state for CassBlogs", e);
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
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-greater-than/:category/:blogId}
     *
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassBlog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than")
    public List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan method for CassBlogs with parameteres category: {}, blogId: {}",
            category,
            blogId
        );
        return cassBlogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThan(category, blogId);
    }

    /**
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-pageable/:category/:blogId} : get paginated entities by composite key.
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-pageable")
    public ResponseEntity<List<CassBlogDTO>> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassBlogs with parameters category: {}, blogId: {}, pagingState: {}, size: {}",
            category,
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
                LOG.error("Invalid paging state for CassBlogs", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassBlogDTO> slice = cassBlogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable(
            category,
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
                LOG.warn("Unable to resolve next paging state for CassBlogs", e);
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
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal/:category/:blogId}
     *
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassBlog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal")
    public List<CassBlogDTO> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId
    ) {
        // Composite Primary Key Code
        LOG.debug(
            "REST request to findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual method for CassBlogs with parameteres category: {}, blogId: {}",
            category,
            blogId
        );
        return cassBlogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqual(category, blogId);
    }

    /**
     * {@code GET /find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal-pageable/:category/:blogId} : get paginated entities by composite key.
     *
     * @param category the Category of the entity to retrieve.
     * @param blogId the Blog Id of the entity to retrieve.
     * @param pagingState the Cassandra paging state (Base64 URL-safe encoded) for cursor-based pagination.
     * @param size the page size (default 20).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entities in body.
     */
    @GetMapping("/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal-pageable")
    public ResponseEntity<List<CassBlogDTO>> findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable(
        @RequestParam(name = "category", required = true) final String category,
        @RequestParam(name = "blogId", required = true) final UUID blogId,
        @RequestParam(name = "pagingState", required = false) String pagingState,
        @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        LOG.debug(
            "REST request to get paginated CassBlogs with parameters category: {}, blogId: {}, pagingState: {}, size: {}",
            category,
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
                LOG.error("Invalid paging state for CassBlogs", e);
                cassandraPageRequest = CassandraPageRequest.first(size);
            }
        }

        Slice<CassBlogDTO> slice = cassBlogService.findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable(
            category,
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
                LOG.warn("Unable to resolve next paging state for CassBlogs", e);
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
     * {@code GET /find-latest-by-composite-id-category/:category}
     *
     *
     * @param category the Category of the entity to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the CassBlog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-latest-by-composite-id-category")
    public CassBlogDTO findLatestByCompositeIdCategory(@RequestParam(name = "category", required = true) final String category) {
        // Composite Primary Key Code
        LOG.debug("REST request to findLatestByCompositeIdCategory method for CassBlogs with parameteres category: {}", category);
        return cassBlogService.findLatestByCompositeIdCategory(category);
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
