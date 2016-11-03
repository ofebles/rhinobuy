package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.ReferenceLanguageService;
import org.ingenia.rhinobuy.domain.ReferenceLanguage;
import org.ingenia.rhinobuy.repository.ReferenceLanguageRepository;
import org.ingenia.rhinobuy.repository.search.ReferenceLanguageSearchRepository;
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
 * Service Implementation for managing ReferenceLanguage.
 */
@Service
@Transactional
public class ReferenceLanguageServiceImpl implements ReferenceLanguageService{

    private final Logger log = LoggerFactory.getLogger(ReferenceLanguageServiceImpl.class);
    
    @Inject
    private ReferenceLanguageRepository referenceLanguageRepository;

    @Inject
    private ReferenceLanguageSearchRepository referenceLanguageSearchRepository;

    /**
     * Save a referenceLanguage.
     *
     * @param referenceLanguage the entity to save
     * @return the persisted entity
     */
    public ReferenceLanguage save(ReferenceLanguage referenceLanguage) {
        log.debug("Request to save ReferenceLanguage : {}", referenceLanguage);
        ReferenceLanguage result = referenceLanguageRepository.save(referenceLanguage);
        referenceLanguageSearchRepository.save(result);
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
        referenceLanguageSearchRepository.delete(id);
    }

    /**
     * Search for the referenceLanguage corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReferenceLanguage> search(String query) {
        log.debug("Request to search ReferenceLanguages for query {}", query);
        return StreamSupport
            .stream(referenceLanguageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
