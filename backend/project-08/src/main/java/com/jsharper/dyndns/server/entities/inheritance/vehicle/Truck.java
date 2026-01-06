package com.jsharper.dyndns.server.entities.inheritance.vehicle;

import com.jsharper.dyndns.server.entities.inheritance.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.math.BigDecimal;
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Truck  extends Vehicle {
    private BigDecimal capacity;
    private boolean hasCrane;

    public Truck(BigDecimal capacity, boolean hasCrane, String plate, String brand) {
        this.capacity = capacity;
        this.hasCrane = hasCrane;
        this.setPlate(plate);
        this.setBrand(brand);
    }

    public Truck() {
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public boolean isHasCrane() {
        return hasCrane;
    }

    public void setHasCrane(boolean hasCrane) {
        this.hasCrane = hasCrane;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "capacity=" + capacity +
                ", hasCrane=" + hasCrane +
                "} " + super.toString();
    }
}
