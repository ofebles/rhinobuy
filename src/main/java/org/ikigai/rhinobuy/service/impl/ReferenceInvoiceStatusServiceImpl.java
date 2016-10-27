package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.ReferenceInvoiceStatusService;
import org.ikigai.rhinobuy.domain.ReferenceInvoiceStatus;
import org.ikigai.rhinobuy.repository.ReferenceInvoiceStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ReferenceInvoiceStatus.
 */
@Service
@Transactional
public class ReferenceInvoiceStatusServiceImpl implements ReferenceInvoiceStatusService{

    private final Logger log = LoggerFactory.getLogger(ReferenceInvoiceStatusServiceImpl.class);
    
    @Inject
    private ReferenceInvoiceStatusRepository referenceInvoiceStatusRepository;

    /**
     * Save a referenceInvoiceStatus.
     *
     * @param referenceInvoiceStatus the entity to save
     * @return the persisted entity
     */
    public ReferenceInvoiceStatus save(ReferenceInvoiceStatus referenceInvoiceStatus) {
        log.debug("Request to save ReferenceInvoiceStatus : {}", referenceInvoiceStatus);
        ReferenceInvoiceStatus result = referenceInvoiceStatusRepository.save(referenceInvoiceStatus);
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
    }
}
