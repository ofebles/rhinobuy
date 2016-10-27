package org.ikigai.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ikigai.rhinobuy.domain.Shipment;
import org.ikigai.rhinobuy.service.ShipmentService;
import org.ikigai.rhinobuy.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Shipment.
 */
@RestController
@RequestMapping("/api")
public class ShipmentResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentResource.class);
        
    @Inject
    private ShipmentService shipmentService;

    /**
     * POST  /shipments : Create a new shipment.
     *
     * @param shipment the shipment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipment, or with status 400 (Bad Request) if the shipment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/shipments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) throws URISyntaxException {
        log.debug("REST request to save Shipment : {}", shipment);
        if (shipment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("shipment", "idexists", "A new shipment cannot already have an ID")).body(null);
        }
        Shipment result = shipmentService.save(shipment);
        return ResponseEntity.created(new URI("/api/shipments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("shipment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipments : Updates an existing shipment.
     *
     * @param shipment the shipment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipment,
     * or with status 400 (Bad Request) if the shipment is not valid,
     * or with status 500 (Internal Server Error) if the shipment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/shipments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Shipment> updateShipment(@RequestBody Shipment shipment) throws URISyntaxException {
        log.debug("REST request to update Shipment : {}", shipment);
        if (shipment.getId() == null) {
            return createShipment(shipment);
        }
        Shipment result = shipmentService.save(shipment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("shipment", shipment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipments : get all the shipments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipments in body
     */
    @RequestMapping(value = "/shipments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Shipment> getAllShipments() {
        log.debug("REST request to get all Shipments");
        return shipmentService.findAll();
    }

    /**
     * GET  /shipments/:id : get the "id" shipment.
     *
     * @param id the id of the shipment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipment, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/shipments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Shipment> getShipment(@PathVariable Long id) {
        log.debug("REST request to get Shipment : {}", id);
        Shipment shipment = shipmentService.findOne(id);
        return Optional.ofNullable(shipment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shipments/:id : delete the "id" shipment.
     *
     * @param id the id of the shipment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/shipments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        log.debug("REST request to delete Shipment : {}", id);
        shipmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("shipment", id.toString())).build();
    }

}
