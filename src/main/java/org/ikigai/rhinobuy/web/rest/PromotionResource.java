package org.ikigai.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ikigai.rhinobuy.domain.Promotion;
import org.ikigai.rhinobuy.service.PromotionService;
import org.ikigai.rhinobuy.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Promotion.
 */
@RestController
@RequestMapping("/api")
public class PromotionResource {

    private final Logger log = LoggerFactory.getLogger(PromotionResource.class);
        
    @Inject
    private PromotionService promotionService;

    /**
     * POST  /promotions : Create a new promotion.
     *
     * @param promotion the promotion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new promotion, or with status 400 (Bad Request) if the promotion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/promotions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) throws URISyntaxException {
        log.debug("REST request to save Promotion : {}", promotion);
        if (promotion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("promotion", "idexists", "A new promotion cannot already have an ID")).body(null);
        }
        Promotion result = promotionService.save(promotion);
        return ResponseEntity.created(new URI("/api/promotions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("promotion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /promotions : Updates an existing promotion.
     *
     * @param promotion the promotion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated promotion,
     * or with status 400 (Bad Request) if the promotion is not valid,
     * or with status 500 (Internal Server Error) if the promotion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/promotions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Promotion> updatePromotion(@RequestBody Promotion promotion) throws URISyntaxException {
        log.debug("REST request to update Promotion : {}", promotion);
        if (promotion.getId() == null) {
            return createPromotion(promotion);
        }
        Promotion result = promotionService.save(promotion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("promotion", promotion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /promotions : get all the promotions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of promotions in body
     */
    @RequestMapping(value = "/promotions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Promotion> getAllPromotions() {
        log.debug("REST request to get all Promotions");
        return promotionService.findAll();
    }

    /**
     * GET  /promotions/:id : get the "id" promotion.
     *
     * @param id the id of the promotion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the promotion, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/promotions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Promotion> getPromotion(@PathVariable Long id) {
        log.debug("REST request to get Promotion : {}", id);
        Promotion promotion = promotionService.findOne(id);
        return Optional.ofNullable(promotion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /promotions/:id : delete the "id" promotion.
     *
     * @param id the id of the promotion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/promotions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        log.debug("REST request to delete Promotion : {}", id);
        promotionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("promotion", id.toString())).build();
    }

}
