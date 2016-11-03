package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.ProductDescription;

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

    /**
     * Search for the productDescription corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ProductDescription> search(String query);
}
