package com.saathratri.developer.cassandra.blog.web.rest;

import static com.saathratri.developer.cassandra.blog.domain.CassBlogAsserts.*;
import static com.saathratri.developer.cassandra.blog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.blog.IntegrationTest;
import com.saathratri.developer.cassandra.blog.domain.CassBlog;
import com.saathratri.developer.cassandra.blog.domain.CassBlogId;
import com.saathratri.developer.cassandra.blog.repository.CassBlogRepository;
import com.saathratri.developer.cassandra.blog.service.dto.CassBlogDTO;
import com.saathratri.developer.cassandra.blog.service.mapper.CassBlogMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CassBlogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassBlogResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final UUID DEFAULT_BLOG_ID = UUID.randomUUID();
    private static final UUID UPDATED_BLOG_ID = UUID.randomUUID();

    private static final String DEFAULT_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_HANDLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cass-blogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{category}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassBlogRepository cassBlogRepository;

    @Autowired
    private CassBlogMapper cassBlogMapper;

    @Autowired
    private MockMvc restCassBlogMockMvc;

    private CassBlog cassBlog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassBlog createEntity() {
        CassBlog cassBlog = new CassBlog()
            .compositeId(new CassBlogId().category(DEFAULT_CATEGORY).blogId(DEFAULT_BLOG_ID))
            .handle(DEFAULT_HANDLE)
            .content(DEFAULT_CONTENT);
        cassBlog.setCompositeId(new CassBlogId(DEFAULT_CATEGORY, DEFAULT_BLOG_ID));
        return cassBlog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassBlog createUpdatedEntity() {
        CassBlog cassBlog = new CassBlog()
            .compositeId(new CassBlogId().category(UPDATED_CATEGORY).blogId(UPDATED_BLOG_ID))
            .handle(UPDATED_HANDLE)
            .content(UPDATED_CONTENT);
        cassBlog.setCompositeId(new CassBlogId(UPDATED_CATEGORY, UPDATED_BLOG_ID));
        return cassBlog;
    }

    @BeforeEach
    public void initTest() {
        cassBlogRepository.deleteAll();
        cassBlog = createEntity();
    }

    @Test
    void createCassBlog() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassBlog
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(cassBlog);
        var returnedCassBlogDTO = om.readValue(
            restCassBlogMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassBlogDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassBlogDTO.class
        );

        // Validate the CassBlog in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassBlog = cassBlogMapper.toEntity(returnedCassBlogDTO);
        assertCassBlogUpdatableFieldsEquals(returnedCassBlog, getPersistedCassBlog(returnedCassBlog));
    }

    @Test
    void createCassBlogWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(cassBlog);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassBlogMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassBlogDTO)))
            .andExpect(status().isCreated());

        // Validate the CassBlog was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkHandleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassBlog.setHandle(null);

        // Create the CassBlog, which fails.
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(cassBlog);

        restCassBlogMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassBlogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkContentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassBlog.setContent(null);

        // Create the CassBlog, which fails.
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(cassBlog);

        restCassBlogMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassBlogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCassBlogs() throws Exception {
        // Initialize the database
        cassBlog.getCompositeId().setCategory(UUID.randomUUID().toString());
        cassBlog.getCompositeId().setBlogId(UUID.randomUUID());
        cassBlogRepository.save(cassBlog);

        // Get all the cassBlogList
        restCassBlogMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            .andExpect(jsonPath("$.[*].compositeId.category").value(hasItem(cassBlog.getCompositeId().getCategory().toString())))

            .andExpect(jsonPath("$.[*].compositeId.blogId").value(hasItem(cassBlog.getCompositeId().getBlogId().toString())))

            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))

            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    void getCassBlog() throws Exception {
        // Initialize the database
        cassBlog.getCompositeId().setCategory(UUID.randomUUID().toString());
        cassBlog.getCompositeId().setBlogId(UUID.randomUUID());
        cassBlogRepository.save(cassBlog);

        // Get the cassBlog
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            .andExpect(jsonPath("$.compositeId.category").value(cassBlog.getCompositeId().getCategory().toString()))

            .andExpect(jsonPath("$.compositeId.blogId").value(cassBlog.getCompositeId().getBlogId().toString()))

            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE))

            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    void getAllCassBlogsByCompositeKeySearches() throws Exception {
        // Initialize the database
        cassBlogRepository.save(cassBlog);

        // Exercise every generated composite-key search endpoint (partial-partition findAllBy
        // carry @AllowFiltering, clustering/comparison/findBy are plain valid queries), plus
        // /slice. A 200 confirms the derived CQL + parameter binding executes against real
        // Cassandra; body shape is covered by the get()/getAll() tests above.
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category").param(
                    "category",
                    String.valueOf(cassBlog.getCompositeId().getCategory())
                )
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-pageable")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-by-composite-id-category-and-composite-id-blog-id")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-less-than")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-pageable")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-less-than-equal-pageable")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-pageable")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-all-by-composite-id-category-and-composite-id-blog-id-greater-than-equal-pageable")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
                    .param("size", "20")
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/find-latest-by-composite-id-category").param(
                    "category",
                    String.valueOf(cassBlog.getCompositeId().getCategory())
                )
            )
            .andExpect(status().isOk());
        restCassBlogMockMvc.perform(get(ENTITY_API_URL + "/slice").param("size", "20")).andExpect(status().isOk());
    }

    @Test
    void getNonExistingCassBlog() throws Exception {
        // Get the cassBlog
        restCassBlogMockMvc
            .perform(
                get(ENTITY_API_URL + "/get")
                    .param("category", String.valueOf(cassBlog.getCompositeId().getCategory()))
                    .param("blogId", String.valueOf(cassBlog.getCompositeId().getBlogId()))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassBlog() throws Exception {
        // Initialize the database
        cassBlog.getCompositeId().setCategory(UUID.randomUUID().toString());
        cassBlog.getCompositeId().setBlogId(UUID.randomUUID());
        cassBlogRepository.save(cassBlog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassBlog
        CassBlog updatedCassBlog = cassBlogRepository.findById(cassBlog.getCompositeId()).orElseThrow();
        updatedCassBlog

            .handle(UPDATED_HANDLE)

            .content(UPDATED_CONTENT);
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(updatedCassBlog);

        restCassBlogMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{category}/{blogId}",
                    cassBlogDTO.getCompositeId().getCategory(),
                    cassBlogDTO.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassBlogDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassBlogToMatchAllProperties(updatedCassBlog);
    }

    @Test
    void putNonExistingCassBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassBlog.setCompositeId(new CassBlogId(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the CassBlog
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(cassBlog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassBlogMockMvc
            .perform(
                put(
                    ENTITY_API_URL + "/{category}/{blogId}",
                    cassBlogDTO.getCompositeId().getCategory(),
                    cassBlogDTO.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassBlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassBlog.setCompositeId(new CassBlogId(UUID.randomUUID().toString(), UUID.randomUUID()));
        // Create the CassBlog
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(cassBlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassBlogMockMvc
            .perform(
                put(ENTITY_API_URL + "/{category}/{blogId}", UUID.randomUUID().toString(), UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassBlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassBlog.setCompositeId(new CassBlogId(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the CassBlog
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(cassBlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassBlogMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassBlogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassBlogWithPatch() throws Exception {
        // Initialize the database
        cassBlog.getCompositeId().setCategory(UUID.randomUUID().toString());
        cassBlog.getCompositeId().setBlogId(UUID.randomUUID());
        cassBlogRepository.save(cassBlog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassBlog using partial update
        CassBlog partialUpdatedCassBlog = new CassBlog();
        partialUpdatedCassBlog.setCompositeId(cassBlog.getCompositeId());

        partialUpdatedCassBlog

            .handle(UPDATED_HANDLE)

            .content(UPDATED_CONTENT);

        restCassBlogMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{category}/{blogId}",
                    partialUpdatedCassBlog.getCompositeId().getCategory(),
                    partialUpdatedCassBlog.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassBlog))
            )
            .andExpect(status().isOk());

        // Validate the CassBlog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassBlogUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCassBlog, cassBlog), getPersistedCassBlog(cassBlog));
    }

    @Test
    void fullUpdateCassBlogWithPatch() throws Exception {
        // Initialize the database
        cassBlog.getCompositeId().setCategory(UUID.randomUUID().toString());
        cassBlog.getCompositeId().setBlogId(UUID.randomUUID());
        cassBlogRepository.save(cassBlog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassBlog using partial update
        CassBlog partialUpdatedCassBlog = new CassBlog();
        partialUpdatedCassBlog.setCompositeId(cassBlog.getCompositeId());

        partialUpdatedCassBlog

            .handle(UPDATED_HANDLE)

            .content(UPDATED_CONTENT);

        restCassBlogMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{category}/{blogId}",
                    partialUpdatedCassBlog.getCompositeId().getCategory(),
                    partialUpdatedCassBlog.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassBlog))
            )
            .andExpect(status().isOk());

        // Validate the CassBlog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassBlogUpdatableFieldsEquals(partialUpdatedCassBlog, getPersistedCassBlog(partialUpdatedCassBlog));
    }

    @Test
    void patchNonExistingCassBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassBlog.setCompositeId(new CassBlogId(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the CassBlog
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(cassBlog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassBlogMockMvc
            .perform(
                patch(
                    ENTITY_API_URL + "/{category}/{blogId}",
                    cassBlogDTO.getCompositeId().getCategory(),
                    cassBlogDTO.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassBlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassBlog.setCompositeId(new CassBlogId(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the CassBlog
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(cassBlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassBlogMockMvc
            .perform(
                patch(ENTITY_API_URL + "/{category}/{blogId}", UUID.randomUUID().toString(), UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassBlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassBlog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassBlog.setCompositeId(new CassBlogId(UUID.randomUUID().toString(), UUID.randomUUID()));

        // Create the CassBlog
        CassBlogDTO cassBlogDTO = cassBlogMapper.toDto(cassBlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassBlogMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cassBlogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassBlog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassBlog() throws Exception {
        // Initialize the database
        cassBlog.getCompositeId().setCategory(UUID.randomUUID().toString());
        cassBlog.getCompositeId().setBlogId(UUID.randomUUID());
        cassBlogRepository.save(cassBlog);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassBlog
        restCassBlogMockMvc
            .perform(
                delete(
                    ENTITY_API_URL + "/{category}/{blogId}",
                    cassBlog.getCompositeId().getCategory(),
                    cassBlog.getCompositeId().getBlogId()
                )
                    .with(csrf())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassBlogRepository.count();
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

    protected CassBlog getPersistedCassBlog(CassBlog cassBlog) {
        return cassBlogRepository.findById(cassBlog.getCompositeId()).orElseThrow();
    }

    protected void assertPersistedCassBlogToMatchAllProperties(CassBlog expectedCassBlog) {
        assertCassBlogAllPropertiesEquals(expectedCassBlog, getPersistedCassBlog(expectedCassBlog));
    }

    protected void assertPersistedCassBlogToMatchUpdatableProperties(CassBlog expectedCassBlog) {
        assertCassBlogAllUpdatablePropertiesEquals(expectedCassBlog, getPersistedCassBlog(expectedCassBlog));
    }
}
