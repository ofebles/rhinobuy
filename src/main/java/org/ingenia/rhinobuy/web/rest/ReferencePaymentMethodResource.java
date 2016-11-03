package org.ingenia.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ingenia.rhinobuy.domain.ReferencePaymentMethod;
import org.ingenia.rhinobuy.service.ReferencePaymentMethodService;
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
 * REST controller for managing ReferencePaymentMethod.
 */
@RestController
@RequestMapping("/api")
public class ReferencePaymentMethodResource {

    private final Logger log = LoggerFactory.getLogger(ReferencePaymentMethodResource.class);
        
    @Inject
    private ReferencePaymentMethodService referencePaymentMethodService;

    /**
     * POST  /reference-payment-methods : Create a new referencePaymentMethod.
     *
     * @param referencePaymentMethod the referencePaymentMethod to create
     * @return the ResponseEntity with status 201 (Created) and with body the new referencePaymentMethod, or with status 400 (Bad Request) if the referencePaymentMethod has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reference-payment-methods")
    @Timed
    public ResponseEntity<ReferencePaymentMethod> createReferencePaymentMethod(@RequestBody ReferencePaymentMethod referencePaymentMethod) throws URISyntaxException {
        log.debug("REST request to save ReferencePaymentMethod : {}", referencePaymentMethod);
        if (referencePaymentMethod.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("referencePaymentMethod", "idexists", "A new referencePaymentMethod cannot already have an ID")).body(null);
        }
        ReferencePaymentMethod result = referencePaymentMethodService.save(referencePaymentMethod);
        return ResponseEntity.created(new URI("/api/reference-payment-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("referencePaymentMethod", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reference-payment-methods : Updates an existing referencePaymentMethod.
     *
     * @param referencePaymentMethod the referencePaymentMethod to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated referencePaymentMethod,
     * or with status 400 (Bad Request) if the referencePaymentMethod is not valid,
     * or with status 500 (Internal Server Error) if the referencePaymentMethod couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reference-payment-methods")
    @Timed
    public ResponseEntity<ReferencePaymentMethod> updateReferencePaymentMethod(@RequestBody ReferencePaymentMethod referencePaymentMethod) throws URISyntaxException {
        log.debug("REST request to update ReferencePaymentMethod : {}", referencePaymentMethod);
        if (referencePaymentMethod.getId() == null) {
            return createReferencePaymentMethod(referencePaymentMethod);
        }
        ReferencePaymentMethod result = referencePaymentMethodService.save(referencePaymentMethod);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("referencePaymentMethod", referencePaymentMethod.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reference-payment-methods : get all the referencePaymentMethods.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of referencePaymentMethods in body
     */
    @GetMapping("/reference-payment-methods")
    @Timed
    public List<ReferencePaymentMethod> getAllReferencePaymentMethods() {
        log.debug("REST request to get all ReferencePaymentMethods");
        return referencePaymentMethodService.findAll();
    }

    /**
     * GET  /reference-payment-methods/:id : get the "id" referencePaymentMethod.
     *
     * @param id the id of the referencePaymentMethod to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the referencePaymentMethod, or with status 404 (Not Found)
     */
    @GetMapping("/reference-payment-methods/{id}")
    @Timed
    public ResponseEntity<ReferencePaymentMethod> getReferencePaymentMethod(@PathVariable Long id) {
        log.debug("REST request to get ReferencePaymentMethod : {}", id);
        ReferencePaymentMethod referencePaymentMethod = referencePaymentMethodService.findOne(id);
        return Optional.ofNullable(referencePaymentMethod)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /reference-payment-methods/:id : delete the "id" referencePaymentMethod.
     *
     * @param id the id of the referencePaymentMethod to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reference-payment-methods/{id}")
    @Timed
    public ResponseEntity<Void> deleteReferencePaymentMethod(@PathVariable Long id) {
        log.debug("REST request to delete ReferencePaymentMethod : {}", id);
        referencePaymentMethodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("referencePaymentMethod", id.toString())).build();
    }

    /**
     * SEARCH  /_search/reference-payment-methods?query=:query : search for the referencePaymentMethod corresponding
     * to the query.
     *
     * @param query the query of the referencePaymentMethod search 
     * @return the result of the search
     */
    @GetMapping("/_search/reference-payment-methods")
    @Timed
    public List<ReferencePaymentMethod> searchReferencePaymentMethods(@RequestParam String query) {
        log.debug("REST request to search ReferencePaymentMethods for query {}", query);
        return referencePaymentMethodService.search(query);
    }


}
