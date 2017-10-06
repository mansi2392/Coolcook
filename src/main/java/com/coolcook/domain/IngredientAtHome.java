package com.coolcook.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A IngredientAtHome.
 */
@Entity
@Table(name = "ingredient_at_home")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ingredientathome")
public class IngredientAtHome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "expiry_date")
    private ZonedDateTime expiryDate;

    @ManyToOne
    private IngredientQtyMapping ingredientQtyMap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public IngredientAtHome createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public IngredientAtHome updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public IngredientAtHome expiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public IngredientQtyMapping getIngredientQtyMap() {
        return ingredientQtyMap;
    }

    public IngredientAtHome ingredientQtyMap(IngredientQtyMapping ingredientQtyMapping) {
        this.ingredientQtyMap = ingredientQtyMapping;
        return this;
    }

    public void setIngredientQtyMap(IngredientQtyMapping ingredientQtyMapping) {
        this.ingredientQtyMap = ingredientQtyMapping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientAtHome ingredientAtHome = (IngredientAtHome) o;
        if (ingredientAtHome.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientAtHome.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientAtHome{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            "}";
    }
}
