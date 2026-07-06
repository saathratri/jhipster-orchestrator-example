package com.saathratri.developer.psql.blog.web.rest;

import static com.saathratri.developer.psql.blog.domain.PsqlTagAsserts.*;
import static com.saathratri.developer.psql.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.psql.blog.IntegrationTest;
import com.saathratri.developer.psql.blog.domain.PsqlTag;
import com.saathratri.developer.psql.blog.repository.PsqlTagRepository;
import com.saathratri.developer.psql.blog.service.PsqlTagService;
import com.saathratri.developer.psql.blog.service.dto.PsqlTagDTO;
import com.saathratri.developer.psql.blog.service.mapper.PsqlTagMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PsqlTagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PsqlTagResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/psql-tags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PsqlTagRepository psqlTagRepository;

    @Autowired
    private PsqlTagMapper psqlTagMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPsqlTagMockMvc;

    private PsqlTag psqlTag;

    private PsqlTag insertedPsqlTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlTag createEntity() {
        return new PsqlTag().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlTag createUpdatedEntity() {
        return new PsqlTag().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    void initTest() {
        psqlTag = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPsqlTag != null) {
            psqlTagRepository.delete(insertedPsqlTag);
            insertedPsqlTag = null;
        }
    }

    @Test
    @Transactional
    void createPsqlTag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PsqlTag
        PsqlTagDTO psqlTagDTO = psqlTagMapper.toDto(psqlTag);
        var returnedPsqlTagDTO = om.readValue(
            restPsqlTagMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlTagDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PsqlTagDTO.class
        );

        // Validate the PsqlTag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPsqlTag = psqlTagMapper.toEntity(returnedPsqlTagDTO);
        assertPsqlTagUpdatableFieldsEquals(returnedPsqlTag, getPersistedPsqlTag(returnedPsqlTag));

        insertedPsqlTag = returnedPsqlTag;
    }

    @Test
    @Transactional
    void createPsqlTagWithExistingId() throws Exception {
        // Create the PsqlTag with an existing ID
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);
        PsqlTagDTO psqlTagDTO = psqlTagMapper.toDto(psqlTag);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPsqlTagMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PsqlTag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlTag.setName(null);

        // Create the PsqlTag, which fails.
        PsqlTagDTO psqlTagDTO = psqlTagMapper.toDto(psqlTag);

        restPsqlTagMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlTagDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPsqlTags() throws Exception {
        // Initialize the database
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);

        // Get all the psqlTagList
        restPsqlTagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psqlTag.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getPsqlTag() throws Exception {
        // Initialize the database
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);

        // Get the psqlTag
        restPsqlTagMockMvc
            .perform(get(ENTITY_API_URL_ID, psqlTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(psqlTag.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingPsqlTag() throws Exception {
        // Get the psqlTag
        restPsqlTagMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPsqlTag() throws Exception {
        // Initialize the database
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlTag
        PsqlTag updatedPsqlTag = psqlTagRepository.findById(psqlTag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPsqlTag are not directly saved in db
        em.detach(updatedPsqlTag);
        updatedPsqlTag.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        PsqlTagDTO psqlTagDTO = psqlTagMapper.toDto(updatedPsqlTag);

        restPsqlTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlTagDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlTagDTO))
            )
            .andExpect(status().isOk());

        // Validate the PsqlTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPsqlTagToMatchAllProperties(updatedPsqlTag);
    }

    @Test
    @Transactional
    void putNonExistingPsqlTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTag.setId(UUID.randomUUID());

        // Create the PsqlTag
        PsqlTagDTO psqlTagDTO = psqlTagMapper.toDto(psqlTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlTagDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPsqlTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTag.setId(UUID.randomUUID());

        // Create the PsqlTag
        PsqlTagDTO psqlTagDTO = psqlTagMapper.toDto(psqlTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlTagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPsqlTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTag.setId(UUID.randomUUID());

        // Create the PsqlTag
        PsqlTagDTO psqlTagDTO = psqlTagMapper.toDto(psqlTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlTagMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlTagDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePsqlTagWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlTag using partial update
        PsqlTag partialUpdatedPsqlTag = new PsqlTag();
        partialUpdatedPsqlTag.setId(psqlTag.getId());

        partialUpdatedPsqlTag.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restPsqlTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlTag.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlTag))
            )
            .andExpect(status().isOk());

        // Validate the PsqlTag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlTagUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPsqlTag, psqlTag), getPersistedPsqlTag(psqlTag));
    }

    @Test
    @Transactional
    void fullUpdatePsqlTagWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlTag using partial update
        PsqlTag partialUpdatedPsqlTag = new PsqlTag();
        partialUpdatedPsqlTag.setId(psqlTag.getId());

        partialUpdatedPsqlTag.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restPsqlTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlTag.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlTag))
            )
            .andExpect(status().isOk());

        // Validate the PsqlTag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlTagUpdatableFieldsEquals(partialUpdatedPsqlTag, getPersistedPsqlTag(partialUpdatedPsqlTag));
    }

    @Test
    @Transactional
    void patchNonExistingPsqlTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTag.setId(UUID.randomUUID());

        // Create the PsqlTag
        PsqlTagDTO psqlTagDTO = psqlTagMapper.toDto(psqlTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, psqlTagDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPsqlTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTag.setId(UUID.randomUUID());

        // Create the PsqlTag
        PsqlTagDTO psqlTagDTO = psqlTagMapper.toDto(psqlTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlTagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlTagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPsqlTag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlTag.setId(UUID.randomUUID());

        // Create the PsqlTag
        PsqlTagDTO psqlTagDTO = psqlTagMapper.toDto(psqlTag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlTagMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(psqlTagDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlTag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePsqlTag() throws Exception {
        // Initialize the database
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the psqlTag
        restPsqlTagMockMvc
            .perform(delete(ENTITY_API_URL_ID, psqlTag.getId().toString()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return psqlTagRepository.count();
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

    protected PsqlTag getPersistedPsqlTag(PsqlTag psqlTag) {
        return psqlTagRepository.findById(psqlTag.getId()).orElseThrow();
    }

    protected void assertPersistedPsqlTagToMatchAllProperties(PsqlTag expectedPsqlTag) {
        assertPsqlTagAllPropertiesEquals(expectedPsqlTag, getPersistedPsqlTag(expectedPsqlTag));
    }

    protected void assertPersistedPsqlTagToMatchUpdatableProperties(PsqlTag expectedPsqlTag) {
        assertPsqlTagAllUpdatablePropertiesEquals(expectedPsqlTag, getPersistedPsqlTag(expectedPsqlTag));
    }

    // ==================== Vector / AI-search integration tests (Saathratri) ====================

    private static final float[] DEFAULT_NAME_EMBEDDING = sampleVectorSaathratri(1536, 0.10f);

    private static final float[] DEFAULT_DESCRIPTION_EMBEDDING = sampleVectorSaathratri(1536, 0.10f);

    @MockitoBean
    private EmbeddingModel embeddingModelSaathratriMock;

    /**
     * Build a deterministic, non-zero embedding of the given dimension so it round-trips through the
     * <code>vector(n)</code> column and yields a well-defined cosine distance (a zero vector is undefined).
     */
    private static float[] sampleVectorSaathratri(int dimension, float base) {
        float[] vector = new float[dimension];
        for (int i = 0; i < dimension; i++) {
            vector[i] = base + ((i % 8) * 0.01f);
        }
        return vector;
    }

    /**
     * Format a float[] as a pgvector literal "[v1, v2, ...]" for the vector-search request body.
     */
    private static String vectorToPgStringSaathratri(float[] vector) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < vector.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(String.format(java.util.Locale.ROOT, "%.8f", vector[i]));
        }
        return sb.append("]").toString();
    }

    @Test
    @Transactional
    void vectorSearchByNameEmbeddingReturnsTheSimilarRow() throws Exception {
        // Persist a row whose nameEmbedding equals the query vector (cosine distance 0).
        psqlTag.setNameEmbedding(DEFAULT_NAME_EMBEDDING);
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);

        restPsqlTagMockMvc
            .perform(
                post(ENTITY_API_URL + "/vector-search/nameEmbedding")
                    .with(csrf())
                    .param("limit", "10")
                    .contentType(MediaType.TEXT_PLAIN)
                    .content(vectorToPgStringSaathratri(DEFAULT_NAME_EMBEDDING))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[*].id").value(hasItem(insertedPsqlTag.getId().toString())));
    }

    @Test
    @Transactional
    void vectorSearchByNameEmbeddingWithThresholdFiltersByDistance() throws Exception {
        psqlTag.setNameEmbedding(DEFAULT_NAME_EMBEDDING);
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);
        String queryVector = vectorToPgStringSaathratri(DEFAULT_NAME_EMBEDDING);

        // A generous threshold keeps the identical match (distance 0 < 1.0).
        restPsqlTagMockMvc
            .perform(
                post(ENTITY_API_URL + "/vector-search/nameEmbedding/threshold")
                    .with(csrf())
                    .param("maxDistance", "1.0")
                    .param("limit", "10")
                    .contentType(MediaType.TEXT_PLAIN)
                    .content(queryVector)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id").value(hasItem(insertedPsqlTag.getId().toString())));

        // The native query uses a strict "< maxDistance", so maxDistance 0.0 excludes even an identical match.
        restPsqlTagMockMvc
            .perform(
                post(ENTITY_API_URL + "/vector-search/nameEmbedding/threshold")
                    .with(csrf())
                    .param("maxDistance", "0.0")
                    .param("limit", "10")
                    .contentType(MediaType.TEXT_PLAIN)
                    .content(queryVector)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[*].id").value(not(hasItem(insertedPsqlTag.getId().toString()))));
    }

    @Test
    @Transactional
    void vectorSearchByDescriptionEmbeddingReturnsTheSimilarRow() throws Exception {
        // Persist a row whose descriptionEmbedding equals the query vector (cosine distance 0).
        psqlTag.setDescriptionEmbedding(DEFAULT_DESCRIPTION_EMBEDDING);
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);

        restPsqlTagMockMvc
            .perform(
                post(ENTITY_API_URL + "/vector-search/descriptionEmbedding")
                    .with(csrf())
                    .param("limit", "10")
                    .contentType(MediaType.TEXT_PLAIN)
                    .content(vectorToPgStringSaathratri(DEFAULT_DESCRIPTION_EMBEDDING))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[*].id").value(hasItem(insertedPsqlTag.getId().toString())));
    }

    @Test
    @Transactional
    void vectorSearchByDescriptionEmbeddingWithThresholdFiltersByDistance() throws Exception {
        psqlTag.setDescriptionEmbedding(DEFAULT_DESCRIPTION_EMBEDDING);
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);
        String queryVector = vectorToPgStringSaathratri(DEFAULT_DESCRIPTION_EMBEDDING);

        // A generous threshold keeps the identical match (distance 0 < 1.0).
        restPsqlTagMockMvc
            .perform(
                post(ENTITY_API_URL + "/vector-search/descriptionEmbedding/threshold")
                    .with(csrf())
                    .param("maxDistance", "1.0")
                    .param("limit", "10")
                    .contentType(MediaType.TEXT_PLAIN)
                    .content(queryVector)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id").value(hasItem(insertedPsqlTag.getId().toString())));

        // The native query uses a strict "< maxDistance", so maxDistance 0.0 excludes even an identical match.
        restPsqlTagMockMvc
            .perform(
                post(ENTITY_API_URL + "/vector-search/descriptionEmbedding/threshold")
                    .with(csrf())
                    .param("maxDistance", "0.0")
                    .param("limit", "10")
                    .contentType(MediaType.TEXT_PLAIN)
                    .content(queryVector)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[*].id").value(not(hasItem(insertedPsqlTag.getId().toString()))));
    }

    @Test
    @Transactional
    void aiSearchReturnsSemanticMatchesWhenEmbeddingModelIsAvailable() throws Exception {
        psqlTag.setNameEmbedding(DEFAULT_NAME_EMBEDDING);
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);
        // Mock the embedding model so the text query embeds to the same vector stored above
        // (cosine distance 0, well within the service's 0.8 threshold).
        when(embeddingModelSaathratriMock.embedForResponse(any())).thenReturn(
            new EmbeddingResponse(List.of(new Embedding(DEFAULT_NAME_EMBEDDING, 0)))
        );

        restPsqlTagMockMvc
            .perform(
                get(ENTITY_API_URL + "/ai-search")
                    .param("query", "find similar rows")
                    .param("limit", "10")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.[*].id").value(hasItem(insertedPsqlTag.getId().toString())));
    }

    @Test
    @Transactional
    void aiSearchReturnsEmptyForBlankQuery() throws Exception {
        restPsqlTagMockMvc
            .perform(
                get(ENTITY_API_URL + "/ai-search")
                    .param("query", "  ")
                    .param("limit", "10")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @Transactional
    void nameEmbeddingRoundTripsThroughTheVectorColumn() {
        // A float[] written through the entity persists into the vector(1536) column.
        // Read it back as text via native SQL (the pgvector text form is "[v1,v2,...]") and assert it
        // stored as a vector of the expected dimension. This verifies the column round-trip without
        // loading the entity, whose vector read goes through the PgVectorType converter.
        psqlTag.setNameEmbedding(DEFAULT_NAME_EMBEDDING);
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);

        Object storedVector = em
            .createNativeQuery("SELECT CAST(name_embedding AS text) FROM psql_tag WHERE id = :id")
            .setParameter("id", insertedPsqlTag.getId())
            .getSingleResult();

        assertThat(storedVector).isNotNull();
        String vectorText = storedVector.toString().trim();
        assertThat(vectorText).startsWith("[").endsWith("]");
        assertThat(vectorText.substring(1, vectorText.length() - 1).split(",")).hasSize(DEFAULT_NAME_EMBEDDING.length);
    }

    // ---- Embedding lifecycle: generated on create, regenerated on update / partial update ----

    @Autowired
    private PsqlTagService psqlTagServiceSaathratri;

    @Test
    @Transactional
    void serviceSaveGeneratesEmbeddingsFromSourceFields() {
        // The embedding model is stubbed, so save() must populate every vector field from its source text.
        when(embeddingModelSaathratriMock.embedForResponse(any())).thenReturn(
            new EmbeddingResponse(List.of(new Embedding(DEFAULT_NAME_EMBEDDING, 0)))
        );

        PsqlTagDTO saved = psqlTagServiceSaathratri.save(psqlTagMapper.toDto(psqlTag));
        insertedPsqlTag = psqlTagRepository.findById(saved.getId()).orElseThrow();

        assertThat(insertedPsqlTag.getNameEmbedding()).containsExactly(DEFAULT_NAME_EMBEDDING);
        assertThat(insertedPsqlTag.getDescriptionEmbedding()).containsExactly(DEFAULT_NAME_EMBEDDING);
    }

    @Test
    @Transactional
    void serviceUpdateRegeneratesEmbeddings() {
        psqlTag.setNameEmbedding(DEFAULT_NAME_EMBEDDING);
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);
        // The model now embeds to a DIFFERENT vector; update() must overwrite the stored one.
        float[] regenerated = sampleVectorSaathratri(1536, 0.20f);
        when(embeddingModelSaathratriMock.embedForResponse(any())).thenReturn(
            new EmbeddingResponse(List.of(new Embedding(regenerated, 0)))
        );

        psqlTagServiceSaathratri.update(psqlTagMapper.toDto(insertedPsqlTag));

        PsqlTag persisted = psqlTagRepository.findById(insertedPsqlTag.getId()).orElseThrow();
        assertThat(persisted.getNameEmbedding()).containsExactly(regenerated);
    }

    @Test
    @Transactional
    void servicePartialUpdateRegeneratesEmbeddings() {
        // Guards the PATCH staleness fix: without regeneration in partialUpdate, the previously
        // stored vector would survive a patch that changes its source text.
        psqlTag.setNameEmbedding(DEFAULT_NAME_EMBEDDING);
        insertedPsqlTag = psqlTagRepository.saveAndFlush(psqlTag);
        float[] regenerated = sampleVectorSaathratri(1536, 0.20f);
        when(embeddingModelSaathratriMock.embedForResponse(any())).thenReturn(
            new EmbeddingResponse(List.of(new Embedding(regenerated, 0)))
        );

        psqlTagServiceSaathratri.partialUpdate(psqlTagMapper.toDto(insertedPsqlTag)).orElseThrow();

        PsqlTag persisted = psqlTagRepository.findById(insertedPsqlTag.getId()).orElseThrow();
        assertThat(persisted.getNameEmbedding()).containsExactly(regenerated);
    }
}
