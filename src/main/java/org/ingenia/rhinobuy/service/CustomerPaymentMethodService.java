package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.CustomerPaymentMethod;

import java.util.List;

/**
 * Service Interface for managing CustomerPaymentMethod.
 */
public interface CustomerPaymentMethodService {

    /**
     * Save a customerPaymentMethod.
     *
     * @param customerPaymentMethod the entity to save
     * @return the persisted entity
     */
    CustomerPaymentMethod save(CustomerPaymentMethod customerPaymentMethod);

    /**
     *  Get all the customerPaymentMethods.
     *  
     *  @return the list of entities
     */
    List<CustomerPaymentMethod> findAll();

    /**
     *  Get the "id" customerPaymentMethod.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerPaymentMethod findOne(Long id);

    /**
     *  Delete the "id" customerPaymentMethod.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the customerPaymentMethod corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<CustomerPaymentMethod> search(String query);
}
