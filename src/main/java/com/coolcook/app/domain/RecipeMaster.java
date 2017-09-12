package com.coolcook.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RecipeMaster.
 */
@Entity
@Table(name = "recipe_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RecipeMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_desc")
    private String desc;

    @Column(name = "process")
    private String process;

    @Column(name = "duration_in_min")
    private Integer durationInMin;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "is_original")
    private Boolean isOriginal;

    @Column(name = "original_recipe_id")
    private Integer originalRecipeId;

    @Column(name = "is_veg")
    private Boolean isVeg;

    @ManyToOne
    private Category catagory;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "recipe_master_ingredient_qty_map",
               joinColumns = @JoinColumn(name="recipe_masters_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="ingredient_qty_maps_id", referencedColumnName="id"))
    private Set<IngredientQtyMapping> ingredientQtyMaps = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RecipeMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public RecipeMaster desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProcess() {
        return process;
    }

    public RecipeMaster process(String process) {
        this.process = process;
        return this;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Integer getDurationInMin() {
        return durationInMin;
    }

    public RecipeMaster durationInMin(Integer durationInMin) {
        this.durationInMin = durationInMin;
        return this;
    }

    public void setDurationInMin(Integer durationInMin) {
        this.durationInMin = durationInMin;
    }

    public byte[] getImage() {
        return image;
    }

    public RecipeMaster image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public RecipeMaster imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean isIsOriginal() {
        return isOriginal;
    }

    public RecipeMaster isOriginal(Boolean isOriginal) {
        this.isOriginal = isOriginal;
        return this;
    }

    public void setIsOriginal(Boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    public Integer getOriginalRecipeId() {
        return originalRecipeId;
    }

    public RecipeMaster originalRecipeId(Integer originalRecipeId) {
        this.originalRecipeId = originalRecipeId;
        return this;
    }

    public void setOriginalRecipeId(Integer originalRecipeId) {
        this.originalRecipeId = originalRecipeId;
    }

    public Boolean isIsVeg() {
        return isVeg;
    }

    public RecipeMaster isVeg(Boolean isVeg) {
        this.isVeg = isVeg;
        return this;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }

    public Category getCatagory() {
        return catagory;
    }

    public RecipeMaster catagory(Category category) {
        this.catagory = category;
        return this;
    }

    public void setCatagory(Category category) {
        this.catagory = category;
    }

    public Set<IngredientQtyMapping> getIngredientQtyMaps() {
        return ingredientQtyMaps;
    }

    public RecipeMaster ingredientQtyMaps(Set<IngredientQtyMapping> ingredientQtyMappings) {
        this.ingredientQtyMaps = ingredientQtyMappings;
        return this;
    }

    public RecipeMaster addIngredientQtyMap(IngredientQtyMapping ingredientQtyMapping) {
        this.ingredientQtyMaps.add(ingredientQtyMapping);
        return this;
    }

    public RecipeMaster removeIngredientQtyMap(IngredientQtyMapping ingredientQtyMapping) {
        this.ingredientQtyMaps.remove(ingredientQtyMapping);
        return this;
    }

    public void setIngredientQtyMaps(Set<IngredientQtyMapping> ingredientQtyMappings) {
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
        RecipeMaster recipeMaster = (RecipeMaster) o;
        if (recipeMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", process='" + getProcess() + "'" +
            ", durationInMin='" + getDurationInMin() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", isOriginal='" + isIsOriginal() + "'" +
            ", originalRecipeId='" + getOriginalRecipeId() + "'" +
            ", isVeg='" + isIsVeg() + "'" +
            "}";
    }
}
