package org.ikigai.rhinobuy.service;

import org.ikigai.rhinobuy.domain.ReferencePaymentMethod;

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
}
