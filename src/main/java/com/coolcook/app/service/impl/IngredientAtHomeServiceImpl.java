package com.coolcook.app.service.impl;

import com.coolcook.app.service.IngredientAtHomeService;
import com.coolcook.app.domain.IngredientAtHome;
import com.coolcook.app.repository.IngredientAtHomeRepository;
import com.coolcook.app.service.dto.IngredientAtHomeDTO;
import com.coolcook.app.service.mapper.IngredientAtHomeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing IngredientAtHome.
 */
@Service
@Transactional
public class IngredientAtHomeServiceImpl implements IngredientAtHomeService{

    private final Logger log = LoggerFactory.getLogger(IngredientAtHomeServiceImpl.class);

    private final IngredientAtHomeRepository ingredientAtHomeRepository;

    private final IngredientAtHomeMapper ingredientAtHomeMapper;

    public IngredientAtHomeServiceImpl(IngredientAtHomeRepository ingredientAtHomeRepository, IngredientAtHomeMapper ingredientAtHomeMapper) {
        this.ingredientAtHomeRepository = ingredientAtHomeRepository;
        this.ingredientAtHomeMapper = ingredientAtHomeMapper;
    }

    /**
     * Save a ingredientAtHome.
     *
     * @param ingredientAtHomeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IngredientAtHomeDTO save(IngredientAtHomeDTO ingredientAtHomeDTO) {
        log.debug("Request to save IngredientAtHome : {}", ingredientAtHomeDTO);
        IngredientAtHome ingredientAtHome = ingredientAtHomeMapper.toEntity(ingredientAtHomeDTO);
        ingredientAtHome = ingredientAtHomeRepository.save(ingredientAtHome);
        return ingredientAtHomeMapper.toDto(ingredientAtHome);
    }

    /**
     *  Get all the ingredientAtHomes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientAtHomeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IngredientAtHomes");
        return ingredientAtHomeRepository.findAll(pageable)
            .map(ingredientAtHomeMapper::toDto);
    }

    /**
     *  Get one ingredientAtHome by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IngredientAtHomeDTO findOne(Long id) {
        log.debug("Request to get IngredientAtHome : {}", id);
        IngredientAtHome ingredientAtHome = ingredientAtHomeRepository.findOne(id);
        return ingredientAtHomeMapper.toDto(ingredientAtHome);
    }

    /**
     *  Delete the  ingredientAtHome by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IngredientAtHome : {}", id);
        ingredientAtHomeRepository.delete(id);
    }
}
