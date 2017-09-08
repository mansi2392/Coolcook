package com.coolcook.app.service.impl;

import com.coolcook.app.service.IngredientMasterService;
import com.coolcook.app.domain.IngredientMaster;
import com.coolcook.app.repository.IngredientMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing IngredientMaster.
 */
@Service
@Transactional
public class IngredientMasterServiceImpl implements IngredientMasterService{

    private final Logger log = LoggerFactory.getLogger(IngredientMasterServiceImpl.class);

    private final IngredientMasterRepository ingredientMasterRepository;

    public IngredientMasterServiceImpl(IngredientMasterRepository ingredientMasterRepository) {
        this.ingredientMasterRepository = ingredientMasterRepository;
    }

    /**
     * Save a ingredientMaster.
     *
     * @param ingredientMaster the entity to save
     * @return the persisted entity
     */
    @Override
    public IngredientMaster save(IngredientMaster ingredientMaster) {
        log.debug("Request to save IngredientMaster : {}", ingredientMaster);
        return ingredientMasterRepository.save(ingredientMaster);
    }

    /**
     *  Get all the ingredientMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientMaster> findAll(Pageable pageable) {
        log.debug("Request to get all IngredientMasters");
        return ingredientMasterRepository.findAll(pageable);
    }

    /**
     *  Get one ingredientMaster by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IngredientMaster findOne(Long id) {
        log.debug("Request to get IngredientMaster : {}", id);
        return ingredientMasterRepository.findOne(id);
    }

    /**
     *  Delete the  ingredientMaster by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IngredientMaster : {}", id);
        ingredientMasterRepository.delete(id);
    }
}
