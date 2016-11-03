package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.ProductDescriptionService;
import org.ingenia.rhinobuy.domain.ProductDescription;
import org.ingenia.rhinobuy.repository.ProductDescriptionRepository;
import org.ingenia.rhinobuy.repository.search.ProductDescriptionSearchRepository;
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
 * Service Implementation for managing ProductDescription.
 */
@Service
@Transactional
public class ProductDescriptionServiceImpl implements ProductDescriptionService{

    private final Logger log = LoggerFactory.getLogger(ProductDescriptionServiceImpl.class);
    
    @Inject
    private ProductDescriptionRepository productDescriptionRepository;

    @Inject
    private ProductDescriptionSearchRepository productDescriptionSearchRepository;

    /**
     * Save a productDescription.
     *
     * @param productDescription the entity to save
     * @return the persisted entity
     */
    public ProductDescription save(ProductDescription productDescription) {
        log.debug("Request to save ProductDescription : {}", productDescription);
        ProductDescription result = productDescriptionRepository.save(productDescription);
        productDescriptionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the productDescriptions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ProductDescription> findAll() {
        log.debug("Request to get all ProductDescriptions");
        List<ProductDescription> result = productDescriptionRepository.findAll();

        return result;
    }

    /**
     *  Get one productDescription by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProductDescription findOne(Long id) {
        log.debug("Request to get ProductDescription : {}", id);
        ProductDescription productDescription = productDescriptionRepository.findOne(id);
        return productDescription;
    }

    /**
     *  Delete the  productDescription by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductDescription : {}", id);
        productDescriptionRepository.delete(id);
        productDescriptionSearchRepository.delete(id);
    }

    /**
     * Search for the productDescription corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProductDescription> search(String query) {
        log.debug("Request to search ProductDescriptions for query {}", query);
        return StreamSupport
            .stream(productDescriptionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
