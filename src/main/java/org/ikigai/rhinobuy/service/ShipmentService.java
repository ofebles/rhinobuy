package org.ikigai.rhinobuy.service;

import org.ikigai.rhinobuy.domain.Shipment;

import java.util.List;

/**
 * Service Interface for managing Shipment.
 */
public interface ShipmentService {

    /**
     * Save a shipment.
     *
     * @param shipment the entity to save
     * @return the persisted entity
     */
    Shipment save(Shipment shipment);

    /**
     *  Get all the shipments.
     *  
     *  @return the list of entities
     */
    List<Shipment> findAll();

    /**
     *  Get the "id" shipment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Shipment findOne(Long id);

    /**
     *  Delete the "id" shipment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
