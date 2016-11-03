package org.ingenia.rhinobuy.web.rest;

import org.ingenia.rhinobuy.RhinobuyApp;

import org.ingenia.rhinobuy.domain.ReferenceLanguage;
import org.ingenia.rhinobuy.repository.ReferenceLanguageRepository;
import org.ingenia.rhinobuy.service.ReferenceLanguageService;
import org.ingenia.rhinobuy.repository.search.ReferenceLanguageSearchRepository;

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
 * Test class for the ReferenceLanguageResource REST controller.
 *
 * @see ReferenceLanguageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class ReferenceLanguageResourceIntTest {

    private static final String DEFAULT_LANG = "AAAAA";
    private static final String UPDATED_LANG = "BBBBB";

    @Inject
    private ReferenceLanguageRepository referenceLanguageRepository;

    @Inject
    private ReferenceLanguageService referenceLanguageService;

    @Inject
    private ReferenceLanguageSearchRepository referenceLanguageSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restReferenceLanguageMockMvc;

    private ReferenceLanguage referenceLanguage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReferenceLanguageResource referenceLanguageResource = new ReferenceLanguageResource();
        ReflectionTestUtils.setField(referenceLanguageResource, "referenceLanguageService", referenceLanguageService);
        this.restReferenceLanguageMockMvc = MockMvcBuilders.standaloneSetup(referenceLanguageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenceLanguage createEntity(EntityManager em) {
        ReferenceLanguage referenceLanguage = new ReferenceLanguage()
                .lang(DEFAULT_LANG);
        return referenceLanguage;
    }

    @Before
    public void initTest() {
        referenceLanguageSearchRepository.deleteAll();
        referenceLanguage = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferenceLanguage() throws Exception {
        int databaseSizeBeforeCreate = referenceLanguageRepository.findAll().size();

        // Create the ReferenceLanguage

        restReferenceLanguageMockMvc.perform(post("/api/reference-languages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(referenceLanguage)))
                .andExpect(status().isCreated());

        // Validate the ReferenceLanguage in the database
        List<ReferenceLanguage> referenceLanguages = referenceLanguageRepository.findAll();
        assertThat(referenceLanguages).hasSize(databaseSizeBeforeCreate + 1);
        ReferenceLanguage testReferenceLanguage = referenceLanguages.get(referenceLanguages.size() - 1);
        assertThat(testReferenceLanguage.getLang()).isEqualTo(DEFAULT_LANG);

        // Validate the ReferenceLanguage in ElasticSearch
        ReferenceLanguage referenceLanguageEs = referenceLanguageSearchRepository.findOne(testReferenceLanguage.getId());
        assertThat(referenceLanguageEs).isEqualToComparingFieldByField(testReferenceLanguage);
    }

    @Test
    @Transactional
    public void getAllReferenceLanguages() throws Exception {
        // Initialize the database
        referenceLanguageRepository.saveAndFlush(referenceLanguage);

        // Get all the referenceLanguages
        restReferenceLanguageMockMvc.perform(get("/api/reference-languages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(referenceLanguage.getId().intValue())))
                .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG.toString())));
    }

    @Test
    @Transactional
    public void getReferenceLanguage() throws Exception {
        // Initialize the database
        referenceLanguageRepository.saveAndFlush(referenceLanguage);

        // Get the referenceLanguage
        restReferenceLanguageMockMvc.perform(get("/api/reference-languages/{id}", referenceLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(referenceLanguage.getId().intValue()))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReferenceLanguage() throws Exception {
        // Get the referenceLanguage
        restReferenceLanguageMockMvc.perform(get("/api/reference-languages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferenceLanguage() throws Exception {
        // Initialize the database
        referenceLanguageService.save(referenceLanguage);

        int databaseSizeBeforeUpdate = referenceLanguageRepository.findAll().size();

        // Update the referenceLanguage
        ReferenceLanguage updatedReferenceLanguage = referenceLanguageRepository.findOne(referenceLanguage.getId());
        updatedReferenceLanguage
                .lang(UPDATED_LANG);

        restReferenceLanguageMockMvc.perform(put("/api/reference-languages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedReferenceLanguage)))
                .andExpect(status().isOk());

        // Validate the ReferenceLanguage in the database
        List<ReferenceLanguage> referenceLanguages = referenceLanguageRepository.findAll();
        assertThat(referenceLanguages).hasSize(databaseSizeBeforeUpdate);
        ReferenceLanguage testReferenceLanguage = referenceLanguages.get(referenceLanguages.size() - 1);
        assertThat(testReferenceLanguage.getLang()).isEqualTo(UPDATED_LANG);

        // Validate the ReferenceLanguage in ElasticSearch
        ReferenceLanguage referenceLanguageEs = referenceLanguageSearchRepository.findOne(testReferenceLanguage.getId());
        assertThat(referenceLanguageEs).isEqualToComparingFieldByField(testReferenceLanguage);
    }

    @Test
    @Transactional
    public void deleteReferenceLanguage() throws Exception {
        // Initialize the database
        referenceLanguageService.save(referenceLanguage);

        int databaseSizeBeforeDelete = referenceLanguageRepository.findAll().size();

        // Get the referenceLanguage
        restReferenceLanguageMockMvc.perform(delete("/api/reference-languages/{id}", referenceLanguage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean referenceLanguageExistsInEs = referenceLanguageSearchRepository.exists(referenceLanguage.getId());
        assertThat(referenceLanguageExistsInEs).isFalse();

        // Validate the database is empty
        List<ReferenceLanguage> referenceLanguages = referenceLanguageRepository.findAll();
        assertThat(referenceLanguages).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReferenceLanguage() throws Exception {
        // Initialize the database
        referenceLanguageService.save(referenceLanguage);

        // Search the referenceLanguage
        restReferenceLanguageMockMvc.perform(get("/api/_search/reference-languages?query=id:" + referenceLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenceLanguage.getId().intValue())))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG.toString())));
    }
}
