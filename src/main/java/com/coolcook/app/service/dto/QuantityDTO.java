package com.coolcook.app.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.coolcook.app.domain.enumeration.UnitOfQuantity;

/**
 * A DTO for the Quantity entity.
 */
public class QuantityDTO implements Serializable {

    private Long id;

    private Double qty;

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

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public UnitOfQuantity getUnit() {
        return unit;
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

        QuantityDTO quantityDTO = (QuantityDTO) o;
        if(quantityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quantityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuantityDTO{" +
            "id=" + getId() +
            ", qty='" + getQty() + "'" +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
