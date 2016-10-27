package org.ikigai.rhinobuy.web.rest;

import org.ikigai.rhinobuy.RhinobuyApp;

import org.ikigai.rhinobuy.domain.ProductDescription;
import org.ikigai.rhinobuy.repository.ProductDescriptionRepository;
import org.ikigai.rhinobuy.service.ProductDescriptionService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductDescriptionResource REST controller.
 *
 * @see ProductDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class ProductDescriptionResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAA";
    private static final String UPDATED_CONTENT = "BBBBB";

    @Inject
    private ProductDescriptionRepository productDescriptionRepository;

    @Inject
    private ProductDescriptionService productDescriptionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProductDescriptionMockMvc;

    private ProductDescription productDescription;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductDescriptionResource productDescriptionResource = new ProductDescriptionResource();
        ReflectionTestUtils.setField(productDescriptionResource, "productDescriptionService", productDescriptionService);
        this.restProductDescriptionMockMvc = MockMvcBuilders.standaloneSetup(productDescriptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDescription createEntity(EntityManager em) {
        ProductDescription productDescription = new ProductDescription()
                .content(DEFAULT_CONTENT);
        return productDescription;
    }

    @Before
    public void initTest() {
        productDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductDescription() throws Exception {
        int databaseSizeBeforeCreate = productDescriptionRepository.findAll().size();

        // Create the ProductDescription

        restProductDescriptionMockMvc.perform(post("/api/product-descriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDescription)))
                .andExpect(status().isCreated());

        // Validate the ProductDescription in the database
        List<ProductDescription> productDescriptions = productDescriptionRepository.findAll();
        assertThat(productDescriptions).hasSize(databaseSizeBeforeCreate + 1);
        ProductDescription testProductDescription = productDescriptions.get(productDescriptions.size() - 1);
        assertThat(testProductDescription.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void getAllProductDescriptions() throws Exception {
        // Initialize the database
        productDescriptionRepository.saveAndFlush(productDescription);

        // Get all the productDescriptions
        restProductDescriptionMockMvc.perform(get("/api/product-descriptions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(productDescription.getId().intValue())))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getProductDescription() throws Exception {
        // Initialize the database
        productDescriptionRepository.saveAndFlush(productDescription);

        // Get the productDescription
        restProductDescriptionMockMvc.perform(get("/api/product-descriptions/{id}", productDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productDescription.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductDescription() throws Exception {
        // Get the productDescription
        restProductDescriptionMockMvc.perform(get("/api/product-descriptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDescription() throws Exception {
        // Initialize the database
        productDescriptionService.save(productDescription);

        int databaseSizeBeforeUpdate = productDescriptionRepository.findAll().size();

        // Update the productDescription
        ProductDescription updatedProductDescription = productDescriptionRepository.findOne(productDescription.getId());
        updatedProductDescription
                .content(UPDATED_CONTENT);

        restProductDescriptionMockMvc.perform(put("/api/product-descriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProductDescription)))
                .andExpect(status().isOk());

        // Validate the ProductDescription in the database
        List<ProductDescription> productDescriptions = productDescriptionRepository.findAll();
        assertThat(productDescriptions).hasSize(databaseSizeBeforeUpdate);
        ProductDescription testProductDescription = productDescriptions.get(productDescriptions.size() - 1);
        assertThat(testProductDescription.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void deleteProductDescription() throws Exception {
        // Initialize the database
        productDescriptionService.save(productDescription);

        int databaseSizeBeforeDelete = productDescriptionRepository.findAll().size();

        // Get the productDescription
        restProductDescriptionMockMvc.perform(delete("/api/product-descriptions/{id}", productDescription.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductDescription> productDescriptions = productDescriptionRepository.findAll();
        assertThat(productDescriptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
