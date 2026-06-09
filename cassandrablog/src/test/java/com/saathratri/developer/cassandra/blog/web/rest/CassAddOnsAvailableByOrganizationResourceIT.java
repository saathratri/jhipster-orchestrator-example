package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganizationAsserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganization;
import com.saathratri.developer.cassandra.blog.domain.CassAddOnsAvailableByOrganizationId;
import com.saathratri.developer.cassandra.blog.repository.CassAddOnsAvailableByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.dto.CassAddOnsAvailableByOrganizationDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassAddOnsAvailableByOrganizationMapper;
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
 * Integration tests for the {@link CassAddOnsAvailableByOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassAddOnsAvailableByOrganizationResourceIT {

    private static final UUID DEFAULT_ORGANIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORGANIZATION_ID = UUID.randomUUID();

    private static final String DEFAULT_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final UUID DEFAULT_ENTITY_ID = UUID.randomUUID();
    private static final UUID UPDATED_ENTITY_ID = UUID.randomUUID();

    private static final UUID DEFAULT_ADD_ON_ID = UUID.randomUUID();
    private static final UUID UPDATED_ADD_ON_ID = UUID.randomUUID();

    private static final String DEFAULT_ADD_ON_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ADD_ON_TYPE = "BBBBBBBBBB";

    private static final Map<String, String> DEFAULT_ADD_ON_DETAILS_TEXT = new HashMap<String, String>();
    private static final Map<String, String> UPDATED_ADD_ON_DETAILS_TEXT = new HashMap<String, String>();

    static {
        DEFAULT_ADD_ON_DETAILS_TEXT.put("AAAAAAAAAA", "1");
        UPDATED_ADD_ON_DETAILS_TEXT.put("AAAAAAAAAA", "2");
    }

    private static final Map<String, BigDecimal> DEFAULT_ADD_ON_DETAILS_DECIMAL = new HashMap<String, BigDecimal>();
    private static final Map<String, BigDecimal> UPDATED_ADD_ON_DETAILS_DECIMAL = new HashMap<String, BigDecimal>();

    static {
        DEFAULT_ADD_ON_DETAILS_DECIMAL.put("AAAAAAAAAA", new BigDecimal(1));
        UPDATED_ADD_ON_DETAILS_DECIMAL.put("AAAAAAAAAA", new BigDecimal(2));
    }

    private static final Map<String, Boolean> DEFAULT_ADD_ON_DETAILS_BOOLEAN = new HashMap<String, Boolean>();
    private static final Map<String, Boolean> UPDATED_ADD_ON_DETAILS_BOOLEAN = new HashMap<String, Boolean>();

    static {
        DEFAULT_ADD_ON_DETAILS_BOOLEAN.put("AAAAAAAAAA", false);
        UPDATED_ADD_ON_DETAILS_BOOLEAN.put("AAAAAAAAAA", true);
    }

    private static final Map<String, Long> DEFAULT_ADD_ON_DETAILS_BIG_INT = new HashMap<String, Long>();
    private static final Map<String, Long> UPDATED_ADD_ON_DETAILS_BIG_INT = new HashMap<String, Long>();

    static {
        DEFAULT_ADD_ON_DETAILS_BIG_INT.put("AAAAAAAAAA", 1L);
        UPDATED_ADD_ON_DETAILS_BIG_INT.put("AAAAAAAAAA", 2L);
    }

    private static final String ENTITY_API_URL = "/api/cass-add-ons-available-by-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassAddOnsAvailableByOrganizationRepository cassAddOnsAvailableByOrganizationRepository;

    @Autowired
    private CassAddOnsAvailableByOrganizationMapper cassAddOnsAvailableByOrganizationMapper;

    @Autowired
    private MockMvc restCassAddOnsAvailableByOrganizationMockMvc;

    private CassAddOnsAvailableByOrganization cassAddOnsAvailableByOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassAddOnsAvailableByOrganization createEntity() {
        CassAddOnsAvailableByOrganization cassAddOnsAvailableByOrganization = new CassAddOnsAvailableByOrganization()
            .compositeId(
                new CassAddOnsAvailableByOrganizationId()
                    .organizationId(DEFAULT_ORGANIZATION_ID)
                    .entityType(DEFAULT_ENTITY_TYPE)
                    .entityId(DEFAULT_ENTITY_ID)
                    .addOnId(DEFAULT_ADD_ON_ID)
            )
            .addOnType(DEFAULT_ADD_ON_TYPE)
            .addOnDetailsText(DEFAULT_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(DEFAULT_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(DEFAULT_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(DEFAULT_ADD_ON_DETAILS_BIG_INT);
        cassAddOnsAvailableByOrganization.setCompositeId(
            new CassAddOnsAvailableByOrganizationId(DEFAULT_ORGANIZATION_ID, DEFAULT_ENTITY_TYPE, DEFAULT_ENTITY_ID, DEFAULT_ADD_ON_ID)
        );
        return cassAddOnsAvailableByOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassAddOnsAvailableByOrganization createUpdatedEntity() {
        CassAddOnsAvailableByOrganization cassAddOnsAvailableByOrganization = new CassAddOnsAvailableByOrganization()
            .compositeId(
                new CassAddOnsAvailableByOrganizationId()
                    .organizationId(UPDATED_ORGANIZATION_ID)
                    .entityType(UPDATED_ENTITY_TYPE)
                    .entityId(UPDATED_ENTITY_ID)
                    .addOnId(UPDATED_ADD_ON_ID)
            )
            .addOnType(UPDATED_ADD_ON_TYPE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);
        cassAddOnsAvailableByOrganization.setCompositeId(
            new CassAddOnsAvailableByOrganizationId(UPDATED_ORGANIZATION_ID, UPDATED_ENTITY_TYPE, UPDATED_ENTITY_ID, UPDATED_ADD_ON_ID)
        );
        return cassAddOnsAvailableByOrganization;
    }

    @BeforeEach
    public void initTest() {
        cassAddOnsAvailableByOrganizationRepository.deleteAll();
        cassAddOnsAvailableByOrganization = createEntity();
    }

    @Test
    void createCassAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassAddOnsAvailableByOrganization
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationMapper.toDto(
            cassAddOnsAvailableByOrganization
        );
        var returnedCassAddOnsAvailableByOrganizationDTO = om.readValue(
            restCassAddOnsAvailableByOrganizationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(cassAddOnsAvailableByOrganizationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassAddOnsAvailableByOrganizationDTO.class
        );

        // Validate the CassAddOnsAvailableByOrganization in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassAddOnsAvailableByOrganization = cassAddOnsAvailableByOrganizationMapper.toEntity(
            returnedCassAddOnsAvailableByOrganizationDTO
        );
        assertCassAddOnsAvailableByOrganizationUpdatableFieldsEquals(
            returnedCassAddOnsAvailableByOrganization,
            getPersistedCassAddOnsAvailableByOrganization(returnedCassAddOnsAvailableByOrganization)
        );
    }

    @Test
    void createCassAddOnsAvailableByOrganizationWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationMapper.toDto(
            cassAddOnsAvailableByOrganization
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassAddOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CassAddOnsAvailableByOrganization was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCassAddOnsAvailableByOrganizations() throws Exception {
        // Initialize the database
        cassAddOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        cassAddOnsAvailableByOrganizationRepository.save(cassAddOnsAvailableByOrganization);

        // Get all the cassAddOnsAvailableByOrganizationList
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.entityType").value(
                    hasItem(cassAddOnsAvailableByOrganization.getCompositeId().getEntityType().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.entityId").value(
                    hasItem(cassAddOnsAvailableByOrganization.getCompositeId().getEntityId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.addOnId").value(
                    hasItem(cassAddOnsAvailableByOrganization.getCompositeId().getAddOnId().toString())
                )
            )
            .andExpect(jsonPath("$.[*].addOnType").value(hasItem(DEFAULT_ADD_ON_TYPE)))
            .andExpect(jsonPath("$.[*].addOnDetailsText").exists())
            .andExpect(jsonPath("$.[*].addOnDetailsDecimal").exists())
            .andExpect(jsonPath("$.[*].addOnDetailsBoolean").exists())
            .andExpect(jsonPath("$.[*].addOnDetailsBigInt").exists());
    }

    @Test
    void getCassAddOnsAvailableByOrganization() throws Exception {
        // Initialize the database
        cassAddOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        cassAddOnsAvailableByOrganizationRepository.save(cassAddOnsAvailableByOrganization);

        // Get the cassAddOnsAvailableByOrganization
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("organizationId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId()))
                    .param("entityType", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityType()))
                    .param("entityId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityId()))
                    .param("addOnId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getAddOnId()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.compositeId.organizationId").value(
                    cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId().toString()
                )
            )
            .andExpect(
                jsonPath("$.compositeId.entityType").value(cassAddOnsAvailableByOrganization.getCompositeId().getEntityType().toString())
            )
            .andExpect(
                jsonPath("$.compositeId.entityId").value(cassAddOnsAvailableByOrganization.getCompositeId().getEntityId().toString())
            )
            .andExpect(jsonPath("$.compositeId.addOnId").value(cassAddOnsAvailableByOrganization.getCompositeId().getAddOnId().toString()))
            .andExpect(jsonPath("$.addOnType").value(DEFAULT_ADD_ON_TYPE))
            .andExpect(jsonPath("$.addOnDetailsText").exists())
            .andExpect(jsonPath("$.addOnDetailsDecimal").exists())
            .andExpect(jsonPath("$.addOnDetailsBoolean").exists())
            .andExpect(jsonPath("$.addOnDetailsBigInt").exists());
    }

    @Test
    void getAllCassAddOnsAvailableByOrganizationsByCompositeKeySearches() throws Exception {
        // Initialize the database
        cassAddOnsAvailableByOrganizationRepository.save(cassAddOnsAvailableByOrganization);

        // Exercise every generated composite-key search endpoint (partial-partition findAllBy
        // carry @AllowFiltering, clustering/comparison/findBy are plain valid queries), plus
        // /slice. A 200 confirms the derived CQL + parameter binding executes against real
        // Cassandra; body shape is covered by the get()/getAll() tests above.
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id").param(
                    "organizationId",
                    String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId())
                )
            )
            .andExpect(status().isOk());
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-pageable")
                    .param("organizationId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-entity-type")
                    .param("organizationId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId()))
                    .param("entityType", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityType()))
            )
            .andExpect(status().isOk());
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-entity-type-pageable")
                    .param("organizationId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId()))
                    .param("entityType", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityType()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id")
                    .param("organizationId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId()))
                    .param("entityType", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityType()))
                    .param("entityId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityId()))
            )
            .andExpect(status().isOk());
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-pageable"
                )
                    .param("organizationId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId()))
                    .param("entityType", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityType()))
                    .param("entityId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-by-composite-id-organization-id-and-composite-id-entity-type-and-composite-id-entity-id-and-composite-id-add-on-id"
                )
                    .param("organizationId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId()))
                    .param("entityType", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityType()))
                    .param("entityId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityId()))
                    .param("addOnId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getAddOnId()))
            )
            .andExpect(status().isOk());
        restCassAddOnsAvailableByOrganizationMockMvc.perform(get(ENTITY_API_URL + "/slice").param("size", "20")).andExpect(status().isOk());
    }

    @Test
    void getNonExistingCassAddOnsAvailableByOrganization() throws Exception {
        // Get the cassAddOnsAvailableByOrganization
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("organizationId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId()))
                    .param("entityType", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityType()))
                    .param("entityId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getEntityId()))
                    .param("addOnId", String.valueOf(cassAddOnsAvailableByOrganization.getCompositeId().getAddOnId()))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassAddOnsAvailableByOrganization() throws Exception {
        // Initialize the database
        cassAddOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        cassAddOnsAvailableByOrganizationRepository.save(cassAddOnsAvailableByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassAddOnsAvailableByOrganization
        CassAddOnsAvailableByOrganization updatedCassAddOnsAvailableByOrganization = cassAddOnsAvailableByOrganizationRepository
            .findById(cassAddOnsAvailableByOrganization.getCompositeId())
            .orElseThrow();
        updatedCassAddOnsAvailableByOrganization
            .addOnType(UPDATED_ADD_ON_TYPE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationMapper.toDto(
            updatedCassAddOnsAvailableByOrganization
        );

        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{organizationId}/{entityType}/{entityId}/{addOnId}",
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassAddOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassAddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassAddOnsAvailableByOrganizationToMatchAllProperties(updatedCassAddOnsAvailableByOrganization);
    }

    @Test
    void putNonExistingCassAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsAvailableByOrganization.setCompositeId(
            new CassAddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the CassAddOnsAvailableByOrganization
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationMapper.toDto(
            cassAddOnsAvailableByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{organizationId}/{entityType}/{entityId}/{addOnId}",
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassAddOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassAddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsAvailableByOrganization.setCompositeId(
            new CassAddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );
        // Create the CassAddOnsAvailableByOrganization
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationMapper.toDto(
            cassAddOnsAvailableByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{organizationId}/{entityType}/{entityId}/{addOnId}",
                    UUID.randomUUID(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID(),
                    UUID.randomUUID()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassAddOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassAddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsAvailableByOrganization.setCompositeId(
            new CassAddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the CassAddOnsAvailableByOrganization
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationMapper.toDto(
            cassAddOnsAvailableByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassAddOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassAddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassAddOnsAvailableByOrganizationWithPatch() throws Exception {
        // Initialize the database
        cassAddOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        cassAddOnsAvailableByOrganizationRepository.save(cassAddOnsAvailableByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassAddOnsAvailableByOrganization using partial update
        CassAddOnsAvailableByOrganization partialUpdatedCassAddOnsAvailableByOrganization = new CassAddOnsAvailableByOrganization();
        partialUpdatedCassAddOnsAvailableByOrganization.setCompositeId(cassAddOnsAvailableByOrganization.getCompositeId());

        partialUpdatedCassAddOnsAvailableByOrganization
            .addOnType(UPDATED_ADD_ON_TYPE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);

        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{entityType}/{entityId}/{addOnId}",
                    partialUpdatedCassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId(),
                    partialUpdatedCassAddOnsAvailableByOrganization.getCompositeId().getEntityType(),
                    partialUpdatedCassAddOnsAvailableByOrganization.getCompositeId().getEntityId(),
                    partialUpdatedCassAddOnsAvailableByOrganization.getCompositeId().getAddOnId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassAddOnsAvailableByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the CassAddOnsAvailableByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassAddOnsAvailableByOrganizationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassAddOnsAvailableByOrganization, cassAddOnsAvailableByOrganization),
            getPersistedCassAddOnsAvailableByOrganization(cassAddOnsAvailableByOrganization)
        );
    }

    @Test
    void fullUpdateCassAddOnsAvailableByOrganizationWithPatch() throws Exception {
        // Initialize the database
        cassAddOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        cassAddOnsAvailableByOrganizationRepository.save(cassAddOnsAvailableByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassAddOnsAvailableByOrganization using partial update
        CassAddOnsAvailableByOrganization partialUpdatedCassAddOnsAvailableByOrganization = new CassAddOnsAvailableByOrganization();
        partialUpdatedCassAddOnsAvailableByOrganization.setCompositeId(cassAddOnsAvailableByOrganization.getCompositeId());

        partialUpdatedCassAddOnsAvailableByOrganization
            .addOnType(UPDATED_ADD_ON_TYPE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);

        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{entityType}/{entityId}/{addOnId}",
                    partialUpdatedCassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId(),
                    partialUpdatedCassAddOnsAvailableByOrganization.getCompositeId().getEntityType(),
                    partialUpdatedCassAddOnsAvailableByOrganization.getCompositeId().getEntityId(),
                    partialUpdatedCassAddOnsAvailableByOrganization.getCompositeId().getAddOnId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassAddOnsAvailableByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the CassAddOnsAvailableByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassAddOnsAvailableByOrganizationUpdatableFieldsEquals(
            partialUpdatedCassAddOnsAvailableByOrganization,
            getPersistedCassAddOnsAvailableByOrganization(partialUpdatedCassAddOnsAvailableByOrganization)
        );
    }

    @Test
    void patchNonExistingCassAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsAvailableByOrganization.setCompositeId(
            new CassAddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the CassAddOnsAvailableByOrganization
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationMapper.toDto(
            cassAddOnsAvailableByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{entityType}/{entityId}/{addOnId}",
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityType(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getEntityId(),
                    cassAddOnsAvailableByOrganizationDTO.getCompositeId().getAddOnId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassAddOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassAddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsAvailableByOrganization.setCompositeId(
            new CassAddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the CassAddOnsAvailableByOrganization
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationMapper.toDto(
            cassAddOnsAvailableByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{entityType}/{entityId}/{addOnId}",
                    UUID.randomUUID(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID(),
                    UUID.randomUUID()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassAddOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassAddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassAddOnsAvailableByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsAvailableByOrganization.setCompositeId(
            new CassAddOnsAvailableByOrganizationId(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID())
        );

        // Create the CassAddOnsAvailableByOrganization
        CassAddOnsAvailableByOrganizationDTO cassAddOnsAvailableByOrganizationDTO = cassAddOnsAvailableByOrganizationMapper.toDto(
            cassAddOnsAvailableByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassAddOnsAvailableByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassAddOnsAvailableByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassAddOnsAvailableByOrganization() throws Exception {
        // Initialize the database
        cassAddOnsAvailableByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityType(UUID.randomUUID().toString());
        cassAddOnsAvailableByOrganization.getCompositeId().setEntityId(UUID.randomUUID());
        cassAddOnsAvailableByOrganization.getCompositeId().setAddOnId(UUID.randomUUID());
        cassAddOnsAvailableByOrganizationRepository.save(cassAddOnsAvailableByOrganization);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassAddOnsAvailableByOrganization
        restCassAddOnsAvailableByOrganizationMockMvc
            .perform(
                delete(
                    ENTITY_API_URL + "/{organizationId}/{entityType}/{entityId}/{addOnId}",
                    cassAddOnsAvailableByOrganization.getCompositeId().getOrganizationId(),
                    cassAddOnsAvailableByOrganization.getCompositeId().getEntityType(),
                    cassAddOnsAvailableByOrganization.getCompositeId().getEntityId(),
                    cassAddOnsAvailableByOrganization.getCompositeId().getAddOnId()
                )
                    .with(csrf())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassAddOnsAvailableByOrganizationRepository.count();
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

    protected CassAddOnsAvailableByOrganization getPersistedCassAddOnsAvailableByOrganization(
        CassAddOnsAvailableByOrganization cassAddOnsAvailableByOrganization
    ) {
        return cassAddOnsAvailableByOrganizationRepository.findById(cassAddOnsAvailableByOrganization.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedCassAddOnsAvailableByOrganizationToMatchAllProperties(
        CassAddOnsAvailableByOrganization expectedCassAddOnsAvailableByOrganization
    ) {
        assertCassAddOnsAvailableByOrganizationAllPropertiesEquals(
            expectedCassAddOnsAvailableByOrganization,
            getPersistedCassAddOnsAvailableByOrganization(expectedCassAddOnsAvailableByOrganization)
        );
    }

    protected void assertPersistedCassAddOnsAvailableByOrganizationToMatchUpdatableProperties(
        CassAddOnsAvailableByOrganization expectedCassAddOnsAvailableByOrganization
    ) {
        assertCassAddOnsAvailableByOrganizationAllUpdatablePropertiesEquals(
            expectedCassAddOnsAvailableByOrganization,
            getPersistedCassAddOnsAvailableByOrganization(expectedCassAddOnsAvailableByOrganization)
        );
    }
}
