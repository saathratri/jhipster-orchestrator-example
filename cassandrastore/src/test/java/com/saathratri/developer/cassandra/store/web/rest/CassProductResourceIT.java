package com.saathratri.developer.cassandra.store.web.rest;

import static com.saathratri.developer.cassandra.store.domain.CassProductAsserts.*;
import static com.saathratri.developer.cassandra.store.web.rest.TestUtil.createUpdateProxyForBean;
import static com.saathratri.developer.cassandra.store.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saathratri.developer.cassandra.store.IntegrationTest;
import com.saathratri.developer.cassandra.store.domain.CassProduct;
import com.saathratri.developer.cassandra.store.repository.CassProductRepository;
import com.saathratri.developer.cassandra.store.service.dto.CassProductDTO;
import com.saathratri.developer.cassandra.store.service.mapper.CassProductMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link CassProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CassProductResourceIT {

    private static final UUID DEFAULT_ID = UUID.randomUUID();
    private static final UUID UPDATED_ID = UUID.randomUUID();

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    private static final ByteBuffer DEFAULT_IMAGE = ByteBuffer.wrap(TestUtil.createByteArray(1, "0"));
    private static final ByteBuffer UPDATED_IMAGE = ByteBuffer.wrap(TestUtil.createByteArray(1, "1"));
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_ADDED_DATE = 1L;
    private static final Long UPDATED_ADDED_DATE = 2L;

    private static final String ENTITY_API_URL = "/api/cass-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CassProductRepository cassProductRepository;

    @Autowired
    private CassProductMapper cassProductMapper;

    @Autowired
    private MockMvc restCassProductMockMvc;

    private CassProduct cassProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassProduct createEntity() {
        CassProduct cassProduct = new CassProduct()
            .id(DEFAULT_ID)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .addedDate(DEFAULT_ADDED_DATE);
        return cassProduct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CassProduct createUpdatedEntity() {
        CassProduct cassProduct = new CassProduct()
            .id(UPDATED_ID)
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .addedDate(UPDATED_ADDED_DATE);
        return cassProduct;
    }

    @BeforeEach
    public void initTest() {
        cassProductRepository.deleteAll();
        cassProduct = createEntity();
    }

    @Test
    void createCassProduct() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CassProduct
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);
        var returnedCassProductDTO = om.readValue(
            restCassProductMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassProductDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CassProductDTO.class
        );

        // Validate the CassProduct in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCassProduct = cassProductMapper.toEntity(returnedCassProductDTO);
        assertCassProductUpdatableFieldsEquals(returnedCassProduct, getPersistedCassProduct(returnedCassProduct));
    }

    @Test
    void createCassProductWithExistingId() throws Exception {
        // In Cassandra the primary key is always supplied by the client (there is no
        // server-generated surrogate id to reject), so an entity that already carries its id
        // is a valid create — POSTing it succeeds and inserts the row.
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);

        long databaseSizeBeforeCreate = getRepositoryCount();

        restCassProductMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassProductDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CassProduct was created in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassProduct.setTitle(null);

        // Create the CassProduct, which fails.
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);

        restCassProductMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassProductDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassProduct.setPrice(null);

        // Create the CassProduct, which fails.
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);

        restCassProductMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassProductDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAddedDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cassProduct.setAddedDate(null);

        // Create the CassProduct, which fails.
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);

        restCassProductMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassProductDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCassProducts() throws Exception {
        // Initialize the database
        cassProduct.setId(UUID.randomUUID());
        cassProductRepository.save(cassProduct);

        // Get all the cassProductList
        restCassProductMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cassProduct.getId().toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE.array()))))
            .andExpect(jsonPath("$.[*].addedDate").value(hasItem(DEFAULT_ADDED_DATE.intValue())));
    }

    @Test
    void getCassProduct() throws Exception {
        // Initialize the database
        cassProduct.setId(UUID.randomUUID());
        cassProductRepository.save(cassProduct);

        // Get the cassProduct
        restCassProductMockMvc
            .perform(get(ENTITY_API_URL_ID, cassProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cassProduct.getId().toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE.array())))
            .andExpect(jsonPath("$.addedDate").value(DEFAULT_ADDED_DATE.intValue()));
    }

    @Test
    void getNonExistingCassProduct() throws Exception {
        // Get the cassProduct
        restCassProductMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCassProduct() throws Exception {
        // Initialize the database
        cassProduct.setId(UUID.randomUUID());
        cassProductRepository.save(cassProduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassProduct
        CassProduct updatedCassProduct = cassProductRepository.findById(cassProduct.getId()).orElseThrow();
        updatedCassProduct
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .addedDate(UPDATED_ADDED_DATE);
        CassProductDTO cassProductDTO = cassProductMapper.toDto(updatedCassProduct);

        restCassProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassProductDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassProductDTO))
            )
            .andExpect(status().isOk());

        // Validate the CassProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCassProductToMatchAllProperties(updatedCassProduct);
    }

    @Test
    void putNonExistingCassProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassProduct.setId(UUID.randomUUID());

        // Create the CassProduct
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cassProductDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCassProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassProduct.setId(UUID.randomUUID());
        // Create the CassProduct
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cassProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCassProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassProduct.setId(UUID.randomUUID());

        // Create the CassProduct
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassProductMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cassProductDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCassProductWithPatch() throws Exception {
        // Initialize the database
        cassProduct.setId(UUID.randomUUID());
        cassProductRepository.save(cassProduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassProduct using partial update
        CassProduct partialUpdatedCassProduct = new CassProduct();
        partialUpdatedCassProduct.setId(cassProduct.getId());

        partialUpdatedCassProduct
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .addedDate(UPDATED_ADDED_DATE);

        restCassProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassProduct.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassProduct))
            )
            .andExpect(status().isOk());

        // Validate the CassProduct in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassProductUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCassProduct, cassProduct),
            getPersistedCassProduct(cassProduct)
        );
    }

    @Test
    void fullUpdateCassProductWithPatch() throws Exception {
        // Initialize the database
        cassProduct.setId(UUID.randomUUID());
        cassProductRepository.save(cassProduct);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cassProduct using partial update
        CassProduct partialUpdatedCassProduct = new CassProduct();
        partialUpdatedCassProduct.setId(cassProduct.getId());

        partialUpdatedCassProduct
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .addedDate(UPDATED_ADDED_DATE);

        restCassProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCassProduct.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCassProduct))
            )
            .andExpect(status().isOk());

        // Validate the CassProduct in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCassProductUpdatableFieldsEquals(partialUpdatedCassProduct, getPersistedCassProduct(partialUpdatedCassProduct));
    }

    @Test
    void patchNonExistingCassProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassProduct.setId(UUID.randomUUID());

        // Create the CassProduct
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCassProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cassProductDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCassProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassProduct.setId(UUID.randomUUID());

        // Create the CassProduct
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cassProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CassProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCassProduct() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cassProduct.setId(UUID.randomUUID());

        // Create the CassProduct
        CassProductDTO cassProductDTO = cassProductMapper.toDto(cassProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCassProductMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cassProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CassProduct in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCassProduct() throws Exception {
        // Initialize the database
        cassProduct.setId(UUID.randomUUID());
        cassProductRepository.save(cassProduct);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cassProduct
        restCassProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, cassProduct.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cassProductRepository.count();
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

    protected CassProduct getPersistedCassProduct(CassProduct cassProduct) {
        return cassProductRepository.findById(cassProduct.getId()).orElseThrow();
    }

    protected void assertPersistedCassProductToMatchAllProperties(CassProduct expectedCassProduct) {
        assertCassProductAllPropertiesEquals(expectedCassProduct, getPersistedCassProduct(expectedCassProduct));
    }

    protected void assertPersistedCassProductToMatchUpdatableProperties(CassProduct expectedCassProduct) {
        assertCassProductAllUpdatablePropertiesEquals(expectedCassProduct, getPersistedCassProduct(expectedCassProduct));
    }
}
