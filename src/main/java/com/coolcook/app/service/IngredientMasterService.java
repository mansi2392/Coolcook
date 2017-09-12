package com.coolcook.app.service;

import com.coolcook.app.service.dto.IngredientMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing IngredientMaster.
 */
public interface IngredientMasterService {

    /**
     * Save a ingredientMaster.
     *
     * @param ingredientMasterDTO the entity to save
     * @return the persisted entity
     */
    IngredientMasterDTO save(IngredientMasterDTO ingredientMasterDTO);

    /**
     *  Get all the ingredientMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<IngredientMasterDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" ingredientMaster.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    IngredientMasterDTO findOne(Long id);

    /**
     *  Delete the "id" ingredientMaster.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
