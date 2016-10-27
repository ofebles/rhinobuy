package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.ShopingCartService;
import org.ikigai.rhinobuy.domain.ShopingCart;
import org.ikigai.rhinobuy.repository.ShopingCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ShopingCart.
 */
@Service
@Transactional
public class ShopingCartServiceImpl implements ShopingCartService{

    private final Logger log = LoggerFactory.getLogger(ShopingCartServiceImpl.class);
    
    @Inject
    private ShopingCartRepository shopingCartRepository;

    /**
     * Save a shopingCart.
     *
     * @param shopingCart the entity to save
     * @return the persisted entity
     */
    public ShopingCart save(ShopingCart shopingCart) {
        log.debug("Request to save ShopingCart : {}", shopingCart);
        ShopingCart result = shopingCartRepository.save(shopingCart);
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
    }
}
