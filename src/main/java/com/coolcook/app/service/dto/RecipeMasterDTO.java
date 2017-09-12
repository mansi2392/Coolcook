package com.coolcook.app.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the RecipeMaster entity.
 */
public class RecipeMasterDTO implements Serializable {

    private Long id;

    private String name;

    private String desc;

    private String process;

    private Integer durationInMin;

    @Lob
    private byte[] image;
    private String imageContentType;

    private Boolean isOriginal;

    private Integer originalRecipeId;

    private Boolean isVeg;

    private Long catagoryId;

    private Set<IngredientQtyMappingDTO> ingredientQtyMaps = new HashSet<>();

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Integer getDurationInMin() {
        return durationInMin;
    }

    public void setDurationInMin(Integer durationInMin) {
        this.durationInMin = durationInMin;
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

    public Boolean isIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(Boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    public Integer getOriginalRecipeId() {
        return originalRecipeId;
    }

    public void setOriginalRecipeId(Integer originalRecipeId) {
        this.originalRecipeId = originalRecipeId;
    }

    public Boolean isIsVeg() {
        return isVeg;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }

    public Long getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(Long categoryId) {
        this.catagoryId = categoryId;
    }

    public Set<IngredientQtyMappingDTO> getIngredientQtyMaps() {
        return ingredientQtyMaps;
    }

    public void setIngredientQtyMaps(Set<IngredientQtyMappingDTO> ingredientQtyMappings) {
        this.ingredientQtyMaps = ingredientQtyMappings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeMasterDTO recipeMasterDTO = (RecipeMasterDTO) o;
        if(recipeMasterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeMasterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeMasterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", process='" + getProcess() + "'" +
            ", durationInMin='" + getDurationInMin() + "'" +
            ", image='" + getImage() + "'" +
            ", isOriginal='" + isIsOriginal() + "'" +
            ", originalRecipeId='" + getOriginalRecipeId() + "'" +
            ", isVeg='" + isIsVeg() + "'" +
            "}";
    }
}
