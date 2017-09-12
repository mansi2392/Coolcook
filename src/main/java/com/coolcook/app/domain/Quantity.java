package com.coolcook.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.coolcook.app.domain.enumeration.UnitOfQuantity;

/**
 * A Quantity.
 */
@Entity
@Table(name = "quantity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Quantity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qty")
    private Double qty;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private UnitOfQuantity unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQty() {
        return qty;
    }

    public Quantity qty(Double qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public UnitOfQuantity getUnit() {
        return unit;
    }

    public Quantity unit(UnitOfQuantity unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(UnitOfQuantity unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity quantity = (Quantity) o;
        if (quantity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quantity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Quantity{" +
            "id=" + getId() +
            ", qty='" + getQty() + "'" +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
