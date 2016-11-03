package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.ReferencePaymentMethod;

import java.util.List;

/**
 * Service Interface for managing ReferencePaymentMethod.
 */
public interface ReferencePaymentMethodService {

    /**
     * Save a referencePaymentMethod.
     *
     * @param referencePaymentMethod the entity to save
     * @return the persisted entity
     */
    ReferencePaymentMethod save(ReferencePaymentMethod referencePaymentMethod);

    /**
     *  Get all the referencePaymentMethods.
     *  
     *  @return the list of entities
     */
    List<ReferencePaymentMethod> findAll();

    /**
     *  Get the "id" referencePaymentMethod.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReferencePaymentMethod findOne(Long id);

    /**
     *  Delete the "id" referencePaymentMethod.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the referencePaymentMethod corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ReferencePaymentMethod> search(String query);
}
