package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.PromotionService;
import org.ikigai.rhinobuy.domain.Promotion;
import org.ikigai.rhinobuy.repository.PromotionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Promotion.
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService{

    private final Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);
    
    @Inject
    private PromotionRepository promotionRepository;

    /**
     * Save a promotion.
     *
     * @param promotion the entity to save
     * @return the persisted entity
     */
    public Promotion save(Promotion promotion) {
        log.debug("Request to save Promotion : {}", promotion);
        Promotion result = promotionRepository.save(promotion);
        return result;
    }

    /**
     *  Get all the promotions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Promotion> findAll() {
        log.debug("Request to get all Promotions");
        List<Promotion> result = promotionRepository.findAll();

        return result;
    }

    /**
     *  Get one promotion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Promotion findOne(Long id) {
        log.debug("Request to get Promotion : {}", id);
        Promotion promotion = promotionRepository.findOne(id);
        return promotion;
    }

    /**
     *  Delete the  promotion by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Promotion : {}", id);
        promotionRepository.delete(id);
    }
}
