package com.coolcook.service.mapper;

import com.coolcook.domain.*;
import com.coolcook.service.dto.IngredientAtHomeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IngredientAtHome and its DTO IngredientAtHomeDTO.
 */
@Mapper(componentModel = "spring", uses = {IngredientQtyMappingMapper.class, })
public interface IngredientAtHomeMapper extends EntityMapper <IngredientAtHomeDTO, IngredientAtHome> {

    @Mapping(source = "ingredientQtyMap.id", target = "ingredientQtyMapId")
    IngredientAtHomeDTO toDto(IngredientAtHome ingredientAtHome); 

    @Mapping(source = "ingredientQtyMapId", target = "ingredientQtyMap")
    IngredientAtHome toEntity(IngredientAtHomeDTO ingredientAtHomeDTO); 
    default IngredientAtHome fromId(Long id) {
        if (id == null) {
            return null;
        }
        IngredientAtHome ingredientAtHome = new IngredientAtHome();
        ingredientAtHome.setId(id);
        return ingredientAtHome;
    }
}
