package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.OrderItemStatusCodeService;
import org.ingenia.rhinobuy.domain.OrderItemStatusCode;
import org.ingenia.rhinobuy.repository.OrderItemStatusCodeRepository;
import org.ingenia.rhinobuy.repository.search.OrderItemStatusCodeSearchRepository;
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
 * Service Implementation for managing OrderItemStatusCode.
 */
@Service
@Transactional
public class OrderItemStatusCodeServiceImpl implements OrderItemStatusCodeService{

    private final Logger log = LoggerFactory.getLogger(OrderItemStatusCodeServiceImpl.class);
    
    @Inject
    private OrderItemStatusCodeRepository orderItemStatusCodeRepository;

    @Inject
    private OrderItemStatusCodeSearchRepository orderItemStatusCodeSearchRepository;

    /**
     * Save a orderItemStatusCode.
     *
     * @param orderItemStatusCode the entity to save
     * @return the persisted entity
     */
    public OrderItemStatusCode save(OrderItemStatusCode orderItemStatusCode) {
        log.debug("Request to save OrderItemStatusCode : {}", orderItemStatusCode);
        OrderItemStatusCode result = orderItemStatusCodeRepository.save(orderItemStatusCode);
        orderItemStatusCodeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the orderItemStatusCodes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<OrderItemStatusCode> findAll() {
        log.debug("Request to get all OrderItemStatusCodes");
        List<OrderItemStatusCode> result = orderItemStatusCodeRepository.findAll();

        return result;
    }

    /**
     *  Get one orderItemStatusCode by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OrderItemStatusCode findOne(Long id) {
        log.debug("Request to get OrderItemStatusCode : {}", id);
        OrderItemStatusCode orderItemStatusCode = orderItemStatusCodeRepository.findOne(id);
        return orderItemStatusCode;
    }

    /**
     *  Delete the  orderItemStatusCode by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderItemStatusCode : {}", id);
        orderItemStatusCodeRepository.delete(id);
        orderItemStatusCodeSearchRepository.delete(id);
    }

    /**
     * Search for the orderItemStatusCode corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrderItemStatusCode> search(String query) {
        log.debug("Request to search OrderItemStatusCodes for query {}", query);
        return StreamSupport
            .stream(orderItemStatusCodeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
