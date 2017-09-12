package com.coolcook.app.service;

import com.coolcook.app.service.dto.IngredientAtHomeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing IngredientAtHome.
 */
public interface IngredientAtHomeService {

    /**
     * Save a ingredientAtHome.
     *
     * @param ingredientAtHomeDTO the entity to save
     * @return the persisted entity
     */
    IngredientAtHomeDTO save(IngredientAtHomeDTO ingredientAtHomeDTO);

    /**
     *  Get all the ingredientAtHomes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<IngredientAtHomeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" ingredientAtHome.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    IngredientAtHomeDTO findOne(Long id);

    /**
     *  Delete the "id" ingredientAtHome.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
