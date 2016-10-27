package org.ikigai.rhinobuy.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.ikigai.rhinobuy.domain.Casa;

import org.ikigai.rhinobuy.repository.CasaRepository;
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
 * REST controller for managing Casa.
 */
@RestController
@RequestMapping("/api")
public class CasaResource {

    private final Logger log = LoggerFactory.getLogger(CasaResource.class);
        
    @Inject
    private CasaRepository casaRepository;

    /**
     * POST  /casas : Create a new casa.
     *
     * @param casa the casa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new casa, or with status 400 (Bad Request) if the casa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/casas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Casa> createCasa(@RequestBody Casa casa) throws URISyntaxException {
        log.debug("REST request to save Casa : {}", casa);
        if (casa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("casa", "idexists", "A new casa cannot already have an ID")).body(null);
        }
        Casa result = casaRepository.save(casa);
        return ResponseEntity.created(new URI("/api/casas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("casa", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /casas : Updates an existing casa.
     *
     * @param casa the casa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated casa,
     * or with status 400 (Bad Request) if the casa is not valid,
     * or with status 500 (Internal Server Error) if the casa couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/casas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Casa> updateCasa(@RequestBody Casa casa) throws URISyntaxException {
        log.debug("REST request to update Casa : {}", casa);
        if (casa.getId() == null) {
            return createCasa(casa);
        }
        Casa result = casaRepository.save(casa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("casa", casa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /casas : get all the casas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of casas in body
     */
    @RequestMapping(value = "/casas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Casa> getAllCasas() {
        log.debug("REST request to get all Casas");
        List<Casa> casas = casaRepository.findAll();
        return casas;
    }

    /**
     * GET  /casas/:id : get the "id" casa.
     *
     * @param id the id of the casa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the casa, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/casas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Casa> getCasa(@PathVariable Long id) {
        log.debug("REST request to get Casa : {}", id);
        Casa casa = casaRepository.findOne(id);
        return Optional.ofNullable(casa)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /casas/:id : delete the "id" casa.
     *
     * @param id the id of the casa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/casas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCasa(@PathVariable Long id) {
        log.debug("REST request to delete Casa : {}", id);
        casaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("casa", id.toString())).build();
    }

}
