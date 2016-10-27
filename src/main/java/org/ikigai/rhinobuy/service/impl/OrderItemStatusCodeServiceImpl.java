package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.OrderItemStatusCodeService;
import org.ikigai.rhinobuy.domain.OrderItemStatusCode;
import org.ikigai.rhinobuy.repository.OrderItemStatusCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing OrderItemStatusCode.
 */
@Service
@Transactional
public class OrderItemStatusCodeServiceImpl implements OrderItemStatusCodeService{

    private final Logger log = LoggerFactory.getLogger(OrderItemStatusCodeServiceImpl.class);
    
    @Inject
    private OrderItemStatusCodeRepository orderItemStatusCodeRepository;

    /**
     * Save a orderItemStatusCode.
     *
     * @param orderItemStatusCode the entity to save
     * @return the persisted entity
     */
    public OrderItemStatusCode save(OrderItemStatusCode orderItemStatusCode) {
        log.debug("Request to save OrderItemStatusCode : {}", orderItemStatusCode);
        OrderItemStatusCode result = orderItemStatusCodeRepository.save(orderItemStatusCode);
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
    }
}
