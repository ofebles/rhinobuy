package org.ingenia.rhinobuy.web.rest;

import org.ingenia.rhinobuy.RhinobuyApp;

import org.ingenia.rhinobuy.domain.ReferenceInvoiceStatus;
import org.ingenia.rhinobuy.repository.ReferenceInvoiceStatusRepository;
import org.ingenia.rhinobuy.service.ReferenceInvoiceStatusService;
import org.ingenia.rhinobuy.repository.search.ReferenceInvoiceStatusSearchRepository;

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
 * Test class for the ReferenceInvoiceStatusResource REST controller.
 *
 * @see ReferenceInvoiceStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class ReferenceInvoiceStatusResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAA";
    private static final String UPDATED_NUMBER = "BBBBB";

    private static final ZonedDateTime DEFAULT_INVOICE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_INVOICE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_INVOICE_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_INVOICE_DATE);

    private static final String DEFAULT_INVOICE_DETAILS = "AAAAA";
    private static final String UPDATED_INVOICE_DETAILS = "BBBBB";

    @Inject
    private ReferenceInvoiceStatusRepository referenceInvoiceStatusRepository;

    @Inject
    private ReferenceInvoiceStatusService referenceInvoiceStatusService;

    @Inject
    private ReferenceInvoiceStatusSearchRepository referenceInvoiceStatusSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restReferenceInvoiceStatusMockMvc;

    private ReferenceInvoiceStatus referenceInvoiceStatus;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReferenceInvoiceStatusResource referenceInvoiceStatusResource = new ReferenceInvoiceStatusResource();
        ReflectionTestUtils.setField(referenceInvoiceStatusResource, "referenceInvoiceStatusService", referenceInvoiceStatusService);
        this.restReferenceInvoiceStatusMockMvc = MockMvcBuilders.standaloneSetup(referenceInvoiceStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenceInvoiceStatus createEntity(EntityManager em) {
        ReferenceInvoiceStatus referenceInvoiceStatus = new ReferenceInvoiceStatus()
                .number(DEFAULT_NUMBER)
                .invoiceDate(DEFAULT_INVOICE_DATE)
                .invoiceDetails(DEFAULT_INVOICE_DETAILS);
        return referenceInvoiceStatus;
    }

    @Before
    public void initTest() {
        referenceInvoiceStatusSearchRepository.deleteAll();
        referenceInvoiceStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferenceInvoiceStatus() throws Exception {
        int databaseSizeBeforeCreate = referenceInvoiceStatusRepository.findAll().size();

        // Create the ReferenceInvoiceStatus

        restReferenceInvoiceStatusMockMvc.perform(post("/api/reference-invoice-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(referenceInvoiceStatus)))
                .andExpect(status().isCreated());

        // Validate the ReferenceInvoiceStatus in the database
        List<ReferenceInvoiceStatus> referenceInvoiceStatuses = referenceInvoiceStatusRepository.findAll();
        assertThat(referenceInvoiceStatuses).hasSize(databaseSizeBeforeCreate + 1);
        ReferenceInvoiceStatus testReferenceInvoiceStatus = referenceInvoiceStatuses.get(referenceInvoiceStatuses.size() - 1);
        assertThat(testReferenceInvoiceStatus.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testReferenceInvoiceStatus.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testReferenceInvoiceStatus.getInvoiceDetails()).isEqualTo(DEFAULT_INVOICE_DETAILS);

        // Validate the ReferenceInvoiceStatus in ElasticSearch
        ReferenceInvoiceStatus referenceInvoiceStatusEs = referenceInvoiceStatusSearchRepository.findOne(testReferenceInvoiceStatus.getId());
        assertThat(referenceInvoiceStatusEs).isEqualToComparingFieldByField(testReferenceInvoiceStatus);
    }

    @Test
    @Transactional
    public void getAllReferenceInvoiceStatuses() throws Exception {
        // Initialize the database
        referenceInvoiceStatusRepository.saveAndFlush(referenceInvoiceStatus);

        // Get all the referenceInvoiceStatuses
        restReferenceInvoiceStatusMockMvc.perform(get("/api/reference-invoice-statuses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(referenceInvoiceStatus.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE_STR)))
                .andExpect(jsonPath("$.[*].invoiceDetails").value(hasItem(DEFAULT_INVOICE_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getReferenceInvoiceStatus() throws Exception {
        // Initialize the database
        referenceInvoiceStatusRepository.saveAndFlush(referenceInvoiceStatus);

        // Get the referenceInvoiceStatus
        restReferenceInvoiceStatusMockMvc.perform(get("/api/reference-invoice-statuses/{id}", referenceInvoiceStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(referenceInvoiceStatus.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE_STR))
            .andExpect(jsonPath("$.invoiceDetails").value(DEFAULT_INVOICE_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReferenceInvoiceStatus() throws Exception {
        // Get the referenceInvoiceStatus
        restReferenceInvoiceStatusMockMvc.perform(get("/api/reference-invoice-statuses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferenceInvoiceStatus() throws Exception {
        // Initialize the database
        referenceInvoiceStatusService.save(referenceInvoiceStatus);

        int databaseSizeBeforeUpdate = referenceInvoiceStatusRepository.findAll().size();

        // Update the referenceInvoiceStatus
        ReferenceInvoiceStatus updatedReferenceInvoiceStatus = referenceInvoiceStatusRepository.findOne(referenceInvoiceStatus.getId());
        updatedReferenceInvoiceStatus
                .number(UPDATED_NUMBER)
                .invoiceDate(UPDATED_INVOICE_DATE)
                .invoiceDetails(UPDATED_INVOICE_DETAILS);

        restReferenceInvoiceStatusMockMvc.perform(put("/api/reference-invoice-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedReferenceInvoiceStatus)))
                .andExpect(status().isOk());

        // Validate the ReferenceInvoiceStatus in the database
        List<ReferenceInvoiceStatus> referenceInvoiceStatuses = referenceInvoiceStatusRepository.findAll();
        assertThat(referenceInvoiceStatuses).hasSize(databaseSizeBeforeUpdate);
        ReferenceInvoiceStatus testReferenceInvoiceStatus = referenceInvoiceStatuses.get(referenceInvoiceStatuses.size() - 1);
        assertThat(testReferenceInvoiceStatus.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testReferenceInvoiceStatus.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testReferenceInvoiceStatus.getInvoiceDetails()).isEqualTo(UPDATED_INVOICE_DETAILS);

        // Validate the ReferenceInvoiceStatus in ElasticSearch
        ReferenceInvoiceStatus referenceInvoiceStatusEs = referenceInvoiceStatusSearchRepository.findOne(testReferenceInvoiceStatus.getId());
        assertThat(referenceInvoiceStatusEs).isEqualToComparingFieldByField(testReferenceInvoiceStatus);
    }

    @Test
    @Transactional
    public void deleteReferenceInvoiceStatus() throws Exception {
        // Initialize the database
        referenceInvoiceStatusService.save(referenceInvoiceStatus);

        int databaseSizeBeforeDelete = referenceInvoiceStatusRepository.findAll().size();

        // Get the referenceInvoiceStatus
        restReferenceInvoiceStatusMockMvc.perform(delete("/api/reference-invoice-statuses/{id}", referenceInvoiceStatus.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean referenceInvoiceStatusExistsInEs = referenceInvoiceStatusSearchRepository.exists(referenceInvoiceStatus.getId());
        assertThat(referenceInvoiceStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<ReferenceInvoiceStatus> referenceInvoiceStatuses = referenceInvoiceStatusRepository.findAll();
        assertThat(referenceInvoiceStatuses).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReferenceInvoiceStatus() throws Exception {
        // Initialize the database
        referenceInvoiceStatusService.save(referenceInvoiceStatus);

        // Search the referenceInvoiceStatus
        restReferenceInvoiceStatusMockMvc.perform(get("/api/_search/reference-invoice-statuses?query=id:" + referenceInvoiceStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenceInvoiceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE_STR)))
            .andExpect(jsonPath("$.[*].invoiceDetails").value(hasItem(DEFAULT_INVOICE_DETAILS.toString())));
    }
}
