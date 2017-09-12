package com.coolcook.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A IngredientQtyMapping.
 */
@Entity
@Table(name = "ingredient_qty_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IngredientQtyMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Quantity qty;

    @ManyToOne
    private IngredientMaster ingredient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quantity getQty() {
        return qty;
    }

    public IngredientQtyMapping qty(Quantity quantity) {
        this.qty = quantity;
        return this;
    }

    public void setQty(Quantity quantity) {
        this.qty = quantity;
    }

    public IngredientMaster getIngredient() {
        return ingredient;
    }

    public IngredientQtyMapping ingredient(IngredientMaster ingredientMaster) {
        this.ingredient = ingredientMaster;
        return this;
    }

    public void setIngredient(IngredientMaster ingredientMaster) {
        this.ingredient = ingredientMaster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientQtyMapping ingredientQtyMapping = (IngredientQtyMapping) o;
        if (ingredientQtyMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientQtyMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientQtyMapping{" +
            "id=" + getId() +
            "}";
    }
}
