package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.Shipment;

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

    /**
     * Search for the shipment corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Shipment> search(String query);
}
