package org.ikigai.rhinobuy.web.rest;

import org.ikigai.rhinobuy.RhinobuyApp;

import org.ikigai.rhinobuy.domain.Casa;
import org.ikigai.rhinobuy.repository.CasaRepository;

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
 * Test class for the CasaResource REST controller.
 *
 * @see CasaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class CasaResourceIntTest {

    @Inject
    private CasaRepository casaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCasaMockMvc;

    private Casa casa;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CasaResource casaResource = new CasaResource();
        ReflectionTestUtils.setField(casaResource, "casaRepository", casaRepository);
        this.restCasaMockMvc = MockMvcBuilders.standaloneSetup(casaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Casa createEntity(EntityManager em) {
        Casa casa = new Casa();
        return casa;
    }

    @Before
    public void initTest() {
        casa = createEntity(em);
    }

    @Test
    @Transactional
    public void createCasa() throws Exception {
        int databaseSizeBeforeCreate = casaRepository.findAll().size();

        // Create the Casa

        restCasaMockMvc.perform(post("/api/casas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(casa)))
                .andExpect(status().isCreated());

        // Validate the Casa in the database
        List<Casa> casas = casaRepository.findAll();
        assertThat(casas).hasSize(databaseSizeBeforeCreate + 1);
        Casa testCasa = casas.get(casas.size() - 1);
    }

    @Test
    @Transactional
    public void getAllCasas() throws Exception {
        // Initialize the database
        casaRepository.saveAndFlush(casa);

        // Get all the casas
        restCasaMockMvc.perform(get("/api/casas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(casa.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCasa() throws Exception {
        // Initialize the database
        casaRepository.saveAndFlush(casa);

        // Get the casa
        restCasaMockMvc.perform(get("/api/casas/{id}", casa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(casa.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCasa() throws Exception {
        // Get the casa
        restCasaMockMvc.perform(get("/api/casas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCasa() throws Exception {
        // Initialize the database
        casaRepository.saveAndFlush(casa);
        int databaseSizeBeforeUpdate = casaRepository.findAll().size();

        // Update the casa
        Casa updatedCasa = casaRepository.findOne(casa.getId());

        restCasaMockMvc.perform(put("/api/casas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCasa)))
                .andExpect(status().isOk());

        // Validate the Casa in the database
        List<Casa> casas = casaRepository.findAll();
        assertThat(casas).hasSize(databaseSizeBeforeUpdate);
        Casa testCasa = casas.get(casas.size() - 1);
    }

    @Test
    @Transactional
    public void deleteCasa() throws Exception {
        // Initialize the database
        casaRepository.saveAndFlush(casa);
        int databaseSizeBeforeDelete = casaRepository.findAll().size();

        // Get the casa
        restCasaMockMvc.perform(delete("/api/casas/{id}", casa.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Casa> casas = casaRepository.findAll();
        assertThat(casas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
