package com.coolcook.app.service.impl;

import com.coolcook.app.service.IngredientMasterService;
import com.coolcook.app.domain.IngredientMaster;
import com.coolcook.app.repository.IngredientMasterRepository;
import com.coolcook.app.service.dto.IngredientMasterDTO;
import com.coolcook.app.service.mapper.IngredientMasterMapper;
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

    private final IngredientMasterMapper ingredientMasterMapper;

    public IngredientMasterServiceImpl(IngredientMasterRepository ingredientMasterRepository, IngredientMasterMapper ingredientMasterMapper) {
        this.ingredientMasterRepository = ingredientMasterRepository;
        this.ingredientMasterMapper = ingredientMasterMapper;
    }

    /**
     * Save a ingredientMaster.
     *
     * @param ingredientMasterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IngredientMasterDTO save(IngredientMasterDTO ingredientMasterDTO) {
        log.debug("Request to save IngredientMaster : {}", ingredientMasterDTO);
        IngredientMaster ingredientMaster = ingredientMasterMapper.toEntity(ingredientMasterDTO);
        ingredientMaster = ingredientMasterRepository.save(ingredientMaster);
        return ingredientMasterMapper.toDto(ingredientMaster);
    }

    /**
     *  Get all the ingredientMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IngredientMasters");
        return ingredientMasterRepository.findAll(pageable)
            .map(ingredientMasterMapper::toDto);
    }

    /**
     *  Get one ingredientMaster by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IngredientMasterDTO findOne(Long id) {
        log.debug("Request to get IngredientMaster : {}", id);
        IngredientMaster ingredientMaster = ingredientMasterRepository.findOne(id);
        return ingredientMasterMapper.toDto(ingredientMaster);
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
