package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.InvoiceService;
import org.ikigai.rhinobuy.domain.Invoice;
import org.ikigai.rhinobuy.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Invoice.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService{

    private final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    
    @Inject
    private InvoiceRepository invoiceRepository;

    /**
     * Save a invoice.
     *
     * @param invoice the entity to save
     * @return the persisted entity
     */
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        Invoice result = invoiceRepository.save(invoice);
        return result;
    }

    /**
     *  Get all the invoices.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Invoice> findAll() {
        log.debug("Request to get all Invoices");
        List<Invoice> result = invoiceRepository.findAll();

        return result;
    }

    /**
     *  Get one invoice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Invoice findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        Invoice invoice = invoiceRepository.findOne(id);
        return invoice;
    }

    /**
     *  Delete the  invoice by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepository.delete(id);
    }
}
