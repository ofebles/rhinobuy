package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.Supplier;

import java.util.List;

/**
 * Service Interface for managing Supplier.
 */
public interface SupplierService {

    /**
     * Save a supplier.
     *
     * @param supplier the entity to save
     * @return the persisted entity
     */
    Supplier save(Supplier supplier);

    /**
     *  Get all the suppliers.
     *  
     *  @return the list of entities
     */
    List<Supplier> findAll();

    /**
     *  Get the "id" supplier.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Supplier findOne(Long id);

    /**
     *  Delete the "id" supplier.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplier corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Supplier> search(String query);
}
