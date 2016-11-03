package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.PictureService;
import org.ingenia.rhinobuy.domain.Picture;
import org.ingenia.rhinobuy.repository.PictureRepository;
import org.ingenia.rhinobuy.repository.search.PictureSearchRepository;
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
 * Service Implementation for managing Picture.
 */
@Service
@Transactional
public class PictureServiceImpl implements PictureService{

    private final Logger log = LoggerFactory.getLogger(PictureServiceImpl.class);
    
    @Inject
    private PictureRepository pictureRepository;

    @Inject
    private PictureSearchRepository pictureSearchRepository;

    /**
     * Save a picture.
     *
     * @param picture the entity to save
     * @return the persisted entity
     */
    public Picture save(Picture picture) {
        log.debug("Request to save Picture : {}", picture);
        Picture result = pictureRepository.save(picture);
        pictureSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the pictures.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Picture> findAll() {
        log.debug("Request to get all Pictures");
        List<Picture> result = pictureRepository.findAll();

        return result;
    }

    /**
     *  Get one picture by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Picture findOne(Long id) {
        log.debug("Request to get Picture : {}", id);
        Picture picture = pictureRepository.findOne(id);
        return picture;
    }

    /**
     *  Delete the  picture by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Picture : {}", id);
        pictureRepository.delete(id);
        pictureSearchRepository.delete(id);
    }

    /**
     * Search for the picture corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Picture> search(String query) {
        log.debug("Request to search Pictures for query {}", query);
        return StreamSupport
            .stream(pictureSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
