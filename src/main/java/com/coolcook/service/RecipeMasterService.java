package com.coolcook.service;

import com.coolcook.service.dto.RecipeMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RecipeMaster.
 */
public interface RecipeMasterService {

    /**
     * Save a recipeMaster.
     *
     * @param recipeMasterDTO the entity to save
     * @return the persisted entity
     */
    RecipeMasterDTO save(RecipeMasterDTO recipeMasterDTO);

    /**
     *  Get all the recipeMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RecipeMasterDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" recipeMaster.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RecipeMasterDTO findOne(Long id);

    /**
     *  Delete the "id" recipeMaster.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the recipeMaster corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RecipeMasterDTO> search(String query, Pageable pageable);
}
