package org.ikigai.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ikigai.rhinobuy.domain.CustomerPaymentMethod;
import org.ikigai.rhinobuy.service.CustomerPaymentMethodService;
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
 * REST controller for managing CustomerPaymentMethod.
 */
@RestController
@RequestMapping("/api")
public class CustomerPaymentMethodResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentMethodResource.class);
        
    @Inject
    private CustomerPaymentMethodService customerPaymentMethodService;

    /**
     * POST  /customer-payment-methods : Create a new customerPaymentMethod.
     *
     * @param customerPaymentMethod the customerPaymentMethod to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerPaymentMethod, or with status 400 (Bad Request) if the customerPaymentMethod has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/customer-payment-methods",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerPaymentMethod> createCustomerPaymentMethod(@RequestBody CustomerPaymentMethod customerPaymentMethod) throws URISyntaxException {
        log.debug("REST request to save CustomerPaymentMethod : {}", customerPaymentMethod);
        if (customerPaymentMethod.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customerPaymentMethod", "idexists", "A new customerPaymentMethod cannot already have an ID")).body(null);
        }
        CustomerPaymentMethod result = customerPaymentMethodService.save(customerPaymentMethod);
        return ResponseEntity.created(new URI("/api/customer-payment-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customerPaymentMethod", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-payment-methods : Updates an existing customerPaymentMethod.
     *
     * @param customerPaymentMethod the customerPaymentMethod to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerPaymentMethod,
     * or with status 400 (Bad Request) if the customerPaymentMethod is not valid,
     * or with status 500 (Internal Server Error) if the customerPaymentMethod couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/customer-payment-methods",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerPaymentMethod> updateCustomerPaymentMethod(@RequestBody CustomerPaymentMethod customerPaymentMethod) throws URISyntaxException {
        log.debug("REST request to update CustomerPaymentMethod : {}", customerPaymentMethod);
        if (customerPaymentMethod.getId() == null) {
            return createCustomerPaymentMethod(customerPaymentMethod);
        }
        CustomerPaymentMethod result = customerPaymentMethodService.save(customerPaymentMethod);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customerPaymentMethod", customerPaymentMethod.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-payment-methods : get all the customerPaymentMethods.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerPaymentMethods in body
     */
    @RequestMapping(value = "/customer-payment-methods",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CustomerPaymentMethod> getAllCustomerPaymentMethods() {
        log.debug("REST request to get all CustomerPaymentMethods");
        return customerPaymentMethodService.findAll();
    }

    /**
     * GET  /customer-payment-methods/:id : get the "id" customerPaymentMethod.
     *
     * @param id the id of the customerPaymentMethod to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerPaymentMethod, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/customer-payment-methods/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomerPaymentMethod> getCustomerPaymentMethod(@PathVariable Long id) {
        log.debug("REST request to get CustomerPaymentMethod : {}", id);
        CustomerPaymentMethod customerPaymentMethod = customerPaymentMethodService.findOne(id);
        return Optional.ofNullable(customerPaymentMethod)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customer-payment-methods/:id : delete the "id" customerPaymentMethod.
     *
     * @param id the id of the customerPaymentMethod to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/customer-payment-methods/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCustomerPaymentMethod(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPaymentMethod : {}", id);
        customerPaymentMethodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customerPaymentMethod", id.toString())).build();
    }

}
