package org.ikigai.rhinobuy.service.impl;

import org.ikigai.rhinobuy.service.CategoryService;
import org.ikigai.rhinobuy.domain.Category;
import org.ikigai.rhinobuy.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Category.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    
    @Inject
    private CategoryRepository categoryRepository;

    /**
     * Save a category.
     *
     * @param category the entity to save
     * @return the persisted entity
     */
    public Category save(Category category) {
        log.debug("Request to save Category : {}", category);
        Category result = categoryRepository.save(category);
        return result;
    }

    /**
     *  Get all the categories.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Category> findAll() {
        log.debug("Request to get all Categories");
        List<Category> result = categoryRepository.findAll();

        return result;
    }

    /**
     *  Get one category by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Category findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        Category category = categoryRepository.findOne(id);
        return category;
    }

    /**
     *  Delete the  category by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.delete(id);
    }
}
