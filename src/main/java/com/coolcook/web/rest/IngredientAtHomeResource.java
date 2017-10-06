package com.coolcook.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coolcook.service.IngredientAtHomeService;
import com.coolcook.web.rest.util.HeaderUtil;
import com.coolcook.web.rest.util.PaginationUtil;
import com.coolcook.service.dto.IngredientAtHomeDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing IngredientAtHome.
 */
@RestController
@RequestMapping("/api")
public class IngredientAtHomeResource {

    private final Logger log = LoggerFactory.getLogger(IngredientAtHomeResource.class);

    private static final String ENTITY_NAME = "ingredientAtHome";

    private final IngredientAtHomeService ingredientAtHomeService;

    public IngredientAtHomeResource(IngredientAtHomeService ingredientAtHomeService) {
        this.ingredientAtHomeService = ingredientAtHomeService;
    }

    /**
     * POST  /ingredient-at-homes : Create a new ingredientAtHome.
     *
     * @param ingredientAtHomeDTO the ingredientAtHomeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ingredientAtHomeDTO, or with status 400 (Bad Request) if the ingredientAtHome has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ingredient-at-homes")
    @Timed
    public ResponseEntity<IngredientAtHomeDTO> createIngredientAtHome(@RequestBody IngredientAtHomeDTO ingredientAtHomeDTO) throws URISyntaxException {
        log.debug("REST request to save IngredientAtHome : {}", ingredientAtHomeDTO);
        if (ingredientAtHomeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ingredientAtHome cannot already have an ID")).body(null);
        }
        IngredientAtHomeDTO result = ingredientAtHomeService.save(ingredientAtHomeDTO);
        return ResponseEntity.created(new URI("/api/ingredient-at-homes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ingredient-at-homes : Updates an existing ingredientAtHome.
     *
     * @param ingredientAtHomeDTO the ingredientAtHomeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ingredientAtHomeDTO,
     * or with status 400 (Bad Request) if the ingredientAtHomeDTO is not valid,
     * or with status 500 (Internal Server Error) if the ingredientAtHomeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ingredient-at-homes")
    @Timed
    public ResponseEntity<IngredientAtHomeDTO> updateIngredientAtHome(@RequestBody IngredientAtHomeDTO ingredientAtHomeDTO) throws URISyntaxException {
        log.debug("REST request to update IngredientAtHome : {}", ingredientAtHomeDTO);
        if (ingredientAtHomeDTO.getId() == null) {
            return createIngredientAtHome(ingredientAtHomeDTO);
        }
        IngredientAtHomeDTO result = ingredientAtHomeService.save(ingredientAtHomeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ingredientAtHomeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ingredient-at-homes : get all the ingredientAtHomes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ingredientAtHomes in body
     */
    @GetMapping("/ingredient-at-homes")
    @Timed
    public ResponseEntity<List<IngredientAtHomeDTO>> getAllIngredientAtHomes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of IngredientAtHomes");
        Page<IngredientAtHomeDTO> page = ingredientAtHomeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ingredient-at-homes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ingredient-at-homes/:id : get the "id" ingredientAtHome.
     *
     * @param id the id of the ingredientAtHomeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ingredientAtHomeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ingredient-at-homes/{id}")
    @Timed
    public ResponseEntity<IngredientAtHomeDTO> getIngredientAtHome(@PathVariable Long id) {
        log.debug("REST request to get IngredientAtHome : {}", id);
        IngredientAtHomeDTO ingredientAtHomeDTO = ingredientAtHomeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ingredientAtHomeDTO));
    }

    /**
     * DELETE  /ingredient-at-homes/:id : delete the "id" ingredientAtHome.
     *
     * @param id the id of the ingredientAtHomeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ingredient-at-homes/{id}")
    @Timed
    public ResponseEntity<Void> deleteIngredientAtHome(@PathVariable Long id) {
        log.debug("REST request to delete IngredientAtHome : {}", id);
        ingredientAtHomeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ingredient-at-homes?query=:query : search for the ingredientAtHome corresponding
     * to the query.
     *
     * @param query the query of the ingredientAtHome search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ingredient-at-homes")
    @Timed
    public ResponseEntity<List<IngredientAtHomeDTO>> searchIngredientAtHomes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of IngredientAtHomes for query {}", query);
        Page<IngredientAtHomeDTO> page = ingredientAtHomeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ingredient-at-homes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
