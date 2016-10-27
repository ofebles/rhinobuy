package org.ikigai.rhinobuy.service;

import org.ikigai.rhinobuy.domain.ProductDescription;

import java.util.List;

/**
 * Service Interface for managing ProductDescription.
 */
public interface ProductDescriptionService {

    /**
     * Save a productDescription.
     *
     * @param productDescription the entity to save
     * @return the persisted entity
     */
    ProductDescription save(ProductDescription productDescription);

    /**
     *  Get all the productDescriptions.
     *  
     *  @return the list of entities
     */
    List<ProductDescription> findAll();

    /**
     *  Get the "id" productDescription.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductDescription findOne(Long id);

    /**
     *  Delete the "id" productDescription.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
