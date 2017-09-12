package com.coolcook.app.service.impl;

import com.coolcook.app.service.RecipeMasterService;
import com.coolcook.app.domain.RecipeMaster;
import com.coolcook.app.repository.RecipeMasterRepository;
import com.coolcook.app.service.dto.RecipeMasterDTO;
import com.coolcook.app.service.mapper.RecipeMasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RecipeMaster.
 */
@Service
@Transactional
public class RecipeMasterServiceImpl implements RecipeMasterService{

    private final Logger log = LoggerFactory.getLogger(RecipeMasterServiceImpl.class);

    private final RecipeMasterRepository recipeMasterRepository;

    private final RecipeMasterMapper recipeMasterMapper;

    public RecipeMasterServiceImpl(RecipeMasterRepository recipeMasterRepository, RecipeMasterMapper recipeMasterMapper) {
        this.recipeMasterRepository = recipeMasterRepository;
        this.recipeMasterMapper = recipeMasterMapper;
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
        return recipeMasterMapper.toDto(recipeMaster);
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
    }
}
