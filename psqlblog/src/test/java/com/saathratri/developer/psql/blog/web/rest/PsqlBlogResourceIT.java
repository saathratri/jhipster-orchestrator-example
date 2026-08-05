package com.saathratri.developer.psql.blog.web.rest;

import static com.saathratri.developer.psql.blog.domain.PsqlBlogAsserts.*;
import static com.saathratri.developer.psql.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.psql.blog.IntegrationTest;
import com.saathratri.developer.psql.blog.domain.PsqlBlog;
import com.saathratri.developer.psql.blog.repository.PsqlBlogRepository;
import com.saathratri.developer.psql.blog.service.PsqlBlogService;
import com.saathratri.developer.psql.blog.service.dto.PsqlBlogDTO;
import com.saathratri.developer.psql.blog.service.mapper.PsqlBlogMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link PsqlBlogResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PsqlBlogResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_HANDLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/psql-blogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PsqlBlogRepository psqlBlogRepository;

    @Mock
    private PsqlBlogRepository psqlBlogRepositoryMock;

    @Autowired
    private PsqlBlogMapper psqlBlogMapper;

    @Mock
    private PsqlBlogService psqlBlogServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPsqlBlogMockMvc;

    private PsqlBlog psqlBlog;

    private PsqlBlog insertedPsqlBlog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlBlog createEntity() {
        return new PsqlBlog().name(DEFAULT_NAME).handle(DEFAULT_HANDLE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlBlog createUpdatedEntity() {
        return new PsqlBlog().name(UPDATED_NAME).handle(UPDATED_HANDLE);
    }

    @BeforeEach
    void initTest() {
        psqlBlog = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPsqlBlog != null) {
            psqlBlogRepository.delete(insertedPsqlBlog);
            insertedPsqlBlog = null;
        }
    }

    @Test
    @Transactional
    void createPsqlBlog() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PsqlBlog
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(psqlBlog);
        var returnedPsqlBlogDTO = om.readValue(
            restPsqlBlogMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlBlogDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PsqlBlogDTO.class
        );

        // Validate the PsqlBlog in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPsqlBlog = psqlBlogMapper.toEntity(returnedPsqlBlogDTO);
        assertPsqlBlogUpdatableFieldsEquals(returnedPsqlBlog, getPersistedPsqlBlog(returnedPsqlBlog));

        insertedPsqlBlog = returnedPsqlBlog;
    }

    @Test
    @Transactional
    void createPsqlBlogWithExistingId() throws Exception {
        // Create the PsqlBlog with an existing ID
        insertedPsqlBlog = psqlBlogRepository.saveAndFlush(psqlBlog);
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(psqlBlog);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPsqlBlogMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlBlogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PsqlBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlBlog.setName(null);

        // Create the PsqlBlog, which fails.
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(psqlBlog);

        restPsqlBlogMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlBlogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHandleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlBlog.setHandle(null);

        // Create the PsqlBlog, which fails.
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(psqlBlog);

        restPsqlBlogMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlBlogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPsqlBlogs() throws Exception {
        // Initialize the database
        insertedPsqlBlog = psqlBlogRepository.saveAndFlush(psqlBlog);

        // Get all the psqlBlogList
        restPsqlBlogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psqlBlog.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPsqlBlogsWithEagerRelationshipsIsEnabled() throws Exception {
        when(psqlBlogServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPsqlBlogMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(psqlBlogServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPsqlBlogsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(psqlBlogServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPsqlBlogMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(psqlBlogRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPsqlBlog() throws Exception {
        // Initialize the database
        insertedPsqlBlog = psqlBlogRepository.saveAndFlush(psqlBlog);

        // Get the psqlBlog
        restPsqlBlogMockMvc
            .perform(get(ENTITY_API_URL_ID, psqlBlog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(psqlBlog.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE));
    }

    @Test
    @Transactional
    void getNonExistingPsqlBlog() throws Exception {
        // Get the psqlBlog
        restPsqlBlogMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPsqlBlog() throws Exception {
        // Initialize the database
        insertedPsqlBlog = psqlBlogRepository.saveAndFlush(psqlBlog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlBlog
        PsqlBlog updatedPsqlBlog = psqlBlogRepository.findById(psqlBlog.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPsqlBlog are not directly saved in db
        em.detach(updatedPsqlBlog);
        updatedPsqlBlog.name(UPDATED_NAME).handle(UPDATED_HANDLE);
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(updatedPsqlBlog);

        restPsqlBlogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlBlogDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlBlogDTO))
            )
            .andExpect(status().isOk());

        // Validate the PsqlBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPsqlBlogToMatchAllProperties(updatedPsqlBlog);
    }

    @Test
    @Transactional
    void putNonExistingPsqlBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlBlog.setId(UUID.randomUUID());

        // Create the PsqlBlog
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(psqlBlog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlBlogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlBlogDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlBlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPsqlBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlBlog.setId(UUID.randomUUID());

        // Create the PsqlBlog
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(psqlBlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlBlogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlBlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPsqlBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlBlog.setId(UUID.randomUUID());

        // Create the PsqlBlog
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(psqlBlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlBlogMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlBlogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePsqlBlogWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlBlog = psqlBlogRepository.saveAndFlush(psqlBlog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlBlog using partial update
        PsqlBlog partialUpdatedPsqlBlog = new PsqlBlog();
        partialUpdatedPsqlBlog.setId(psqlBlog.getId());

        partialUpdatedPsqlBlog.handle(UPDATED_HANDLE);

        restPsqlBlogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlBlog.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlBlog))
            )
            .andExpect(status().isOk());

        // Validate the PsqlBlog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlBlogUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPsqlBlog, psqlBlog), getPersistedPsqlBlog(psqlBlog));
    }

    @Test
    @Transactional
    void fullUpdatePsqlBlogWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlBlog = psqlBlogRepository.saveAndFlush(psqlBlog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlBlog using partial update
        PsqlBlog partialUpdatedPsqlBlog = new PsqlBlog();
        partialUpdatedPsqlBlog.setId(psqlBlog.getId());

        partialUpdatedPsqlBlog.name(UPDATED_NAME).handle(UPDATED_HANDLE);

        restPsqlBlogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlBlog.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlBlog))
            )
            .andExpect(status().isOk());

        // Validate the PsqlBlog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlBlogUpdatableFieldsEquals(partialUpdatedPsqlBlog, getPersistedPsqlBlog(partialUpdatedPsqlBlog));
    }

    @Test
    @Transactional
    void patchNonExistingPsqlBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlBlog.setId(UUID.randomUUID());

        // Create the PsqlBlog
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(psqlBlog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlBlogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, psqlBlogDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlBlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPsqlBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlBlog.setId(UUID.randomUUID());

        // Create the PsqlBlog
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(psqlBlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlBlogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlBlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPsqlBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlBlog.setId(UUID.randomUUID());

        // Create the PsqlBlog
        PsqlBlogDTO psqlBlogDTO = psqlBlogMapper.toDto(psqlBlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlBlogMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(psqlBlogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePsqlBlog() throws Exception {
        // Initialize the database
        insertedPsqlBlog = psqlBlogRepository.saveAndFlush(psqlBlog);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the psqlBlog
        restPsqlBlogMockMvc
            .perform(delete(ENTITY_API_URL_ID, psqlBlog.getId().toString()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return psqlBlogRepository.count();
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

    protected PsqlBlog getPersistedPsqlBlog(PsqlBlog psqlBlog) {
        return psqlBlogRepository.findById(psqlBlog.getId()).orElseThrow();
    }

    protected void assertPersistedPsqlBlogToMatchAllProperties(PsqlBlog expectedPsqlBlog) {
        assertPsqlBlogAllPropertiesEquals(expectedPsqlBlog, getPersistedPsqlBlog(expectedPsqlBlog));
    }

    protected void assertPersistedPsqlBlogToMatchUpdatableProperties(PsqlBlog expectedPsqlBlog) {
        assertPsqlBlogAllUpdatablePropertiesEquals(expectedPsqlBlog, getPersistedPsqlBlog(expectedPsqlBlog));
    }
}
