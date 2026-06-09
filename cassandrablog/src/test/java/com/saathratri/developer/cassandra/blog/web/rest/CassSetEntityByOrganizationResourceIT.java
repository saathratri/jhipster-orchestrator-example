package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassSetEntityByOrganizationAsserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassSetEntityByOrganization;
import com.saathratri.developer.cassandra.blog.repository.CassSetEntityByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.dto.CassSetEntityByOrganizationDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassSetEntityByOrganizationMapper;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CassSetEntityByOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassSetEntityByOrganizationResourceIT {

    private static final UUID DEFAULT_ORGANIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORGANIZATION_ID = UUID.randomUUID();

    private static final Set<String> DEFAULT_TAGS = new TreeSet<String>();
    private static final Set<String> UPDATED_TAGS = new TreeSet<String>();

    static {
        DEFAULT_TAGS.add("AAAAAAAAAA");
        UPDATED_TAGS.add("BBBBBBBBBB");
    }

    private static final String ENTITY_API_URL = "/api/cass-set-entity-by-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassSetEntityByOrganizationRepository cassSetEntityByOrganizationRepository;

    @Autowired
    private CassSetEntityByOrganizationMapper cassSetEntityByOrganizationMapper;

    @Autowired
    private MockMvc restCassSetEntityByOrganizationMockMvc;

    private CassSetEntityByOrganization cassSetEntityByOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassSetEntityByOrganization createEntity() {
        CassSetEntityByOrganization cassSetEntityByOrganization = new CassSetEntityByOrganization()
            .organizationId(DEFAULT_ORGANIZATION_ID)
            .tags(DEFAULT_TAGS);
        return cassSetEntityByOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassSetEntityByOrganization createUpdatedEntity() {
        CassSetEntityByOrganization cassSetEntityByOrganization = new CassSetEntityByOrganization()
            .organizationId(UPDATED_ORGANIZATION_ID)
            .tags(UPDATED_TAGS);
        return cassSetEntityByOrganization;
    }

    @BeforeEach
    public void initTest() {
        cassSetEntityByOrganizationRepository.deleteAll();
        cassSetEntityByOrganization = createEntity();
    }

    @Test
    void createCassSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassSetEntityByOrganization
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationMapper.toDto(
            cassSetEntityByOrganization
        );
        var returnedCassSetEntityByOrganizationDTO = om.readValue(
            restCassSetEntityByOrganizationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(cassSetEntityByOrganizationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassSetEntityByOrganizationDTO.class
        );

        // Validate the CassSetEntityByOrganization in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassSetEntityByOrganization = cassSetEntityByOrganizationMapper.toEntity(returnedCassSetEntityByOrganizationDTO);
        assertCassSetEntityByOrganizationUpdatableFieldsEquals(
            returnedCassSetEntityByOrganization,
            getPersistedCassSetEntityByOrganization(returnedCassSetEntityByOrganization)
        );
    }

    @Test
    void createCassSetEntityByOrganizationWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationMapper.toDto(
            cassSetEntityByOrganization
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassSetEntityByOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSetEntityByOrganizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CassSetEntityByOrganization was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCassSetEntityByOrganizations() throws Exception {
        // Initialize the database
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());
        cassSetEntityByOrganizationRepository.save(cassSetEntityByOrganization);

        // Get all the cassSetEntityByOrganizationList
        restCassSetEntityByOrganizationMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(cassSetEntityByOrganization.getOrganizationId().toString())))
            .andExpect(jsonPath("$.[*].tags").exists());
    }

    @Test
    void getCassSetEntityByOrganization() throws Exception {
        // Initialize the database
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());
        cassSetEntityByOrganizationRepository.save(cassSetEntityByOrganization);

        // Get the cassSetEntityByOrganization
        restCassSetEntityByOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, cassSetEntityByOrganization.getOrganizationId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.organizationId").value(cassSetEntityByOrganization.getOrganizationId().toString()))
            .andExpect(jsonPath("$.tags").exists());
    }

    @Test
    void getNonExistingCassSetEntityByOrganization() throws Exception {
        // Get the cassSetEntityByOrganization
        restCassSetEntityByOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassSetEntityByOrganization() throws Exception {
        // Initialize the database
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());
        cassSetEntityByOrganizationRepository.save(cassSetEntityByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSetEntityByOrganization
        CassSetEntityByOrganization updatedCassSetEntityByOrganization = cassSetEntityByOrganizationRepository
            .findById(cassSetEntityByOrganization.getOrganizationId())
            .orElseThrow();
        updatedCassSetEntityByOrganization.tags(UPDATED_TAGS);
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationMapper.toDto(
            updatedCassSetEntityByOrganization
        );

        restCassSetEntityByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassSetEntityByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSetEntityByOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassSetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassSetEntityByOrganizationToMatchAllProperties(updatedCassSetEntityByOrganization);
    }

    @Test
    void putNonExistingCassSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the CassSetEntityByOrganization
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationMapper.toDto(
            cassSetEntityByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassSetEntityByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassSetEntityByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSetEntityByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());
        // Create the CassSetEntityByOrganization
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationMapper.toDto(
            cassSetEntityByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSetEntityByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSetEntityByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the CassSetEntityByOrganization
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationMapper.toDto(
            cassSetEntityByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSetEntityByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSetEntityByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassSetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassSetEntityByOrganizationWithPatch() throws Exception {
        // Initialize the database
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());
        cassSetEntityByOrganizationRepository.save(cassSetEntityByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSetEntityByOrganization using partial update
        CassSetEntityByOrganization partialUpdatedCassSetEntityByOrganization = new CassSetEntityByOrganization();
        partialUpdatedCassSetEntityByOrganization.setOrganizationId(cassSetEntityByOrganization.getOrganizationId());

        partialUpdatedCassSetEntityByOrganization.tags(UPDATED_TAGS);

        restCassSetEntityByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassSetEntityByOrganization.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassSetEntityByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the CassSetEntityByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassSetEntityByOrganizationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassSetEntityByOrganization, cassSetEntityByOrganization),
            getPersistedCassSetEntityByOrganization(cassSetEntityByOrganization)
        );
    }

    @Test
    void fullUpdateCassSetEntityByOrganizationWithPatch() throws Exception {
        // Initialize the database
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());
        cassSetEntityByOrganizationRepository.save(cassSetEntityByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSetEntityByOrganization using partial update
        CassSetEntityByOrganization partialUpdatedCassSetEntityByOrganization = new CassSetEntityByOrganization();
        partialUpdatedCassSetEntityByOrganization.setOrganizationId(cassSetEntityByOrganization.getOrganizationId());

        partialUpdatedCassSetEntityByOrganization.tags(UPDATED_TAGS);

        restCassSetEntityByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassSetEntityByOrganization.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassSetEntityByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the CassSetEntityByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassSetEntityByOrganizationUpdatableFieldsEquals(
            partialUpdatedCassSetEntityByOrganization,
            getPersistedCassSetEntityByOrganization(partialUpdatedCassSetEntityByOrganization)
        );
    }

    @Test
    void patchNonExistingCassSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the CassSetEntityByOrganization
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationMapper.toDto(
            cassSetEntityByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassSetEntityByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cassSetEntityByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSetEntityByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the CassSetEntityByOrganization
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationMapper.toDto(
            cassSetEntityByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSetEntityByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSetEntityByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassSetEntityByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the CassSetEntityByOrganization
        CassSetEntityByOrganizationDTO cassSetEntityByOrganizationDTO = cassSetEntityByOrganizationMapper.toDto(
            cassSetEntityByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSetEntityByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSetEntityByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassSetEntityByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassSetEntityByOrganization() throws Exception {
        // Initialize the database
        cassSetEntityByOrganization.setOrganizationId(UUID.randomUUID());
        cassSetEntityByOrganizationRepository.save(cassSetEntityByOrganization);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassSetEntityByOrganization
        restCassSetEntityByOrganizationMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, cassSetEntityByOrganization.getOrganizationId()).with(csrf()).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassSetEntityByOrganizationRepository.count();
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

    protected CassSetEntityByOrganization getPersistedCassSetEntityByOrganization(CassSetEntityByOrganization cassSetEntityByOrganization) {
        return cassSetEntityByOrganizationRepository.findById(cassSetEntityByOrganization.getOrganizationId()).orElseThrow();
    }

    protected void assertPersistedCassSetEntityByOrganizationToMatchAllProperties(
        CassSetEntityByOrganization expectedCassSetEntityByOrganization
    ) {
        assertCassSetEntityByOrganizationAllPropertiesEquals(
            expectedCassSetEntityByOrganization,
            getPersistedCassSetEntityByOrganization(expectedCassSetEntityByOrganization)
        );
    }

    protected void assertPersistedCassSetEntityByOrganizationToMatchUpdatableProperties(
        CassSetEntityByOrganization expectedCassSetEntityByOrganization
    ) {
        assertCassSetEntityByOrganizationAllUpdatablePropertiesEquals(
            expectedCassSetEntityByOrganization,
            getPersistedCassSetEntityByOrganization(expectedCassSetEntityByOrganization)
        );
    }
}
