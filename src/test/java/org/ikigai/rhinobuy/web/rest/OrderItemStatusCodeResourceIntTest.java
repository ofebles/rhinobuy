package org.ikigai.rhinobuy.web.rest;

import org.ikigai.rhinobuy.RhinobuyApp;

import org.ikigai.rhinobuy.domain.OrderItemStatusCode;
import org.ikigai.rhinobuy.repository.OrderItemStatusCodeRepository;
import org.ikigai.rhinobuy.service.OrderItemStatusCodeService;

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
 * Test class for the OrderItemStatusCodeResource REST controller.
 *
 * @see OrderItemStatusCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class OrderItemStatusCodeResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private OrderItemStatusCodeRepository orderItemStatusCodeRepository;

    @Inject
    private OrderItemStatusCodeService orderItemStatusCodeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOrderItemStatusCodeMockMvc;

    private OrderItemStatusCode orderItemStatusCode;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderItemStatusCodeResource orderItemStatusCodeResource = new OrderItemStatusCodeResource();
        ReflectionTestUtils.setField(orderItemStatusCodeResource, "orderItemStatusCodeService", orderItemStatusCodeService);
        this.restOrderItemStatusCodeMockMvc = MockMvcBuilders.standaloneSetup(orderItemStatusCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItemStatusCode createEntity(EntityManager em) {
        OrderItemStatusCode orderItemStatusCode = new OrderItemStatusCode()
                .description(DEFAULT_DESCRIPTION);
        return orderItemStatusCode;
    }

    @Before
    public void initTest() {
        orderItemStatusCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderItemStatusCode() throws Exception {
        int databaseSizeBeforeCreate = orderItemStatusCodeRepository.findAll().size();

        // Create the OrderItemStatusCode

        restOrderItemStatusCodeMockMvc.perform(post("/api/order-item-status-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItemStatusCode)))
                .andExpect(status().isCreated());

        // Validate the OrderItemStatusCode in the database
        List<OrderItemStatusCode> orderItemStatusCodes = orderItemStatusCodeRepository.findAll();
        assertThat(orderItemStatusCodes).hasSize(databaseSizeBeforeCreate + 1);
        OrderItemStatusCode testOrderItemStatusCode = orderItemStatusCodes.get(orderItemStatusCodes.size() - 1);
        assertThat(testOrderItemStatusCode.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOrderItemStatusCodes() throws Exception {
        // Initialize the database
        orderItemStatusCodeRepository.saveAndFlush(orderItemStatusCode);

        // Get all the orderItemStatusCodes
        restOrderItemStatusCodeMockMvc.perform(get("/api/order-item-status-codes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(orderItemStatusCode.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOrderItemStatusCode() throws Exception {
        // Initialize the database
        orderItemStatusCodeRepository.saveAndFlush(orderItemStatusCode);

        // Get the orderItemStatusCode
        restOrderItemStatusCodeMockMvc.perform(get("/api/order-item-status-codes/{id}", orderItemStatusCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderItemStatusCode.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderItemStatusCode() throws Exception {
        // Get the orderItemStatusCode
        restOrderItemStatusCodeMockMvc.perform(get("/api/order-item-status-codes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItemStatusCode() throws Exception {
        // Initialize the database
        orderItemStatusCodeService.save(orderItemStatusCode);

        int databaseSizeBeforeUpdate = orderItemStatusCodeRepository.findAll().size();

        // Update the orderItemStatusCode
        OrderItemStatusCode updatedOrderItemStatusCode = orderItemStatusCodeRepository.findOne(orderItemStatusCode.getId());
        updatedOrderItemStatusCode
                .description(UPDATED_DESCRIPTION);

        restOrderItemStatusCodeMockMvc.perform(put("/api/order-item-status-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedOrderItemStatusCode)))
                .andExpect(status().isOk());

        // Validate the OrderItemStatusCode in the database
        List<OrderItemStatusCode> orderItemStatusCodes = orderItemStatusCodeRepository.findAll();
        assertThat(orderItemStatusCodes).hasSize(databaseSizeBeforeUpdate);
        OrderItemStatusCode testOrderItemStatusCode = orderItemStatusCodes.get(orderItemStatusCodes.size() - 1);
        assertThat(testOrderItemStatusCode.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteOrderItemStatusCode() throws Exception {
        // Initialize the database
        orderItemStatusCodeService.save(orderItemStatusCode);

        int databaseSizeBeforeDelete = orderItemStatusCodeRepository.findAll().size();

        // Get the orderItemStatusCode
        restOrderItemStatusCodeMockMvc.perform(delete("/api/order-item-status-codes/{id}", orderItemStatusCode.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderItemStatusCode> orderItemStatusCodes = orderItemStatusCodeRepository.findAll();
        assertThat(orderItemStatusCodes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
