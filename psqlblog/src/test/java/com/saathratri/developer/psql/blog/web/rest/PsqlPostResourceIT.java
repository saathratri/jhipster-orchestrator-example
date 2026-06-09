package com.saathratri.developer.psql.blog.web.rest;

import static com.saathratri.developer.psql.blog.domain.PsqlPostAsserts.*;
import static com.saathratri.developer.psql.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.psql.blog.IntegrationTest;
import com.saathratri.developer.psql.blog.domain.PsqlPost;
import com.saathratri.developer.psql.blog.repository.PsqlPostRepository;
import com.saathratri.developer.psql.blog.service.PsqlPostService;
import com.saathratri.developer.psql.blog.service.dto.PsqlPostDTO;
import com.saathratri.developer.psql.blog.service.mapper.PsqlPostMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PsqlPostResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PsqlPostResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/psql-posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PsqlPostRepository psqlPostRepository;

    @Mock
    private PsqlPostRepository psqlPostRepositoryMock;

    @Autowired
    private PsqlPostMapper psqlPostMapper;

    @Mock
    private PsqlPostService psqlPostServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPsqlPostMockMvc;

    private PsqlPost psqlPost;

    private PsqlPost insertedPsqlPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlPost createEntity() {
        return new PsqlPost().title(DEFAULT_TITLE).content(DEFAULT_CONTENT).date(DEFAULT_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlPost createUpdatedEntity() {
        return new PsqlPost().title(UPDATED_TITLE).content(UPDATED_CONTENT).date(UPDATED_DATE);
    }

    @BeforeEach
    void initTest() {
        psqlPost = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPsqlPost != null) {
            psqlPostRepository.delete(insertedPsqlPost);
            insertedPsqlPost = null;
        }
    }

    @Test
    @Transactional
    void createPsqlPost() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PsqlPost
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(psqlPost);
        var returnedPsqlPostDTO = om.readValue(
            restPsqlPostMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlPostDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PsqlPostDTO.class
        );

        // Validate the PsqlPost in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPsqlPost = psqlPostMapper.toEntity(returnedPsqlPostDTO);
        assertPsqlPostUpdatableFieldsEquals(returnedPsqlPost, getPersistedPsqlPost(returnedPsqlPost));

        insertedPsqlPost = returnedPsqlPost;
    }

    @Test
    @Transactional
    void createPsqlPostWithExistingId() throws Exception {
        // Create the PsqlPost with an existing ID
        insertedPsqlPost = psqlPostRepository.saveAndFlush(psqlPost);
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(psqlPost);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPsqlPostMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlPostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PsqlPost in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlPost.setTitle(null);

        // Create the PsqlPost, which fails.
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(psqlPost);

        restPsqlPostMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlPostDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlPost.setDate(null);

        // Create the PsqlPost, which fails.
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(psqlPost);

        restPsqlPostMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlPostDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPsqlPosts() throws Exception {
        // Initialize the database
        insertedPsqlPost = psqlPostRepository.saveAndFlush(psqlPost);

        // Get all the psqlPostList
        restPsqlPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psqlPost.getId().toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPsqlPostsWithEagerRelationshipsIsEnabled() throws Exception {
        when(psqlPostServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPsqlPostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(psqlPostServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPsqlPostsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(psqlPostServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPsqlPostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(psqlPostRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPsqlPost() throws Exception {
        // Initialize the database
        insertedPsqlPost = psqlPostRepository.saveAndFlush(psqlPost);

        // Get the psqlPost
        restPsqlPostMockMvc
            .perform(get(ENTITY_API_URL_ID, psqlPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(psqlPost.getId().toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPsqlPost() throws Exception {
        // Get the psqlPost
        restPsqlPostMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPsqlPost() throws Exception {
        // Initialize the database
        insertedPsqlPost = psqlPostRepository.saveAndFlush(psqlPost);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlPost
        PsqlPost updatedPsqlPost = psqlPostRepository.findById(psqlPost.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPsqlPost are not directly saved in db
        em.detach(updatedPsqlPost);
        updatedPsqlPost.title(UPDATED_TITLE).content(UPDATED_CONTENT).date(UPDATED_DATE);
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(updatedPsqlPost);

        restPsqlPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlPostDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlPostDTO))
            )
            .andExpect(status().isOk());

        // Validate the PsqlPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPsqlPostToMatchAllProperties(updatedPsqlPost);
    }

    @Test
    @Transactional
    void putNonExistingPsqlPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlPost.setId(UUID.randomUUID());

        // Create the PsqlPost
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(psqlPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlPostDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPsqlPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlPost.setId(UUID.randomUUID());

        // Create the PsqlPost
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(psqlPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPsqlPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlPost.setId(UUID.randomUUID());

        // Create the PsqlPost
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(psqlPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlPostMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlPostDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePsqlPostWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlPost = psqlPostRepository.saveAndFlush(psqlPost);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlPost using partial update
        PsqlPost partialUpdatedPsqlPost = new PsqlPost();
        partialUpdatedPsqlPost.setId(psqlPost.getId());

        partialUpdatedPsqlPost.date(UPDATED_DATE);

        restPsqlPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlPost.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlPost))
            )
            .andExpect(status().isOk());

        // Validate the PsqlPost in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlPostUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPsqlPost, psqlPost), getPersistedPsqlPost(psqlPost));
    }

    @Test
    @Transactional
    void fullUpdatePsqlPostWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlPost = psqlPostRepository.saveAndFlush(psqlPost);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlPost using partial update
        PsqlPost partialUpdatedPsqlPost = new PsqlPost();
        partialUpdatedPsqlPost.setId(psqlPost.getId());

        partialUpdatedPsqlPost.title(UPDATED_TITLE).content(UPDATED_CONTENT).date(UPDATED_DATE);

        restPsqlPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlPost.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlPost))
            )
            .andExpect(status().isOk());

        // Validate the PsqlPost in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlPostUpdatableFieldsEquals(partialUpdatedPsqlPost, getPersistedPsqlPost(partialUpdatedPsqlPost));
    }

    @Test
    @Transactional
    void patchNonExistingPsqlPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlPost.setId(UUID.randomUUID());

        // Create the PsqlPost
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(psqlPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, psqlPostDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPsqlPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlPost.setId(UUID.randomUUID());

        // Create the PsqlPost
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(psqlPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPsqlPost() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlPost.setId(UUID.randomUUID());

        // Create the PsqlPost
        PsqlPostDTO psqlPostDTO = psqlPostMapper.toDto(psqlPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlPostMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(psqlPostDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlPost in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePsqlPost() throws Exception {
        // Initialize the database
        insertedPsqlPost = psqlPostRepository.saveAndFlush(psqlPost);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the psqlPost
        restPsqlPostMockMvc
            .perform(delete(ENTITY_API_URL_ID, psqlPost.getId().toString()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return psqlPostRepository.count();
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

    protected PsqlPost getPersistedPsqlPost(PsqlPost psqlPost) {
        return psqlPostRepository.findById(psqlPost.getId()).orElseThrow();
    }

    protected void assertPersistedPsqlPostToMatchAllProperties(PsqlPost expectedPsqlPost) {
        assertPsqlPostAllPropertiesEquals(expectedPsqlPost, getPersistedPsqlPost(expectedPsqlPost));
    }

    protected void assertPersistedPsqlPostToMatchUpdatableProperties(PsqlPost expectedPsqlPost) {
        assertPsqlPostAllUpdatablePropertiesEquals(expectedPsqlPost, getPersistedPsqlPost(expectedPsqlPost));
    }
}
