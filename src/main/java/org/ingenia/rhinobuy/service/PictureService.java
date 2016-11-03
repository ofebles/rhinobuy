package org.ingenia.rhinobuy.service;

import org.ingenia.rhinobuy.domain.Picture;

import java.util.List;

/**
 * Service Interface for managing Picture.
 */
public interface PictureService {

    /**
     * Save a picture.
     *
     * @param picture the entity to save
     * @return the persisted entity
     */
    Picture save(Picture picture);

    /**
     *  Get all the pictures.
     *  
     *  @return the list of entities
     */
    List<Picture> findAll();

    /**
     *  Get the "id" picture.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Picture findOne(Long id);

    /**
     *  Delete the "id" picture.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the picture corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Picture> search(String query);
}
