package com.coolcook.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.coolcook.domain.enumeration.UnitOfQuantity;

/**
 * A DTO for the IngredientMaster entity.
 */
public class IngredientMasterDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private byte[] image;
    private String imageContentType;

    private UnitOfQuantity unit;

    private Double defaultQty;

    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public UnitOfQuantity getUnit() {
        return unit;
    }

    public void setUnit(UnitOfQuantity unit) {
        this.unit = unit;
    }

    public Double getDefaultQty() {
        return defaultQty;
    }

    public void setDefaultQty(Double defaultQty) {
        this.defaultQty = defaultQty;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IngredientMasterDTO ingredientMasterDTO = (IngredientMasterDTO) o;
        if(ingredientMasterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientMasterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientMasterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", unit='" + getUnit() + "'" +
            ", defaultQty='" + getDefaultQty() + "'" +
            "}";
    }
}
