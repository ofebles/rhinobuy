package org.ikigai.rhinobuy.service;

import org.ikigai.rhinobuy.domain.OrderItemStatusCode;

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
}
