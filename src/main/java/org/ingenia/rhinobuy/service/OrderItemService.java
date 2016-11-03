package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.OrderItem;

import java.util.List;

/**
 * Service Interface for managing OrderItem.
 */
public interface OrderItemService {

    /**
     * Save a orderItem.
     *
     * @param orderItem the entity to save
     * @return the persisted entity
     */
    OrderItem save(OrderItem orderItem);

    /**
     *  Get all the orderItems.
     *  
     *  @return the list of entities
     */
    List<OrderItem> findAll();

    /**
     *  Get the "id" orderItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrderItem findOne(Long id);

    /**
     *  Delete the "id" orderItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orderItem corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<OrderItem> search(String query);
}
