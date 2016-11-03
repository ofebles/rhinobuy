package org.ingenia.rhinobuy.web.rest;

import org.ingenia.rhinobuy.RhinobuyApp;

import org.ingenia.rhinobuy.domain.WishList;
import org.ingenia.rhinobuy.repository.WishListRepository;
import org.ingenia.rhinobuy.service.WishListService;
import org.ingenia.rhinobuy.repository.search.WishListSearchRepository;

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
 * Test class for the WishListResource REST controller.
 *
 * @see WishListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class WishListResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private WishListRepository wishListRepository;

    @Inject
    private WishListService wishListService;

    @Inject
    private WishListSearchRepository wishListSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWishListMockMvc;

    private WishList wishList;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WishListResource wishListResource = new WishListResource();
        ReflectionTestUtils.setField(wishListResource, "wishListService", wishListService);
        this.restWishListMockMvc = MockMvcBuilders.standaloneSetup(wishListResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WishList createEntity(EntityManager em) {
        WishList wishList = new WishList()
                .name(DEFAULT_NAME);
        return wishList;
    }

    @Before
    public void initTest() {
        wishListSearchRepository.deleteAll();
        wishList = createEntity(em);
    }

    @Test
    @Transactional
    public void createWishList() throws Exception {
        int databaseSizeBeforeCreate = wishListRepository.findAll().size();

        // Create the WishList

        restWishListMockMvc.perform(post("/api/wish-lists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(wishList)))
                .andExpect(status().isCreated());

        // Validate the WishList in the database
        List<WishList> wishLists = wishListRepository.findAll();
        assertThat(wishLists).hasSize(databaseSizeBeforeCreate + 1);
        WishList testWishList = wishLists.get(wishLists.size() - 1);
        assertThat(testWishList.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the WishList in ElasticSearch
        WishList wishListEs = wishListSearchRepository.findOne(testWishList.getId());
        assertThat(wishListEs).isEqualToComparingFieldByField(testWishList);
    }

    @Test
    @Transactional
    public void getAllWishLists() throws Exception {
        // Initialize the database
        wishListRepository.saveAndFlush(wishList);

        // Get all the wishLists
        restWishListMockMvc.perform(get("/api/wish-lists?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(wishList.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getWishList() throws Exception {
        // Initialize the database
        wishListRepository.saveAndFlush(wishList);

        // Get the wishList
        restWishListMockMvc.perform(get("/api/wish-lists/{id}", wishList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wishList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWishList() throws Exception {
        // Get the wishList
        restWishListMockMvc.perform(get("/api/wish-lists/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWishList() throws Exception {
        // Initialize the database
        wishListService.save(wishList);

        int databaseSizeBeforeUpdate = wishListRepository.findAll().size();

        // Update the wishList
        WishList updatedWishList = wishListRepository.findOne(wishList.getId());
        updatedWishList
                .name(UPDATED_NAME);

        restWishListMockMvc.perform(put("/api/wish-lists")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedWishList)))
                .andExpect(status().isOk());

        // Validate the WishList in the database
        List<WishList> wishLists = wishListRepository.findAll();
        assertThat(wishLists).hasSize(databaseSizeBeforeUpdate);
        WishList testWishList = wishLists.get(wishLists.size() - 1);
        assertThat(testWishList.getName()).isEqualTo(UPDATED_NAME);

        // Validate the WishList in ElasticSearch
        WishList wishListEs = wishListSearchRepository.findOne(testWishList.getId());
        assertThat(wishListEs).isEqualToComparingFieldByField(testWishList);
    }

    @Test
    @Transactional
    public void deleteWishList() throws Exception {
        // Initialize the database
        wishListService.save(wishList);

        int databaseSizeBeforeDelete = wishListRepository.findAll().size();

        // Get the wishList
        restWishListMockMvc.perform(delete("/api/wish-lists/{id}", wishList.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean wishListExistsInEs = wishListSearchRepository.exists(wishList.getId());
        assertThat(wishListExistsInEs).isFalse();

        // Validate the database is empty
        List<WishList> wishLists = wishListRepository.findAll();
        assertThat(wishLists).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWishList() throws Exception {
        // Initialize the database
        wishListService.save(wishList);

        // Search the wishList
        restWishListMockMvc.perform(get("/api/_search/wish-lists?query=id:" + wishList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wishList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
}
