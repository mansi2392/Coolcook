package com.coolcook.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the IngredientAtHome entity.
 */
public class IngredientAtHomeDTO implements Serializable {

    private Long id;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime expiryDate;

    private Long ingredientQtyMapId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getIngredientQtyMapId() {
        return ingredientQtyMapId;
    }

    public void setIngredientQtyMapId(Long ingredientQtyMappingId) {
        this.ingredientQtyMapId = ingredientQtyMappingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IngredientAtHomeDTO ingredientAtHomeDTO = (IngredientAtHomeDTO) o;
        if(ingredientAtHomeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientAtHomeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientAtHomeDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            "}";
    }
}
