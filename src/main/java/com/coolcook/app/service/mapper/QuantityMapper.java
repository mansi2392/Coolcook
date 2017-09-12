package com.coolcook.app.service.mapper;

import com.coolcook.app.domain.*;
import com.coolcook.app.service.dto.QuantityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Quantity and its DTO QuantityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuantityMapper extends EntityMapper <QuantityDTO, Quantity> {
    
    
    default Quantity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Quantity quantity = new Quantity();
        quantity.setId(id);
        return quantity;
    }
}
