package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2Asserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2;
import com.saathratri.developer.cassandra.blog.domain.CassSaathratriEntity2Id;
import com.saathratri.developer.cassandra.blog.repository.CassSaathratriEntity2Repository;
import com.saathratri.developer.cassandra.blog.service.dto.CassSaathratriEntity2DTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassSaathratriEntity2Mapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link CassSaathratriEntity2Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassSaathratriEntity2ResourceIT {

    private static final UUID DEFAULT_ENTITY_TYPE_ID = UUID.randomUUID();
    private static final UUID UPDATED_ENTITY_TYPE_ID = UUID.randomUUID();

    private static final Long DEFAULT_YEAR_OF_DATE_ADDED = 1L;
    private static final Long UPDATED_YEAR_OF_DATE_ADDED = 2L;

    private static final Long DEFAULT_ARRIVAL_DATE = 1L;
    private static final Long UPDATED_ARRIVAL_DATE = 2L;

    private static final UUID DEFAULT_BLOG_ID = UUID.randomUUID();
    private static final UUID UPDATED_BLOG_ID = UUID.randomUUID();

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ENTITY_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_ENTITY_COST = new BigDecimal(2);

    private static final Long DEFAULT_DEPARTURE_DATE = 1L;
    private static final Long UPDATED_DEPARTURE_DATE = 2L;

    private static final String ENTITY_API_URL = "/api/cass-saathratri-entity-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{entityTypeId}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassSaathratriEntity2Repository cassSaathratriEntity2Repository;

    @Autowired
    private CassSaathratriEntity2Mapper cassSaathratriEntity2Mapper;

    @Autowired
    private MockMvc restCassSaathratriEntity2MockMvc;

    private CassSaathratriEntity2 cassSaathratriEntity2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassSaathratriEntity2 createEntity() {
        CassSaathratriEntity2 cassSaathratriEntity2 = new CassSaathratriEntity2()
            .compositeId(
                new CassSaathratriEntity2Id()
                    .entityTypeId(DEFAULT_ENTITY_TYPE_ID)
                    .yearOfDateAdded(DEFAULT_YEAR_OF_DATE_ADDED)
                    .arrivalDate(DEFAULT_ARRIVAL_DATE)
                    .blogId(DEFAULT_BLOG_ID)
            )
            .entityName(DEFAULT_ENTITY_NAME)
            .entityDescription(DEFAULT_ENTITY_DESCRIPTION)
            .entityCost(DEFAULT_ENTITY_COST)
            .departureDate(DEFAULT_DEPARTURE_DATE);
        cassSaathratriEntity2.setCompositeId(
            new CassSaathratriEntity2Id(DEFAULT_ENTITY_TYPE_ID, DEFAULT_YEAR_OF_DATE_ADDED, DEFAULT_ARRIVAL_DATE, DEFAULT_BLOG_ID)
        );
        return cassSaathratriEntity2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassSaathratriEntity2 createUpdatedEntity() {
        CassSaathratriEntity2 cassSaathratriEntity2 = new CassSaathratriEntity2()
            .compositeId(
                new CassSaathratriEntity2Id()
                    .entityTypeId(UPDATED_ENTITY_TYPE_ID)
                    .yearOfDateAdded(UPDATED_YEAR_OF_DATE_ADDED)
                    .arrivalDate(UPDATED_ARRIVAL_DATE)
                    .blogId(UPDATED_BLOG_ID)
            )
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE);
        cassSaathratriEntity2.setCompositeId(
            new CassSaathratriEntity2Id(UPDATED_ENTITY_TYPE_ID, UPDATED_YEAR_OF_DATE_ADDED, UPDATED_ARRIVAL_DATE, UPDATED_BLOG_ID)
        );
        return cassSaathratriEntity2;
    }

    @BeforeEach
    public void initTest() {
        cassSaathratriEntity2Repository.deleteAll();
        cassSaathratriEntity2 = createEntity();
    }

    @Test
    void createCassSaathratriEntity2() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassSaathratriEntity2
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO = cassSaathratriEntity2Mapper.toDto(cassSaathratriEntity2);
        var returnedCassSaathratriEntity2DTO = om.readValue(
            restCassSaathratriEntity2MockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(cassSaathratriEntity2DTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassSaathratriEntity2DTO.class
        );

        // Validate the CassSaathratriEntity2 in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassSaathratriEntity2 = cassSaathratriEntity2Mapper.toEntity(returnedCassSaathratriEntity2DTO);
        assertCassSaathratriEntity2UpdatableFieldsEquals(
            returnedCassSaathratriEntity2,
            getPersistedCassSaathratriEntity2(returnedCassSaathratriEntity2)
        );
    }

    @Test
    void createCassSaathratriEntity2WithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO = cassSaathratriEntity2Mapper.toDto(cassSaathratriEntity2);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassSaathratriEntity2MockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity2DTO))
            )
            .andExpect(status().isCreated());

        // Validate the CassSaathratriEntity2 was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCassSaathratriEntity2s() throws Exception {
        // Initialize the database
        cassSaathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        cassSaathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        cassSaathratriEntity2Repository.save(cassSaathratriEntity2);

        // Get all the cassSaathratriEntity2List
        restCassSaathratriEntity2MockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                jsonPath("$.[*].compositeId.entityTypeId").value(
                    hasItem(cassSaathratriEntity2.getCompositeId().getEntityTypeId().toString())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.yearOfDateAdded").value(
                    hasItem(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded().intValue())
                )
            )
            .andExpect(
                jsonPath("$.[*].compositeId.arrivalDate").value(hasItem(cassSaathratriEntity2.getCompositeId().getArrivalDate().intValue()))
            )
            .andExpect(jsonPath("$.[*].compositeId.blogId").value(hasItem(cassSaathratriEntity2.getCompositeId().getBlogId().toString())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].entityDescription").value(hasItem(DEFAULT_ENTITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].entityCost").value(hasItem(sameNumber(DEFAULT_ENTITY_COST))))
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.intValue())));
    }

    @Test
    void getCassSaathratriEntity2() throws Exception {
        // Initialize the database
        cassSaathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        cassSaathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        cassSaathratriEntity2Repository.save(cassSaathratriEntity2);

        // Get the cassSaathratriEntity2
        restCassSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.compositeId.entityTypeId").value(cassSaathratriEntity2.getCompositeId().getEntityTypeId().toString()))
            .andExpect(
                jsonPath("$.compositeId.yearOfDateAdded").value(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded().intValue())
            )
            .andExpect(jsonPath("$.compositeId.arrivalDate").value(cassSaathratriEntity2.getCompositeId().getArrivalDate().intValue()))
            .andExpect(jsonPath("$.compositeId.blogId").value(cassSaathratriEntity2.getCompositeId().getBlogId().toString()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))
            .andExpect(jsonPath("$.entityDescription").value(DEFAULT_ENTITY_DESCRIPTION))
            .andExpect(jsonPath("$.entityCost").value(sameNumber(DEFAULT_ENTITY_COST)))
            .andExpect(jsonPath("$.departureDate").value(DEFAULT_DEPARTURE_DATE.intValue()));
    }

    @Test
    void getAllCassSaathratriEntity2sByCompositeKeySearches() throws Exception {
        // Initialize the database
        cassSaathratriEntity2Repository.save(cassSaathratriEntity2);

        // Exercise every generated composite-key search endpoint (partial-partition findAllBy
        // carry @AllowFiltering, clustering/comparison/findBy are plain valid queries), plus
        // /slice. A 200 confirms the derived CQL + parameter binding executes against real
        // Cassandra; body shape is covered by the get()/getAll() tests above.
        restCassSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-id").param(
                    "entityTypeId",
                    String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId())
                )
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-id-pageable")
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added")
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-pageable")
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-pageable"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-pageable"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-equal"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-less-than-equal-pageable"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-pageable"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-equal"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-greater-than-equal-pageable"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-pageable"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-equal"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-less-than-equal-pageable"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-pageable"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-equal"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-all-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date-and-composite-id-blog-id-greater-than-equal-pageable"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc
            .perform(
                get(
                    ENTITY_API_URL +
                        "/find-latest-by-composite-id-entity-type-id-and-composite-id-year-of-date-added-and-composite-id-arrival-date"
                )
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
            )
            .andExpect(status().isOk());
        restCassSaathratriEntity2MockMvc.perform(get(ENTITY_API_URL + "/slice").param("size", "20")).andExpect(status().isOk());
    }

    @Test
    void getNonExistingCassSaathratriEntity2() throws Exception {
        // Get the cassSaathratriEntity2
        restCassSaathratriEntity2MockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("entityTypeId", String.valueOf(cassSaathratriEntity2.getCompositeId().getEntityTypeId()))
                    .param("yearOfDateAdded", String.valueOf(cassSaathratriEntity2.getCompositeId().getYearOfDateAdded()))
                    .param("arrivalDate", String.valueOf(cassSaathratriEntity2.getCompositeId().getArrivalDate()))
                    .param("blogId", String.valueOf(cassSaathratriEntity2.getCompositeId().getBlogId()))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassSaathratriEntity2() throws Exception {
        // Initialize the database
        cassSaathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        cassSaathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        cassSaathratriEntity2Repository.save(cassSaathratriEntity2);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity2
        CassSaathratriEntity2 updatedCassSaathratriEntity2 = cassSaathratriEntity2Repository
            .findById(cassSaathratriEntity2.getCompositeId())
            .orElseThrow();
        updatedCassSaathratriEntity2
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE);
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO = cassSaathratriEntity2Mapper.toDto(updatedCassSaathratriEntity2);

        restCassSaathratriEntity2MockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    cassSaathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    cassSaathratriEntity2DTO.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity2DTO))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassSaathratriEntity2ToMatchAllProperties(updatedCassSaathratriEntity2);
    }

    @Test
    void putNonExistingCassSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity2.setCompositeId(
            new CassSaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );

        // Create the CassSaathratriEntity2
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO = cassSaathratriEntity2Mapper.toDto(cassSaathratriEntity2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassSaathratriEntity2MockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    cassSaathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    cassSaathratriEntity2DTO.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity2.setCompositeId(
            new CassSaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );
        // Create the CassSaathratriEntity2
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO = cassSaathratriEntity2Mapper.toDto(cassSaathratriEntity2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity2MockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    UUID.randomUUID(),
                    longCount.incrementAndGet(),
                    longCount.incrementAndGet(),
                    UUID.randomUUID()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity2.setCompositeId(
            new CassSaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );

        // Create the CassSaathratriEntity2
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO = cassSaathratriEntity2Mapper.toDto(cassSaathratriEntity2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity2MockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassSaathratriEntity2DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassSaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassSaathratriEntity2WithPatch() throws Exception {
        // Initialize the database
        cassSaathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        cassSaathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        cassSaathratriEntity2Repository.save(cassSaathratriEntity2);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity2 using partial update
        CassSaathratriEntity2 partialUpdatedCassSaathratriEntity2 = new CassSaathratriEntity2();
        partialUpdatedCassSaathratriEntity2.setCompositeId(cassSaathratriEntity2.getCompositeId());

        partialUpdatedCassSaathratriEntity2
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE);

        restCassSaathratriEntity2MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    partialUpdatedCassSaathratriEntity2.getCompositeId().getEntityTypeId(),
                    partialUpdatedCassSaathratriEntity2.getCompositeId().getYearOfDateAdded(),
                    partialUpdatedCassSaathratriEntity2.getCompositeId().getArrivalDate(),
                    partialUpdatedCassSaathratriEntity2.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassSaathratriEntity2))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity2 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassSaathratriEntity2UpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassSaathratriEntity2, cassSaathratriEntity2),
            getPersistedCassSaathratriEntity2(cassSaathratriEntity2)
        );
    }

    @Test
    void fullUpdateCassSaathratriEntity2WithPatch() throws Exception {
        // Initialize the database
        cassSaathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        cassSaathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        cassSaathratriEntity2Repository.save(cassSaathratriEntity2);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassSaathratriEntity2 using partial update
        CassSaathratriEntity2 partialUpdatedCassSaathratriEntity2 = new CassSaathratriEntity2();
        partialUpdatedCassSaathratriEntity2.setCompositeId(cassSaathratriEntity2.getCompositeId());

        partialUpdatedCassSaathratriEntity2
            .entityName(UPDATED_ENTITY_NAME)
            .entityDescription(UPDATED_ENTITY_DESCRIPTION)
            .entityCost(UPDATED_ENTITY_COST)
            .departureDate(UPDATED_DEPARTURE_DATE);

        restCassSaathratriEntity2MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    partialUpdatedCassSaathratriEntity2.getCompositeId().getEntityTypeId(),
                    partialUpdatedCassSaathratriEntity2.getCompositeId().getYearOfDateAdded(),
                    partialUpdatedCassSaathratriEntity2.getCompositeId().getArrivalDate(),
                    partialUpdatedCassSaathratriEntity2.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassSaathratriEntity2))
            )
            .andExpect(status().isOk());

        // Validate the CassSaathratriEntity2 in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassSaathratriEntity2UpdatableFieldsEquals(
            partialUpdatedCassSaathratriEntity2,
            getPersistedCassSaathratriEntity2(partialUpdatedCassSaathratriEntity2)
        );
    }

    @Test
    void patchNonExistingCassSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity2.setCompositeId(
            new CassSaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );

        // Create the CassSaathratriEntity2
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO = cassSaathratriEntity2Mapper.toDto(cassSaathratriEntity2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassSaathratriEntity2MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    cassSaathratriEntity2DTO.getCompositeId().getEntityTypeId(),
                    cassSaathratriEntity2DTO.getCompositeId().getYearOfDateAdded(),
                    cassSaathratriEntity2DTO.getCompositeId().getArrivalDate(),
                    cassSaathratriEntity2DTO.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntity2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity2.setCompositeId(
            new CassSaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );

        // Create the CassSaathratriEntity2
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO = cassSaathratriEntity2Mapper.toDto(cassSaathratriEntity2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity2MockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    UUID.randomUUID(),
                    longCount.incrementAndGet(),
                    longCount.incrementAndGet(),
                    UUID.randomUUID()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntity2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassSaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassSaathratriEntity2() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassSaathratriEntity2.setCompositeId(
            new CassSaathratriEntity2Id(
                UUID.randomUUID(),
                new java.util.Random().nextLong(),
                new java.util.Random().nextLong(),
                UUID.randomUUID()
            )
        );

        // Create the CassSaathratriEntity2
        CassSaathratriEntity2DTO cassSaathratriEntity2DTO = cassSaathratriEntity2Mapper.toDto(cassSaathratriEntity2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassSaathratriEntity2MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassSaathratriEntity2DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassSaathratriEntity2 in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassSaathratriEntity2() throws Exception {
        // Initialize the database
        cassSaathratriEntity2.getCompositeId().setEntityTypeId(UUID.randomUUID());
        cassSaathratriEntity2.getCompositeId().setYearOfDateAdded(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setArrivalDate(longCount.incrementAndGet());
        cassSaathratriEntity2.getCompositeId().setBlogId(UUID.randomUUID());
        cassSaathratriEntity2Repository.save(cassSaathratriEntity2);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassSaathratriEntity2
        restCassSaathratriEntity2MockMvc
            .perform(
                delete(
                    ENTITY_API_URL + "/{entityTypeId}/{yearOfDateAdded}/{arrivalDate}/{blogId}",
                    cassSaathratriEntity2.getCompositeId().getEntityTypeId(),
                    cassSaathratriEntity2.getCompositeId().getYearOfDateAdded(),
                    cassSaathratriEntity2.getCompositeId().getArrivalDate(),
                    cassSaathratriEntity2.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassSaathratriEntity2Repository.count();
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

    protected CassSaathratriEntity2 getPersistedCassSaathratriEntity2(CassSaathratriEntity2 cassSaathratriEntity2) {
        return cassSaathratriEntity2Repository.findById(cassSaathratriEntity2.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedCassSaathratriEntity2ToMatchAllProperties(CassSaathratriEntity2 expectedCassSaathratriEntity2) {
        assertCassSaathratriEntity2AllPropertiesEquals(
            expectedCassSaathratriEntity2,
            getPersistedCassSaathratriEntity2(expectedCassSaathratriEntity2)
        );
    }

    protected void assertPersistedCassSaathratriEntity2ToMatchUpdatableProperties(CassSaathratriEntity2 expectedCassSaathratriEntity2) {
        assertCassSaathratriEntity2AllUpdatablePropertiesEquals(
            expectedCassSaathratriEntity2,
            getPersistedCassSaathratriEntity2(expectedCassSaathratriEntity2)
        );
    }
}
