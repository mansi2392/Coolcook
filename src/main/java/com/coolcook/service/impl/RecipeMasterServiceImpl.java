package com.coolcook.service.impl;

import com.coolcook.service.RecipeMasterService;
import com.coolcook.domain.RecipeMaster;
import com.coolcook.repository.RecipeMasterRepository;
import com.coolcook.repository.search.RecipeMasterSearchRepository;
import com.coolcook.service.dto.RecipeMasterDTO;
import com.coolcook.service.mapper.RecipeMasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RecipeMaster.
 */
@Service
@Transactional
public class RecipeMasterServiceImpl implements RecipeMasterService{

    private final Logger log = LoggerFactory.getLogger(RecipeMasterServiceImpl.class);

    private final RecipeMasterRepository recipeMasterRepository;

    private final RecipeMasterMapper recipeMasterMapper;

    private final RecipeMasterSearchRepository recipeMasterSearchRepository;

    public RecipeMasterServiceImpl(RecipeMasterRepository recipeMasterRepository, RecipeMasterMapper recipeMasterMapper, RecipeMasterSearchRepository recipeMasterSearchRepository) {
        this.recipeMasterRepository = recipeMasterRepository;
        this.recipeMasterMapper = recipeMasterMapper;
        this.recipeMasterSearchRepository = recipeMasterSearchRepository;
    }

    /**
     * Save a recipeMaster.
     *
     * @param recipeMasterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecipeMasterDTO save(RecipeMasterDTO recipeMasterDTO) {
        log.debug("Request to save RecipeMaster : {}", recipeMasterDTO);
        RecipeMaster recipeMaster = recipeMasterMapper.toEntity(recipeMasterDTO);
        recipeMaster = recipeMasterRepository.save(recipeMaster);
        RecipeMasterDTO result = recipeMasterMapper.toDto(recipeMaster);
        recipeMasterSearchRepository.save(recipeMaster);
        return result;
    }

    /**
     *  Get all the recipeMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecipeMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecipeMasters");
        return recipeMasterRepository.findAll(pageable)
            .map(recipeMasterMapper::toDto);
    }

    /**
     *  Get one recipeMaster by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RecipeMasterDTO findOne(Long id) {
        log.debug("Request to get RecipeMaster : {}", id);
        RecipeMaster recipeMaster = recipeMasterRepository.findOneWithEagerRelationships(id);
        return recipeMasterMapper.toDto(recipeMaster);
    }

    /**
     *  Delete the  recipeMaster by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecipeMaster : {}", id);
        recipeMasterRepository.delete(id);
        recipeMasterSearchRepository.delete(id);
    }

    /**
     * Search for the recipeMaster corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecipeMasterDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RecipeMasters for query {}", query);
        Page<RecipeMaster> result = recipeMasterSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(recipeMasterMapper::toDto);
    }
}
