package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntityAsserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntityRepository;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntityDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassSaathratriEntityMapper;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CassSaathratriEntityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassSaathratriEntityResourceIT {

    private static final UUID DEFAULT_ENTITY_ID = UUID.randomUUID();
    private static final UUID UPDATED_ENTITY_ID = UUID.randomUUID();

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ENTITY_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_ENTITY_COST = new BigDecimal(2);

    private static final UUID DEFAULT_CREATED_ID = UUID.randomUUID();
    private static final UUID UPDATED_CREATED_ID = UUID.randomUUID();

    private static final UUID DEFAULT_CREATED_TIME_ID = UUID.randomUUID();
    private static final UUID UPDATED_CREATED_TIME_ID = UUID.randomUUID();

    private static final String ENTITY_API_URL = "/api/cass-saathratri-entities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{entityId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassSaathratriEntityRepository cassSaathratriEntityRepository;

    @Autowired
    private CassSaathratriEntityMapper cassSaathratriEntityMapper;

    @Autowired
    private MockMvc restCassSaathratriEntityMockMvc;

    private CassSaathratriEntity cassSaathratriEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassSaathratriEntity createEntity() {
        CassSaathratriEntity cassSaathratriEntity = new CassSaathratriEntity()

            .entityId(DEFAULT_ENTITY_ID)
            .entityName(DEFAULT_ENTITY_NAME)
            .entityDescription(DEFAULT_ENTITY_DESCRIPTION)
            .entityCost(DEFAULT_ENTITY_COST)
            .createdId(DEFAULT_CREATED_ID)
            .createdTimeId(DEFAULT_CREATED_TIME_ID);
        return cassSaathratriEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassSaathratriEntity createUpdatedEntity() {
        CassSaathratriEntity cassSaathratriEntity = new CassSaathratriEntity()

            .entityId(UPDATED_ENTITY_ID)
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .createdId(UPDATED_CREATED_ID)
            .createdTimeId(UPDATED_CREATED_TIME_ID);
        return cassSaathratriEntity;
    }

    @BeforeEach
    public void initTest() {
        cassSaathratriEntityRepository.deleteAll();
        cassSaathratriEntity = createEntity();
    }

    @Test
    void createCassSaathratriEntity() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassSaathratriEntity
        CassSaathratriEntityDTO cassSaathratriEntityDTO = cassSaathratriEntityMapper.toDto(cassSaathratriEntity);
        var returnedCassSaathratriEntityDTO = om.readValue(
            restCassSaathratriEntityMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(cassSaathratriEntityDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassSaathratriEntityDTO.class
        );

        // Validate the CassSaathratriEntity in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassSaathratriEntity = cassSaathratriEntityMapper.toEntity(returnedCassSaathratriEntityDTO);
        assertCassSaathratriEntityUpdatableFieldsEquals(
            returnedCassSaathratriEntity,
            getPersistedCassSaathratriEntity(returnedCassSaathratriEntity)
        );
    }

    @Test
    void createCassSaathratriEntityWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassSaathratriEntityDTO cassSaathratriEntityDTO = cassSaathratriEntityMapper.toDto(cassSaathratriEntity);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassSaathratriEntityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CassSaathratriEntity was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCassSaathratriEntities() throws Exception {
        // Initialize the database
        cassSaathratriEntity.setEntityId(UUID.randomUUID());
        cassSaathratriEntityRepository.save(cassSaathratriEntity);

        // Get all the cassSaathratriEntityList
        restCassSaathratriEntityMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(cassSaathratriEntity.getEntityId().toString())))

            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))

            .andExpect(jsonPath("$.[*].entityDescription").value(hasItem(DEFAULT_ENTITY_DESCRIPTION)))

            .andExpect(jsonPath("$.[*].entityCost").value(hasItem(sameNumber(DEFAULT_ENTITY_COST))))

            .andExpect(jsonPath("$.[*].createdId").value(hasItem(DEFAULT_CREATED_ID.toString())))

            .andExpect(jsonPath("$.[*].createdTimeId").value(hasItem(DEFAULT_CREATED_TIME_ID.toString())));
    }

    @Test
    void getCassSaathratriEntity() throws Exception {
        // Initialize the database
        cassSaathratriEntity.setEntityId(UUID.randomUUID());
        cassSaathratriEntityRepository.save(cassSaathratriEntity);

        // Get the cassSaathratriEntity
        restCassSaathratriEntityMockMvc
            .perform(get(ENTITY_API_URL_ID, cassSaathratriEntity.getEntityId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.entityId").value(cassSaathratriEntity.getEntityId().toString()))

            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))

            .andExpect(jsonPath("$.entityDescription").value(DEFAULT_ENTITY_DESCRIPTION))

            .andExpect(jsonPath("$.entityCost").value(sameNumber(DEFAULT_ENTITY_COST)))

            .andExpect(jsonPath("$.createdId").value(DEFAULT_CREATED_ID.toString()))

            .andExpect(jsonPath("$.createdTimeId").value(DEFAULT_CREATED_TIME_ID.toString()));
    }

    @Test
    void getNonExistingCassSaathratriEntity() throws Exception {
        // Get the cassSaathratriEntity
        restCassSaathratriEntityMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassSaathratriEntity() throws Exception {
        // Initialize the database
        cassSaathratriEntity.setEntityId(UUID.randomUUID());
        cassSaathratriEntityRepository.save(cassSaathratriEntity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity
        CassSaathratriEntity updatedCassSaathratriEntity = cassSaathratriEntityRepository
            .findById(cassSaathratriEntity.getEntityId())
            .orElseThrow();
        updatedCassSaathratriEntity

            .entityName(UPDATED_ENTITY_NAME)

            .entityDescription(UPDATED_ENTITY_DESCRIPTION)

            .entityCost(UPDATED_ENTITY_COST)

            .createdId(UPDATED_CREATED_ID)

            .createdTimeId(UPDATED_CREATED_TIME_ID);
        CassSaathratriEntityDTO cassSaathratriEntityDTO = cassSaathratriEntityMapper.toDto(updatedCassSaathratriEntity);

        restCassSaathratriEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassSaathratriEntityDTO.getEntityId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntityDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassSaathratriEntityToMatchAllProperties(updatedCassSaathratriEntity);
    }

    @Test
    void putNonExistingCassSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity.setEntityId(UUID.randomUUID());

        // Create the CassSaathratriEntity
        CassSaathratriEntityDTO cassSaathratriEntityDTO = cassSaathratriEntityMapper.toDto(cassSaathratriEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassSaathratriEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassSaathratriEntityDTO.getEntityId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity.setEntityId(UUID.randomUUID());
        // Create the CassSaathratriEntity
        CassSaathratriEntityDTO cassSaathratriEntityDTO = cassSaathratriEntityMapper.toDto(cassSaathratriEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity.setEntityId(UUID.randomUUID());

        // Create the CassSaathratriEntity
        CassSaathratriEntityDTO cassSaathratriEntityDTO = cassSaathratriEntityMapper.toDto(cassSaathratriEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntityMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassSaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassSaathratriEntityWithPatch() throws Exception {
        // Initialize the database
        cassSaathratriEntity.setEntityId(UUID.randomUUID());
        cassSaathratriEntityRepository.save(cassSaathratriEntity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity using partial update
        CassSaathratriEntity partialUpdatedCassSaathratriEntity = new CassSaathratriEntity();
        partialUpdatedCassSaathratriEntity.setEntityId(cassSaathratriEntity.getEntityId());

        partialUpdatedCassSaathratriEntity

            .entityName(UPDATED_ENTITY_NAME)

            .entityDescription(UPDATED_ENTITY_DESCRIPTION)

            .entityCost(UPDATED_ENTITY_COST)

            .createdId(UPDATED_CREATED_ID)

            .createdTimeId(UPDATED_CREATED_TIME_ID);

        restCassSaathratriEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassSaathratriEntity.getEntityId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassSaathratriEntity))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassSaathratriEntityUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassSaathratriEntity, cassSaathratriEntity),
            getPersistedCassSaathratriEntity(cassSaathratriEntity)
        );
    }

    @Test
    void fullUpdateCassSaathratriEntityWithPatch() throws Exception {
        // Initialize the database
        cassSaathratriEntity.setEntityId(UUID.randomUUID());
        cassSaathratriEntityRepository.save(cassSaathratriEntity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity using partial update
        CassSaathratriEntity partialUpdatedCassSaathratriEntity = new CassSaathratriEntity();
        partialUpdatedCassSaathratriEntity.setEntityId(cassSaathratriEntity.getEntityId());

        partialUpdatedCassSaathratriEntity

            .entityName(UPDATED_ENTITY_NAME)

            .entityDescription(UPDATED_ENTITY_DESCRIPTION)

            .entityCost(UPDATED_ENTITY_COST)

            .createdId(UPDATED_CREATED_ID)

            .createdTimeId(UPDATED_CREATED_TIME_ID);

        restCassSaathratriEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassSaathratriEntity.getEntityId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassSaathratriEntity))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassSaathratriEntityUpdatableFieldsEquals(
            partialUpdatedCassSaathratriEntity,
            getPersistedCassSaathratriEntity(partialUpdatedCassSaathratriEntity)
        );
    }

    @Test
    void patchNonExistingCassSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity.setEntityId(UUID.randomUUID());

        // Create the CassSaathratriEntity
        CassSaathratriEntityDTO cassSaathratriEntityDTO = cassSaathratriEntityMapper.toDto(cassSaathratriEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassSaathratriEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cassSaathratriEntityDTO.getEntityId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity.setEntityId(UUID.randomUUID());

        // Create the CassSaathratriEntity
        CassSaathratriEntityDTO cassSaathratriEntityDTO = cassSaathratriEntityMapper.toDto(cassSaathratriEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassSaathratriEntity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity.setEntityId(UUID.randomUUID());

        // Create the CassSaathratriEntity
        CassSaathratriEntityDTO cassSaathratriEntityDTO = cassSaathratriEntityMapper.toDto(cassSaathratriEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassSaathratriEntity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassSaathratriEntity() throws Exception {
        // Initialize the database
        cassSaathratriEntity.setEntityId(UUID.randomUUID());
        cassSaathratriEntityRepository.save(cassSaathratriEntity);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassSaathratriEntity
        restCassSaathratriEntityMockMvc
            .perform(delete(ENTITY_API_URL_ID, cassSaathratriEntity.getEntityId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassSaathratriEntityRepository.count();
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

    protected CassSaathratriEntity getPersistedCassSaathratriEntity(CassSaathratriEntity cassSaathratriEntity) {
        return cassSaathratriEntityRepository.findById(cassSaathratriEntity.getEntityId()).orElseThrow();
    }

    protected void assertPersistedCassSaathratriEntityToMatchAllProperties(CassSaathratriEntity expectedCassSaathratriEntity) {
        assertCassSaathratriEntityAllPropertiesEquals(
            expectedCassSaathratriEntity,
            getPersistedCassSaathratriEntity(expectedCassSaathratriEntity)
        );
    }

    protected void assertPersistedCassSaathratriEntityToMatchUpdatableProperties(CassSaathratriEntity expectedCassSaathratriEntity) {
        assertCassSaathratriEntityAllUpdatablePropertiesEquals(
            expectedCassSaathratriEntity,
            getPersistedCassSaathratriEntity(expectedCassSaathratriEntity)
        );
    }
}
