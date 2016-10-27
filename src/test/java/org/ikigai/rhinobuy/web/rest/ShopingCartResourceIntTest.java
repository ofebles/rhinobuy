package org.ikigai.rhinobuy.web.rest;

import org.ikigai.rhinobuy.RhinobuyApp;

import org.ikigai.rhinobuy.domain.ShopingCart;
import org.ikigai.rhinobuy.repository.ShopingCartRepository;
import org.ikigai.rhinobuy.service.ShopingCartService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShopingCartResource REST controller.
 *
 * @see ShopingCartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class ShopingCartResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ShopingCartRepository shopingCartRepository;

    @Inject
    private ShopingCartService shopingCartService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restShopingCartMockMvc;

    private ShopingCart shopingCart;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShopingCartResource shopingCartResource = new ShopingCartResource();
        ReflectionTestUtils.setField(shopingCartResource, "shopingCartService", shopingCartService);
        this.restShopingCartMockMvc = MockMvcBuilders.standaloneSetup(shopingCartResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShopingCart createEntity(EntityManager em) {
        ShopingCart shopingCart = new ShopingCart()
                .date(DEFAULT_DATE);
        return shopingCart;
    }

    @Before
    public void initTest() {
        shopingCart = createEntity(em);
    }

    @Test
    @Transactional
    public void createShopingCart() throws Exception {
        int databaseSizeBeforeCreate = shopingCartRepository.findAll().size();

        // Create the ShopingCart

        restShopingCartMockMvc.perform(post("/api/shoping-carts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shopingCart)))
                .andExpect(status().isCreated());

        // Validate the ShopingCart in the database
        List<ShopingCart> shopingCarts = shopingCartRepository.findAll();
        assertThat(shopingCarts).hasSize(databaseSizeBeforeCreate + 1);
        ShopingCart testShopingCart = shopingCarts.get(shopingCarts.size() - 1);
        assertThat(testShopingCart.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllShopingCarts() throws Exception {
        // Initialize the database
        shopingCartRepository.saveAndFlush(shopingCart);

        // Get all the shopingCarts
        restShopingCartMockMvc.perform(get("/api/shoping-carts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(shopingCart.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getShopingCart() throws Exception {
        // Initialize the database
        shopingCartRepository.saveAndFlush(shopingCart);

        // Get the shopingCart
        restShopingCartMockMvc.perform(get("/api/shoping-carts/{id}", shopingCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shopingCart.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShopingCart() throws Exception {
        // Get the shopingCart
        restShopingCartMockMvc.perform(get("/api/shoping-carts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShopingCart() throws Exception {
        // Initialize the database
        shopingCartService.save(shopingCart);

        int databaseSizeBeforeUpdate = shopingCartRepository.findAll().size();

        // Update the shopingCart
        ShopingCart updatedShopingCart = shopingCartRepository.findOne(shopingCart.getId());
        updatedShopingCart
                .date(UPDATED_DATE);

        restShopingCartMockMvc.perform(put("/api/shoping-carts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedShopingCart)))
                .andExpect(status().isOk());

        // Validate the ShopingCart in the database
        List<ShopingCart> shopingCarts = shopingCartRepository.findAll();
        assertThat(shopingCarts).hasSize(databaseSizeBeforeUpdate);
        ShopingCart testShopingCart = shopingCarts.get(shopingCarts.size() - 1);
        assertThat(testShopingCart.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteShopingCart() throws Exception {
        // Initialize the database
        shopingCartService.save(shopingCart);

        int databaseSizeBeforeDelete = shopingCartRepository.findAll().size();

        // Get the shopingCart
        restShopingCartMockMvc.perform(delete("/api/shoping-carts/{id}", shopingCart.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ShopingCart> shopingCarts = shopingCartRepository.findAll();
        assertThat(shopingCarts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
