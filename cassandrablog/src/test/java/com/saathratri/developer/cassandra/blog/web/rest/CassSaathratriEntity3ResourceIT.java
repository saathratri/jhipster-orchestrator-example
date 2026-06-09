package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3Asserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity3Id;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntity3Repository;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity3DTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassSaathratriEntity3Mapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link CassSaathratriEntity3Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassSaathratriEntity3ResourceIT {

    private static final String DEFAULT_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final UUID DEFAULT_CREATED_TIME_ID = UUID.randomUUID();
    private static final UUID UPDATED_CREATED_TIME_ID = UUID.randomUUID();

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ENTITY_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_ENTITY_COST = new BigDecimal(2);

    private static final Long DEFAULT_DEPARTURE_DATE = 1L;
    private static final Long UPDATED_DEPARTURE_DATE = 2L;

    private static final Set<String> DEFAULT_TAGS = new TreeSet<String>();
    private static final Set<String> UPDATED_TAGS = new TreeSet<String>();

    static {
        DEFAULT_TAGS.add("AAAAAAAAAA");
        UPDATED_TAGS.add("BBBBBBBBBB");
    }

    private static final String ENTITY_API_URL = "/api/cass-saathratri-entity-3-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{entityType}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassSaathratriEntity3Repository cassSaathratriEntity3Repository;

    @Autowired
    private CassSaathratriEntity3Mapper cassSaathratriEntity3Mapper;

    @Autowired
    private MockMvc restCassSaathratriEntity3MockMvc;

    private CassSaathratriEntity3 cassSaathratriEntity3;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassSaathratriEntity3 createEntity() {
        CassSaathratriEntity3 cassSaathratriEntity3 = new CassSaathratriEntity3()
            .compositeId(new CassSaathratriEntity3Id().entityType(DEFAULT_ENTITY_TYPE).createdTimeId(DEFAULT_CREATED_TIME_ID))
            .entityName(DEFAULT_ENTITY_NAME)
            .entityDescription(DEFAULT_ENTITY_DESCRIPTION)
            .entityCost(DEFAULT_ENTITY_COST)
            .departureDate(DEFAULT_DEPARTURE_DATE)
            .tags(DEFAULT_TAGS);
        cassSaathratriEntity3.setCompositeId(new CassSaathratriEntity3Id(DEFAULT_ENTITY_TYPE, DEFAULT_CREATED_TIME_ID));
        return cassSaathratriEntity3;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassSaathratriEntity3 createUpdatedEntity() {
        CassSaathratriEntity3 cassSaathratriEntity3 = new CassSaathratriEntity3()
            .compositeId(new CassSaathratriEntity3Id().entityType(UPDATED_ENTITY_TYPE).createdTimeId(UPDATED_CREATED_TIME_ID))
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .tags(UPDATED_TAGS);
        cassSaathratriEntity3.setCompositeId(new CassSaathratriEntity3Id(UPDATED_ENTITY_TYPE, UPDATED_CREATED_TIME_ID));
        return cassSaathratriEntity3;
    }

    @BeforeEach
    public void initTest() {
        cassSaathratriEntity3Repository.deleteAll();
        cassSaathratriEntity3 = createEntity();
    }

    @Test
    void createCassSaathratriEntity3() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassSaathratriEntity3
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO = cassSaathratriEntity3Mapper.toDto(cassSaathratriEntity3);
        var returnedCassSaathratriEntity3DTO = om.readValue(
            restCassSaathratriEntity3MockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(cassSaathratriEntity3DTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassSaathratriEntity3DTO.class
        );

        // Validate the CassSaathratriEntity3 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassSaathratriEntity3 = cassSaathratriEntity3Mapper.toEntity(returnedCassSaathratriEntity3DTO);
        assertCassSaathratriEntity3UpdatableFieldsEquals(
            returnedCassSaathratriEntity3,
            getPersistedCassSaathratriEntity3(returnedCassSaathratriEntity3)
        );
    }

    @Test
    void createCassSaathratriEntity3WithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO = cassSaathratriEntity3Mapper.toDto(cassSaathratriEntity3);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassSaathratriEntity3MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity3DTO))
            )
            .andExpect(status().isCreated());

        // Validate the CassSaathratriEntity3 was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCassSaathratriEntity3s() throws Exception {
        // Initialize the database
        cassSaathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassSaathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassSaathratriEntity3Repository.save(cassSaathratriEntity3);

        // Get all the cassSaathratriEntity3List
        restCassSaathratriEntity3MockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.entityType").value(hasItem(cassSaathratriEntity3.getCompositeId().getEntityType().toString()))
            )
            .andExpect(
                jsonPath("$.[*].compositeId.createdTimeId").value(
                    hasItem(cassSaathratriEntity3.getCompositeId().getCreatedTimeId().toString())
                )
            )
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].entityDescription").value(hasItem(DEFAULT_ENTITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].entityCost").value(hasItem(sameNumber(DEFAULT_ENTITY_COST))))
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.intValue())))
            .andExpect(jsonPath("$.[*].tags").exists());
    }

    @Test
    void getCassSaathratriEntity3() throws Exception {
        // Initialize the database
        cassSaathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassSaathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassSaathratriEntity3Repository.save(cassSaathratriEntity3);

        // Get the cassSaathratriEntity3
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.compositeId.entityType").value(cassSaathratriEntity3.getCompositeId().getEntityType().toString()))
            .andExpect(jsonPath("$.compositeId.createdTimeId").value(cassSaathratriEntity3.getCompositeId().getCreatedTimeId().toString()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))
            .andExpect(jsonPath("$.entityDescription").value(DEFAULT_ENTITY_DESCRIPTION))
            .andExpect(jsonPath("$.entityCost").value(sameNumber(DEFAULT_ENTITY_COST)))
            .andExpect(jsonPath("$.departureDate").value(DEFAULT_DEPARTURE_DATE.intValue()))
            .andExpect(jsonPath("$.tags").exists());
    }

    @Test
    void getAllCassSaathratriEntity3sByCompositeKeySearches() throws Exception {
        // Initialize the database
        cassSaathratriEntity3Repository.save(cassSaathratriEntity3);

        // Exercise every generated composite-key search endpoint (partial-partition findAllBy
        // carry @AllowFiltering, clustering/comparison/findBy are plain valid queries), plus
        // /slice. A 200 confirms the derived CQL + parameter binding executes against real
        // Cassandra; body shape is covered by the get()/getAll() tests above.
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type").param(
                    "entityType",
                    String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType())
                )
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-pageable")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-by-composite-id-entity-type-and-composite-id-created-time-id")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-pageable")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-less-than-equal-pageable")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-pageable")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-and-composite-id-created-time-id-greater-than-equal-pageable")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-latest-by-composite-id-entity-type").param(
                    "entityType",
                    String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType())
                )
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity3MockMvc.perform(get(ENTITY_API_URL + "/slice").param("size", "20")).andExpect(status().isOk());
    }

    @Test
    void getNonExistingCassSaathratriEntity3() throws Exception {
        // Get the cassSaathratriEntity3
        restCassSaathratriEntity3MockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("entityType", String.valueOf(cassSaathratriEntity3.getCompositeId().getEntityType()))
                    .param("createdTimeId", String.valueOf(cassSaathratriEntity3.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassSaathratriEntity3() throws Exception {
        // Initialize the database
        cassSaathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassSaathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassSaathratriEntity3Repository.save(cassSaathratriEntity3);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity3
        CassSaathratriEntity3 updatedCassSaathratriEntity3 = cassSaathratriEntity3Repository
            .findById(cassSaathratriEntity3.getCompositeId())
            .orElseThrow();
        updatedCassSaathratriEntity3
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .tags(UPDATED_TAGS);
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO = cassSaathratriEntity3Mapper.toDto(updatedCassSaathratriEntity3);

        restCassSaathratriEntity3MockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{entityType}/{createdTimeId}",
                    cassSaathratriEntity3DTO.getCompositeId().getEntityType(),
                    cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity3DTO))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassSaathratriEntity3ToMatchAllProperties(updatedCassSaathratriEntity3);
    }

    @Test
    void putNonExistingCassSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity3.setCompositeId(new CassSaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the CassSaathratriEntity3
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO = cassSaathratriEntity3Mapper.toDto(cassSaathratriEntity3);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassSaathratriEntity3MockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{entityType}/{createdTimeId}",
                    cassSaathratriEntity3DTO.getCompositeId().getEntityType(),
                    cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity3.setCompositeId(new CassSaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));
        // Create the CassSaathratriEntity3
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO = cassSaathratriEntity3Mapper.toDto(cassSaathratriEntity3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity3MockMvc
            .perform(
                put(ENTITY_API_URL + "/{entityType}/{createdTimeId}", UUID.randomUUID().toString(), UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity3.setCompositeId(new CassSaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the CassSaathratriEntity3
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO = cassSaathratriEntity3Mapper.toDto(cassSaathratriEntity3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity3MockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity3DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassSaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassSaathratriEntity3WithPatch() throws Exception {
        // Initialize the database
        cassSaathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassSaathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassSaathratriEntity3Repository.save(cassSaathratriEntity3);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity3 using partial update
        CassSaathratriEntity3 partialUpdatedCassSaathratriEntity3 = new CassSaathratriEntity3();
        partialUpdatedCassSaathratriEntity3.setCompositeId(cassSaathratriEntity3.getCompositeId());

        partialUpdatedCassSaathratriEntity3
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .tags(UPDATED_TAGS);

        restCassSaathratriEntity3MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityType}/{createdTimeId}",
                    partialUpdatedCassSaathratriEntity3.getCompositeId().getEntityType(),
                    partialUpdatedCassSaathratriEntity3.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassSaathratriEntity3))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity3 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassSaathratriEntity3UpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassSaathratriEntity3, cassSaathratriEntity3),
            getPersistedCassSaathratriEntity3(cassSaathratriEntity3)
        );
    }

    @Test
    void fullUpdateCassSaathratriEntity3WithPatch() throws Exception {
        // Initialize the database
        cassSaathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassSaathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassSaathratriEntity3Repository.save(cassSaathratriEntity3);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity3 using partial update
        CassSaathratriEntity3 partialUpdatedCassSaathratriEntity3 = new CassSaathratriEntity3();
        partialUpdatedCassSaathratriEntity3.setCompositeId(cassSaathratriEntity3.getCompositeId());

        partialUpdatedCassSaathratriEntity3
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE)
            .tags(UPDATED_TAGS);

        restCassSaathratriEntity3MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityType}/{createdTimeId}",
                    partialUpdatedCassSaathratriEntity3.getCompositeId().getEntityType(),
                    partialUpdatedCassSaathratriEntity3.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassSaathratriEntity3))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity3 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassSaathratriEntity3UpdatableFieldsEquals(
            partialUpdatedCassSaathratriEntity3,
            getPersistedCassSaathratriEntity3(partialUpdatedCassSaathratriEntity3)
        );
    }

    @Test
    void patchNonExistingCassSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity3.setCompositeId(new CassSaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the CassSaathratriEntity3
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO = cassSaathratriEntity3Mapper.toDto(cassSaathratriEntity3);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassSaathratriEntity3MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityType}/{createdTimeId}",
                    cassSaathratriEntity3DTO.getCompositeId().getEntityType(),
                    cassSaathratriEntity3DTO.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntity3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity3.setCompositeId(new CassSaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the CassSaathratriEntity3
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO = cassSaathratriEntity3Mapper.toDto(cassSaathratriEntity3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity3MockMvc
            .perform(
                patch(ENTITY_API_URL + "/{entityType}/{createdTimeId}", UUID.randomUUID().toString(), UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntity3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassSaathratriEntity3() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity3.setCompositeId(new CassSaathratriEntity3Id(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the CassSaathratriEntity3
        CassSaathratriEntity3DTO cassSaathratriEntity3DTO = cassSaathratriEntity3Mapper.toDto(cassSaathratriEntity3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity3MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntity3DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassSaathratriEntity3 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassSaathratriEntity3() throws Exception {
        // Initialize the database
        cassSaathratriEntity3.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassSaathratriEntity3.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassSaathratriEntity3Repository.save(cassSaathratriEntity3);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassSaathratriEntity3
        restCassSaathratriEntity3MockMvc
            .perform(
                delete(
                    ENTITY_API_URL + "/{entityType}/{createdTimeId}",
                    cassSaathratriEntity3.getCompositeId().getEntityType(),
                    cassSaathratriEntity3.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassSaathratriEntity3Repository.count();
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

    protected CassSaathratriEntity3 getPersistedCassSaathratriEntity3(CassSaathratriEntity3 cassSaathratriEntity3) {
        return cassSaathratriEntity3Repository.findById(cassSaathratriEntity3.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedCassSaathratriEntity3ToMatchAllProperties(CassSaathratriEntity3 expectedCassSaathratriEntity3) {
        assertCassSaathratriEntity3AllPropertiesEquals(
            expectedCassSaathratriEntity3,
            getPersistedCassSaathratriEntity3(expectedCassSaathratriEntity3)
        );
    }

    protected void assertPersistedCassSaathratriEntity3ToMatchUpdatableProperties(CassSaathratriEntity3 expectedCassSaathratriEntity3) {
        assertCassSaathratriEntity3AllUpdatablePropertiesEquals(
            expectedCassSaathratriEntity3,
            getPersistedCassSaathratriEntity3(expectedCassSaathratriEntity3)
        );
    }
}
