package org.ingenia.rhinobuy.web.rest;

import org.ingenia.rhinobuy.RhinobuyApp;

import org.ingenia.rhinobuy.domain.Promotion;
import org.ingenia.rhinobuy.repository.PromotionRepository;
import org.ingenia.rhinobuy.service.PromotionService;
import org.ingenia.rhinobuy.repository.search.PromotionSearchRepository;

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
 * Test class for the PromotionResource REST controller.
 *
 * @see PromotionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class PromotionResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private PromotionRepository promotionRepository;

    @Inject
    private PromotionService promotionService;

    @Inject
    private PromotionSearchRepository promotionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPromotionMockMvc;

    private Promotion promotion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PromotionResource promotionResource = new PromotionResource();
        ReflectionTestUtils.setField(promotionResource, "promotionService", promotionService);
        this.restPromotionMockMvc = MockMvcBuilders.standaloneSetup(promotionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Promotion createEntity(EntityManager em) {
        Promotion promotion = new Promotion()
                .description(DEFAULT_DESCRIPTION);
        return promotion;
    }

    @Before
    public void initTest() {
        promotionSearchRepository.deleteAll();
        promotion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPromotion() throws Exception {
        int databaseSizeBeforeCreate = promotionRepository.findAll().size();

        // Create the Promotion

        restPromotionMockMvc.perform(post("/api/promotions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(promotion)))
                .andExpect(status().isCreated());

        // Validate the Promotion in the database
        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeCreate + 1);
        Promotion testPromotion = promotions.get(promotions.size() - 1);
        assertThat(testPromotion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Promotion in ElasticSearch
        Promotion promotionEs = promotionSearchRepository.findOne(testPromotion.getId());
        assertThat(promotionEs).isEqualToComparingFieldByField(testPromotion);
    }

    @Test
    @Transactional
    public void getAllPromotions() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotions
        restPromotionMockMvc.perform(get("/api/promotions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(promotion.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get the promotion
        restPromotionMockMvc.perform(get("/api/promotions/{id}", promotion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(promotion.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPromotion() throws Exception {
        // Get the promotion
        restPromotionMockMvc.perform(get("/api/promotions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromotion() throws Exception {
        // Initialize the database
        promotionService.save(promotion);

        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();

        // Update the promotion
        Promotion updatedPromotion = promotionRepository.findOne(promotion.getId());
        updatedPromotion
                .description(UPDATED_DESCRIPTION);

        restPromotionMockMvc.perform(put("/api/promotions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPromotion)))
                .andExpect(status().isOk());

        // Validate the Promotion in the database
        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeUpdate);
        Promotion testPromotion = promotions.get(promotions.size() - 1);
        assertThat(testPromotion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Promotion in ElasticSearch
        Promotion promotionEs = promotionSearchRepository.findOne(testPromotion.getId());
        assertThat(promotionEs).isEqualToComparingFieldByField(testPromotion);
    }

    @Test
    @Transactional
    public void deletePromotion() throws Exception {
        // Initialize the database
        promotionService.save(promotion);

        int databaseSizeBeforeDelete = promotionRepository.findAll().size();

        // Get the promotion
        restPromotionMockMvc.perform(delete("/api/promotions/{id}", promotion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean promotionExistsInEs = promotionSearchRepository.exists(promotion.getId());
        assertThat(promotionExistsInEs).isFalse();

        // Validate the database is empty
        List<Promotion> promotions = promotionRepository.findAll();
        assertThat(promotions).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPromotion() throws Exception {
        // Initialize the database
        promotionService.save(promotion);

        // Search the promotion
        restPromotionMockMvc.perform(get("/api/_search/promotions?query=id:" + promotion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promotion.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
}
