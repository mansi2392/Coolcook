package com.coolcook.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coolcook.app.domain.IngredientMaster;
import com.coolcook.app.service.IngredientMasterService;
import com.coolcook.app.web.rest.util.HeaderUtil;
import com.coolcook.app.web.rest.util.PaginationUtil;
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

/**
 * REST controller for managing IngredientMaster.
 */
@RestController
@RequestMapping("/api")
public class IngredientMasterResource {

    private final Logger log = LoggerFactory.getLogger(IngredientMasterResource.class);

    private static final String ENTITY_NAME = "ingredientMaster";

    private final IngredientMasterService ingredientMasterService;

    public IngredientMasterResource(IngredientMasterService ingredientMasterService) {
        this.ingredientMasterService = ingredientMasterService;
    }

    /**
     * POST  /ingredient-masters : Create a new ingredientMaster.
     *
     * @param ingredientMaster the ingredientMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ingredientMaster, or with status 400 (Bad Request) if the ingredientMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ingredient-masters")
    @Timed
    public ResponseEntity<IngredientMaster> createIngredientMaster(@RequestBody IngredientMaster ingredientMaster) throws URISyntaxException {
        log.debug("REST request to save IngredientMaster : {}", ingredientMaster);
        if (ingredientMaster.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ingredientMaster cannot already have an ID")).body(null);
        }
        IngredientMaster result = ingredientMasterService.save(ingredientMaster);
        return ResponseEntity.created(new URI("/api/ingredient-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ingredient-masters : Updates an existing ingredientMaster.
     *
     * @param ingredientMaster the ingredientMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ingredientMaster,
     * or with status 400 (Bad Request) if the ingredientMaster is not valid,
     * or with status 500 (Internal Server Error) if the ingredientMaster couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ingredient-masters")
    @Timed
    public ResponseEntity<IngredientMaster> updateIngredientMaster(@RequestBody IngredientMaster ingredientMaster) throws URISyntaxException {
        log.debug("REST request to update IngredientMaster : {}", ingredientMaster);
        if (ingredientMaster.getId() == null) {
            return createIngredientMaster(ingredientMaster);
        }
        IngredientMaster result = ingredientMasterService.save(ingredientMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ingredientMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ingredient-masters : get all the ingredientMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ingredientMasters in body
     */
    @GetMapping("/ingredient-masters")
    @Timed
    public ResponseEntity<List<IngredientMaster>> getAllIngredientMasters(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of IngredientMasters");
        Page<IngredientMaster> page = ingredientMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ingredient-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ingredient-masters/:id : get the "id" ingredientMaster.
     *
     * @param id the id of the ingredientMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ingredientMaster, or with status 404 (Not Found)
     */
    @GetMapping("/ingredient-masters/{id}")
    @Timed
    public ResponseEntity<IngredientMaster> getIngredientMaster(@PathVariable Long id) {
        log.debug("REST request to get IngredientMaster : {}", id);
        IngredientMaster ingredientMaster = ingredientMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ingredientMaster));
    }

    /**
     * DELETE  /ingredient-masters/:id : delete the "id" ingredientMaster.
     *
     * @param id the id of the ingredientMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ingredient-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteIngredientMaster(@PathVariable Long id) {
        log.debug("REST request to delete IngredientMaster : {}", id);
        ingredientMasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
