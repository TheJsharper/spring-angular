package com.jsharper.dyndns.server.repositories.inherintace;

import com.jsharper.dyndns.server.entities.inheritance.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
}
