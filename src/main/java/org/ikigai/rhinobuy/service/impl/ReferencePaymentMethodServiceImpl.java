package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.ReferencePaymentMethodService;
import org.ikigai.rhinobuy.domain.ReferencePaymentMethod;
import org.ikigai.rhinobuy.repository.ReferencePaymentMethodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ReferencePaymentMethod.
 */
@Service
@Transactional
public class ReferencePaymentMethodServiceImpl implements ReferencePaymentMethodService{

    private final Logger log = LoggerFactory.getLogger(ReferencePaymentMethodServiceImpl.class);
    
    @Inject
    private ReferencePaymentMethodRepository referencePaymentMethodRepository;

    /**
     * Save a referencePaymentMethod.
     *
     * @param referencePaymentMethod the entity to save
     * @return the persisted entity
     */
    public ReferencePaymentMethod save(ReferencePaymentMethod referencePaymentMethod) {
        log.debug("Request to save ReferencePaymentMethod : {}", referencePaymentMethod);
        ReferencePaymentMethod result = referencePaymentMethodRepository.save(referencePaymentMethod);
        return result;
    }

    /**
     *  Get all the referencePaymentMethods.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ReferencePaymentMethod> findAll() {
        log.debug("Request to get all ReferencePaymentMethods");
        List<ReferencePaymentMethod> result = referencePaymentMethodRepository.findAll();

        return result;
    }

    /**
     *  Get one referencePaymentMethod by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ReferencePaymentMethod findOne(Long id) {
        log.debug("Request to get ReferencePaymentMethod : {}", id);
        ReferencePaymentMethod referencePaymentMethod = referencePaymentMethodRepository.findOne(id);
        return referencePaymentMethod;
    }

    /**
     *  Delete the  referencePaymentMethod by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ReferencePaymentMethod : {}", id);
        referencePaymentMethodRepository.delete(id);
    }
}
