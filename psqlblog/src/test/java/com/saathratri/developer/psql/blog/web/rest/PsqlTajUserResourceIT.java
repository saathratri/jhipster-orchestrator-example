package com.saathratri.developer.psql.blog.web.rest;

import static com.saathratri.developer.psql.blog.domain.PsqlTajUserAsserts.*;
import static com.saathratri.developer.psql.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.psql.blog.IntegrationTest;
import com.saathratri.developer.psql.blog.domain.PsqlTajUser;
import com.saathratri.developer.psql.blog.repository.PsqlTajUserRepository;
import com.saathratri.developer.psql.blog.service.dto.PsqlTajUserDTO;
import com.saathratri.developer.psql.blog.service.mapper.PsqlTajUserMapper;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PsqlTajUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PsqlTajUserResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/psql-taj-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PsqlTajUserRepository psqlTajUserRepository;

    @Autowired
    private PsqlTajUserMapper psqlTajUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPsqlTajUserMockMvc;

    private PsqlTajUser psqlTajUser;

    private PsqlTajUser insertedPsqlTajUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlTajUser createEntity() {
        return new PsqlTajUser().login(DEFAULT_LOGIN);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlTajUser createUpdatedEntity() {
        return new PsqlTajUser().login(UPDATED_LOGIN);
    }

    @BeforeEach
    void initTest() {
        psqlTajUser = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPsqlTajUser != null) {
            psqlTajUserRepository.delete(insertedPsqlTajUser);
            insertedPsqlTajUser = null;
        }
    }

    @Test
    @Transactional
    void createPsqlTajUser() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PsqlTajUser
        PsqlTajUserDTO psqlTajUserDTO = psqlTajUserMapper.toDto(psqlTajUser);
        var returnedPsqlTajUserDTO = om.readValue(
            restPsqlTajUserMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlTajUserDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PsqlTajUserDTO.class
        );

        // Validate the PsqlTajUser in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPsqlTajUser = psqlTajUserMapper.toEntity(returnedPsqlTajUserDTO);
        assertPsqlTajUserUpdatableFieldsEquals(returnedPsqlTajUser, getPersistedPsqlTajUser(returnedPsqlTajUser));

        insertedPsqlTajUser = returnedPsqlTajUser;
    }

    @Test
    @Transactional
    void createPsqlTajUserWithExistingId() throws Exception {
        // Create the PsqlTajUser with an existing ID
        insertedPsqlTajUser = psqlTajUserRepository.saveAndFlush(psqlTajUser);
        PsqlTajUserDTO psqlTajUserDTO = psqlTajUserMapper.toDto(psqlTajUser);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPsqlTajUserMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLoginIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlTajUser.setLogin(null);

        // Create the PsqlTajUser, which fails.
        PsqlTajUserDTO psqlTajUserDTO = psqlTajUserMapper.toDto(psqlTajUser);

        restPsqlTajUserMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPsqlTajUsers() throws Exception {
        // Initialize the database
        insertedPsqlTajUser = psqlTajUserRepository.saveAndFlush(psqlTajUser);

        // Get all the psqlTajUserList
        restPsqlTajUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psqlTajUser.getId().toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)));
    }

    @Test
    @Transactional
    void getPsqlTajUser() throws Exception {
        // Initialize the database
        insertedPsqlTajUser = psqlTajUserRepository.saveAndFlush(psqlTajUser);

        // Get the psqlTajUser
        restPsqlTajUserMockMvc
            .perform(get(ENTITY_API_URL_ID, psqlTajUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(psqlTajUser.getId().toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN));
    }

    @Test
    @Transactional
    void getNonExistingPsqlTajUser() throws Exception {
        // Get the psqlTajUser
        restPsqlTajUserMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPsqlTajUser() throws Exception {
        // Initialize the database
        insertedPsqlTajUser = psqlTajUserRepository.saveAndFlush(psqlTajUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlTajUser
        PsqlTajUser updatedPsqlTajUser = psqlTajUserRepository.findById(psqlTajUser.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPsqlTajUser are not directly saved in db
        em.detach(updatedPsqlTajUser);
        updatedPsqlTajUser.login(UPDATED_LOGIN);
        PsqlTajUserDTO psqlTajUserDTO = psqlTajUserMapper.toDto(updatedPsqlTajUser);

        restPsqlTajUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlTajUserDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlTajUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the PsqlTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPsqlTajUserToMatchAllProperties(updatedPsqlTajUser);
    }

    @Test
    @Transactional
    void putNonExistingPsqlTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTajUser.setId(UUID.randomUUID());

        // Create the PsqlTajUser
        PsqlTajUserDTO psqlTajUserDTO = psqlTajUserMapper.toDto(psqlTajUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlTajUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlTajUserDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPsqlTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTajUser.setId(UUID.randomUUID());

        // Create the PsqlTajUser
        PsqlTajUserDTO psqlTajUserDTO = psqlTajUserMapper.toDto(psqlTajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlTajUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPsqlTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTajUser.setId(UUID.randomUUID());

        // Create the PsqlTajUser
        PsqlTajUserDTO psqlTajUserDTO = psqlTajUserMapper.toDto(psqlTajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlTajUserMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlTajUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePsqlTajUserWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlTajUser = psqlTajUserRepository.saveAndFlush(psqlTajUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlTajUser using partial update
        PsqlTajUser partialUpdatedPsqlTajUser = new PsqlTajUser();
        partialUpdatedPsqlTajUser.setId(psqlTajUser.getId());

        partialUpdatedPsqlTajUser.login(UPDATED_LOGIN);

        restPsqlTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlTajUser.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlTajUser))
            )
            .andExpect(status().isOk());

        // Validate the PsqlTajUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlTajUserUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPsqlTajUser, psqlTajUser),
            getPersistedPsqlTajUser(psqlTajUser)
        );
    }

    @Test
    @Transactional
    void fullUpdatePsqlTajUserWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlTajUser = psqlTajUserRepository.saveAndFlush(psqlTajUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlTajUser using partial update
        PsqlTajUser partialUpdatedPsqlTajUser = new PsqlTajUser();
        partialUpdatedPsqlTajUser.setId(psqlTajUser.getId());

        partialUpdatedPsqlTajUser.login(UPDATED_LOGIN);

        restPsqlTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlTajUser.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlTajUser))
            )
            .andExpect(status().isOk());

        // Validate the PsqlTajUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlTajUserUpdatableFieldsEquals(partialUpdatedPsqlTajUser, getPersistedPsqlTajUser(partialUpdatedPsqlTajUser));
    }

    @Test
    @Transactional
    void patchNonExistingPsqlTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTajUser.setId(UUID.randomUUID());

        // Create the PsqlTajUser
        PsqlTajUserDTO psqlTajUserDTO = psqlTajUserMapper.toDto(psqlTajUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, psqlTajUserDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPsqlTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTajUser.setId(UUID.randomUUID());

        // Create the PsqlTajUser
        PsqlTajUserDTO psqlTajUserDTO = psqlTajUserMapper.toDto(psqlTajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPsqlTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTajUser.setId(UUID.randomUUID());

        // Create the PsqlTajUser
        PsqlTajUserDTO psqlTajUserDTO = psqlTajUserMapper.toDto(psqlTajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(psqlTajUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePsqlTajUser() throws Exception {
        // Initialize the database
        insertedPsqlTajUser = psqlTajUserRepository.saveAndFlush(psqlTajUser);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the psqlTajUser
        restPsqlTajUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, psqlTajUser.getId().toString()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return psqlTajUserRepository.count();
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

    protected PsqlTajUser getPersistedPsqlTajUser(PsqlTajUser psqlTajUser) {
        return psqlTajUserRepository.findById(psqlTajUser.getId()).orElseThrow();
    }

    protected void assertPersistedPsqlTajUserToMatchAllProperties(PsqlTajUser expectedPsqlTajUser) {
        assertPsqlTajUserAllPropertiesEquals(expectedPsqlTajUser, getPersistedPsqlTajUser(expectedPsqlTajUser));
    }

    protected void assertPersistedPsqlTajUserToMatchUpdatableProperties(PsqlTajUser expectedPsqlTajUser) {
        assertPsqlTajUserAllUpdatablePropertiesEquals(expectedPsqlTajUser, getPersistedPsqlTajUser(expectedPsqlTajUser));
    }
}
