package org.ingenia.rhinobuy.web.rest;

import org.ingenia.rhinobuy.RhinobuyApp;

import org.ingenia.rhinobuy.domain.CustomerPaymentMethod;
import org.ingenia.rhinobuy.repository.CustomerPaymentMethodRepository;
import org.ingenia.rhinobuy.service.CustomerPaymentMethodService;
import org.ingenia.rhinobuy.repository.search.CustomerPaymentMethodSearchRepository;

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
 * Test class for the CustomerPaymentMethodResource REST controller.
 *
 * @see CustomerPaymentMethodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class CustomerPaymentMethodResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_CREDIT_CARD_NUMBER = "AAAAA";
    private static final String UPDATED_CREDIT_CARD_NUMBER = "BBBBB";

    private static final String DEFAULT_DETAILS = "AAAAA";
    private static final String UPDATED_DETAILS = "BBBBB";

    @Inject
    private CustomerPaymentMethodRepository customerPaymentMethodRepository;

    @Inject
    private CustomerPaymentMethodService customerPaymentMethodService;

    @Inject
    private CustomerPaymentMethodSearchRepository customerPaymentMethodSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCustomerPaymentMethodMockMvc;

    private CustomerPaymentMethod customerPaymentMethod;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerPaymentMethodResource customerPaymentMethodResource = new CustomerPaymentMethodResource();
        ReflectionTestUtils.setField(customerPaymentMethodResource, "customerPaymentMethodService", customerPaymentMethodService);
        this.restCustomerPaymentMethodMockMvc = MockMvcBuilders.standaloneSetup(customerPaymentMethodResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentMethod createEntity(EntityManager em) {
        CustomerPaymentMethod customerPaymentMethod = new CustomerPaymentMethod()
                .code(DEFAULT_CODE)
                .creditCardNumber(DEFAULT_CREDIT_CARD_NUMBER)
                .details(DEFAULT_DETAILS);
        return customerPaymentMethod;
    }

    @Before
    public void initTest() {
        customerPaymentMethodSearchRepository.deleteAll();
        customerPaymentMethod = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPaymentMethod() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentMethodRepository.findAll().size();

        // Create the CustomerPaymentMethod

        restCustomerPaymentMethodMockMvc.perform(post("/api/customer-payment-methods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customerPaymentMethod)))
                .andExpect(status().isCreated());

        // Validate the CustomerPaymentMethod in the database
        List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodRepository.findAll();
        assertThat(customerPaymentMethods).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPaymentMethod testCustomerPaymentMethod = customerPaymentMethods.get(customerPaymentMethods.size() - 1);
        assertThat(testCustomerPaymentMethod.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomerPaymentMethod.getCreditCardNumber()).isEqualTo(DEFAULT_CREDIT_CARD_NUMBER);
        assertThat(testCustomerPaymentMethod.getDetails()).isEqualTo(DEFAULT_DETAILS);

        // Validate the CustomerPaymentMethod in ElasticSearch
        CustomerPaymentMethod customerPaymentMethodEs = customerPaymentMethodSearchRepository.findOne(testCustomerPaymentMethod.getId());
        assertThat(customerPaymentMethodEs).isEqualToComparingFieldByField(testCustomerPaymentMethod);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentMethods() throws Exception {
        // Initialize the database
        customerPaymentMethodRepository.saveAndFlush(customerPaymentMethod);

        // Get all the customerPaymentMethods
        restCustomerPaymentMethodMockMvc.perform(get("/api/customer-payment-methods?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentMethod.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].creditCardNumber").value(hasItem(DEFAULT_CREDIT_CARD_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getCustomerPaymentMethod() throws Exception {
        // Initialize the database
        customerPaymentMethodRepository.saveAndFlush(customerPaymentMethod);

        // Get the customerPaymentMethod
        restCustomerPaymentMethodMockMvc.perform(get("/api/customer-payment-methods/{id}", customerPaymentMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerPaymentMethod.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.creditCardNumber").value(DEFAULT_CREDIT_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerPaymentMethod() throws Exception {
        // Get the customerPaymentMethod
        restCustomerPaymentMethodMockMvc.perform(get("/api/customer-payment-methods/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPaymentMethod() throws Exception {
        // Initialize the database
        customerPaymentMethodService.save(customerPaymentMethod);

        int databaseSizeBeforeUpdate = customerPaymentMethodRepository.findAll().size();

        // Update the customerPaymentMethod
        CustomerPaymentMethod updatedCustomerPaymentMethod = customerPaymentMethodRepository.findOne(customerPaymentMethod.getId());
        updatedCustomerPaymentMethod
                .code(UPDATED_CODE)
                .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
                .details(UPDATED_DETAILS);

        restCustomerPaymentMethodMockMvc.perform(put("/api/customer-payment-methods")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCustomerPaymentMethod)))
                .andExpect(status().isOk());

        // Validate the CustomerPaymentMethod in the database
        List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodRepository.findAll();
        assertThat(customerPaymentMethods).hasSize(databaseSizeBeforeUpdate);
        CustomerPaymentMethod testCustomerPaymentMethod = customerPaymentMethods.get(customerPaymentMethods.size() - 1);
        assertThat(testCustomerPaymentMethod.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomerPaymentMethod.getCreditCardNumber()).isEqualTo(UPDATED_CREDIT_CARD_NUMBER);
        assertThat(testCustomerPaymentMethod.getDetails()).isEqualTo(UPDATED_DETAILS);

        // Validate the CustomerPaymentMethod in ElasticSearch
        CustomerPaymentMethod customerPaymentMethodEs = customerPaymentMethodSearchRepository.findOne(testCustomerPaymentMethod.getId());
        assertThat(customerPaymentMethodEs).isEqualToComparingFieldByField(testCustomerPaymentMethod);
    }

    @Test
    @Transactional
    public void deleteCustomerPaymentMethod() throws Exception {
        // Initialize the database
        customerPaymentMethodService.save(customerPaymentMethod);

        int databaseSizeBeforeDelete = customerPaymentMethodRepository.findAll().size();

        // Get the customerPaymentMethod
        restCustomerPaymentMethodMockMvc.perform(delete("/api/customer-payment-methods/{id}", customerPaymentMethod.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean customerPaymentMethodExistsInEs = customerPaymentMethodSearchRepository.exists(customerPaymentMethod.getId());
        assertThat(customerPaymentMethodExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerPaymentMethod> customerPaymentMethods = customerPaymentMethodRepository.findAll();
        assertThat(customerPaymentMethods).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerPaymentMethod() throws Exception {
        // Initialize the database
        customerPaymentMethodService.save(customerPaymentMethod);

        // Search the customerPaymentMethod
        restCustomerPaymentMethodMockMvc.perform(get("/api/_search/customer-payment-methods?query=id:" + customerPaymentMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].creditCardNumber").value(hasItem(DEFAULT_CREDIT_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())));
    }
}
