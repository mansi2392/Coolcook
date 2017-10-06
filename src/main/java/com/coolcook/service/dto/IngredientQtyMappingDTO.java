package com.coolcook.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the IngredientQtyMapping entity.
 */
public class IngredientQtyMappingDTO implements Serializable {

    private Long id;

    private Long qtyId;

    private Long ingredientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQtyId() {
        return qtyId;
    }

    public void setQtyId(Long quantityId) {
        this.qtyId = quantityId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientMasterId) {
        this.ingredientId = ingredientMasterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IngredientQtyMappingDTO ingredientQtyMappingDTO = (IngredientQtyMappingDTO) o;
        if(ingredientQtyMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientQtyMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientQtyMappingDTO{" +
            "id=" + getId() +
            "}";
    }
}
