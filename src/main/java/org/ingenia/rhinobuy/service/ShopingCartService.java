package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.ShopingCart;

import java.util.List;

/**
 * Service Interface for managing ShopingCart.
 */
public interface ShopingCartService {

    /**
     * Save a shopingCart.
     *
     * @param shopingCart the entity to save
     * @return the persisted entity
     */
    ShopingCart save(ShopingCart shopingCart);

    /**
     *  Get all the shopingCarts.
     *  
     *  @return the list of entities
     */
    List<ShopingCart> findAll();

    /**
     *  Get the "id" shopingCart.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShopingCart findOne(Long id);

    /**
     *  Delete the "id" shopingCart.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shopingCart corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ShopingCart> search(String query);
}
