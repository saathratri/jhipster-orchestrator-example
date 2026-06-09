package com.saathratri.developer.cassandra.store.web.rest;

import static com.saathratri.developer.cassandra.store.domain.CassReportAsserts.*;
import static com.saathratri.developer.cassandra.store.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.store.IntegrationTest;
import com.saathratri.developer.cassandra.store.domain.CassReport;
import com.saathratri.developer.cassandra.store.repository.CassReportRepository;
import com.saathratri.developer.cassandra.store.service.dto.CassReportDTO;
import com.saathratri.developer.cassandra.store.service.mapper.CassReportMapper;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CassReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassReportResourceIT {

    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final UUID UPDATED_ID = UUID.randomUUID();

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_EXTENSION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATE_DATE = 1L;
    private static final Long UPDATED_CREATE_DATE = 2L;

    private static final ByteBuffer DEFAULT_FILE = ByteBuffer.wrap(TestUtil.createByteArray(1, "0"));
    private static final ByteBuffer UPDATED_FILE = ByteBuffer.wrap(TestUtil.createByteArray(1, "1"));
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final String ENTITY_API_URL = "/api/cass-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassReportRepository cassReportRepository;

    @Autowired
    private CassReportMapper cassReportMapper;

    @Autowired
    private MockMvc restCassReportMockMvc;

    private CassReport cassReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassReport createEntity() {
        CassReport cassReport = new CassReport()
            .id(DEFAULT_ID)
            .fileName(DEFAULT_FILE_NAME)
            .fileExtension(DEFAULT_FILE_EXTENSION)
            .createDate(DEFAULT_CREATE_DATE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .approved(DEFAULT_APPROVED);
        return cassReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassReport createUpdatedEntity() {
        CassReport cassReport = new CassReport()
            .id(UPDATED_ID)
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);
        return cassReport;
    }

    @BeforeEach
    public void initTest() {
        cassReportRepository.deleteAll();
        cassReport = createEntity();
    }

    @Test
    void createCassReport() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassReport
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);
        var returnedCassReportDTO = om.readValue(
            restCassReportMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassReportDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassReportDTO.class
        );

        // Validate the CassReport in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassReport = cassReportMapper.toEntity(returnedCassReportDTO);
        assertCassReportUpdatableFieldsEquals(returnedCassReport, getPersistedCassReport(returnedCassReport));
    }

    @Test
    void createCassReportWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassReportDTO)))
            .andExpect(status().isCreated());

        // Validate the CassReport was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkFileNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassReport.setFileName(null);

        // Create the CassReport, which fails.
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);

        restCassReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassReportDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFileExtensionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassReport.setFileExtension(null);

        // Create the CassReport, which fails.
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);

        restCassReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassReportDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCreateDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassReport.setCreateDate(null);

        // Create the CassReport, which fails.
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);

        restCassReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassReportDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCassReports() throws Exception {
        // Initialize the database
        cassReport.setId(UUID.randomUUID());
        cassReportRepository.save(cassReport);

        // Get all the cassReportList
        restCassReportMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cassReport.getId().toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileExtension").value(hasItem(DEFAULT_FILE_EXTENSION)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FILE.array()))))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())));
    }

    @Test
    void getCassReport() throws Exception {
        // Initialize the database
        cassReport.setId(UUID.randomUUID());
        cassReportRepository.save(cassReport);

        // Get the cassReport
        restCassReportMockMvc
            .perform(get(ENTITY_API_URL_ID, cassReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cassReport.getId().toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileExtension").value(DEFAULT_FILE_EXTENSION))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64.getEncoder().encodeToString(DEFAULT_FILE.array())))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()));
    }

    @Test
    void getNonExistingCassReport() throws Exception {
        // Get the cassReport
        restCassReportMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassReport() throws Exception {
        // Initialize the database
        cassReport.setId(UUID.randomUUID());
        cassReportRepository.save(cassReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassReport
        CassReport updatedCassReport = cassReportRepository.findById(cassReport.getId()).orElseThrow();
        updatedCassReport
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);
        CassReportDTO cassReportDTO = cassReportMapper.toDto(updatedCassReport);

        restCassReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassReportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassReportDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassReportToMatchAllProperties(updatedCassReport);
    }

    @Test
    void putNonExistingCassReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassReport.setId(UUID.randomUUID());

        // Create the CassReport
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassReportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassReport.setId(UUID.randomUUID());
        // Create the CassReport
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassReport.setId(UUID.randomUUID());

        // Create the CassReport
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassReportMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassReportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassReportWithPatch() throws Exception {
        // Initialize the database
        cassReport.setId(UUID.randomUUID());
        cassReportRepository.save(cassReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassReport using partial update
        CassReport partialUpdatedCassReport = new CassReport();
        partialUpdatedCassReport.setId(cassReport.getId());

        partialUpdatedCassReport
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);

        restCassReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassReport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassReport))
            )
            .andExpect(status().isOk());

        // Validate the CassReport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassReportUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassReport, cassReport),
            getPersistedCassReport(cassReport)
        );
    }

    @Test
    void fullUpdateCassReportWithPatch() throws Exception {
        // Initialize the database
        cassReport.setId(UUID.randomUUID());
        cassReportRepository.save(cassReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassReport using partial update
        CassReport partialUpdatedCassReport = new CassReport();
        partialUpdatedCassReport.setId(cassReport.getId());

        partialUpdatedCassReport
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);

        restCassReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassReport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassReport))
            )
            .andExpect(status().isOk());

        // Validate the CassReport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassReportUpdatableFieldsEquals(partialUpdatedCassReport, getPersistedCassReport(partialUpdatedCassReport));
    }

    @Test
    void patchNonExistingCassReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassReport.setId(UUID.randomUUID());

        // Create the CassReport
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cassReportDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassReport.setId(UUID.randomUUID());

        // Create the CassReport
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassReport.setId(UUID.randomUUID());

        // Create the CassReport
        CassReportDTO cassReportDTO = cassReportMapper.toDto(cassReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassReportMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cassReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassReport() throws Exception {
        // Initialize the database
        cassReport.setId(UUID.randomUUID());
        cassReportRepository.save(cassReport);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassReport
        restCassReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, cassReport.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassReportRepository.count();
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

    protected CassReport getPersistedCassReport(CassReport cassReport) {
        return cassReportRepository.findById(cassReport.getId()).orElseThrow();
    }

    protected void assertPersistedCassReportToMatchAllProperties(CassReport expectedCassReport) {
        assertCassReportAllPropertiesEquals(expectedCassReport, getPersistedCassReport(expectedCassReport));
    }

    protected void assertPersistedCassReportToMatchUpdatableProperties(CassReport expectedCassReport) {
        assertCassReportAllUpdatablePropertiesEquals(expectedCassReport, getPersistedCassReport(expectedCassReport));
    }
}
