package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassTajUserAsserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassTajUser;
import com.saathratri.developer.cassandra.blog.repository.CassTajUserRepository;
import com.saathratri.developer.cassandra.blog.service.dto.CassTajUserDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassTajUserMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CassTajUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassTajUserResourceIT {

    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final UUID UPDATED_ID = UUID.randomUUID();

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cass-taj-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassTajUserRepository cassTajUserRepository;

    @Autowired
    private CassTajUserMapper cassTajUserMapper;

    @Autowired
    private MockMvc restCassTajUserMockMvc;

    private CassTajUser cassTajUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassTajUser createEntity() {
        CassTajUser cassTajUser = new CassTajUser().id(DEFAULT_ID).login(DEFAULT_LOGIN);
        return cassTajUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassTajUser createUpdatedEntity() {
        CassTajUser cassTajUser = new CassTajUser().id(UPDATED_ID).login(UPDATED_LOGIN);
        return cassTajUser;
    }

    @BeforeEach
    public void initTest() {
        cassTajUserRepository.deleteAll();
        cassTajUser = createEntity();
    }

    @Test
    void createCassTajUser() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassTajUser
        CassTajUserDTO cassTajUserDTO = cassTajUserMapper.toDto(cassTajUser);
        var returnedCassTajUserDTO = om.readValue(
            restCassTajUserMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassTajUserDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassTajUserDTO.class
        );

        // Validate the CassTajUser in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassTajUser = cassTajUserMapper.toEntity(returnedCassTajUserDTO);
        assertCassTajUserUpdatableFieldsEquals(returnedCassTajUser, getPersistedCassTajUser(returnedCassTajUser));
    }

    @Test
    void createCassTajUserWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassTajUserDTO cassTajUserDTO = cassTajUserMapper.toDto(cassTajUser);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassTajUserMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassTajUserDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CassTajUser was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkLoginIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassTajUser.setLogin(null);

        // Create the CassTajUser, which fails.
        CassTajUserDTO cassTajUserDTO = cassTajUserMapper.toDto(cassTajUser);

        restCassTajUserMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCassTajUsers() throws Exception {
        // Initialize the database
        cassTajUser.setId(UUID.randomUUID());
        cassTajUserRepository.save(cassTajUser);

        // Get all the cassTajUserList
        restCassTajUserMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cassTajUser.getId().toString())))

            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)));
    }

    @Test
    void getCassTajUser() throws Exception {
        // Initialize the database
        cassTajUser.setId(UUID.randomUUID());
        cassTajUserRepository.save(cassTajUser);

        // Get the cassTajUser
        restCassTajUserMockMvc
            .perform(get(ENTITY_API_URL_ID, cassTajUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cassTajUser.getId().toString()))

            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN));
    }

    @Test
    void getNonExistingCassTajUser() throws Exception {
        // Get the cassTajUser
        restCassTajUserMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassTajUser() throws Exception {
        // Initialize the database
        cassTajUser.setId(UUID.randomUUID());
        cassTajUserRepository.save(cassTajUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassTajUser
        CassTajUser updatedCassTajUser = cassTajUserRepository.findById(cassTajUser.getId()).orElseThrow();
        updatedCassTajUser.login(UPDATED_LOGIN);
        CassTajUserDTO cassTajUserDTO = cassTajUserMapper.toDto(updatedCassTajUser);

        restCassTajUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassTajUserDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassTajUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassTajUserToMatchAllProperties(updatedCassTajUser);
    }

    @Test
    void putNonExistingCassTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTajUser.setId(UUID.randomUUID());

        // Create the CassTajUser
        CassTajUserDTO cassTajUserDTO = cassTajUserMapper.toDto(cassTajUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassTajUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassTajUserDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTajUser.setId(UUID.randomUUID());
        // Create the CassTajUser
        CassTajUserDTO cassTajUserDTO = cassTajUserMapper.toDto(cassTajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassTajUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTajUser.setId(UUID.randomUUID());

        // Create the CassTajUser
        CassTajUserDTO cassTajUserDTO = cassTajUserMapper.toDto(cassTajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassTajUserMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassTajUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassTajUserWithPatch() throws Exception {
        // Initialize the database
        cassTajUser.setId(UUID.randomUUID());
        cassTajUserRepository.save(cassTajUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassTajUser using partial update
        CassTajUser partialUpdatedCassTajUser = new CassTajUser();
        partialUpdatedCassTajUser.setId(cassTajUser.getId());

        partialUpdatedCassTajUser.login(UPDATED_LOGIN);

        restCassTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassTajUser.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassTajUser))
            )
            .andExpect(status().isOk());

        // Validate the CassTajUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassTajUserUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassTajUser, cassTajUser),
            getPersistedCassTajUser(cassTajUser)
        );
    }

    @Test
    void fullUpdateCassTajUserWithPatch() throws Exception {
        // Initialize the database
        cassTajUser.setId(UUID.randomUUID());
        cassTajUserRepository.save(cassTajUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassTajUser using partial update
        CassTajUser partialUpdatedCassTajUser = new CassTajUser();
        partialUpdatedCassTajUser.setId(cassTajUser.getId());

        partialUpdatedCassTajUser.login(UPDATED_LOGIN);

        restCassTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassTajUser.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassTajUser))
            )
            .andExpect(status().isOk());

        // Validate the CassTajUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassTajUserUpdatableFieldsEquals(partialUpdatedCassTajUser, getPersistedCassTajUser(partialUpdatedCassTajUser));
    }

    @Test
    void patchNonExistingCassTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTajUser.setId(UUID.randomUUID());

        // Create the CassTajUser
        CassTajUserDTO cassTajUserDTO = cassTajUserMapper.toDto(cassTajUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cassTajUserDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTajUser.setId(UUID.randomUUID());

        // Create the CassTajUser
        CassTajUserDTO cassTajUserDTO = cassTajUserMapper.toDto(cassTajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassTajUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassTajUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTajUser.setId(UUID.randomUUID());

        // Create the CassTajUser
        CassTajUserDTO cassTajUserDTO = cassTajUserMapper.toDto(cassTajUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassTajUserMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cassTajUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassTajUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassTajUser() throws Exception {
        // Initialize the database
        cassTajUser.setId(UUID.randomUUID());
        cassTajUserRepository.save(cassTajUser);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassTajUser
        restCassTajUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, cassTajUser.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassTajUserRepository.count();
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

    protected CassTajUser getPersistedCassTajUser(CassTajUser cassTajUser) {
        return cassTajUserRepository.findById(cassTajUser.getId()).orElseThrow();
    }

    protected void assertPersistedCassTajUserToMatchAllProperties(CassTajUser expectedCassTajUser) {
        assertCassTajUserAllPropertiesEquals(expectedCassTajUser, getPersistedCassTajUser(expectedCassTajUser));
    }

    protected void assertPersistedCassTajUserToMatchUpdatableProperties(CassTajUser expectedCassTajUser) {
        assertCassTajUserAllUpdatablePropertiesEquals(expectedCassTajUser, getPersistedCassTajUser(expectedCassTajUser));
    }
}
