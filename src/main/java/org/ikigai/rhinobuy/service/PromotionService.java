package org.ikigai.rhinobuy.service;

import org.ikigai.rhinobuy.domain.Promotion;

import java.util.List;

/**
 * Service Interface for managing Promotion.
 */
public interface PromotionService {

    /**
     * Save a promotion.
     *
     * @param promotion the entity to save
     * @return the persisted entity
     */
    Promotion save(Promotion promotion);

    /**
     *  Get all the promotions.
     *  
     *  @return the list of entities
     */
    List<Promotion> findAll();

    /**
     *  Get the "id" promotion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Promotion findOne(Long id);

    /**
     *  Delete the "id" promotion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
