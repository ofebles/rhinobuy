package org.ingenia.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ingenia.rhinobuy.domain.ProductDescription;
import org.ingenia.rhinobuy.service.ProductDescriptionService;
import org.ingenia.rhinobuy.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProductDescription.
 */
@RestController
@RequestMapping("/api")
public class ProductDescriptionResource {

    private final Logger log = LoggerFactory.getLogger(ProductDescriptionResource.class);
        
    @Inject
    private ProductDescriptionService productDescriptionService;

    /**
     * POST  /product-descriptions : Create a new productDescription.
     *
     * @param productDescription the productDescription to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productDescription, or with status 400 (Bad Request) if the productDescription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-descriptions")
    @Timed
    public ResponseEntity<ProductDescription> createProductDescription(@RequestBody ProductDescription productDescription) throws URISyntaxException {
        log.debug("REST request to save ProductDescription : {}", productDescription);
        if (productDescription.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productDescription", "idexists", "A new productDescription cannot already have an ID")).body(null);
        }
        ProductDescription result = productDescriptionService.save(productDescription);
        return ResponseEntity.created(new URI("/api/product-descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productDescription", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-descriptions : Updates an existing productDescription.
     *
     * @param productDescription the productDescription to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productDescription,
     * or with status 400 (Bad Request) if the productDescription is not valid,
     * or with status 500 (Internal Server Error) if the productDescription couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-descriptions")
    @Timed
    public ResponseEntity<ProductDescription> updateProductDescription(@RequestBody ProductDescription productDescription) throws URISyntaxException {
        log.debug("REST request to update ProductDescription : {}", productDescription);
        if (productDescription.getId() == null) {
            return createProductDescription(productDescription);
        }
        ProductDescription result = productDescriptionService.save(productDescription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productDescription", productDescription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-descriptions : get all the productDescriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productDescriptions in body
     */
    @GetMapping("/product-descriptions")
    @Timed
    public List<ProductDescription> getAllProductDescriptions() {
        log.debug("REST request to get all ProductDescriptions");
        return productDescriptionService.findAll();
    }

    /**
     * GET  /product-descriptions/:id : get the "id" productDescription.
     *
     * @param id the id of the productDescription to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productDescription, or with status 404 (Not Found)
     */
    @GetMapping("/product-descriptions/{id}")
    @Timed
    public ResponseEntity<ProductDescription> getProductDescription(@PathVariable Long id) {
        log.debug("REST request to get ProductDescription : {}", id);
        ProductDescription productDescription = productDescriptionService.findOne(id);
        return Optional.ofNullable(productDescription)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /product-descriptions/:id : delete the "id" productDescription.
     *
     * @param id the id of the productDescription to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-descriptions/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductDescription(@PathVariable Long id) {
        log.debug("REST request to delete ProductDescription : {}", id);
        productDescriptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productDescription", id.toString())).build();
    }

    /**
     * SEARCH  /_search/product-descriptions?query=:query : search for the productDescription corresponding
     * to the query.
     *
     * @param query the query of the productDescription search 
     * @return the result of the search
     */
    @GetMapping("/_search/product-descriptions")
    @Timed
    public List<ProductDescription> searchProductDescriptions(@RequestParam String query) {
        log.debug("REST request to search ProductDescriptions for query {}", query);
        return productDescriptionService.search(query);
    }


}
