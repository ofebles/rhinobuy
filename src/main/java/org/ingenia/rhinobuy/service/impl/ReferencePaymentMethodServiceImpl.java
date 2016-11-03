package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.ReferencePaymentMethodService;
import org.ingenia.rhinobuy.domain.ReferencePaymentMethod;
import org.ingenia.rhinobuy.repository.ReferencePaymentMethodRepository;
import org.ingenia.rhinobuy.repository.search.ReferencePaymentMethodSearchRepository;
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
 * Service Implementation for managing ReferencePaymentMethod.
 */
@Service
@Transactional
public class ReferencePaymentMethodServiceImpl implements ReferencePaymentMethodService{

    private final Logger log = LoggerFactory.getLogger(ReferencePaymentMethodServiceImpl.class);
    
    @Inject
    private ReferencePaymentMethodRepository referencePaymentMethodRepository;

    @Inject
    private ReferencePaymentMethodSearchRepository referencePaymentMethodSearchRepository;

    /**
     * Save a referencePaymentMethod.
     *
     * @param referencePaymentMethod the entity to save
     * @return the persisted entity
     */
    public ReferencePaymentMethod save(ReferencePaymentMethod referencePaymentMethod) {
        log.debug("Request to save ReferencePaymentMethod : {}", referencePaymentMethod);
        ReferencePaymentMethod result = referencePaymentMethodRepository.save(referencePaymentMethod);
        referencePaymentMethodSearchRepository.save(result);
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
        referencePaymentMethodSearchRepository.delete(id);
    }

    /**
     * Search for the referencePaymentMethod corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReferencePaymentMethod> search(String query) {
        log.debug("Request to search ReferencePaymentMethods for query {}", query);
        return StreamSupport
            .stream(referencePaymentMethodSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
