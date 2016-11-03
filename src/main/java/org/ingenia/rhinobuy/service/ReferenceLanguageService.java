package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.ReferenceLanguage;

import java.util.List;

/**
 * Service Interface for managing ReferenceLanguage.
 */
public interface ReferenceLanguageService {

    /**
     * Save a referenceLanguage.
     *
     * @param referenceLanguage the entity to save
     * @return the persisted entity
     */
    ReferenceLanguage save(ReferenceLanguage referenceLanguage);

    /**
     *  Get all the referenceLanguages.
     *  
     *  @return the list of entities
     */
    List<ReferenceLanguage> findAll();

    /**
     *  Get the "id" referenceLanguage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReferenceLanguage findOne(Long id);

    /**
     *  Delete the "id" referenceLanguage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the referenceLanguage corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ReferenceLanguage> search(String query);
}
