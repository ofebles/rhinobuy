package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.PictureService;
import org.ikigai.rhinobuy.domain.Picture;
import org.ikigai.rhinobuy.repository.PictureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Picture.
 */
@Service
@Transactional
public class PictureServiceImpl implements PictureService{

    private final Logger log = LoggerFactory.getLogger(PictureServiceImpl.class);
    
    @Inject
    private PictureRepository pictureRepository;

    /**
     * Save a picture.
     *
     * @param picture the entity to save
     * @return the persisted entity
     */
    public Picture save(Picture picture) {
        log.debug("Request to save Picture : {}", picture);
        Picture result = pictureRepository.save(picture);
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
    }
}
