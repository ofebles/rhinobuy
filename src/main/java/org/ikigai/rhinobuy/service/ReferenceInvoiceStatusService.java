package org.ikigai.rhinobuy.service;

import org.ikigai.rhinobuy.domain.ReferenceInvoiceStatus;

import java.util.List;

/**
 * Service Interface for managing ReferenceInvoiceStatus.
 */
public interface ReferenceInvoiceStatusService {

    /**
     * Save a referenceInvoiceStatus.
     *
     * @param referenceInvoiceStatus the entity to save
     * @return the persisted entity
     */
    ReferenceInvoiceStatus save(ReferenceInvoiceStatus referenceInvoiceStatus);

    /**
     *  Get all the referenceInvoiceStatuses.
     *  
     *  @return the list of entities
     */
    List<ReferenceInvoiceStatus> findAll();

    /**
     *  Get the "id" referenceInvoiceStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReferenceInvoiceStatus findOne(Long id);

    /**
     *  Delete the "id" referenceInvoiceStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
