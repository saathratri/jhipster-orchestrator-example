package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassLandingPageByOrganizationAsserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassLandingPageByOrganization;
import com.saathratri.developer.cassandra.blog.repository.CassLandingPageByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.dto.CassLandingPageByOrganizationDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassLandingPageByOrganizationMapper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CassLandingPageByOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassLandingPageByOrganizationResourceIT {

    private static final UUID DEFAULT_ORGANIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORGANIZATION_ID = UUID.randomUUID();

    private static final Map<String, String> DEFAULT_DETAILS_TEXT = new HashMap<String, String>();
    private static final Map<String, String> UPDATED_DETAILS_TEXT = new HashMap<String, String>();

    static {
        DEFAULT_DETAILS_TEXT.put("AAAAAAAAAA", "1");
        UPDATED_DETAILS_TEXT.put("AAAAAAAAAA", "2");
    }

    private static final Map<String, BigDecimal> DEFAULT_DETAILS_DECIMAL = new HashMap<String, BigDecimal>();
    private static final Map<String, BigDecimal> UPDATED_DETAILS_DECIMAL = new HashMap<String, BigDecimal>();

    static {
        DEFAULT_DETAILS_DECIMAL.put("AAAAAAAAAA", new BigDecimal(1));
        UPDATED_DETAILS_DECIMAL.put("AAAAAAAAAA", new BigDecimal(2));
    }

    private static final Map<String, Boolean> DEFAULT_DETAILS_BOOLEAN = new HashMap<String, Boolean>();
    private static final Map<String, Boolean> UPDATED_DETAILS_BOOLEAN = new HashMap<String, Boolean>();

    static {
        DEFAULT_DETAILS_BOOLEAN.put("AAAAAAAAAA", false);
        UPDATED_DETAILS_BOOLEAN.put("AAAAAAAAAA", true);
    }

    private static final Map<String, Long> DEFAULT_DETAILS_BIG_INT = new HashMap<String, Long>();
    private static final Map<String, Long> UPDATED_DETAILS_BIG_INT = new HashMap<String, Long>();

    static {
        DEFAULT_DETAILS_BIG_INT.put("AAAAAAAAAA", 1L);
        UPDATED_DETAILS_BIG_INT.put("AAAAAAAAAA", 2L);
    }

    private static final String ENTITY_API_URL = "/api/cass-landing-page-by-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassLandingPageByOrganizationRepository cassLandingPageByOrganizationRepository;

    @Autowired
    private CassLandingPageByOrganizationMapper cassLandingPageByOrganizationMapper;

    @Autowired
    private MockMvc restCassLandingPageByOrganizationMockMvc;

    private CassLandingPageByOrganization cassLandingPageByOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassLandingPageByOrganization createEntity() {
        CassLandingPageByOrganization cassLandingPageByOrganization = new CassLandingPageByOrganization()

            .organizationId(DEFAULT_ORGANIZATION_ID)
            .detailsText(DEFAULT_DETAILS_TEXT)
            .detailsDecimal(DEFAULT_DETAILS_DECIMAL)
            .detailsBoolean(DEFAULT_DETAILS_BOOLEAN)
            .detailsBigInt(DEFAULT_DETAILS_BIG_INT);
        return cassLandingPageByOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassLandingPageByOrganization createUpdatedEntity() {
        CassLandingPageByOrganization cassLandingPageByOrganization = new CassLandingPageByOrganization()

            .organizationId(UPDATED_ORGANIZATION_ID)
            .detailsText(UPDATED_DETAILS_TEXT)
            .detailsDecimal(UPDATED_DETAILS_DECIMAL)
            .detailsBoolean(UPDATED_DETAILS_BOOLEAN)
            .detailsBigInt(UPDATED_DETAILS_BIG_INT);
        return cassLandingPageByOrganization;
    }

    @BeforeEach
    public void initTest() {
        cassLandingPageByOrganizationRepository.deleteAll();
        cassLandingPageByOrganization = createEntity();
    }

    @Test
    void createCassLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassLandingPageByOrganization
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationMapper.toDto(
            cassLandingPageByOrganization
        );
        var returnedCassLandingPageByOrganizationDTO = om.readValue(
            restCassLandingPageByOrganizationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(cassLandingPageByOrganizationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassLandingPageByOrganizationDTO.class
        );

        // Validate the CassLandingPageByOrganization in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassLandingPageByOrganization = cassLandingPageByOrganizationMapper.toEntity(returnedCassLandingPageByOrganizationDTO);
        assertCassLandingPageByOrganizationUpdatableFieldsEquals(
            returnedCassLandingPageByOrganization,
            getPersistedCassLandingPageByOrganization(returnedCassLandingPageByOrganization)
        );
    }

    @Test
    void createCassLandingPageByOrganizationWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationMapper.toDto(
            cassLandingPageByOrganization
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassLandingPageByOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassLandingPageByOrganizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CassLandingPageByOrganization was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCassLandingPageByOrganizations() throws Exception {
        // Initialize the database
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());
        cassLandingPageByOrganizationRepository.save(cassLandingPageByOrganization);

        // Get all the cassLandingPageByOrganizationList
        restCassLandingPageByOrganizationMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(cassLandingPageByOrganization.getOrganizationId().toString())))

            .andExpect(jsonPath("$.[*].detailsText").exists())

            .andExpect(jsonPath("$.[*].detailsDecimal").exists())

            .andExpect(jsonPath("$.[*].detailsBoolean").exists())

            .andExpect(jsonPath("$.[*].detailsBigInt").exists());
    }

    @Test
    void getCassLandingPageByOrganization() throws Exception {
        // Initialize the database
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());
        cassLandingPageByOrganizationRepository.save(cassLandingPageByOrganization);

        // Get the cassLandingPageByOrganization
        restCassLandingPageByOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, cassLandingPageByOrganization.getOrganizationId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.organizationId").value(cassLandingPageByOrganization.getOrganizationId().toString()))

            .andExpect(jsonPath("$.detailsText").exists())

            .andExpect(jsonPath("$.detailsDecimal").exists())

            .andExpect(jsonPath("$.detailsBoolean").exists())

            .andExpect(jsonPath("$.detailsBigInt").exists());
    }

    @Test
    void getNonExistingCassLandingPageByOrganization() throws Exception {
        // Get the cassLandingPageByOrganization
        restCassLandingPageByOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassLandingPageByOrganization() throws Exception {
        // Initialize the database
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());
        cassLandingPageByOrganizationRepository.save(cassLandingPageByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassLandingPageByOrganization
        CassLandingPageByOrganization updatedCassLandingPageByOrganization = cassLandingPageByOrganizationRepository
            .findById(cassLandingPageByOrganization.getOrganizationId())
            .orElseThrow();
        updatedCassLandingPageByOrganization

            .detailsText(UPDATED_DETAILS_TEXT)

            .detailsDecimal(UPDATED_DETAILS_DECIMAL)

            .detailsBoolean(UPDATED_DETAILS_BOOLEAN)

            .detailsBigInt(UPDATED_DETAILS_BIG_INT);
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationMapper.toDto(
            updatedCassLandingPageByOrganization
        );

        restCassLandingPageByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassLandingPageByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassLandingPageByOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassLandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassLandingPageByOrganizationToMatchAllProperties(updatedCassLandingPageByOrganization);
    }

    @Test
    void putNonExistingCassLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the CassLandingPageByOrganization
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationMapper.toDto(
            cassLandingPageByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassLandingPageByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassLandingPageByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassLandingPageByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassLandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());
        // Create the CassLandingPageByOrganization
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationMapper.toDto(
            cassLandingPageByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassLandingPageByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassLandingPageByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassLandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the CassLandingPageByOrganization
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationMapper.toDto(
            cassLandingPageByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassLandingPageByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassLandingPageByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassLandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassLandingPageByOrganizationWithPatch() throws Exception {
        // Initialize the database
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());
        cassLandingPageByOrganizationRepository.save(cassLandingPageByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassLandingPageByOrganization using partial update
        CassLandingPageByOrganization partialUpdatedCassLandingPageByOrganization = new CassLandingPageByOrganization();
        partialUpdatedCassLandingPageByOrganization.setOrganizationId(cassLandingPageByOrganization.getOrganizationId());

        partialUpdatedCassLandingPageByOrganization

            .detailsText(UPDATED_DETAILS_TEXT)

            .detailsDecimal(UPDATED_DETAILS_DECIMAL)

            .detailsBoolean(UPDATED_DETAILS_BOOLEAN)

            .detailsBigInt(UPDATED_DETAILS_BIG_INT);

        restCassLandingPageByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassLandingPageByOrganization.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassLandingPageByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the CassLandingPageByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassLandingPageByOrganizationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassLandingPageByOrganization, cassLandingPageByOrganization),
            getPersistedCassLandingPageByOrganization(cassLandingPageByOrganization)
        );
    }

    @Test
    void fullUpdateCassLandingPageByOrganizationWithPatch() throws Exception {
        // Initialize the database
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());
        cassLandingPageByOrganizationRepository.save(cassLandingPageByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassLandingPageByOrganization using partial update
        CassLandingPageByOrganization partialUpdatedCassLandingPageByOrganization = new CassLandingPageByOrganization();
        partialUpdatedCassLandingPageByOrganization.setOrganizationId(cassLandingPageByOrganization.getOrganizationId());

        partialUpdatedCassLandingPageByOrganization

            .detailsText(UPDATED_DETAILS_TEXT)

            .detailsDecimal(UPDATED_DETAILS_DECIMAL)

            .detailsBoolean(UPDATED_DETAILS_BOOLEAN)

            .detailsBigInt(UPDATED_DETAILS_BIG_INT);

        restCassLandingPageByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassLandingPageByOrganization.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassLandingPageByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the CassLandingPageByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassLandingPageByOrganizationUpdatableFieldsEquals(
            partialUpdatedCassLandingPageByOrganization,
            getPersistedCassLandingPageByOrganization(partialUpdatedCassLandingPageByOrganization)
        );
    }

    @Test
    void patchNonExistingCassLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the CassLandingPageByOrganization
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationMapper.toDto(
            cassLandingPageByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassLandingPageByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cassLandingPageByOrganizationDTO.getOrganizationId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassLandingPageByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassLandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the CassLandingPageByOrganization
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationMapper.toDto(
            cassLandingPageByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassLandingPageByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassLandingPageByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassLandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassLandingPageByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());

        // Create the CassLandingPageByOrganization
        CassLandingPageByOrganizationDTO cassLandingPageByOrganizationDTO = cassLandingPageByOrganizationMapper.toDto(
            cassLandingPageByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassLandingPageByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassLandingPageByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassLandingPageByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassLandingPageByOrganization() throws Exception {
        // Initialize the database
        cassLandingPageByOrganization.setOrganizationId(UUID.randomUUID());
        cassLandingPageByOrganizationRepository.save(cassLandingPageByOrganization);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassLandingPageByOrganization
        restCassLandingPageByOrganizationMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, cassLandingPageByOrganization.getOrganizationId()).with(csrf()).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassLandingPageByOrganizationRepository.count();
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

    protected CassLandingPageByOrganization getPersistedCassLandingPageByOrganization(
        CassLandingPageByOrganization cassLandingPageByOrganization
    ) {
        return cassLandingPageByOrganizationRepository.findById(cassLandingPageByOrganization.getOrganizationId()).orElseThrow();
    }

    protected void assertPersistedCassLandingPageByOrganizationToMatchAllProperties(
        CassLandingPageByOrganization expectedCassLandingPageByOrganization
    ) {
        assertCassLandingPageByOrganizationAllPropertiesEquals(
            expectedCassLandingPageByOrganization,
            getPersistedCassLandingPageByOrganization(expectedCassLandingPageByOrganization)
        );
    }

    protected void assertPersistedCassLandingPageByOrganizationToMatchUpdatableProperties(
        CassLandingPageByOrganization expectedCassLandingPageByOrganization
    ) {
        assertCassLandingPageByOrganizationAllUpdatablePropertiesEquals(
            expectedCassLandingPageByOrganization,
            getPersistedCassLandingPageByOrganization(expectedCassLandingPageByOrganization)
        );
    }
}
