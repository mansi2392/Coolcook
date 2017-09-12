package com.coolcook.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.coolcook.app.domain.enumeration.UnitOfQuantity;

/**
 * A IngredientMaster.
 */
@Entity
@Table(name = "ingredient_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IngredientMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private UnitOfQuantity unit;

    @Column(name = "default_qty")
    private Double defaultQty;

    @ManyToOne
    private Category catagory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public IngredientMaster name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public IngredientMaster image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public IngredientMaster imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public UnitOfQuantity getUnit() {
        return unit;
    }

    public IngredientMaster unit(UnitOfQuantity unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(UnitOfQuantity unit) {
        this.unit = unit;
    }

    public Double getDefaultQty() {
        return defaultQty;
    }

    public IngredientMaster defaultQty(Double defaultQty) {
        this.defaultQty = defaultQty;
        return this;
    }

    public void setDefaultQty(Double defaultQty) {
        this.defaultQty = defaultQty;
    }

    public Category getCatagory() {
        return catagory;
    }

    public IngredientMaster catagory(Category category) {
        this.catagory = category;
        return this;
    }

    public void setCatagory(Category category) {
        this.catagory = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientMaster ingredientMaster = (IngredientMaster) o;
        if (ingredientMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", unit='" + getUnit() + "'" +
            ", defaultQty='" + getDefaultQty() + "'" +
            "}";
    }
}
