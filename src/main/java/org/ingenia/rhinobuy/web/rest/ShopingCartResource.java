package org.ingenia.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ingenia.rhinobuy.domain.ShopingCart;
import org.ingenia.rhinobuy.service.ShopingCartService;
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
 * REST controller for managing ShopingCart.
 */
@RestController
@RequestMapping("/api")
public class ShopingCartResource {

    private final Logger log = LoggerFactory.getLogger(ShopingCartResource.class);
        
    @Inject
    private ShopingCartService shopingCartService;

    /**
     * POST  /shoping-carts : Create a new shopingCart.
     *
     * @param shopingCart the shopingCart to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shopingCart, or with status 400 (Bad Request) if the shopingCart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shoping-carts")
    @Timed
    public ResponseEntity<ShopingCart> createShopingCart(@RequestBody ShopingCart shopingCart) throws URISyntaxException {
        log.debug("REST request to save ShopingCart : {}", shopingCart);
        if (shopingCart.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("shopingCart", "idexists", "A new shopingCart cannot already have an ID")).body(null);
        }
        ShopingCart result = shopingCartService.save(shopingCart);
        return ResponseEntity.created(new URI("/api/shoping-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("shopingCart", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shoping-carts : Updates an existing shopingCart.
     *
     * @param shopingCart the shopingCart to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shopingCart,
     * or with status 400 (Bad Request) if the shopingCart is not valid,
     * or with status 500 (Internal Server Error) if the shopingCart couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shoping-carts")
    @Timed
    public ResponseEntity<ShopingCart> updateShopingCart(@RequestBody ShopingCart shopingCart) throws URISyntaxException {
        log.debug("REST request to update ShopingCart : {}", shopingCart);
        if (shopingCart.getId() == null) {
            return createShopingCart(shopingCart);
        }
        ShopingCart result = shopingCartService.save(shopingCart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("shopingCart", shopingCart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shoping-carts : get all the shopingCarts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shopingCarts in body
     */
    @GetMapping("/shoping-carts")
    @Timed
    public List<ShopingCart> getAllShopingCarts() {
        log.debug("REST request to get all ShopingCarts");
        return shopingCartService.findAll();
    }

    /**
     * GET  /shoping-carts/:id : get the "id" shopingCart.
     *
     * @param id the id of the shopingCart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shopingCart, or with status 404 (Not Found)
     */
    @GetMapping("/shoping-carts/{id}")
    @Timed
    public ResponseEntity<ShopingCart> getShopingCart(@PathVariable Long id) {
        log.debug("REST request to get ShopingCart : {}", id);
        ShopingCart shopingCart = shopingCartService.findOne(id);
        return Optional.ofNullable(shopingCart)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shoping-carts/:id : delete the "id" shopingCart.
     *
     * @param id the id of the shopingCart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shoping-carts/{id}")
    @Timed
    public ResponseEntity<Void> deleteShopingCart(@PathVariable Long id) {
        log.debug("REST request to delete ShopingCart : {}", id);
        shopingCartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("shopingCart", id.toString())).build();
    }

    /**
     * SEARCH  /_search/shoping-carts?query=:query : search for the shopingCart corresponding
     * to the query.
     *
     * @param query the query of the shopingCart search 
     * @return the result of the search
     */
    @GetMapping("/_search/shoping-carts")
    @Timed
    public List<ShopingCart> searchShopingCarts(@RequestParam String query) {
        log.debug("REST request to search ShopingCarts for query {}", query);
        return shopingCartService.search(query);
    }


}
