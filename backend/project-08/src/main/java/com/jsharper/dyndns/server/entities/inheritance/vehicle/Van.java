package com.jsharper.dyndns.server.entities.inheritance.vehicle;

import com.jsharper.dyndns.server.entities.inheritance.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.math.BigDecimal;
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Van extends Vehicle {
    private BigDecimal volume;
    private boolean refrigerated;

    public Van(BigDecimal volume, boolean refrigerated,  String plate, String brand) {
        this.volume = volume;
        this.refrigerated = refrigerated;
        this.setPlate(plate);
        this.setBrand(brand);
    }

    public Van() {
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public boolean isRefrigerated() {
        return refrigerated;
    }

    public void setRefrigerated(boolean refrigerated) {
        this.refrigerated = refrigerated;
    }

    @Override
    public String toString() {
        return "Van{" +
                "volume=" + volume +
                ", refrigerated=" + refrigerated +
                "} " + super.toString();
    }
}
