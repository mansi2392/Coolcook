package com.coolcook.app.service;

import com.coolcook.app.domain.IngredientMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing IngredientMaster.
 */
public interface IngredientMasterService {

    /**
     * Save a ingredientMaster.
     *
     * @param ingredientMaster the entity to save
     * @return the persisted entity
     */
    IngredientMaster save(IngredientMaster ingredientMaster);

    /**
     *  Get all the ingredientMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<IngredientMaster> findAll(Pageable pageable);

    /**
     *  Get the "id" ingredientMaster.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    IngredientMaster findOne(Long id);

    /**
     *  Delete the "id" ingredientMaster.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
