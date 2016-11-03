package org.ingenia.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ingenia.rhinobuy.domain.Invoice;
import org.ingenia.rhinobuy.service.InvoiceService;
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
 * REST controller for managing Invoice.
 */
@RestController
@RequestMapping("/api")
public class InvoiceResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);
        
    @Inject
    private InvoiceService invoiceService;

    /**
     * POST  /invoices : Create a new invoice.
     *
     * @param invoice the invoice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoice, or with status 400 (Bad Request) if the invoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoices")
    @Timed
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) throws URISyntaxException {
        log.debug("REST request to save Invoice : {}", invoice);
        if (invoice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("invoice", "idexists", "A new invoice cannot already have an ID")).body(null);
        }
        Invoice result = invoiceService.save(invoice);
        return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("invoice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoices : Updates an existing invoice.
     *
     * @param invoice the invoice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoice,
     * or with status 400 (Bad Request) if the invoice is not valid,
     * or with status 500 (Internal Server Error) if the invoice couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoices")
    @Timed
    public ResponseEntity<Invoice> updateInvoice(@RequestBody Invoice invoice) throws URISyntaxException {
        log.debug("REST request to update Invoice : {}", invoice);
        if (invoice.getId() == null) {
            return createInvoice(invoice);
        }
        Invoice result = invoiceService.save(invoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("invoice", invoice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoices : get all the invoices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoices in body
     */
    @GetMapping("/invoices")
    @Timed
    public List<Invoice> getAllInvoices() {
        log.debug("REST request to get all Invoices");
        return invoiceService.findAll();
    }

    /**
     * GET  /invoices/:id : get the "id" invoice.
     *
     * @param id the id of the invoice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoice, or with status 404 (Not Found)
     */
    @GetMapping("/invoices/{id}")
    @Timed
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
        log.debug("REST request to get Invoice : {}", id);
        Invoice invoice = invoiceService.findOne(id);
        return Optional.ofNullable(invoice)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /invoices/:id : delete the "id" invoice.
     *
     * @param id the id of the invoice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoices/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        log.debug("REST request to delete Invoice : {}", id);
        invoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("invoice", id.toString())).build();
    }

    /**
     * SEARCH  /_search/invoices?query=:query : search for the invoice corresponding
     * to the query.
     *
     * @param query the query of the invoice search 
     * @return the result of the search
     */
    @GetMapping("/_search/invoices")
    @Timed
    public List<Invoice> searchInvoices(@RequestParam String query) {
        log.debug("REST request to search Invoices for query {}", query);
        return invoiceService.search(query);
    }


}
