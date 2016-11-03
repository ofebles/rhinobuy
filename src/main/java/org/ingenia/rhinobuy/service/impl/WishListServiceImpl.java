package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.WishListService;
import org.ingenia.rhinobuy.domain.WishList;
import org.ingenia.rhinobuy.repository.WishListRepository;
import org.ingenia.rhinobuy.repository.search.WishListSearchRepository;
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
 * Service Implementation for managing WishList.
 */
@Service
@Transactional
public class WishListServiceImpl implements WishListService{

    private final Logger log = LoggerFactory.getLogger(WishListServiceImpl.class);
    
    @Inject
    private WishListRepository wishListRepository;

    @Inject
    private WishListSearchRepository wishListSearchRepository;

    /**
     * Save a wishList.
     *
     * @param wishList the entity to save
     * @return the persisted entity
     */
    public WishList save(WishList wishList) {
        log.debug("Request to save WishList : {}", wishList);
        WishList result = wishListRepository.save(wishList);
        wishListSearchRepository.save(result);
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
        wishListSearchRepository.delete(id);
    }

    /**
     * Search for the wishList corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WishList> search(String query) {
        log.debug("Request to search WishLists for query {}", query);
        return StreamSupport
            .stream(wishListSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
