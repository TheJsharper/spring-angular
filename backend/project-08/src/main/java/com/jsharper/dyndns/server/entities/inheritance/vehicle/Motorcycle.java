package com.jsharper.dyndns.server.entities.inheritance.vehicle;

import com.jsharper.dyndns.server.entities.inheritance.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Motorcycle extends Vehicle {
    private Integer engineSize;

    public Motorcycle(Integer engineSize, String plate, String brand) {
        this.engineSize = engineSize;
        this.setPlate(plate);
        this.setBrand(brand);
    }

    public Motorcycle() {
    }

    public Integer getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(Integer engineSize) {
        this.engineSize = engineSize;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "engineSize=" + engineSize +
                "} " + super.toString();
    }
}
