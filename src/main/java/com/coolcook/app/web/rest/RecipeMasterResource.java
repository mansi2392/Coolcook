package com.coolcook.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coolcook.app.service.RecipeMasterService;
import com.coolcook.app.web.rest.util.HeaderUtil;
import com.coolcook.app.web.rest.util.PaginationUtil;
import com.coolcook.app.service.dto.RecipeMasterDTO;
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
 * REST controller for managing RecipeMaster.
 */
@RestController
@RequestMapping("/api")
public class RecipeMasterResource {

    private final Logger log = LoggerFactory.getLogger(RecipeMasterResource.class);

    private static final String ENTITY_NAME = "recipeMaster";

    private final RecipeMasterService recipeMasterService;

    public RecipeMasterResource(RecipeMasterService recipeMasterService) {
        this.recipeMasterService = recipeMasterService;
    }

    /**
     * POST  /recipe-masters : Create a new recipeMaster.
     *
     * @param recipeMasterDTO the recipeMasterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipeMasterDTO, or with status 400 (Bad Request) if the recipeMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipe-masters")
    @Timed
    public ResponseEntity<RecipeMasterDTO> createRecipeMaster(@RequestBody RecipeMasterDTO recipeMasterDTO) throws URISyntaxException {
        log.debug("REST request to save RecipeMaster : {}", recipeMasterDTO);
        if (recipeMasterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new recipeMaster cannot already have an ID")).body(null);
        }
        RecipeMasterDTO result = recipeMasterService.save(recipeMasterDTO);
        return ResponseEntity.created(new URI("/api/recipe-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipe-masters : Updates an existing recipeMaster.
     *
     * @param recipeMasterDTO the recipeMasterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipeMasterDTO,
     * or with status 400 (Bad Request) if the recipeMasterDTO is not valid,
     * or with status 500 (Internal Server Error) if the recipeMasterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipe-masters")
    @Timed
    public ResponseEntity<RecipeMasterDTO> updateRecipeMaster(@RequestBody RecipeMasterDTO recipeMasterDTO) throws URISyntaxException {
        log.debug("REST request to update RecipeMaster : {}", recipeMasterDTO);
        if (recipeMasterDTO.getId() == null) {
            return createRecipeMaster(recipeMasterDTO);
        }
        RecipeMasterDTO result = recipeMasterService.save(recipeMasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipeMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipe-masters : get all the recipeMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recipeMasters in body
     */
    @GetMapping("/recipe-masters")
    @Timed
    public ResponseEntity<List<RecipeMasterDTO>> getAllRecipeMasters(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RecipeMasters");
        Page<RecipeMasterDTO> page = recipeMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recipe-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recipe-masters/:id : get the "id" recipeMaster.
     *
     * @param id the id of the recipeMasterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipeMasterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipe-masters/{id}")
    @Timed
    public ResponseEntity<RecipeMasterDTO> getRecipeMaster(@PathVariable Long id) {
        log.debug("REST request to get RecipeMaster : {}", id);
        RecipeMasterDTO recipeMasterDTO = recipeMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recipeMasterDTO));
    }

    /**
     * DELETE  /recipe-masters/:id : delete the "id" recipeMaster.
     *
     * @param id the id of the recipeMasterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipe-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecipeMaster(@PathVariable Long id) {
        log.debug("REST request to delete RecipeMaster : {}", id);
        recipeMasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
