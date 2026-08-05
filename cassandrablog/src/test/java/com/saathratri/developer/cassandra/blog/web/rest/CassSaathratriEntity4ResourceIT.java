package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4Asserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity4Id;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntity4Repository;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity4DTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassSaathratriEntity4Mapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CassSaathratriEntity4Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassSaathratriEntity4ResourceIT {

    private static final UUID DEFAULT_ORGANIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORGANIZATION_ID = UUID.randomUUID();

    private static final String DEFAULT_ATTRIBUTE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cass-saathratri-entity-4-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassSaathratriEntity4Repository cassSaathratriEntity4Repository;

    @Autowired
    private CassSaathratriEntity4Mapper cassSaathratriEntity4Mapper;

    @Autowired
    private MockMvc restCassSaathratriEntity4MockMvc;

    private CassSaathratriEntity4 cassSaathratriEntity4;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassSaathratriEntity4 createEntity() {
        CassSaathratriEntity4 cassSaathratriEntity4 = new CassSaathratriEntity4()
            .compositeId(new CassSaathratriEntity4Id().organizationId(DEFAULT_ORGANIZATION_ID).attributeKey(DEFAULT_ATTRIBUTE_KEY))
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        cassSaathratriEntity4.setCompositeId(new CassSaathratriEntity4Id(DEFAULT_ORGANIZATION_ID, DEFAULT_ATTRIBUTE_KEY));
        return cassSaathratriEntity4;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassSaathratriEntity4 createUpdatedEntity() {
        CassSaathratriEntity4 cassSaathratriEntity4 = new CassSaathratriEntity4()
            .compositeId(new CassSaathratriEntity4Id().organizationId(UPDATED_ORGANIZATION_ID).attributeKey(UPDATED_ATTRIBUTE_KEY))
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        cassSaathratriEntity4.setCompositeId(new CassSaathratriEntity4Id(UPDATED_ORGANIZATION_ID, UPDATED_ATTRIBUTE_KEY));
        return cassSaathratriEntity4;
    }

    @BeforeEach
    public void initTest() {
        cassSaathratriEntity4Repository.deleteAll();
        cassSaathratriEntity4 = createEntity();
    }

    @Test
    void createCassSaathratriEntity4() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassSaathratriEntity4
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO = cassSaathratriEntity4Mapper.toDto(cassSaathratriEntity4);
        var returnedCassSaathratriEntity4DTO = om.readValue(
            restCassSaathratriEntity4MockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(cassSaathratriEntity4DTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassSaathratriEntity4DTO.class
        );

        // Validate the CassSaathratriEntity4 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassSaathratriEntity4 = cassSaathratriEntity4Mapper.toEntity(returnedCassSaathratriEntity4DTO);
        assertCassSaathratriEntity4UpdatableFieldsEquals(
            returnedCassSaathratriEntity4,
            getPersistedCassSaathratriEntity4(returnedCassSaathratriEntity4)
        );
    }

    @Test
    void createCassSaathratriEntity4WithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO = cassSaathratriEntity4Mapper.toDto(cassSaathratriEntity4);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassSaathratriEntity4MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity4DTO))
            )
            .andExpect(status().isCreated());

        // Validate the CassSaathratriEntity4 was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCassSaathratriEntity4s() throws Exception {
        // Initialize the database
        cassSaathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassSaathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        cassSaathratriEntity4Repository.save(cassSaathratriEntity4);

        // Get all the cassSaathratriEntity4List
        restCassSaathratriEntity4MockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(cassSaathratriEntity4.getCompositeId().getOrganizationId().toString())
                )
            )

            .andExpect(
                jsonPath("$.[*].compositeId.attributeKey").value(
                    hasItem(cassSaathratriEntity4.getCompositeId().getAttributeKey().toString())
                )
            )

            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    void getCassSaathratriEntity4() throws Exception {
        // Initialize the database
        cassSaathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassSaathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        cassSaathratriEntity4Repository.save(cassSaathratriEntity4);

        // Get the cassSaathratriEntity4
        restCassSaathratriEntity4MockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("organizationId", String.valueOf(cassSaathratriEntity4.getCompositeId().getOrganizationId()))
                    .param("attributeKey", String.valueOf(cassSaathratriEntity4.getCompositeId().getAttributeKey()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            .andExpect(
                jsonPath("$.compositeId.organizationId").value(cassSaathratriEntity4.getCompositeId().getOrganizationId().toString())
            )

            .andExpect(jsonPath("$.compositeId.attributeKey").value(cassSaathratriEntity4.getCompositeId().getAttributeKey().toString()))

            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }

    @Test
    void getAllCassSaathratriEntity4sByCompositeKeySearches() throws Exception {
        // Initialize the database
        cassSaathratriEntity4Repository.save(cassSaathratriEntity4);

        // Exercise every generated composite-key search endpoint (partial-partition findAllBy
        // carry @AllowFiltering, clustering/comparison/findBy are plain valid queries), plus
        // /slice. A 200 confirms the derived CQL + parameter binding executes against real
        // Cassandra; body shape is covered by the get()/getAll() tests above.
        restCassSaathratriEntity4MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id").param(
                    "organizationId",
                    String.valueOf(cassSaathratriEntity4.getCompositeId().getOrganizationId())
                )
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity4MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-pageable")
                    .param("organizationId", String.valueOf(cassSaathratriEntity4.getCompositeId().getOrganizationId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity4MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-by-composite-id-organization-id-and-composite-id-attribute-key")
                    .param("organizationId", String.valueOf(cassSaathratriEntity4.getCompositeId().getOrganizationId()))
                    .param("attributeKey", String.valueOf(cassSaathratriEntity4.getCompositeId().getAttributeKey()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity4MockMvc.perform(get(ENTITY_API_URL + "/slice").param("size", "20")).andExpect(status().isOk());
    }

    @Test
    void getNonExistingCassSaathratriEntity4() throws Exception {
        // Get the cassSaathratriEntity4
        restCassSaathratriEntity4MockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("organizationId", String.valueOf(cassSaathratriEntity4.getCompositeId().getOrganizationId()))
                    .param("attributeKey", String.valueOf(cassSaathratriEntity4.getCompositeId().getAttributeKey()))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassSaathratriEntity4() throws Exception {
        // Initialize the database
        cassSaathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassSaathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        cassSaathratriEntity4Repository.save(cassSaathratriEntity4);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity4
        CassSaathratriEntity4 updatedCassSaathratriEntity4 = cassSaathratriEntity4Repository
            .findById(cassSaathratriEntity4.getCompositeId())
            .orElseThrow();
        updatedCassSaathratriEntity4.attributeValue(UPDATED_ATTRIBUTE_VALUE);
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO = cassSaathratriEntity4Mapper.toDto(updatedCassSaathratriEntity4);

        restCassSaathratriEntity4MockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{organizationId}/{attributeKey}",
                    cassSaathratriEntity4DTO.getCompositeId().getOrganizationId(),
                    cassSaathratriEntity4DTO.getCompositeId().getAttributeKey()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity4DTO))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassSaathratriEntity4ToMatchAllProperties(updatedCassSaathratriEntity4);
    }

    @Test
    void putNonExistingCassSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity4.setCompositeId(new CassSaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));

        // Create the CassSaathratriEntity4
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO = cassSaathratriEntity4Mapper.toDto(cassSaathratriEntity4);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassSaathratriEntity4MockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{organizationId}/{attributeKey}",
                    cassSaathratriEntity4DTO.getCompositeId().getOrganizationId(),
                    cassSaathratriEntity4DTO.getCompositeId().getAttributeKey()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity4DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity4.setCompositeId(new CassSaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));
        // Create the CassSaathratriEntity4
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO = cassSaathratriEntity4Mapper.toDto(cassSaathratriEntity4);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity4MockMvc
            .perform(
                put(ENTITY_API_URL + "/{organizationId}/{attributeKey}", UUID.randomUUID(), UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity4DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity4.setCompositeId(new CassSaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));

        // Create the CassSaathratriEntity4
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO = cassSaathratriEntity4Mapper.toDto(cassSaathratriEntity4);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity4MockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity4DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassSaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassSaathratriEntity4WithPatch() throws Exception {
        // Initialize the database
        cassSaathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassSaathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        cassSaathratriEntity4Repository.save(cassSaathratriEntity4);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity4 using partial update
        CassSaathratriEntity4 partialUpdatedCassSaathratriEntity4 = new CassSaathratriEntity4();
        partialUpdatedCassSaathratriEntity4.setCompositeId(cassSaathratriEntity4.getCompositeId());

        partialUpdatedCassSaathratriEntity4.attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restCassSaathratriEntity4MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{attributeKey}",
                    partialUpdatedCassSaathratriEntity4.getCompositeId().getOrganizationId(),
                    partialUpdatedCassSaathratriEntity4.getCompositeId().getAttributeKey()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassSaathratriEntity4))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity4 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassSaathratriEntity4UpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassSaathratriEntity4, cassSaathratriEntity4),
            getPersistedCassSaathratriEntity4(cassSaathratriEntity4)
        );
    }

    @Test
    void fullUpdateCassSaathratriEntity4WithPatch() throws Exception {
        // Initialize the database
        cassSaathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassSaathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        cassSaathratriEntity4Repository.save(cassSaathratriEntity4);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity4 using partial update
        CassSaathratriEntity4 partialUpdatedCassSaathratriEntity4 = new CassSaathratriEntity4();
        partialUpdatedCassSaathratriEntity4.setCompositeId(cassSaathratriEntity4.getCompositeId());

        partialUpdatedCassSaathratriEntity4.attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restCassSaathratriEntity4MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{attributeKey}",
                    partialUpdatedCassSaathratriEntity4.getCompositeId().getOrganizationId(),
                    partialUpdatedCassSaathratriEntity4.getCompositeId().getAttributeKey()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassSaathratriEntity4))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity4 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassSaathratriEntity4UpdatableFieldsEquals(
            partialUpdatedCassSaathratriEntity4,
            getPersistedCassSaathratriEntity4(partialUpdatedCassSaathratriEntity4)
        );
    }

    @Test
    void patchNonExistingCassSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity4.setCompositeId(new CassSaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));

        // Create the CassSaathratriEntity4
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO = cassSaathratriEntity4Mapper.toDto(cassSaathratriEntity4);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassSaathratriEntity4MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{attributeKey}",
                    cassSaathratriEntity4DTO.getCompositeId().getOrganizationId(),
                    cassSaathratriEntity4DTO.getCompositeId().getAttributeKey()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntity4DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity4.setCompositeId(new CassSaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));

        // Create the CassSaathratriEntity4
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO = cassSaathratriEntity4Mapper.toDto(cassSaathratriEntity4);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity4MockMvc
            .perform(
                patch(ENTITY_API_URL + "/{organizationId}/{attributeKey}", UUID.randomUUID(), UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntity4DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassSaathratriEntity4() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity4.setCompositeId(new CassSaathratriEntity4Id(UUID.randomUUID(), UUID.randomUUID().toString()));

        // Create the CassSaathratriEntity4
        CassSaathratriEntity4DTO cassSaathratriEntity4DTO = cassSaathratriEntity4Mapper.toDto(cassSaathratriEntity4);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity4MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntity4DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassSaathratriEntity4 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassSaathratriEntity4() throws Exception {
        // Initialize the database
        cassSaathratriEntity4.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassSaathratriEntity4.getCompositeId().setAttributeKey(UUID.randomUUID().toString());
        cassSaathratriEntity4Repository.save(cassSaathratriEntity4);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassSaathratriEntity4
        restCassSaathratriEntity4MockMvc
            .perform(
                delete(
                    ENTITY_API_URL + "/{organizationId}/{attributeKey}",
                    cassSaathratriEntity4.getCompositeId().getOrganizationId(),
                    cassSaathratriEntity4.getCompositeId().getAttributeKey()
                )
                    .with(csrf())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassSaathratriEntity4Repository.count();
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

    protected CassSaathratriEntity4 getPersistedCassSaathratriEntity4(CassSaathratriEntity4 cassSaathratriEntity4) {
        return cassSaathratriEntity4Repository.findById(cassSaathratriEntity4.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedCassSaathratriEntity4ToMatchAllProperties(CassSaathratriEntity4 expectedCassSaathratriEntity4) {
        assertCassSaathratriEntity4AllPropertiesEquals(
            expectedCassSaathratriEntity4,
            getPersistedCassSaathratriEntity4(expectedCassSaathratriEntity4)
        );
    }

    protected void assertPersistedCassSaathratriEntity4ToMatchUpdatableProperties(CassSaathratriEntity4 expectedCassSaathratriEntity4) {
        assertCassSaathratriEntity4AllUpdatablePropertiesEquals(
            expectedCassSaathratriEntity4,
            getPersistedCassSaathratriEntity4(expectedCassSaathratriEntity4)
        );
    }
}
