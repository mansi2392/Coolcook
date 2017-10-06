package com.coolcook.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coolcook.domain.Quantity;

import com.coolcook.repository.QuantityRepository;
import com.coolcook.repository.search.QuantitySearchRepository;
import com.coolcook.web.rest.util.HeaderUtil;
import com.coolcook.service.dto.QuantityDTO;
import com.coolcook.service.mapper.QuantityMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Quantity.
 */
@RestController
@RequestMapping("/api")
public class QuantityResource {

    private final Logger log = LoggerFactory.getLogger(QuantityResource.class);

    private static final String ENTITY_NAME = "quantity";

    private final QuantityRepository quantityRepository;

    private final QuantityMapper quantityMapper;

    private final QuantitySearchRepository quantitySearchRepository;

    public QuantityResource(QuantityRepository quantityRepository, QuantityMapper quantityMapper, QuantitySearchRepository quantitySearchRepository) {
        this.quantityRepository = quantityRepository;
        this.quantityMapper = quantityMapper;
        this.quantitySearchRepository = quantitySearchRepository;
    }

    /**
     * POST  /quantities : Create a new quantity.
     *
     * @param quantityDTO the quantityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quantityDTO, or with status 400 (Bad Request) if the quantity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quantities")
    @Timed
    public ResponseEntity<QuantityDTO> createQuantity(@RequestBody QuantityDTO quantityDTO) throws URISyntaxException {
        log.debug("REST request to save Quantity : {}", quantityDTO);
        if (quantityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new quantity cannot already have an ID")).body(null);
        }
        Quantity quantity = quantityMapper.toEntity(quantityDTO);
        quantity = quantityRepository.save(quantity);
        QuantityDTO result = quantityMapper.toDto(quantity);
        quantitySearchRepository.save(quantity);
        return ResponseEntity.created(new URI("/api/quantities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quantities : Updates an existing quantity.
     *
     * @param quantityDTO the quantityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quantityDTO,
     * or with status 400 (Bad Request) if the quantityDTO is not valid,
     * or with status 500 (Internal Server Error) if the quantityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quantities")
    @Timed
    public ResponseEntity<QuantityDTO> updateQuantity(@RequestBody QuantityDTO quantityDTO) throws URISyntaxException {
        log.debug("REST request to update Quantity : {}", quantityDTO);
        if (quantityDTO.getId() == null) {
            return createQuantity(quantityDTO);
        }
        Quantity quantity = quantityMapper.toEntity(quantityDTO);
        quantity = quantityRepository.save(quantity);
        QuantityDTO result = quantityMapper.toDto(quantity);
        quantitySearchRepository.save(quantity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quantityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quantities : get all the quantities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of quantities in body
     */
    @GetMapping("/quantities")
    @Timed
    public List<QuantityDTO> getAllQuantities() {
        log.debug("REST request to get all Quantities");
        List<Quantity> quantities = quantityRepository.findAll();
        return quantityMapper.toDto(quantities);
    }

    /**
     * GET  /quantities/:id : get the "id" quantity.
     *
     * @param id the id of the quantityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quantityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/quantities/{id}")
    @Timed
    public ResponseEntity<QuantityDTO> getQuantity(@PathVariable Long id) {
        log.debug("REST request to get Quantity : {}", id);
        Quantity quantity = quantityRepository.findOne(id);
        QuantityDTO quantityDTO = quantityMapper.toDto(quantity);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quantityDTO));
    }

    /**
     * DELETE  /quantities/:id : delete the "id" quantity.
     *
     * @param id the id of the quantityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quantities/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuantity(@PathVariable Long id) {
        log.debug("REST request to delete Quantity : {}", id);
        quantityRepository.delete(id);
        quantitySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/quantities?query=:query : search for the quantity corresponding
     * to the query.
     *
     * @param query the query of the quantity search
     * @return the result of the search
     */
    @GetMapping("/_search/quantities")
    @Timed
    public List<QuantityDTO> searchQuantities(@RequestParam String query) {
        log.debug("REST request to search Quantities for query {}", query);
        return StreamSupport
            .stream(quantitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(quantityMapper::toDto)
            .collect(Collectors.toList());
    }

}
