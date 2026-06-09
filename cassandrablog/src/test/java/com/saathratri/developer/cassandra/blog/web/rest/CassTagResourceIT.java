package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassTagAsserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.datastax.oss.driver.api.core.data.CqlVector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassTag;
import com.saathratri.developer.cassandra.blog.repository.CassTagRepository;
import com.saathratri.developer.cassandra.blog.service.dto.CassTagDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassTagMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CassTagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassTagResourceIT {

    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final UUID UPDATED_ID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cass-tags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassTagRepository cassTagRepository;

    @Autowired
    private CassTagMapper cassTagMapper;

    @Autowired
    private MockMvc restCassTagMockMvc;

    private CassTag cassTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassTag createEntity() {
        CassTag cassTag = new CassTag().id(DEFAULT_ID).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return cassTag;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassTag createUpdatedEntity() {
        CassTag cassTag = new CassTag().id(UPDATED_ID).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return cassTag;
    }

    @BeforeEach
    public void initTest() {
        cassTagRepository.deleteAll();
        cassTag = createEntity();
    }

    @Test
    void createCassTag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassTag
        CassTagDTO cassTagDTO = cassTagMapper.toDto(cassTag);
        var returnedCassTagDTO = om.readValue(
            restCassTagMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassTagDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassTagDTO.class
        );

        // Validate the CassTag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassTag = cassTagMapper.toEntity(returnedCassTagDTO);
        assertCassTagUpdatableFieldsEquals(returnedCassTag, getPersistedCassTag(returnedCassTag));
    }

    @Test
    void createCassTagWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassTagDTO cassTagDTO = cassTagMapper.toDto(cassTag);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassTagMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassTagDTO)))
            .andExpect(status().isCreated());

        // Validate the CassTag was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassTag.setName(null);

        // Create the CassTag, which fails.
        CassTagDTO cassTagDTO = cassTagMapper.toDto(cassTag);

        restCassTagMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassTagDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCassTags() throws Exception {
        // Initialize the database
        cassTag.setId(UUID.randomUUID());
        cassTagRepository.save(cassTag);

        // Get all the cassTagList
        restCassTagMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cassTag.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getCassTag() throws Exception {
        // Initialize the database
        cassTag.setId(UUID.randomUUID());
        cassTagRepository.save(cassTag);

        // Get the cassTag
        restCassTagMockMvc
            .perform(get(ENTITY_API_URL_ID, cassTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cassTag.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingCassTag() throws Exception {
        // Get the cassTag
        restCassTagMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassTag() throws Exception {
        // Initialize the database
        cassTag.setId(UUID.randomUUID());
        cassTagRepository.save(cassTag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassTag
        CassTag updatedCassTag = cassTagRepository.findById(cassTag.getId()).orElseThrow();
        updatedCassTag.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        CassTagDTO cassTagDTO = cassTagMapper.toDto(updatedCassTag);

        restCassTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassTagDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassTagDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassTagToMatchAllProperties(updatedCassTag);
    }

    @Test
    void putNonExistingCassTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTag.setId(UUID.randomUUID());

        // Create the CassTag
        CassTagDTO cassTagDTO = cassTagMapper.toDto(cassTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassTagDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTag.setId(UUID.randomUUID());
        // Create the CassTag
        CassTagDTO cassTagDTO = cassTagMapper.toDto(cassTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTag.setId(UUID.randomUUID());

        // Create the CassTag
        CassTagDTO cassTagDTO = cassTagMapper.toDto(cassTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassTagMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassTagDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassTagWithPatch() throws Exception {
        // Initialize the database
        cassTag.setId(UUID.randomUUID());
        cassTagRepository.save(cassTag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassTag using partial update
        CassTag partialUpdatedCassTag = new CassTag();
        partialUpdatedCassTag.setId(cassTag.getId());

        partialUpdatedCassTag.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restCassTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassTag.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassTag))
            )
            .andExpect(status().isOk());

        // Validate the CassTag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassTagUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCassTag, cassTag), getPersistedCassTag(cassTag));
    }

    @Test
    void fullUpdateCassTagWithPatch() throws Exception {
        // Initialize the database
        cassTag.setId(UUID.randomUUID());
        cassTagRepository.save(cassTag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassTag using partial update
        CassTag partialUpdatedCassTag = new CassTag();
        partialUpdatedCassTag.setId(cassTag.getId());

        partialUpdatedCassTag.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restCassTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassTag.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassTag))
            )
            .andExpect(status().isOk());

        // Validate the CassTag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassTagUpdatableFieldsEquals(partialUpdatedCassTag, getPersistedCassTag(partialUpdatedCassTag));
    }

    @Test
    void patchNonExistingCassTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTag.setId(UUID.randomUUID());

        // Create the CassTag
        CassTagDTO cassTagDTO = cassTagMapper.toDto(cassTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cassTagDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTag.setId(UUID.randomUUID());

        // Create the CassTag
        CassTagDTO cassTagDTO = cassTagMapper.toDto(cassTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassTag.setId(UUID.randomUUID());

        // Create the CassTag
        CassTagDTO cassTagDTO = cassTagMapper.toDto(cassTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassTagMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cassTagDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassTag() throws Exception {
        // Initialize the database
        cassTag.setId(UUID.randomUUID());
        cassTagRepository.save(cassTag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassTag
        restCassTagMockMvc
            .perform(delete(ENTITY_API_URL_ID, cassTag.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassTagRepository.count();
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

    protected CassTag getPersistedCassTag(CassTag cassTag) {
        return cassTagRepository.findById(cassTag.getId()).orElseThrow();
    }

    protected void assertPersistedCassTagToMatchAllProperties(CassTag expectedCassTag) {
        assertCassTagAllPropertiesEquals(expectedCassTag, getPersistedCassTag(expectedCassTag));
    }

    protected void assertPersistedCassTagToMatchUpdatableProperties(CassTag expectedCassTag) {
        assertCassTagAllUpdatablePropertiesEquals(expectedCassTag, getPersistedCassTag(expectedCassTag));
    }

    // ==================== AI-search (ANN vector similarity) integration test (Saathratri) ====================

    @MockitoBean
    private EmbeddingModel embeddingModelSaathratriMock;

    /** Deterministic, non-zero embedding so the stored row and the mocked query embed to the same vector. */
    private static float[] sampleFloatsSaathratri(int dimension) {
        float[] vector = new float[dimension];
        for (int i = 0; i < dimension; i++) {
            vector[i] = 0.10f + ((i % 8) * 0.01f);
        }
        return vector;
    }

    private static CqlVector<Float> sampleCqlVectorSaathratri(int dimension) {
        float[] floats = sampleFloatsSaathratri(dimension);
        java.util.List<Float> values = new java.util.ArrayList<>(dimension);
        for (float f : floats) {
            values.add(f);
        }
        return CqlVector.newInstance(values);
    }

    @Test
    void aiSearchReturnsSemanticMatchesWhenEmbeddingModelIsAvailable() throws Exception {
        // Store a row whose nameEmbedding equals the query vector (ANN distance 0 -> top hit).
        cassTag.setNameEmbedding(sampleCqlVectorSaathratri(1536));
        cassTagRepository.save(cassTag);
        when(embeddingModelSaathratriMock.embed(anyString())).thenReturn(sampleFloatsSaathratri(1536));

        restCassTagMockMvc
            .perform(get(ENTITY_API_URL + "/ai-search").param("query", "find similar rows").param("limit", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[*].id").value(hasItem(cassTag.getId().toString())));
    }

    @Test
    void aiSearchReturnsEmptyForBlankQuery() throws Exception {
        restCassTagMockMvc
            .perform(get(ENTITY_API_URL + "/ai-search").param("query", "  ").param("limit", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void nameEmbeddingRoundTripsThroughTheVectorColumn() {
        // Verify a CqlVector<Float> written to the vector<float, 1536> column
        // reads back intact (the Cassandra vector type mapping, independent of ANN search).
        CqlVector<Float> embedding = sampleCqlVectorSaathratri(1536);
        cassTag.setNameEmbedding(embedding);
        cassTagRepository.save(cassTag);

        CassTag persisted = getPersistedCassTag(cassTag);
        assertThat(persisted.getNameEmbedding()).isEqualTo(embedding);
    }
}
