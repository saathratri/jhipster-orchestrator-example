package com.saathratri.developer.psql.store.web.rest;

import static com.saathratri.developer.psql.store.domain.PsqlProductAsserts.*;
import static com.saathratri.developer.psql.store.web.rest.TestUtil.createUpdateProxyForBean;
import static com.saathratri.developer.psql.store.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.psql.store.IntegrationTest;
import com.saathratri.developer.psql.store.domain.PsqlProduct;
import com.saathratri.developer.psql.store.repository.PsqlProductRepository;
import com.saathratri.developer.psql.store.service.dto.PsqlProductDTO;
import com.saathratri.developer.psql.store.service.mapper.PsqlProductMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link PsqlProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PsqlProductResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/psql-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PsqlProductRepository psqlProductRepository;

    @Autowired
    private PsqlProductMapper psqlProductMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPsqlProductMockMvc;

    private PsqlProduct psqlProduct;

    private PsqlProduct insertedPsqlProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlProduct createEntity() {
        return new PsqlProduct()
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PsqlProduct createUpdatedEntity() {
        return new PsqlProduct()
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @BeforeEach
    void initTest() {
        psqlProduct = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPsqlProduct != null) {
            psqlProductRepository.delete(insertedPsqlProduct);
            insertedPsqlProduct = null;
        }
    }

    @Test
    @Transactional
    void createPsqlProduct() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PsqlProduct
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(psqlProduct);
        var returnedPsqlProductDTO = om.readValue(
            restPsqlProductMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlProductDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PsqlProductDTO.class
        );

        // Validate the PsqlProduct in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPsqlProduct = psqlProductMapper.toEntity(returnedPsqlProductDTO);
        assertPsqlProductUpdatableFieldsEquals(returnedPsqlProduct, getPersistedPsqlProduct(returnedPsqlProduct));

        insertedPsqlProduct = returnedPsqlProduct;
    }

    @Test
    @Transactional
    void createPsqlProductWithExistingId() throws Exception {
        // Create the PsqlProduct with an existing ID
        insertedPsqlProduct = psqlProductRepository.saveAndFlush(psqlProduct);
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(psqlProduct);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPsqlProductMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlProduct.setTitle(null);

        // Create the PsqlProduct, which fails.
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(psqlProduct);

        restPsqlProductMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlProductDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        psqlProduct.setPrice(null);

        // Create the PsqlProduct, which fails.
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(psqlProduct);

        restPsqlProductMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlProductDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPsqlProducts() throws Exception {
        // Initialize the database
        insertedPsqlProduct = psqlProductRepository.saveAndFlush(psqlProduct);

        // Get all the psqlProductList
        restPsqlProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(psqlProduct.getId().toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getPsqlProduct() throws Exception {
        // Initialize the database
        insertedPsqlProduct = psqlProductRepository.saveAndFlush(psqlProduct);

        // Get the psqlProduct
        restPsqlProductMockMvc
            .perform(get(ENTITY_API_URL_ID, psqlProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(psqlProduct.getId().toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingPsqlProduct() throws Exception {
        // Get the psqlProduct
        restPsqlProductMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPsqlProduct() throws Exception {
        // Initialize the database
        insertedPsqlProduct = psqlProductRepository.saveAndFlush(psqlProduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlProduct
        PsqlProduct updatedPsqlProduct = psqlProductRepository.findById(psqlProduct.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPsqlProduct are not directly saved in db
        em.detach(updatedPsqlProduct);
        updatedPsqlProduct.title(UPDATED_TITLE).price(UPDATED_PRICE).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(updatedPsqlProduct);

        restPsqlProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlProductDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlProductDTO))
            )
            .andExpect(status().isOk());

        // Validate the PsqlProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPsqlProductToMatchAllProperties(updatedPsqlProduct);
    }

    @Test
    @Transactional
    void putNonExistingPsqlProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlProduct.setId(UUID.randomUUID());

        // Create the PsqlProduct
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(psqlProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, psqlProductDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPsqlProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlProduct.setId(UUID.randomUUID());

        // Create the PsqlProduct
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(psqlProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(psqlProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPsqlProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlProduct.setId(UUID.randomUUID());

        // Create the PsqlProduct
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(psqlProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlProductMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(psqlProductDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePsqlProductWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlProduct = psqlProductRepository.saveAndFlush(psqlProduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlProduct using partial update
        PsqlProduct partialUpdatedPsqlProduct = new PsqlProduct();
        partialUpdatedPsqlProduct.setId(psqlProduct.getId());

        partialUpdatedPsqlProduct.title(UPDATED_TITLE);

        restPsqlProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlProduct.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlProduct))
            )
            .andExpect(status().isOk());

        // Validate the PsqlProduct in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlProductUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPsqlProduct, psqlProduct),
            getPersistedPsqlProduct(psqlProduct)
        );
    }

    @Test
    @Transactional
    void fullUpdatePsqlProductWithPatch() throws Exception {
        // Initialize the database
        insertedPsqlProduct = psqlProductRepository.saveAndFlush(psqlProduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the psqlProduct using partial update
        PsqlProduct partialUpdatedPsqlProduct = new PsqlProduct();
        partialUpdatedPsqlProduct.setId(psqlProduct.getId());

        partialUpdatedPsqlProduct
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restPsqlProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsqlProduct.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPsqlProduct))
            )
            .andExpect(status().isOk());

        // Validate the PsqlProduct in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPsqlProductUpdatableFieldsEquals(partialUpdatedPsqlProduct, getPersistedPsqlProduct(partialUpdatedPsqlProduct));
    }

    @Test
    @Transactional
    void patchNonExistingPsqlProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlProduct.setId(UUID.randomUUID());

        // Create the PsqlProduct
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(psqlProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPsqlProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, psqlProductDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPsqlProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlProduct.setId(UUID.randomUUID());

        // Create the PsqlProduct
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(psqlProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(psqlProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PsqlProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPsqlProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        psqlProduct.setId(UUID.randomUUID());

        // Create the PsqlProduct
        PsqlProductDTO psqlProductDTO = psqlProductMapper.toDto(psqlProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPsqlProductMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(psqlProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PsqlProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePsqlProduct() throws Exception {
        // Initialize the database
        insertedPsqlProduct = psqlProductRepository.saveAndFlush(psqlProduct);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the psqlProduct
        restPsqlProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, psqlProduct.getId().toString()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return psqlProductRepository.count();
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

    protected PsqlProduct getPersistedPsqlProduct(PsqlProduct psqlProduct) {
        return psqlProductRepository.findById(psqlProduct.getId()).orElseThrow();
    }

    protected void assertPersistedPsqlProductToMatchAllProperties(PsqlProduct expectedPsqlProduct) {
        assertPsqlProductAllPropertiesEquals(expectedPsqlProduct, getPersistedPsqlProduct(expectedPsqlProduct));
    }

    protected void assertPersistedPsqlProductToMatchUpdatableProperties(PsqlProduct expectedPsqlProduct) {
        assertPsqlProductAllUpdatablePropertiesEquals(expectedPsqlProduct, getPersistedPsqlProduct(expectedPsqlProduct));
    }
}
