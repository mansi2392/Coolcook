package com.coolcook.service;

import com.coolcook.service.dto.IngredientMasterDTO;
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

    /**
     * Search for the ingredientMaster corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<IngredientMasterDTO> search(String query, Pageable pageable);
}
