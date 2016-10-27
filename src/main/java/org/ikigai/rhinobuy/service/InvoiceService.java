package org.ikigai.rhinobuy.service;

import org.ikigai.rhinobuy.domain.Invoice;

import java.util.List;

/**
 * Service Interface for managing Invoice.
 */
public interface InvoiceService {

    /**
     * Save a invoice.
     *
     * @param invoice the entity to save
     * @return the persisted entity
     */
    Invoice save(Invoice invoice);

    /**
     *  Get all the invoices.
     *  
     *  @return the list of entities
     */
    List<Invoice> findAll();

    /**
     *  Get the "id" invoice.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Invoice findOne(Long id);

    /**
     *  Delete the "id" invoice.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
