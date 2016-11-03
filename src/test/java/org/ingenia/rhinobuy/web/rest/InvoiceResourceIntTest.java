package org.ingenia.rhinobuy.web.rest;

import org.ingenia.rhinobuy.RhinobuyApp;

import org.ingenia.rhinobuy.domain.Invoice;
import org.ingenia.rhinobuy.repository.InvoiceRepository;
import org.ingenia.rhinobuy.service.InvoiceService;
import org.ingenia.rhinobuy.repository.search.InvoiceSearchRepository;

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
 * Test class for the InvoiceResource REST controller.
 *
 * @see InvoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class InvoiceResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAA";
    private static final String UPDATED_NUMBER = "BBBBB";

    private static final ZonedDateTime DEFAULT_INVOICE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_INVOICE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_INVOICE_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_INVOICE_DATE);

    private static final String DEFAULT_INVOICE_DETAILS = "AAAAA";
    private static final String UPDATED_INVOICE_DETAILS = "BBBBB";

    @Inject
    private InvoiceRepository invoiceRepository;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private InvoiceSearchRepository invoiceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvoiceResource invoiceResource = new InvoiceResource();
        ReflectionTestUtils.setField(invoiceResource, "invoiceService", invoiceService);
        this.restInvoiceMockMvc = MockMvcBuilders.standaloneSetup(invoiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createEntity(EntityManager em) {
        Invoice invoice = new Invoice()
                .number(DEFAULT_NUMBER)
                .invoiceDate(DEFAULT_INVOICE_DATE)
                .invoiceDetails(DEFAULT_INVOICE_DETAILS);
        return invoice;
    }

    @Before
    public void initTest() {
        invoiceSearchRepository.deleteAll();
        invoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice

        restInvoiceMockMvc.perform(post("/api/invoices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoice)))
                .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoices.get(invoices.size() - 1);
        assertThat(testInvoice.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testInvoice.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoice.getInvoiceDetails()).isEqualTo(DEFAULT_INVOICE_DETAILS);

        // Validate the Invoice in ElasticSearch
        Invoice invoiceEs = invoiceSearchRepository.findOne(testInvoice.getId());
        assertThat(invoiceEs).isEqualToComparingFieldByField(testInvoice);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoices
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE_STR)))
                .andExpect(jsonPath("$.[*].invoiceDetails").value(hasItem(DEFAULT_INVOICE_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE_STR))
            .andExpect(jsonPath("$.invoiceDetails").value(DEFAULT_INVOICE_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findOne(invoice.getId());
        updatedInvoice
                .number(UPDATED_NUMBER)
                .invoiceDate(UPDATED_INVOICE_DATE)
                .invoiceDetails(UPDATED_INVOICE_DETAILS);

        restInvoiceMockMvc.perform(put("/api/invoices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedInvoice)))
                .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoices.get(invoices.size() - 1);
        assertThat(testInvoice.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testInvoice.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoice.getInvoiceDetails()).isEqualTo(UPDATED_INVOICE_DETAILS);

        // Validate the Invoice in ElasticSearch
        Invoice invoiceEs = invoiceSearchRepository.findOne(testInvoice.getId());
        assertThat(invoiceEs).isEqualToComparingFieldByField(testInvoice);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Get the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean invoiceExistsInEs = invoiceSearchRepository.exists(invoice.getId());
        assertThat(invoiceExistsInEs).isFalse();

        // Validate the database is empty
        List<Invoice> invoices = invoiceRepository.findAll();
        assertThat(invoices).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        // Search the invoice
        restInvoiceMockMvc.perform(get("/api/_search/invoices?query=id:" + invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE_STR)))
            .andExpect(jsonPath("$.[*].invoiceDetails").value(hasItem(DEFAULT_INVOICE_DETAILS.toString())));
    }
}
