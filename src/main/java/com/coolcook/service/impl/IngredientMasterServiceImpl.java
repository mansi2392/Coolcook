package com.coolcook.service.impl;

import com.coolcook.domain.Category;
import com.coolcook.service.IngredientMasterService;
import com.coolcook.domain.IngredientMaster;
import com.coolcook.repository.IngredientMasterRepository;
import com.coolcook.repository.search.IngredientMasterSearchRepository;
import com.coolcook.service.dto.IngredientMasterDTO;
import com.coolcook.service.mapper.IngredientMasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IngredientMaster.
 */
@Service
@Transactional
public class IngredientMasterServiceImpl implements IngredientMasterService{

    private final Logger log = LoggerFactory.getLogger(IngredientMasterServiceImpl.class);

    private final IngredientMasterRepository ingredientMasterRepository;

    private final IngredientMasterMapper ingredientMasterMapper;

    private final IngredientMasterSearchRepository ingredientMasterSearchRepository;

    public IngredientMasterServiceImpl(IngredientMasterRepository ingredientMasterRepository, IngredientMasterMapper ingredientMasterMapper, IngredientMasterSearchRepository ingredientMasterSearchRepository) {
        this.ingredientMasterRepository = ingredientMasterRepository;
        this.ingredientMasterMapper = ingredientMasterMapper;
        this.ingredientMasterSearchRepository = ingredientMasterSearchRepository;
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
        IngredientMasterDTO result = ingredientMasterMapper.toDto(ingredientMaster);
        ingredientMasterSearchRepository.save(ingredientMaster);
        return result;
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
        ingredientMasterSearchRepository.delete(id);
    }

    /**
     * Search for the ingredientMaster corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientMasterDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IngredientMasters for query {}", queryStringQuery(query));
        Page<IngredientMaster> result = ingredientMasterSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(ingredientMasterMapper::toDto);
    }

    @Override
    public Page<IngredientMasterDTO> getIngredientByCategory(String categoryName, Pageable pageable) {
        Category category=new Category();
        category.setCategoryName(categoryName);
        Page<IngredientMaster> result = ingredientMasterRepository.findByCategory(category, pageable);
        return result.map(ingredientMasterMapper::toDto);
    }
}
