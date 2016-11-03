package org.ingenia.rhinobuy.web.rest;

import org.ingenia.rhinobuy.RhinobuyApp;

import org.ingenia.rhinobuy.domain.Supplier;
import org.ingenia.rhinobuy.repository.SupplierRepository;
import org.ingenia.rhinobuy.service.SupplierService;
import org.ingenia.rhinobuy.repository.search.SupplierSearchRepository;

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
 * Test class for the SupplierResource REST controller.
 *
 * @see SupplierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class SupplierResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private SupplierRepository supplierRepository;

    @Inject
    private SupplierService supplierService;

    @Inject
    private SupplierSearchRepository supplierSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSupplierMockMvc;

    private Supplier supplier;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SupplierResource supplierResource = new SupplierResource();
        ReflectionTestUtils.setField(supplierResource, "supplierService", supplierService);
        this.restSupplierMockMvc = MockMvcBuilders.standaloneSetup(supplierResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplier createEntity(EntityManager em) {
        Supplier supplier = new Supplier()
                .name(DEFAULT_NAME);
        return supplier;
    }

    @Before
    public void initTest() {
        supplierSearchRepository.deleteAll();
        supplier = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplier() throws Exception {
        int databaseSizeBeforeCreate = supplierRepository.findAll().size();

        // Create the Supplier

        restSupplierMockMvc.perform(post("/api/suppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplier)))
                .andExpect(status().isCreated());

        // Validate the Supplier in the database
        List<Supplier> suppliers = supplierRepository.findAll();
        assertThat(suppliers).hasSize(databaseSizeBeforeCreate + 1);
        Supplier testSupplier = suppliers.get(suppliers.size() - 1);
        assertThat(testSupplier.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Supplier in ElasticSearch
        Supplier supplierEs = supplierSearchRepository.findOne(testSupplier.getId());
        assertThat(supplierEs).isEqualToComparingFieldByField(testSupplier);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierRepository.findAll().size();
        // set the field null
        supplier.setName(null);

        // Create the Supplier, which fails.

        restSupplierMockMvc.perform(post("/api/suppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supplier)))
                .andExpect(status().isBadRequest());

        List<Supplier> suppliers = supplierRepository.findAll();
        assertThat(suppliers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuppliers() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the suppliers
        restSupplierMockMvc.perform(get("/api/suppliers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get the supplier
        restSupplierMockMvc.perform(get("/api/suppliers/{id}", supplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplier.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupplier() throws Exception {
        // Get the supplier
        restSupplierMockMvc.perform(get("/api/suppliers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplier() throws Exception {
        // Initialize the database
        supplierService.save(supplier);

        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Update the supplier
        Supplier updatedSupplier = supplierRepository.findOne(supplier.getId());
        updatedSupplier
                .name(UPDATED_NAME);

        restSupplierMockMvc.perform(put("/api/suppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSupplier)))
                .andExpect(status().isOk());

        // Validate the Supplier in the database
        List<Supplier> suppliers = supplierRepository.findAll();
        assertThat(suppliers).hasSize(databaseSizeBeforeUpdate);
        Supplier testSupplier = suppliers.get(suppliers.size() - 1);
        assertThat(testSupplier.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Supplier in ElasticSearch
        Supplier supplierEs = supplierSearchRepository.findOne(testSupplier.getId());
        assertThat(supplierEs).isEqualToComparingFieldByField(testSupplier);
    }

    @Test
    @Transactional
    public void deleteSupplier() throws Exception {
        // Initialize the database
        supplierService.save(supplier);

        int databaseSizeBeforeDelete = supplierRepository.findAll().size();

        // Get the supplier
        restSupplierMockMvc.perform(delete("/api/suppliers/{id}", supplier.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean supplierExistsInEs = supplierSearchRepository.exists(supplier.getId());
        assertThat(supplierExistsInEs).isFalse();

        // Validate the database is empty
        List<Supplier> suppliers = supplierRepository.findAll();
        assertThat(suppliers).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSupplier() throws Exception {
        // Initialize the database
        supplierService.save(supplier);

        // Search the supplier
        restSupplierMockMvc.perform(get("/api/_search/suppliers?query=id:" + supplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
