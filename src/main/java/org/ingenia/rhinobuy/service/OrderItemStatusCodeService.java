package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.OrderItemStatusCode;

import java.util.List;

/**
 * Service Interface for managing OrderItemStatusCode.
 */
public interface OrderItemStatusCodeService {

    /**
     * Save a orderItemStatusCode.
     *
     * @param orderItemStatusCode the entity to save
     * @return the persisted entity
     */
    OrderItemStatusCode save(OrderItemStatusCode orderItemStatusCode);

    /**
     *  Get all the orderItemStatusCodes.
     *  
     *  @return the list of entities
     */
    List<OrderItemStatusCode> findAll();

    /**
     *  Get the "id" orderItemStatusCode.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrderItemStatusCode findOne(Long id);

    /**
     *  Delete the "id" orderItemStatusCode.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orderItemStatusCode corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<OrderItemStatusCode> search(String query);
}
