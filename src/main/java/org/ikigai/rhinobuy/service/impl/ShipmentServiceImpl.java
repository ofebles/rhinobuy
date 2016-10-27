package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.ShipmentService;
import org.ikigai.rhinobuy.domain.Shipment;
import org.ikigai.rhinobuy.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Shipment.
 */
@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService{

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);
    
    @Inject
    private ShipmentRepository shipmentRepository;

    /**
     * Save a shipment.
     *
     * @param shipment the entity to save
     * @return the persisted entity
     */
    public Shipment save(Shipment shipment) {
        log.debug("Request to save Shipment : {}", shipment);
        Shipment result = shipmentRepository.save(shipment);
        return result;
    }

    /**
     *  Get all the shipments.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Shipment> findAll() {
        log.debug("Request to get all Shipments");
        List<Shipment> result = shipmentRepository.findAll();

        return result;
    }

    /**
     *  Get one shipment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Shipment findOne(Long id) {
        log.debug("Request to get Shipment : {}", id);
        Shipment shipment = shipmentRepository.findOne(id);
        return shipment;
    }

    /**
     *  Delete the  shipment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Shipment : {}", id);
        shipmentRepository.delete(id);
    }
}
