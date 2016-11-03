package org.ingenia.rhinobuy.web.rest;

import org.ingenia.rhinobuy.RhinobuyApp;

import org.ingenia.rhinobuy.domain.Orders;
import org.ingenia.rhinobuy.repository.OrdersRepository;
import org.ingenia.rhinobuy.service.OrdersService;
import org.ingenia.rhinobuy.repository.search.OrdersSearchRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrdersResource REST controller.
 *
 * @see OrdersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class OrdersResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_PLACED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_PLACED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_PLACED_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_DATE_PLACED);

    private static final String DEFAULT_DETAILS = "AAAAA";
    private static final String UPDATED_DETAILS = "BBBBB";

    @Inject
    private OrdersRepository ordersRepository;

    @Inject
    private OrdersService ordersService;

    @Inject
    private OrdersSearchRepository ordersSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOrdersMockMvc;

    private Orders orders;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrdersResource ordersResource = new OrdersResource();
        ReflectionTestUtils.setField(ordersResource, "ordersService", ordersService);
        this.restOrdersMockMvc = MockMvcBuilders.standaloneSetup(ordersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orders createEntity(EntityManager em) {
        Orders orders = new Orders()
                .datePlaced(DEFAULT_DATE_PLACED)
                .details(DEFAULT_DETAILS);
        return orders;
    }

    @Before
    public void initTest() {
        ordersSearchRepository.deleteAll();
        orders = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrders() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders

        restOrdersMockMvc.perform(post("/api/orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orders)))
                .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> orders = ordersRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeCreate + 1);
        Orders testOrders = orders.get(orders.size() - 1);
        assertThat(testOrders.getDatePlaced()).isEqualTo(DEFAULT_DATE_PLACED);
        assertThat(testOrders.getDetails()).isEqualTo(DEFAULT_DETAILS);

        // Validate the Orders in ElasticSearch
        Orders ordersEs = ordersSearchRepository.findOne(testOrders.getId());
        assertThat(ordersEs).isEqualToComparingFieldByField(testOrders);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the orders
        restOrdersMockMvc.perform(get("/api/orders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
                .andExpect(jsonPath("$.[*].datePlaced").value(hasItem(DEFAULT_DATE_PLACED_STR)))
                .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orders.getId().intValue()))
            .andExpect(jsonPath("$.datePlaced").value(DEFAULT_DATE_PLACED_STR))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrders() throws Exception {
        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrders() throws Exception {
        // Initialize the database
        ordersService.save(orders);

        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders
        Orders updatedOrders = ordersRepository.findOne(orders.getId());
        updatedOrders
                .datePlaced(UPDATED_DATE_PLACED)
                .details(UPDATED_DETAILS);

        restOrdersMockMvc.perform(put("/api/orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedOrders)))
                .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> orders = ordersRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = orders.get(orders.size() - 1);
        assertThat(testOrders.getDatePlaced()).isEqualTo(UPDATED_DATE_PLACED);
        assertThat(testOrders.getDetails()).isEqualTo(UPDATED_DETAILS);

        // Validate the Orders in ElasticSearch
        Orders ordersEs = ordersSearchRepository.findOne(testOrders.getId());
        assertThat(ordersEs).isEqualToComparingFieldByField(testOrders);
    }

    @Test
    @Transactional
    public void deleteOrders() throws Exception {
        // Initialize the database
        ordersService.save(orders);

        int databaseSizeBeforeDelete = ordersRepository.findAll().size();

        // Get the orders
        restOrdersMockMvc.perform(delete("/api/orders/{id}", orders.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean ordersExistsInEs = ordersSearchRepository.exists(orders.getId());
        assertThat(ordersExistsInEs).isFalse();

        // Validate the database is empty
        List<Orders> orders = ordersRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrders() throws Exception {
        // Initialize the database
        ordersService.save(orders);

        // Search the orders
        restOrdersMockMvc.perform(get("/api/_search/orders?query=id:" + orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
            .andExpect(jsonPath("$.[*].datePlaced").value(hasItem(DEFAULT_DATE_PLACED_STR)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())));
    }
}
