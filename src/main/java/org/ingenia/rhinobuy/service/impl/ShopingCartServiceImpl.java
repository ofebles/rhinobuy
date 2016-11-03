package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.ShopingCartService;
import org.ingenia.rhinobuy.domain.ShopingCart;
import org.ingenia.rhinobuy.repository.ShopingCartRepository;
import org.ingenia.rhinobuy.repository.search.ShopingCartSearchRepository;
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
 * Service Implementation for managing ShopingCart.
 */
@Service
@Transactional
public class ShopingCartServiceImpl implements ShopingCartService{

    private final Logger log = LoggerFactory.getLogger(ShopingCartServiceImpl.class);
    
    @Inject
    private ShopingCartRepository shopingCartRepository;

    @Inject
    private ShopingCartSearchRepository shopingCartSearchRepository;

    /**
     * Save a shopingCart.
     *
     * @param shopingCart the entity to save
     * @return the persisted entity
     */
    public ShopingCart save(ShopingCart shopingCart) {
        log.debug("Request to save ShopingCart : {}", shopingCart);
        ShopingCart result = shopingCartRepository.save(shopingCart);
        shopingCartSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the shopingCarts.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ShopingCart> findAll() {
        log.debug("Request to get all ShopingCarts");
        List<ShopingCart> result = shopingCartRepository.findAll();

        return result;
    }

    /**
     *  Get one shopingCart by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ShopingCart findOne(Long id) {
        log.debug("Request to get ShopingCart : {}", id);
        ShopingCart shopingCart = shopingCartRepository.findOne(id);
        return shopingCart;
    }

    /**
     *  Delete the  shopingCart by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ShopingCart : {}", id);
        shopingCartRepository.delete(id);
        shopingCartSearchRepository.delete(id);
    }

    /**
     * Search for the shopingCart corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ShopingCart> search(String query) {
        log.debug("Request to search ShopingCarts for query {}", query);
        return StreamSupport
            .stream(shopingCartSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
