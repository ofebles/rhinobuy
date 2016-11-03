package org.ingenia.rhinobuy.web.rest;

import org.ingenia.rhinobuy.RhinobuyApp;

import org.ingenia.rhinobuy.domain.Shipment;
import org.ingenia.rhinobuy.repository.ShipmentRepository;
import org.ingenia.rhinobuy.service.ShipmentService;
import org.ingenia.rhinobuy.repository.search.ShipmentSearchRepository;

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
 * Test class for the ShipmentResource REST controller.
 *
 * @see ShipmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RhinobuyApp.class)
public class ShipmentResourceIntTest {

    private static final String DEFAULT_TRACKING_NUMBER = "AAAAA";
    private static final String UPDATED_TRACKING_NUMBER = "BBBBB";

    private static final ZonedDateTime DEFAULT_SHIPMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SHIPMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SHIPMENT_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_SHIPMENT_DATE);

    private static final String DEFAULT_OTHER_DETAILS = "AAAAA";
    private static final String UPDATED_OTHER_DETAILS = "BBBBB";

    @Inject
    private ShipmentRepository shipmentRepository;

    @Inject
    private ShipmentService shipmentService;

    @Inject
    private ShipmentSearchRepository shipmentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restShipmentMockMvc;

    private Shipment shipment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShipmentResource shipmentResource = new ShipmentResource();
        ReflectionTestUtils.setField(shipmentResource, "shipmentService", shipmentService);
        this.restShipmentMockMvc = MockMvcBuilders.standaloneSetup(shipmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipment createEntity(EntityManager em) {
        Shipment shipment = new Shipment()
                .trackingNumber(DEFAULT_TRACKING_NUMBER)
                .shipmentDate(DEFAULT_SHIPMENT_DATE)
                .otherDetails(DEFAULT_OTHER_DETAILS);
        return shipment;
    }

    @Before
    public void initTest() {
        shipmentSearchRepository.deleteAll();
        shipment = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipment() throws Exception {
        int databaseSizeBeforeCreate = shipmentRepository.findAll().size();

        // Create the Shipment

        restShipmentMockMvc.perform(post("/api/shipments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shipment)))
                .andExpect(status().isCreated());

        // Validate the Shipment in the database
        List<Shipment> shipments = shipmentRepository.findAll();
        assertThat(shipments).hasSize(databaseSizeBeforeCreate + 1);
        Shipment testShipment = shipments.get(shipments.size() - 1);
        assertThat(testShipment.getTrackingNumber()).isEqualTo(DEFAULT_TRACKING_NUMBER);
        assertThat(testShipment.getShipmentDate()).isEqualTo(DEFAULT_SHIPMENT_DATE);
        assertThat(testShipment.getOtherDetails()).isEqualTo(DEFAULT_OTHER_DETAILS);

        // Validate the Shipment in ElasticSearch
        Shipment shipmentEs = shipmentSearchRepository.findOne(testShipment.getId());
        assertThat(shipmentEs).isEqualToComparingFieldByField(testShipment);
    }

    @Test
    @Transactional
    public void getAllShipments() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get all the shipments
        restShipmentMockMvc.perform(get("/api/shipments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(shipment.getId().intValue())))
                .andExpect(jsonPath("$.[*].trackingNumber").value(hasItem(DEFAULT_TRACKING_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE_STR)))
                .andExpect(jsonPath("$.[*].otherDetails").value(hasItem(DEFAULT_OTHER_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get the shipment
        restShipmentMockMvc.perform(get("/api/shipments/{id}", shipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipment.getId().intValue()))
            .andExpect(jsonPath("$.trackingNumber").value(DEFAULT_TRACKING_NUMBER.toString()))
            .andExpect(jsonPath("$.shipmentDate").value(DEFAULT_SHIPMENT_DATE_STR))
            .andExpect(jsonPath("$.otherDetails").value(DEFAULT_OTHER_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipment() throws Exception {
        // Get the shipment
        restShipmentMockMvc.perform(get("/api/shipments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipment() throws Exception {
        // Initialize the database
        shipmentService.save(shipment);

        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // Update the shipment
        Shipment updatedShipment = shipmentRepository.findOne(shipment.getId());
        updatedShipment
                .trackingNumber(UPDATED_TRACKING_NUMBER)
                .shipmentDate(UPDATED_SHIPMENT_DATE)
                .otherDetails(UPDATED_OTHER_DETAILS);

        restShipmentMockMvc.perform(put("/api/shipments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedShipment)))
                .andExpect(status().isOk());

        // Validate the Shipment in the database
        List<Shipment> shipments = shipmentRepository.findAll();
        assertThat(shipments).hasSize(databaseSizeBeforeUpdate);
        Shipment testShipment = shipments.get(shipments.size() - 1);
        assertThat(testShipment.getTrackingNumber()).isEqualTo(UPDATED_TRACKING_NUMBER);
        assertThat(testShipment.getShipmentDate()).isEqualTo(UPDATED_SHIPMENT_DATE);
        assertThat(testShipment.getOtherDetails()).isEqualTo(UPDATED_OTHER_DETAILS);

        // Validate the Shipment in ElasticSearch
        Shipment shipmentEs = shipmentSearchRepository.findOne(testShipment.getId());
        assertThat(shipmentEs).isEqualToComparingFieldByField(testShipment);
    }

    @Test
    @Transactional
    public void deleteShipment() throws Exception {
        // Initialize the database
        shipmentService.save(shipment);

        int databaseSizeBeforeDelete = shipmentRepository.findAll().size();

        // Get the shipment
        restShipmentMockMvc.perform(delete("/api/shipments/{id}", shipment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean shipmentExistsInEs = shipmentSearchRepository.exists(shipment.getId());
        assertThat(shipmentExistsInEs).isFalse();

        // Validate the database is empty
        List<Shipment> shipments = shipmentRepository.findAll();
        assertThat(shipments).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShipment() throws Exception {
        // Initialize the database
        shipmentService.save(shipment);

        // Search the shipment
        restShipmentMockMvc.perform(get("/api/_search/shipments?query=id:" + shipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].trackingNumber").value(hasItem(DEFAULT_TRACKING_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE_STR)))
            .andExpect(jsonPath("$.[*].otherDetails").value(hasItem(DEFAULT_OTHER_DETAILS.toString())));
    }
}
