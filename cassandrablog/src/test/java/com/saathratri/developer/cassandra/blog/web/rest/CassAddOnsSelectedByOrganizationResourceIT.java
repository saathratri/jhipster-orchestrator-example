package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganizationAsserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganization;
import com.saathratri.developer.cassandra.blog.domain.CassAddOnsSelectedByOrganizationId;
import com.saathratri.developer.cassandra.blog.repository.CassAddOnsSelectedByOrganizationRepository;
import com.saathratri.developer.cassandra.blog.service.dto.CassAddOnsSelectedByOrganizationDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassAddOnsSelectedByOrganizationMapper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CassAddOnsSelectedByOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassAddOnsSelectedByOrganizationResourceIT {

    private static final UUID DEFAULT_ORGANIZATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORGANIZATION_ID = UUID.randomUUID();

    private static final Long DEFAULT_ARRIVAL_DATE = 1L;
    private static final Long UPDATED_ARRIVAL_DATE = 2L;

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final UUID DEFAULT_CREATED_TIME_ID = UUID.randomUUID();
    private static final UUID UPDATED_CREATED_TIME_ID = UUID.randomUUID();

    private static final Long DEFAULT_DEPARTURE_DATE = 1L;
    private static final Long UPDATED_DEPARTURE_DATE = 2L;

    private static final UUID DEFAULT_CUSTOMER_ID = UUID.randomUUID();
    private static final UUID UPDATED_CUSTOMER_ID = UUID.randomUUID();

    private static final String DEFAULT_CUSTOMER_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_UPDATED_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_UPDATED_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ESTIMATED_ARRIVAL_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ESTIMATED_ARRIVAL_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_TINY_URL_SHORT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TINY_URL_SHORT_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/cass-add-ons-selected-by-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{organizationId}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassAddOnsSelectedByOrganizationRepository cassAddOnsSelectedByOrganizationRepository;

    @Autowired
    private CassAddOnsSelectedByOrganizationMapper cassAddOnsSelectedByOrganizationMapper;

    @Autowired
    private MockMvc restCassAddOnsSelectedByOrganizationMockMvc;

    private CassAddOnsSelectedByOrganization cassAddOnsSelectedByOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassAddOnsSelectedByOrganization createEntity() {
        CassAddOnsSelectedByOrganization cassAddOnsSelectedByOrganization = new CassAddOnsSelectedByOrganization()
            .compositeId(
                new CassAddOnsSelectedByOrganizationId()
                    .organizationId(DEFAULT_ORGANIZATION_ID)
                    .arrivalDate(DEFAULT_ARRIVAL_DATE)
                    .accountNumber(DEFAULT_ACCOUNT_NUMBER)
                    .createdTimeId(DEFAULT_CREATED_TIME_ID)
            )
            .departureDate(DEFAULT_DEPARTURE_DATE)
            .customerId(DEFAULT_CUSTOMER_ID)
            .customerFirstName(DEFAULT_CUSTOMER_FIRST_NAME)
            .customerLastName(DEFAULT_CUSTOMER_LAST_NAME)
            .customerUpdatedEmail(DEFAULT_CUSTOMER_UPDATED_EMAIL)
            .customerUpdatedPhoneNumber(DEFAULT_CUSTOMER_UPDATED_PHONE_NUMBER)
            .customerEstimatedArrivalTime(DEFAULT_CUSTOMER_ESTIMATED_ARRIVAL_TIME)
            .tinyUrlShortCode(DEFAULT_TINY_URL_SHORT_CODE)
            .addOnDetailsText(DEFAULT_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(DEFAULT_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(DEFAULT_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(DEFAULT_ADD_ON_DETAILS_BIG_INT);
        cassAddOnsSelectedByOrganization.setCompositeId(
            new CassAddOnsSelectedByOrganizationId(
                DEFAULT_ORGANIZATION_ID,
                DEFAULT_ARRIVAL_DATE,
                DEFAULT_ACCOUNT_NUMBER,
                DEFAULT_CREATED_TIME_ID
            )
        );
        return cassAddOnsSelectedByOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassAddOnsSelectedByOrganization createUpdatedEntity() {
        CassAddOnsSelectedByOrganization cassAddOnsSelectedByOrganization = new CassAddOnsSelectedByOrganization()
            .compositeId(
                new CassAddOnsSelectedByOrganizationId()
                    .organizationId(UPDATED_ORGANIZATION_ID)
                    .arrivalDate(UPDATED_ARRIVAL_DATE)
                    .accountNumber(UPDATED_ACCOUNT_NUMBER)
                    .createdTimeId(UPDATED_CREATED_TIME_ID)
            )
            .departureDate(UPDATED_DEPARTURE_DATE)
            .customerId(UPDATED_CUSTOMER_ID)
            .customerFirstName(UPDATED_CUSTOMER_FIRST_NAME)
            .customerLastName(UPDATED_CUSTOMER_LAST_NAME)
            .customerUpdatedEmail(UPDATED_CUSTOMER_UPDATED_EMAIL)
            .customerUpdatedPhoneNumber(UPDATED_CUSTOMER_UPDATED_PHONE_NUMBER)
            .customerEstimatedArrivalTime(UPDATED_CUSTOMER_ESTIMATED_ARRIVAL_TIME)
            .tinyUrlShortCode(UPDATED_TINY_URL_SHORT_CODE)
            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)
            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)
            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)
            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);
        cassAddOnsSelectedByOrganization.setCompositeId(
            new CassAddOnsSelectedByOrganizationId(
                UPDATED_ORGANIZATION_ID,
                UPDATED_ARRIVAL_DATE,
                UPDATED_ACCOUNT_NUMBER,
                UPDATED_CREATED_TIME_ID
            )
        );
        return cassAddOnsSelectedByOrganization;
    }

    @BeforeEach
    public void initTest() {
        cassAddOnsSelectedByOrganizationRepository.deleteAll();
        cassAddOnsSelectedByOrganization = createEntity();
    }

    @Test
    void createCassAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassAddOnsSelectedByOrganization
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationMapper.toDto(
            cassAddOnsSelectedByOrganization
        );
        var returnedCassAddOnsSelectedByOrganizationDTO = om.readValue(
            restCassAddOnsSelectedByOrganizationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(cassAddOnsSelectedByOrganizationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassAddOnsSelectedByOrganizationDTO.class
        );

        // Validate the CassAddOnsSelectedByOrganization in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassAddOnsSelectedByOrganization = cassAddOnsSelectedByOrganizationMapper.toEntity(
            returnedCassAddOnsSelectedByOrganizationDTO
        );
        assertCassAddOnsSelectedByOrganizationUpdatableFieldsEquals(
            returnedCassAddOnsSelectedByOrganization,
            getPersistedCassAddOnsSelectedByOrganization(returnedCassAddOnsSelectedByOrganization)
        );
    }

    @Test
    void createCassAddOnsSelectedByOrganizationWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationMapper.toDto(
            cassAddOnsSelectedByOrganization
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassAddOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CassAddOnsSelectedByOrganization was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCassAddOnsSelectedByOrganizations() throws Exception {
        // Initialize the database
        cassAddOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassAddOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        cassAddOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassAddOnsSelectedByOrganizationRepository.save(cassAddOnsSelectedByOrganization);

        // Get all the cassAddOnsSelectedByOrganizationList
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            .andExpect(
                jsonPath("$.[*].compositeId.organizationId").value(
                    hasItem(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId().toString())
                )
            )

            .andExpect(
                jsonPath("$.[*].compositeId.arrivalDate").value(
                    hasItem(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate().intValue())
                )
            )

            .andExpect(
                jsonPath("$.[*].compositeId.accountNumber").value(
                    hasItem(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber().toString())
                )
            )

            .andExpect(
                jsonPath("$.[*].compositeId.createdTimeId").value(
                    hasItem(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId().toString())
                )
            )

            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.intValue())))

            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.toString())))

            .andExpect(jsonPath("$.[*].customerFirstName").value(hasItem(DEFAULT_CUSTOMER_FIRST_NAME)))

            .andExpect(jsonPath("$.[*].customerLastName").value(hasItem(DEFAULT_CUSTOMER_LAST_NAME)))

            .andExpect(jsonPath("$.[*].customerUpdatedEmail").value(hasItem(DEFAULT_CUSTOMER_UPDATED_EMAIL)))

            .andExpect(jsonPath("$.[*].customerUpdatedPhoneNumber").value(hasItem(DEFAULT_CUSTOMER_UPDATED_PHONE_NUMBER)))

            .andExpect(jsonPath("$.[*].customerEstimatedArrivalTime").value(hasItem(DEFAULT_CUSTOMER_ESTIMATED_ARRIVAL_TIME)))

            .andExpect(jsonPath("$.[*].tinyUrlShortCode").value(hasItem(DEFAULT_TINY_URL_SHORT_CODE)))

            .andExpect(jsonPath("$.[*].addOnDetailsText").exists())

            .andExpect(jsonPath("$.[*].addOnDetailsDecimal").exists())

            .andExpect(jsonPath("$.[*].addOnDetailsBoolean").exists())

            .andExpect(jsonPath("$.[*].addOnDetailsBigInt").exists());
    }

    @Test
    void getCassAddOnsSelectedByOrganization() throws Exception {
        // Initialize the database
        cassAddOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassAddOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        cassAddOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassAddOnsSelectedByOrganizationRepository.save(cassAddOnsSelectedByOrganization);

        // Get the cassAddOnsSelectedByOrganization
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            .andExpect(
                jsonPath("$.compositeId.organizationId").value(
                    cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId().toString()
                )
            )

            .andExpect(
                jsonPath("$.compositeId.arrivalDate").value(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate().intValue())
            )

            .andExpect(
                jsonPath("$.compositeId.accountNumber").value(
                    cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber().toString()
                )
            )

            .andExpect(
                jsonPath("$.compositeId.createdTimeId").value(
                    cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId().toString()
                )
            )

            .andExpect(jsonPath("$.departureDate").value(DEFAULT_DEPARTURE_DATE.intValue()))

            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.toString()))

            .andExpect(jsonPath("$.customerFirstName").value(DEFAULT_CUSTOMER_FIRST_NAME))

            .andExpect(jsonPath("$.customerLastName").value(DEFAULT_CUSTOMER_LAST_NAME))

            .andExpect(jsonPath("$.customerUpdatedEmail").value(DEFAULT_CUSTOMER_UPDATED_EMAIL))

            .andExpect(jsonPath("$.customerUpdatedPhoneNumber").value(DEFAULT_CUSTOMER_UPDATED_PHONE_NUMBER))

            .andExpect(jsonPath("$.customerEstimatedArrivalTime").value(DEFAULT_CUSTOMER_ESTIMATED_ARRIVAL_TIME))

            .andExpect(jsonPath("$.tinyUrlShortCode").value(DEFAULT_TINY_URL_SHORT_CODE))

            .andExpect(jsonPath("$.addOnDetailsText").exists())

            .andExpect(jsonPath("$.addOnDetailsDecimal").exists())

            .andExpect(jsonPath("$.addOnDetailsBoolean").exists())

            .andExpect(jsonPath("$.addOnDetailsBigInt").exists());
    }

    @Test
    void getAllCassAddOnsSelectedByOrganizationsByCompositeKeySearches() throws Exception {
        // Initialize the database
        cassAddOnsSelectedByOrganizationRepository.save(cassAddOnsSelectedByOrganization);

        // Exercise every generated composite-key search endpoint (partial-partition findAllBy
        // carry @AllowFiltering, clustering/comparison/findBy are plain valid queries), plus
        // /slice. A 200 confirms the derived CQL + parameter binding executes against real
        // Cassandra; body shape is covered by the get()/getAll() tests above.
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id").param(
                    "organizationId",
                    String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId())
                )
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-pageable")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-pageable")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-pageable")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal-pageable")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-pageable")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal-pageable")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-pageable"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-pageable"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal-pageable"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-pageable"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal-pageable"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-latest-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number"
                )
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
            )
            .andExpect(status().isOk());
        restCassAddOnsSelectedByOrganizationMockMvc.perform(get(ENTITY_API_URL + "/slice").param("size", "20")).andExpect(status().isOk());
    }

    @Test
    void getNonExistingCassAddOnsSelectedByOrganization() throws Exception {
        // Get the cassAddOnsSelectedByOrganization
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("organizationId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId()))
                    .param("arrivalDate", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate()))
                    .param("accountNumber", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber()))
                    .param("createdTimeId", String.valueOf(cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassAddOnsSelectedByOrganization() throws Exception {
        // Initialize the database
        cassAddOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassAddOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        cassAddOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassAddOnsSelectedByOrganizationRepository.save(cassAddOnsSelectedByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassAddOnsSelectedByOrganization
        CassAddOnsSelectedByOrganization updatedCassAddOnsSelectedByOrganization = cassAddOnsSelectedByOrganizationRepository
            .findById(cassAddOnsSelectedByOrganization.getCompositeId())
            .orElseThrow();
        updatedCassAddOnsSelectedByOrganization

            .departureDate(UPDATED_DEPARTURE_DATE)

            .customerId(UPDATED_CUSTOMER_ID)

            .customerFirstName(UPDATED_CUSTOMER_FIRST_NAME)

            .customerLastName(UPDATED_CUSTOMER_LAST_NAME)

            .customerUpdatedEmail(UPDATED_CUSTOMER_UPDATED_EMAIL)

            .customerUpdatedPhoneNumber(UPDATED_CUSTOMER_UPDATED_PHONE_NUMBER)

            .customerEstimatedArrivalTime(UPDATED_CUSTOMER_ESTIMATED_ARRIVAL_TIME)

            .tinyUrlShortCode(UPDATED_TINY_URL_SHORT_CODE)

            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)

            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)

            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)

            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationMapper.toDto(
            updatedCassAddOnsSelectedByOrganization
        );

        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassAddOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassAddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassAddOnsSelectedByOrganizationToMatchAllProperties(updatedCassAddOnsSelectedByOrganization);
    }

    @Test
    void putNonExistingCassAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsSelectedByOrganization.setCompositeId(
            new CassAddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );

        // Create the CassAddOnsSelectedByOrganization
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationMapper.toDto(
            cassAddOnsSelectedByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassAddOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassAddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsSelectedByOrganization.setCompositeId(
            new CassAddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );
        // Create the CassAddOnsSelectedByOrganization
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationMapper.toDto(
            cassAddOnsSelectedByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
                    UUID.randomUUID(),
                    longCount.incrementAndGet(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassAddOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassAddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsSelectedByOrganization.setCompositeId(
            new CassAddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );

        // Create the CassAddOnsSelectedByOrganization
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationMapper.toDto(
            cassAddOnsSelectedByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassAddOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassAddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassAddOnsSelectedByOrganizationWithPatch() throws Exception {
        // Initialize the database
        cassAddOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassAddOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        cassAddOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassAddOnsSelectedByOrganizationRepository.save(cassAddOnsSelectedByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassAddOnsSelectedByOrganization using partial update
        CassAddOnsSelectedByOrganization partialUpdatedCassAddOnsSelectedByOrganization = new CassAddOnsSelectedByOrganization();
        partialUpdatedCassAddOnsSelectedByOrganization.setCompositeId(cassAddOnsSelectedByOrganization.getCompositeId());

        partialUpdatedCassAddOnsSelectedByOrganization

            .departureDate(UPDATED_DEPARTURE_DATE)

            .customerId(UPDATED_CUSTOMER_ID)

            .customerFirstName(UPDATED_CUSTOMER_FIRST_NAME)

            .customerLastName(UPDATED_CUSTOMER_LAST_NAME)

            .customerUpdatedEmail(UPDATED_CUSTOMER_UPDATED_EMAIL)

            .customerUpdatedPhoneNumber(UPDATED_CUSTOMER_UPDATED_PHONE_NUMBER)

            .customerEstimatedArrivalTime(UPDATED_CUSTOMER_ESTIMATED_ARRIVAL_TIME)

            .tinyUrlShortCode(UPDATED_TINY_URL_SHORT_CODE)

            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)

            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)

            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)

            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);

        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
                    partialUpdatedCassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId(),
                    partialUpdatedCassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate(),
                    partialUpdatedCassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber(),
                    partialUpdatedCassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassAddOnsSelectedByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the CassAddOnsSelectedByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassAddOnsSelectedByOrganizationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassAddOnsSelectedByOrganization, cassAddOnsSelectedByOrganization),
            getPersistedCassAddOnsSelectedByOrganization(cassAddOnsSelectedByOrganization)
        );
    }

    @Test
    void fullUpdateCassAddOnsSelectedByOrganizationWithPatch() throws Exception {
        // Initialize the database
        cassAddOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassAddOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        cassAddOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassAddOnsSelectedByOrganizationRepository.save(cassAddOnsSelectedByOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassAddOnsSelectedByOrganization using partial update
        CassAddOnsSelectedByOrganization partialUpdatedCassAddOnsSelectedByOrganization = new CassAddOnsSelectedByOrganization();
        partialUpdatedCassAddOnsSelectedByOrganization.setCompositeId(cassAddOnsSelectedByOrganization.getCompositeId());

        partialUpdatedCassAddOnsSelectedByOrganization

            .departureDate(UPDATED_DEPARTURE_DATE)

            .customerId(UPDATED_CUSTOMER_ID)

            .customerFirstName(UPDATED_CUSTOMER_FIRST_NAME)

            .customerLastName(UPDATED_CUSTOMER_LAST_NAME)

            .customerUpdatedEmail(UPDATED_CUSTOMER_UPDATED_EMAIL)

            .customerUpdatedPhoneNumber(UPDATED_CUSTOMER_UPDATED_PHONE_NUMBER)

            .customerEstimatedArrivalTime(UPDATED_CUSTOMER_ESTIMATED_ARRIVAL_TIME)

            .tinyUrlShortCode(UPDATED_TINY_URL_SHORT_CODE)

            .addOnDetailsText(UPDATED_ADD_ON_DETAILS_TEXT)

            .addOnDetailsDecimal(UPDATED_ADD_ON_DETAILS_DECIMAL)

            .addOnDetailsBoolean(UPDATED_ADD_ON_DETAILS_BOOLEAN)

            .addOnDetailsBigInt(UPDATED_ADD_ON_DETAILS_BIG_INT);

        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
                    partialUpdatedCassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId(),
                    partialUpdatedCassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate(),
                    partialUpdatedCassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber(),
                    partialUpdatedCassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassAddOnsSelectedByOrganization))
            )
            .andExpect(status().isOk());

        // Validate the CassAddOnsSelectedByOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassAddOnsSelectedByOrganizationUpdatableFieldsEquals(
            partialUpdatedCassAddOnsSelectedByOrganization,
            getPersistedCassAddOnsSelectedByOrganization(partialUpdatedCassAddOnsSelectedByOrganization)
        );
    }

    @Test
    void patchNonExistingCassAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsSelectedByOrganization.setCompositeId(
            new CassAddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );

        // Create the CassAddOnsSelectedByOrganization
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationMapper.toDto(
            cassAddOnsSelectedByOrganization
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getOrganizationId(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getArrivalDate(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getAccountNumber(),
                    cassAddOnsSelectedByOrganizationDTO.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassAddOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassAddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsSelectedByOrganization.setCompositeId(
            new CassAddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );

        // Create the CassAddOnsSelectedByOrganization
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationMapper.toDto(
            cassAddOnsSelectedByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
                    UUID.randomUUID(),
                    longCount.incrementAndGet(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassAddOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassAddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassAddOnsSelectedByOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassAddOnsSelectedByOrganization.setCompositeId(
            new CassAddOnsSelectedByOrganizationId(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                UUID.randomUUID().toString(),
                UUID.randomUUID()
            )
        );

        // Create the CassAddOnsSelectedByOrganization
        CassAddOnsSelectedByOrganizationDTO cassAddOnsSelectedByOrganizationDTO = cassAddOnsSelectedByOrganizationMapper.toDto(
            cassAddOnsSelectedByOrganization
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassAddOnsSelectedByOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassAddOnsSelectedByOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassAddOnsSelectedByOrganization() throws Exception {
        // Initialize the database
        cassAddOnsSelectedByOrganization.getCompositeId().setOrganizationId(UUID.randomUUID());
        cassAddOnsSelectedByOrganization.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassAddOnsSelectedByOrganization.getCompositeId().setAccountNumber(UUID.randomUUID().toString());
        cassAddOnsSelectedByOrganization.getCompositeId().setCreatedTimeId(UUID.randomUUID());
        cassAddOnsSelectedByOrganizationRepository.save(cassAddOnsSelectedByOrganization);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassAddOnsSelectedByOrganization
        restCassAddOnsSelectedByOrganizationMockMvc
            .perform(
                delete(
                    ENTITY_API_URL + "/{organizationId}/{arrivalDate}/{accountNumber}/{createdTimeId}",
                    cassAddOnsSelectedByOrganization.getCompositeId().getOrganizationId(),
                    cassAddOnsSelectedByOrganization.getCompositeId().getArrivalDate(),
                    cassAddOnsSelectedByOrganization.getCompositeId().getAccountNumber(),
                    cassAddOnsSelectedByOrganization.getCompositeId().getCreatedTimeId()
                )
                    .with(csrf())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassAddOnsSelectedByOrganizationRepository.count();
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

    protected CassAddOnsSelectedByOrganization getPersistedCassAddOnsSelectedByOrganization(
        CassAddOnsSelectedByOrganization cassAddOnsSelectedByOrganization
    ) {
        return cassAddOnsSelectedByOrganizationRepository.findById(cassAddOnsSelectedByOrganization.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedCassAddOnsSelectedByOrganizationToMatchAllProperties(
        CassAddOnsSelectedByOrganization expectedCassAddOnsSelectedByOrganization
    ) {
        assertCassAddOnsSelectedByOrganizationAllPropertiesEquals(
            expectedCassAddOnsSelectedByOrganization,
            getPersistedCassAddOnsSelectedByOrganization(expectedCassAddOnsSelectedByOrganization)
        );
    }

    protected void assertPersistedCassAddOnsSelectedByOrganizationToMatchUpdatableProperties(
        CassAddOnsSelectedByOrganization expectedCassAddOnsSelectedByOrganization
    ) {
        assertCassAddOnsSelectedByOrganizationAllUpdatablePropertiesEquals(
            expectedCassAddOnsSelectedByOrganization,
            getPersistedCassAddOnsSelectedByOrganization(expectedCassAddOnsSelectedByOrganization)
        );
    }
}
