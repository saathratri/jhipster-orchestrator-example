package com.saathratri.developer.psql.store.web.rest;

import static com.saathratri.developer.psql.store.domain.PsqlReportAsserts.*;
import static com.saathratri.developer.psql.store.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.psql.store.IntegrationTest;
import com.saathratri.developer.psql.store.domain.PsqlReport;
import com.saathratri.developer.psql.store.repository.PsqlReportRepository;
import com.saathratri.developer.psql.store.service.dto.PsqlReportDTO;
import com.saathratri.developer.psql.store.service.mapper.PsqlReportMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PsqlReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PsqlReportResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_EXTENSION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final String ENTITY_API_URL = "/api/psql-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PsqlReportRepository psqlReportRepository;

    @Autowired
    private PsqlReportMapper psqlReportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPsqlReportMockMvc;

    private PsqlReport psqlReport;

    private PsqlReport insertedPsqlReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlReport createEntity() {
        return new PsqlReport()
            .fileName(DEFAULT_FILE_NAME)
            .fileExtension(DEFAULT_FILE_EXTENSION)
            .createDate(DEFAULT_CREATE_DATE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .approved(DEFAULT_APPROVED);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlReport createUpdatedEntity() {
        return new PsqlReport()
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);
    }

    @BeforeEach
    void initTest() {
        psqlReport = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPsqlReport != null) {
            psqlReportRepository.delete(insertedPsqlReport);
            insertedPsqlReport = null;
        }
    }

    @Test
    @Transactional
    void createPsqlReport() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PsqlReport
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);
        var returnedPsqlReportDTO = om.readValue(
            restPsqlReportMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlReportDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PsqlReportDTO.class
        );

        // Validate the PsqlReport in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPsqlReport = psqlReportMapper.toEntity(returnedPsqlReportDTO);
        assertPsqlReportUpdatableFieldsEquals(returnedPsqlReport, getPersistedPsqlReport(returnedPsqlReport));

        insertedPsqlReport = returnedPsqlReport;
    }

    @Test
    @Transactional
    void createPsqlReportWithExistingId() throws Exception {
        // Create the PsqlReport with an existing ID
        insertedPsqlReport = psqlReportRepository.saveAndFlush(psqlReport);
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPsqlReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlReportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PsqlReport in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFileNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlReport.setFileName(null);

        // Create the PsqlReport, which fails.
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);

        restPsqlReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlReportDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFileExtensionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlReport.setFileExtension(null);

        // Create the PsqlReport, which fails.
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);

        restPsqlReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlReportDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlReport.setCreateDate(null);

        // Create the PsqlReport, which fails.
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);

        restPsqlReportMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlReportDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPsqlReports() throws Exception {
        // Initialize the database
        insertedPsqlReport = psqlReportRepository.saveAndFlush(psqlReport);

        // Get all the psqlReportList
        restPsqlReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psqlReport.getId().toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileExtension").value(hasItem(DEFAULT_FILE_EXTENSION)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED)));
    }

    @Test
    @Transactional
    void getPsqlReport() throws Exception {
        // Initialize the database
        insertedPsqlReport = psqlReportRepository.saveAndFlush(psqlReport);

        // Get the psqlReport
        restPsqlReportMockMvc
            .perform(get(ENTITY_API_URL_ID, psqlReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(psqlReport.getId().toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileExtension").value(DEFAULT_FILE_EXTENSION))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64.getEncoder().encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED));
    }

    @Test
    @Transactional
    void getNonExistingPsqlReport() throws Exception {
        // Get the psqlReport
        restPsqlReportMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPsqlReport() throws Exception {
        // Initialize the database
        insertedPsqlReport = psqlReportRepository.saveAndFlush(psqlReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlReport
        PsqlReport updatedPsqlReport = psqlReportRepository.findById(psqlReport.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPsqlReport are not directly saved in db
        em.detach(updatedPsqlReport);
        updatedPsqlReport
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(updatedPsqlReport);

        restPsqlReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlReportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlReportDTO))
            )
            .andExpect(status().isOk());

        // Validate the PsqlReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPsqlReportToMatchAllProperties(updatedPsqlReport);
    }

    @Test
    @Transactional
    void putNonExistingPsqlReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlReport.setId(UUID.randomUUID());

        // Create the PsqlReport
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlReportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPsqlReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlReport.setId(UUID.randomUUID());

        // Create the PsqlReport
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPsqlReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlReport.setId(UUID.randomUUID());

        // Create the PsqlReport
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlReportMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlReportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePsqlReportWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlReport = psqlReportRepository.saveAndFlush(psqlReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlReport using partial update
        PsqlReport partialUpdatedPsqlReport = new PsqlReport();
        partialUpdatedPsqlReport.setId(psqlReport.getId());

        partialUpdatedPsqlReport
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);

        restPsqlReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlReport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlReport))
            )
            .andExpect(status().isOk());

        // Validate the PsqlReport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlReportUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPsqlReport, psqlReport),
            getPersistedPsqlReport(psqlReport)
        );
    }

    @Test
    @Transactional
    void fullUpdatePsqlReportWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlReport = psqlReportRepository.saveAndFlush(psqlReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlReport using partial update
        PsqlReport partialUpdatedPsqlReport = new PsqlReport();
        partialUpdatedPsqlReport.setId(psqlReport.getId());

        partialUpdatedPsqlReport
            .fileName(UPDATED_FILE_NAME)
            .fileExtension(UPDATED_FILE_EXTENSION)
            .createDate(UPDATED_CREATE_DATE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .approved(UPDATED_APPROVED);

        restPsqlReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlReport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlReport))
            )
            .andExpect(status().isOk());

        // Validate the PsqlReport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlReportUpdatableFieldsEquals(partialUpdatedPsqlReport, getPersistedPsqlReport(partialUpdatedPsqlReport));
    }

    @Test
    @Transactional
    void patchNonExistingPsqlReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlReport.setId(UUID.randomUUID());

        // Create the PsqlReport
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, psqlReportDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPsqlReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlReport.setId(UUID.randomUUID());

        // Create the PsqlReport
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPsqlReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlReport.setId(UUID.randomUUID());

        // Create the PsqlReport
        PsqlReportDTO psqlReportDTO = psqlReportMapper.toDto(psqlReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlReportMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(psqlReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePsqlReport() throws Exception {
        // Initialize the database
        insertedPsqlReport = psqlReportRepository.saveAndFlush(psqlReport);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the psqlReport
        restPsqlReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, psqlReport.getId().toString()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return psqlReportRepository.count();
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

    protected PsqlReport getPersistedPsqlReport(PsqlReport psqlReport) {
        return psqlReportRepository.findById(psqlReport.getId()).orElseThrow();
    }

    protected void assertPersistedPsqlReportToMatchAllProperties(PsqlReport expectedPsqlReport) {
        assertPsqlReportAllPropertiesEquals(expectedPsqlReport, getPersistedPsqlReport(expectedPsqlReport));
    }

    protected void assertPersistedPsqlReportToMatchUpdatableProperties(PsqlReport expectedPsqlReport) {
        assertPsqlReportAllUpdatablePropertiesEquals(expectedPsqlReport, getPersistedPsqlReport(expectedPsqlReport));
    }
}
