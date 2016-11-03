package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.ReferenceInvoiceStatusService;
import org.ingenia.rhinobuy.domain.ReferenceInvoiceStatus;
import org.ingenia.rhinobuy.repository.ReferenceInvoiceStatusRepository;
import org.ingenia.rhinobuy.repository.search.ReferenceInvoiceStatusSearchRepository;
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
 * Service Implementation for managing ReferenceInvoiceStatus.
 */
@Service
@Transactional
public class ReferenceInvoiceStatusServiceImpl implements ReferenceInvoiceStatusService{

    private final Logger log = LoggerFactory.getLogger(ReferenceInvoiceStatusServiceImpl.class);
    
    @Inject
    private ReferenceInvoiceStatusRepository referenceInvoiceStatusRepository;

    @Inject
    private ReferenceInvoiceStatusSearchRepository referenceInvoiceStatusSearchRepository;

    /**
     * Save a referenceInvoiceStatus.
     *
     * @param referenceInvoiceStatus the entity to save
     * @return the persisted entity
     */
    public ReferenceInvoiceStatus save(ReferenceInvoiceStatus referenceInvoiceStatus) {
        log.debug("Request to save ReferenceInvoiceStatus : {}", referenceInvoiceStatus);
        ReferenceInvoiceStatus result = referenceInvoiceStatusRepository.save(referenceInvoiceStatus);
        referenceInvoiceStatusSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the referenceInvoiceStatuses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ReferenceInvoiceStatus> findAll() {
        log.debug("Request to get all ReferenceInvoiceStatuses");
        List<ReferenceInvoiceStatus> result = referenceInvoiceStatusRepository.findAll();

        return result;
    }

    /**
     *  Get one referenceInvoiceStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ReferenceInvoiceStatus findOne(Long id) {
        log.debug("Request to get ReferenceInvoiceStatus : {}", id);
        ReferenceInvoiceStatus referenceInvoiceStatus = referenceInvoiceStatusRepository.findOne(id);
        return referenceInvoiceStatus;
    }

    /**
     *  Delete the  referenceInvoiceStatus by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ReferenceInvoiceStatus : {}", id);
        referenceInvoiceStatusRepository.delete(id);
        referenceInvoiceStatusSearchRepository.delete(id);
    }

    /**
     * Search for the referenceInvoiceStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReferenceInvoiceStatus> search(String query) {
        log.debug("Request to search ReferenceInvoiceStatuses for query {}", query);
        return StreamSupport
            .stream(referenceInvoiceStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
