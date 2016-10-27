package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.WishListService;
import org.ikigai.rhinobuy.domain.WishList;
import org.ikigai.rhinobuy.repository.WishListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing WishList.
 */
@Service
@Transactional
public class WishListServiceImpl implements WishListService{

    private final Logger log = LoggerFactory.getLogger(WishListServiceImpl.class);
    
    @Inject
    private WishListRepository wishListRepository;

    /**
     * Save a wishList.
     *
     * @param wishList the entity to save
     * @return the persisted entity
     */
    public WishList save(WishList wishList) {
        log.debug("Request to save WishList : {}", wishList);
        WishList result = wishListRepository.save(wishList);
        return result;
    }

    /**
     *  Get all the wishLists.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<WishList> findAll() {
        log.debug("Request to get all WishLists");
        List<WishList> result = wishListRepository.findAllWithEagerRelationships();

        return result;
    }

    /**
     *  Get one wishList by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WishList findOne(Long id) {
        log.debug("Request to get WishList : {}", id);
        WishList wishList = wishListRepository.findOneWithEagerRelationships(id);
        return wishList;
    }

    /**
     *  Delete the  wishList by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WishList : {}", id);
        wishListRepository.delete(id);
    }
}
