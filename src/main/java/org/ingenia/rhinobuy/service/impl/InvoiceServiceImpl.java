package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.InvoiceService;
import org.ingenia.rhinobuy.domain.Invoice;
import org.ingenia.rhinobuy.repository.InvoiceRepository;
import org.ingenia.rhinobuy.repository.search.InvoiceSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Invoice.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService{

    private final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    
    @Inject
    private InvoiceRepository invoiceRepository;

    @Inject
    private InvoiceSearchRepository invoiceSearchRepository;

    /**
     * Save a invoice.
     *
     * @param invoice the entity to save
     * @return the persisted entity
     */
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        Invoice result = invoiceRepository.save(invoice);
        invoiceSearchRepository.save(result);
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
        invoiceSearchRepository.delete(id);
    }

    /**
     * Search for the invoice corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Invoice> search(String query) {
        log.debug("Request to search Invoices for query {}", query);
        return StreamSupport
            .stream(invoiceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
