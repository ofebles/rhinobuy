package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.PaymentService;
import org.ingenia.rhinobuy.domain.Payment;
import org.ingenia.rhinobuy.repository.PaymentRepository;
import org.ingenia.rhinobuy.repository.search.PaymentSearchRepository;
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
 * Service Implementation for managing Payment.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
    
    @Inject
    private PaymentRepository paymentRepository;

    @Inject
    private PaymentSearchRepository paymentSearchRepository;

    /**
     * Save a payment.
     *
     * @param payment the entity to save
     * @return the persisted entity
     */
    public Payment save(Payment payment) {
        log.debug("Request to save Payment : {}", payment);
        Payment result = paymentRepository.save(payment);
        paymentSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the payments.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Payment> findAll() {
        log.debug("Request to get all Payments");
        List<Payment> result = paymentRepository.findAll();

        return result;
    }

    /**
     *  Get one payment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Payment findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        Payment payment = paymentRepository.findOne(id);
        return payment;
    }

    /**
     *  Delete the  payment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.delete(id);
        paymentSearchRepository.delete(id);
    }

    /**
     * Search for the payment corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Payment> search(String query) {
        log.debug("Request to search Payments for query {}", query);
        return StreamSupport
            .stream(paymentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
