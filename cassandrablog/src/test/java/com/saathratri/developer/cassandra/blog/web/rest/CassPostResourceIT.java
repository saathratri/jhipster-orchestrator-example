package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassPostAsserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassPost;
import com.saathratri.developer.cassandra.blog.domain.CassPostId;
import com.saathratri.developer.cassandra.blog.repository.CassPostRepository;
import com.saathratri.developer.cassandra.blog.service.dto.CassPostDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassPostMapper;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CassPostResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassPostResourceIT {

    private static final Long DEFAULT_CREATED_DATE = 1L;
    private static final Long UPDATED_CREATED_DATE = 2L;

    private static final Long DEFAULT_ADDED_DATE_TIME = 1L;
    private static final Long UPDATED_ADDED_DATE_TIME = 2L;

    private static final UUID DEFAULT_POST_ID = UUID.randomUUID();
    private static final UUID UPDATED_POST_ID = UUID.randomUUID();

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Long DEFAULT_PUBLISHED_DATE_TIME = 1L;
    private static final Long UPDATED_PUBLISHED_DATE_TIME = 2L;

    private static final Long DEFAULT_SENT_DATE = 1L;
    private static final Long UPDATED_SENT_DATE = 2L;

    private static final String ENTITY_API_URL = "/api/cass-posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{createdDate}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassPostRepository cassPostRepository;

    @Autowired
    private CassPostMapper cassPostMapper;

    @Autowired
    private MockMvc restCassPostMockMvc;

    private CassPost cassPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassPost createEntity() {
        CassPost cassPost = new CassPost()
            .compositeId(new CassPostId().createdDate(DEFAULT_CREATED_DATE).addedDateTime(DEFAULT_ADDED_DATE_TIME).postId(DEFAULT_POST_ID))
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .publishedDateTime(DEFAULT_PUBLISHED_DATE_TIME)
            .sentDate(DEFAULT_SENT_DATE);
        cassPost.setCompositeId(new CassPostId(DEFAULT_CREATED_DATE, DEFAULT_ADDED_DATE_TIME, DEFAULT_POST_ID));
        return cassPost;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassPost createUpdatedEntity() {
        CassPost cassPost = new CassPost()
            .compositeId(new CassPostId().createdDate(UPDATED_CREATED_DATE).addedDateTime(UPDATED_ADDED_DATE_TIME).postId(UPDATED_POST_ID))
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .publishedDateTime(UPDATED_PUBLISHED_DATE_TIME)
            .sentDate(UPDATED_SENT_DATE);
        cassPost.setCompositeId(new CassPostId(UPDATED_CREATED_DATE, UPDATED_ADDED_DATE_TIME, UPDATED_POST_ID));
        return cassPost;
    }

    @BeforeEach
    public void initTest() {
        cassPostRepository.deleteAll();
        cassPost = createEntity();
    }

    @Test
    void createCassPost() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassPost
        CassPostDTO cassPostDTO = cassPostMapper.toDto(cassPost);
        var returnedCassPostDTO = om.readValue(
            restCassPostMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassPostDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassPostDTO.class
        );

        // Validate the CassPost in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassPost = cassPostMapper.toEntity(returnedCassPostDTO);
        assertCassPostUpdatableFieldsEquals(returnedCassPost, getPersistedCassPost(returnedCassPost));
    }

    @Test
    void createCassPostWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassPostDTO cassPostDTO = cassPostMapper.toDto(cassPost);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassPostMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassPostDTO)))
            .andExpect(status().isCreated());

        // Validate the CassPost was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassPost.setTitle(null);

        // Create the CassPost, which fails.
        CassPostDTO cassPostDTO = cassPostMapper.toDto(cassPost);

        restCassPostMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassPostDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkContentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassPost.setContent(null);

        // Create the CassPost, which fails.
        CassPostDTO cassPostDTO = cassPostMapper.toDto(cassPost);

        restCassPostMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassPostDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCassPosts() throws Exception {
        // Initialize the database
        cassPost.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        cassPost.getCompositeId().setPostId(UUID.randomUUID());
        cassPostRepository.save(cassPost);

        // Get all the cassPostList
        restCassPostMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            .andExpect(jsonPath("$.[*].compositeId.createdDate").value(hasItem(cassPost.getCompositeId().getCreatedDate().intValue())))

            .andExpect(jsonPath("$.[*].compositeId.addedDateTime").value(hasItem(cassPost.getCompositeId().getAddedDateTime().intValue())))

            .andExpect(jsonPath("$.[*].compositeId.postId").value(hasItem(cassPost.getCompositeId().getPostId().toString())))

            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))

            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))

            .andExpect(jsonPath("$.[*].publishedDateTime").value(hasItem(DEFAULT_PUBLISHED_DATE_TIME.intValue())))

            .andExpect(jsonPath("$.[*].sentDate").value(hasItem(DEFAULT_SENT_DATE.intValue())));
    }

    @Test
    void getCassPost() throws Exception {
        // Initialize the database
        cassPost.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        cassPost.getCompositeId().setPostId(UUID.randomUUID());
        cassPostRepository.save(cassPost);

        // Get the cassPost
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
                    .param("postId", String.valueOf(cassPost.getCompositeId().getPostId()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            .andExpect(jsonPath("$.compositeId.createdDate").value(cassPost.getCompositeId().getCreatedDate().intValue()))

            .andExpect(jsonPath("$.compositeId.addedDateTime").value(cassPost.getCompositeId().getAddedDateTime().intValue()))

            .andExpect(jsonPath("$.compositeId.postId").value(cassPost.getCompositeId().getPostId().toString()))

            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))

            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))

            .andExpect(jsonPath("$.publishedDateTime").value(DEFAULT_PUBLISHED_DATE_TIME.intValue()))

            .andExpect(jsonPath("$.sentDate").value(DEFAULT_SENT_DATE.intValue()));
    }

    @Test
    void getAllCassPostsByCompositeKeySearches() throws Exception {
        // Initialize the database
        cassPostRepository.save(cassPost);

        // Exercise every generated composite-key search endpoint (partial-partition findAllBy
        // carry @AllowFiltering, clustering/comparison/findBy are plain valid queries), plus
        // /slice. A 200 confirms the derived CQL + parameter binding executes against real
        // Cassandra; body shape is covered by the get()/getAll() tests above.
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date").param(
                    "createdDate",
                    String.valueOf(cassPost.getCompositeId().getCreatedDate())
                )
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-pageable")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-and-composite-id-added-date-time")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-and-composite-id-added-date-time-pageable")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-pageable")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-equal")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-and-composite-id-added-date-time-less-than-equal-pageable")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-pageable")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-equal")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-created-date-and-composite-id-added-date-time-greater-than-equal-pageable")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-by-composite-id-created-date-and-composite-id-added-date-time-and-composite-id-post-id")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
                    .param("postId", String.valueOf(cassPost.getCompositeId().getPostId()))
            )
            .andExpect(status().isOk());
        restCassPostMockMvc.perform(get(ENTITY_API_URL + "/slice").param("size", "20")).andExpect(status().isOk());
    }

    @Test
    void getNonExistingCassPost() throws Exception {
        // Get the cassPost
        restCassPostMockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("createdDate", String.valueOf(cassPost.getCompositeId().getCreatedDate()))
                    .param("addedDateTime", String.valueOf(cassPost.getCompositeId().getAddedDateTime()))
                    .param("postId", String.valueOf(cassPost.getCompositeId().getPostId()))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassPost() throws Exception {
        // Initialize the database
        cassPost.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        cassPost.getCompositeId().setPostId(UUID.randomUUID());
        cassPostRepository.save(cassPost);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassPost
        CassPost updatedCassPost = cassPostRepository.findById(cassPost.getCompositeId()).orElseThrow();
        updatedCassPost

            .title(UPDATED_TITLE)

            .content(UPDATED_CONTENT)

            .publishedDateTime(UPDATED_PUBLISHED_DATE_TIME)

            .sentDate(UPDATED_SENT_DATE);
        CassPostDTO cassPostDTO = cassPostMapper.toDto(updatedCassPost);

        restCassPostMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{createdDate}/{addedDateTime}/{postId}",
                    cassPostDTO.getCompositeId().getCreatedDate(),
                    cassPostDTO.getCompositeId().getAddedDateTime(),
                    cassPostDTO.getCompositeId().getPostId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassPostDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassPostToMatchAllProperties(updatedCassPost);
    }

    @Test
    void putNonExistingCassPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassPost.setCompositeId(new CassPostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));

        // Create the CassPost
        CassPostDTO cassPostDTO = cassPostMapper.toDto(cassPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassPostMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{createdDate}/{addedDateTime}/{postId}",
                    cassPostDTO.getCompositeId().getCreatedDate(),
                    cassPostDTO.getCompositeId().getAddedDateTime(),
                    cassPostDTO.getCompositeId().getPostId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassPost.setCompositeId(new CassPostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));
        // Create the CassPost
        CassPostDTO cassPostDTO = cassPostMapper.toDto(cassPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassPostMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{createdDate}/{addedDateTime}/{postId}",
                    longCount.incrementAndGet(),
                    longCount.incrementAndGet(),
                    UUID.randomUUID()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassPost.setCompositeId(new CassPostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));

        // Create the CassPost
        CassPostDTO cassPostDTO = cassPostMapper.toDto(cassPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassPostMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassPostDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassPostWithPatch() throws Exception {
        // Initialize the database
        cassPost.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        cassPost.getCompositeId().setPostId(UUID.randomUUID());
        cassPostRepository.save(cassPost);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassPost using partial update
        CassPost partialUpdatedCassPost = new CassPost();
        partialUpdatedCassPost.setCompositeId(cassPost.getCompositeId());

        partialUpdatedCassPost

            .title(UPDATED_TITLE)

            .content(UPDATED_CONTENT)

            .publishedDateTime(UPDATED_PUBLISHED_DATE_TIME)

            .sentDate(UPDATED_SENT_DATE);

        restCassPostMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{createdDate}/{addedDateTime}/{postId}",
                    partialUpdatedCassPost.getCompositeId().getCreatedDate(),
                    partialUpdatedCassPost.getCompositeId().getAddedDateTime(),
                    partialUpdatedCassPost.getCompositeId().getPostId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassPost))
            )
            .andExpect(status().isOk());

        // Validate the CassPost in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassPostUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCassPost, cassPost), getPersistedCassPost(cassPost));
    }

    @Test
    void fullUpdateCassPostWithPatch() throws Exception {
        // Initialize the database
        cassPost.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        cassPost.getCompositeId().setPostId(UUID.randomUUID());
        cassPostRepository.save(cassPost);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassPost using partial update
        CassPost partialUpdatedCassPost = new CassPost();
        partialUpdatedCassPost.setCompositeId(cassPost.getCompositeId());

        partialUpdatedCassPost

            .title(UPDATED_TITLE)

            .content(UPDATED_CONTENT)

            .publishedDateTime(UPDATED_PUBLISHED_DATE_TIME)

            .sentDate(UPDATED_SENT_DATE);

        restCassPostMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{createdDate}/{addedDateTime}/{postId}",
                    partialUpdatedCassPost.getCompositeId().getCreatedDate(),
                    partialUpdatedCassPost.getCompositeId().getAddedDateTime(),
                    partialUpdatedCassPost.getCompositeId().getPostId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassPost))
            )
            .andExpect(status().isOk());

        // Validate the CassPost in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassPostUpdatableFieldsEquals(partialUpdatedCassPost, getPersistedCassPost(partialUpdatedCassPost));
    }

    @Test
    void patchNonExistingCassPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassPost.setCompositeId(new CassPostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));

        // Create the CassPost
        CassPostDTO cassPostDTO = cassPostMapper.toDto(cassPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassPostMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{createdDate}/{addedDateTime}/{postId}",
                    cassPostDTO.getCompositeId().getCreatedDate(),
                    cassPostDTO.getCompositeId().getAddedDateTime(),
                    cassPostDTO.getCompositeId().getPostId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassPost.setCompositeId(new CassPostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));

        // Create the CassPost
        CassPostDTO cassPostDTO = cassPostMapper.toDto(cassPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassPostMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{createdDate}/{addedDateTime}/{postId}",
                    longCount.incrementAndGet(),
                    longCount.incrementAndGet(),
                    UUID.randomUUID()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassPost.setCompositeId(new CassPostId(new java.util.Random().nextLong(), new java.util.Random().nextLong(), UUID.randomUUID()));

        // Create the CassPost
        CassPostDTO cassPostDTO = cassPostMapper.toDto(cassPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassPostMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cassPostDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassPost() throws Exception {
        // Initialize the database
        cassPost.getCompositeId().setAddedDateTime(longCount.incrementAndGet());
        cassPost.getCompositeId().setPostId(UUID.randomUUID());
        cassPostRepository.save(cassPost);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassPost
        restCassPostMockMvc
            .perform(
                delete(
                    ENTITY_API_URL + "/{createdDate}/{addedDateTime}/{postId}",
                    cassPost.getCompositeId().getCreatedDate(),
                    cassPost.getCompositeId().getAddedDateTime(),
                    cassPost.getCompositeId().getPostId()
                )
                    .with(csrf())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassPostRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected CassPost getPersistedCassPost(CassPost cassPost) {
        return cassPostRepository.findById(cassPost.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedCassPostToMatchAllProperties(CassPost expectedCassPost) {
        assertCassPostAllPropertiesEquals(expectedCassPost, getPersistedCassPost(expectedCassPost));
    }

    protected void assertPersistedCassPostToMatchUpdatableProperties(CassPost expectedCassPost) {
        assertCassPostAllUpdatablePropertiesEquals(expectedCassPost, getPersistedCassPost(expectedCassPost));
    }
}
