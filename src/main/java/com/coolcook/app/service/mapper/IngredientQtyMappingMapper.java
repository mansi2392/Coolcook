package com.coolcook.app.service.mapper;

import com.coolcook.app.domain.*;
import com.coolcook.app.service.dto.IngredientQtyMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity IngredientQtyMapping and its DTO IngredientQtyMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {QuantityMapper.class, IngredientMasterMapper.class, })
public interface IngredientQtyMappingMapper extends EntityMapper <IngredientQtyMappingDTO, IngredientQtyMapping> {

    @Mapping(source = "qty.id", target = "qtyId")

    @Mapping(source = "ingredient.id", target = "ingredientId")
    IngredientQtyMappingDTO toDto(IngredientQtyMapping ingredientQtyMapping); 

    @Mapping(source = "qtyId", target = "qty")

    @Mapping(source = "ingredientId", target = "ingredient")
    IngredientQtyMapping toEntity(IngredientQtyMappingDTO ingredientQtyMappingDTO); 
    default IngredientQtyMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        IngredientQtyMapping ingredientQtyMapping = new IngredientQtyMapping();
        ingredientQtyMapping.setId(id);
        return ingredientQtyMapping;
    }
}
