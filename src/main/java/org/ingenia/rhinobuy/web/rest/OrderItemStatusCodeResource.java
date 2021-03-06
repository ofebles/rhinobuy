package org.ingenia.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ingenia.rhinobuy.domain.OrderItemStatusCode;
import org.ingenia.rhinobuy.service.OrderItemStatusCodeService;
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
 * REST controller for managing OrderItemStatusCode.
 */
@RestController
@RequestMapping("/api")
public class OrderItemStatusCodeResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemStatusCodeResource.class);
        
    @Inject
    private OrderItemStatusCodeService orderItemStatusCodeService;

    /**
     * POST  /order-item-status-codes : Create a new orderItemStatusCode.
     *
     * @param orderItemStatusCode the orderItemStatusCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderItemStatusCode, or with status 400 (Bad Request) if the orderItemStatusCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-item-status-codes")
    @Timed
    public ResponseEntity<OrderItemStatusCode> createOrderItemStatusCode(@RequestBody OrderItemStatusCode orderItemStatusCode) throws URISyntaxException {
        log.debug("REST request to save OrderItemStatusCode : {}", orderItemStatusCode);
        if (orderItemStatusCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orderItemStatusCode", "idexists", "A new orderItemStatusCode cannot already have an ID")).body(null);
        }
        OrderItemStatusCode result = orderItemStatusCodeService.save(orderItemStatusCode);
        return ResponseEntity.created(new URI("/api/order-item-status-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orderItemStatusCode", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-item-status-codes : Updates an existing orderItemStatusCode.
     *
     * @param orderItemStatusCode the orderItemStatusCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderItemStatusCode,
     * or with status 400 (Bad Request) if the orderItemStatusCode is not valid,
     * or with status 500 (Internal Server Error) if the orderItemStatusCode couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-item-status-codes")
    @Timed
    public ResponseEntity<OrderItemStatusCode> updateOrderItemStatusCode(@RequestBody OrderItemStatusCode orderItemStatusCode) throws URISyntaxException {
        log.debug("REST request to update OrderItemStatusCode : {}", orderItemStatusCode);
        if (orderItemStatusCode.getId() == null) {
            return createOrderItemStatusCode(orderItemStatusCode);
        }
        OrderItemStatusCode result = orderItemStatusCodeService.save(orderItemStatusCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orderItemStatusCode", orderItemStatusCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-item-status-codes : get all the orderItemStatusCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderItemStatusCodes in body
     */
    @GetMapping("/order-item-status-codes")
    @Timed
    public List<OrderItemStatusCode> getAllOrderItemStatusCodes() {
        log.debug("REST request to get all OrderItemStatusCodes");
        return orderItemStatusCodeService.findAll();
    }

    /**
     * GET  /order-item-status-codes/:id : get the "id" orderItemStatusCode.
     *
     * @param id the id of the orderItemStatusCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderItemStatusCode, or with status 404 (Not Found)
     */
    @GetMapping("/order-item-status-codes/{id}")
    @Timed
    public ResponseEntity<OrderItemStatusCode> getOrderItemStatusCode(@PathVariable Long id) {
        log.debug("REST request to get OrderItemStatusCode : {}", id);
        OrderItemStatusCode orderItemStatusCode = orderItemStatusCodeService.findOne(id);
        return Optional.ofNullable(orderItemStatusCode)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /order-item-status-codes/:id : delete the "id" orderItemStatusCode.
     *
     * @param id the id of the orderItemStatusCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-item-status-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderItemStatusCode(@PathVariable Long id) {
        log.debug("REST request to delete OrderItemStatusCode : {}", id);
        orderItemStatusCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orderItemStatusCode", id.toString())).build();
    }

    /**
     * SEARCH  /_search/order-item-status-codes?query=:query : search for the orderItemStatusCode corresponding
     * to the query.
     *
     * @param query the query of the orderItemStatusCode search 
     * @return the result of the search
     */
    @GetMapping("/_search/order-item-status-codes")
    @Timed
    public List<OrderItemStatusCode> searchOrderItemStatusCodes(@RequestParam String query) {
        log.debug("REST request to search OrderItemStatusCodes for query {}", query);
        return orderItemStatusCodeService.search(query);
    }


}
