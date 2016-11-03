package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.CustomerPaymentMethodService;
import org.ingenia.rhinobuy.domain.CustomerPaymentMethod;
import org.ingenia.rhinobuy.repository.CustomerPaymentMethodRepository;
import org.ingenia.rhinobuy.repository.search.CustomerPaymentMethodSearchRepository;
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
 * Service Implementation for managing CustomerPaymentMethod.
 */
@Service
@Transactional
public class CustomerPaymentMethodServiceImpl implements CustomerPaymentMethodService{

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentMethodServiceImpl.class);
    
    @Inject
    private CustomerPaymentMethodRepository customerPaymentMethodRepository;

    @Inject
    private CustomerPaymentMethodSearchRepository customerPaymentMethodSearchRepository;

    /**
     * Save a customerPaymentMethod.
     *
     * @param customerPaymentMethod the entity to save
     * @return the persisted entity
     */
    public CustomerPaymentMethod save(CustomerPaymentMethod customerPaymentMethod) {
        log.debug("Request to save CustomerPaymentMethod : {}", customerPaymentMethod);
        CustomerPaymentMethod result = customerPaymentMethodRepository.save(customerPaymentMethod);
        customerPaymentMethodSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the customerPaymentMethods.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CustomerPaymentMethod> findAll() {
        log.debug("Request to get all CustomerPaymentMethods");
        List<CustomerPaymentMethod> result = customerPaymentMethodRepository.findAll();

        return result;
    }

    /**
     *  Get one customerPaymentMethod by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CustomerPaymentMethod findOne(Long id) {
        log.debug("Request to get CustomerPaymentMethod : {}", id);
        CustomerPaymentMethod customerPaymentMethod = customerPaymentMethodRepository.findOne(id);
        return customerPaymentMethod;
    }

    /**
     *  Delete the  customerPaymentMethod by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerPaymentMethod : {}", id);
        customerPaymentMethodRepository.delete(id);
        customerPaymentMethodSearchRepository.delete(id);
    }

    /**
     * Search for the customerPaymentMethod corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CustomerPaymentMethod> search(String query) {
        log.debug("Request to search CustomerPaymentMethods for query {}", query);
        return StreamSupport
            .stream(customerPaymentMethodSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
