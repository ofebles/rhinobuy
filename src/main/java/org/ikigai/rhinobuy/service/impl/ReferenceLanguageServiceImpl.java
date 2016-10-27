package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.ReferenceLanguageService;
import org.ikigai.rhinobuy.domain.ReferenceLanguage;
import org.ikigai.rhinobuy.repository.ReferenceLanguageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ReferenceLanguage.
 */
@Service
@Transactional
public class ReferenceLanguageServiceImpl implements ReferenceLanguageService{

    private final Logger log = LoggerFactory.getLogger(ReferenceLanguageServiceImpl.class);
    
    @Inject
    private ReferenceLanguageRepository referenceLanguageRepository;

    /**
     * Save a referenceLanguage.
     *
     * @param referenceLanguage the entity to save
     * @return the persisted entity
     */
    public ReferenceLanguage save(ReferenceLanguage referenceLanguage) {
        log.debug("Request to save ReferenceLanguage : {}", referenceLanguage);
        ReferenceLanguage result = referenceLanguageRepository.save(referenceLanguage);
        return result;
    }

    /**
     *  Get all the referenceLanguages.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ReferenceLanguage> findAll() {
        log.debug("Request to get all ReferenceLanguages");
        List<ReferenceLanguage> result = referenceLanguageRepository.findAll();

        return result;
    }

    /**
     *  Get one referenceLanguage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ReferenceLanguage findOne(Long id) {
        log.debug("Request to get ReferenceLanguage : {}", id);
        ReferenceLanguage referenceLanguage = referenceLanguageRepository.findOne(id);
        return referenceLanguage;
    }

    /**
     *  Delete the  referenceLanguage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ReferenceLanguage : {}", id);
        referenceLanguageRepository.delete(id);
    }
}
