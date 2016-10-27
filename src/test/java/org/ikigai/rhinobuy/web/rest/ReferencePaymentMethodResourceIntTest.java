package org.ikigai.rhinobuy.web.rest;

import org.ikigai.rhinobuy.RhinobuyApp;

import org.ikigai.rhinobuy.domain.ReferencePaymentMethod;
import org.ikigai.rhinobuy.repository.ReferencePaymentMethodRepository;
import org.ikigai.rhinobuy.service.ReferencePaymentMethodService;

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
 * Test class for the ReferencePaymentMethodResource REST controller.
 *
 * @see ReferencePaymentMethodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class ReferencePaymentMethodResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ReferencePaymentMethodRepository referencePaymentMethodRepository;

    @Inject
    private ReferencePaymentMethodService referencePaymentMethodService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restReferencePaymentMethodMockMvc;

    private ReferencePaymentMethod referencePaymentMethod;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReferencePaymentMethodResource referencePaymentMethodResource = new ReferencePaymentMethodResource();
        ReflectionTestUtils.setField(referencePaymentMethodResource, "referencePaymentMethodService", referencePaymentMethodService);
        this.restReferencePaymentMethodMockMvc = MockMvcBuilders.standaloneSetup(referencePaymentMethodResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferencePaymentMethod createEntity(EntityManager em) {
        ReferencePaymentMethod referencePaymentMethod = new ReferencePaymentMethod()
                .description(DEFAULT_DESCRIPTION);
        return referencePaymentMethod;
    }

    @Before
    public void initTest() {
        referencePaymentMethod = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferencePaymentMethod() throws Exception {
        int databaseSizeBeforeCreate = referencePaymentMethodRepository.findAll().size();

        // Create the ReferencePaymentMethod

        restReferencePaymentMethodMockMvc.perform(post("/api/reference-payment-methods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(referencePaymentMethod)))
                .andExpect(status().isCreated());

        // Validate the ReferencePaymentMethod in the database
        List<ReferencePaymentMethod> referencePaymentMethods = referencePaymentMethodRepository.findAll();
        assertThat(referencePaymentMethods).hasSize(databaseSizeBeforeCreate + 1);
        ReferencePaymentMethod testReferencePaymentMethod = referencePaymentMethods.get(referencePaymentMethods.size() - 1);
        assertThat(testReferencePaymentMethod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferencePaymentMethods() throws Exception {
        // Initialize the database
        referencePaymentMethodRepository.saveAndFlush(referencePaymentMethod);

        // Get all the referencePaymentMethods
        restReferencePaymentMethodMockMvc.perform(get("/api/reference-payment-methods?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(referencePaymentMethod.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getReferencePaymentMethod() throws Exception {
        // Initialize the database
        referencePaymentMethodRepository.saveAndFlush(referencePaymentMethod);

        // Get the referencePaymentMethod
        restReferencePaymentMethodMockMvc.perform(get("/api/reference-payment-methods/{id}", referencePaymentMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(referencePaymentMethod.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReferencePaymentMethod() throws Exception {
        // Get the referencePaymentMethod
        restReferencePaymentMethodMockMvc.perform(get("/api/reference-payment-methods/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferencePaymentMethod() throws Exception {
        // Initialize the database
        referencePaymentMethodService.save(referencePaymentMethod);

        int databaseSizeBeforeUpdate = referencePaymentMethodRepository.findAll().size();

        // Update the referencePaymentMethod
        ReferencePaymentMethod updatedReferencePaymentMethod = referencePaymentMethodRepository.findOne(referencePaymentMethod.getId());
        updatedReferencePaymentMethod
                .description(UPDATED_DESCRIPTION);

        restReferencePaymentMethodMockMvc.perform(put("/api/reference-payment-methods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedReferencePaymentMethod)))
                .andExpect(status().isOk());

        // Validate the ReferencePaymentMethod in the database
        List<ReferencePaymentMethod> referencePaymentMethods = referencePaymentMethodRepository.findAll();
        assertThat(referencePaymentMethods).hasSize(databaseSizeBeforeUpdate);
        ReferencePaymentMethod testReferencePaymentMethod = referencePaymentMethods.get(referencePaymentMethods.size() - 1);
        assertThat(testReferencePaymentMethod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteReferencePaymentMethod() throws Exception {
        // Initialize the database
        referencePaymentMethodService.save(referencePaymentMethod);

        int databaseSizeBeforeDelete = referencePaymentMethodRepository.findAll().size();

        // Get the referencePaymentMethod
        restReferencePaymentMethodMockMvc.perform(delete("/api/reference-payment-methods/{id}", referencePaymentMethod.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ReferencePaymentMethod> referencePaymentMethods = referencePaymentMethodRepository.findAll();
        assertThat(referencePaymentMethods).hasSize(databaseSizeBeforeDelete - 1);
    }
}
