package org.ingenia.rhinobuy.service.impl;

import org.ingenia.rhinobuy.service.SupplierService;
import org.ingenia.rhinobuy.domain.Supplier;
import org.ingenia.rhinobuy.repository.SupplierRepository;
import org.ingenia.rhinobuy.repository.search.SupplierSearchRepository;
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
 * Service Implementation for managing Supplier.
 */
@Service
@Transactional
public class SupplierServiceImpl implements SupplierService{

    private final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);
    
    @Inject
    private SupplierRepository supplierRepository;

    @Inject
    private SupplierSearchRepository supplierSearchRepository;

    /**
     * Save a supplier.
     *
     * @param supplier the entity to save
     * @return the persisted entity
     */
    public Supplier save(Supplier supplier) {
        log.debug("Request to save Supplier : {}", supplier);
        Supplier result = supplierRepository.save(supplier);
        supplierSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the suppliers.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Supplier> findAll() {
        log.debug("Request to get all Suppliers");
        List<Supplier> result = supplierRepository.findAllWithEagerRelationships();

        return result;
    }

    /**
     *  Get one supplier by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Supplier findOne(Long id) {
        log.debug("Request to get Supplier : {}", id);
        Supplier supplier = supplierRepository.findOneWithEagerRelationships(id);
        return supplier;
    }

    /**
     *  Delete the  supplier by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Supplier : {}", id);
        supplierRepository.delete(id);
        supplierSearchRepository.delete(id);
    }

    /**
     * Search for the supplier corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Supplier> search(String query) {
        log.debug("Request to search Suppliers for query {}", query);
        return StreamSupport
            .stream(supplierSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
