package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.Orders;

import java.util.List;

/**
 * Service Interface for managing Orders.
 */
public interface OrdersService {

    /**
     * Save a orders.
     *
     * @param orders the entity to save
     * @return the persisted entity
     */
    Orders save(Orders orders);

    /**
     *  Get all the orders.
     *  
     *  @return the list of entities
     */
    List<Orders> findAll();

    /**
     *  Get the "id" orders.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Orders findOne(Long id);

    /**
     *  Delete the "id" orders.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orders corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Orders> search(String query);
}
