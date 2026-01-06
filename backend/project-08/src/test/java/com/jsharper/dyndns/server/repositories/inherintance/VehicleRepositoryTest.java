package com.jsharper.dyndns.server.repositories.inherintance;

import com.jsharper.dyndns.server.entities.inheritance.vehicle.Motorcycle;
import com.jsharper.dyndns.server.entities.inheritance.vehicle.Truck;
import com.jsharper.dyndns.server.entities.inheritance.vehicle.Van;
import com.jsharper.dyndns.server.repositories.inherintace.VehicleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vr;

    @Test
    @Order(1)
    void createTruck_whenProvidedTruckInstance_returnEntityTruck(){
        var truck = new Truck(new BigDecimal(10000), true,"EE-525CU", "Volvo" );

        var storeTruck = vr.save(truck);

        Assertions.assertEquals(truck, storeTruck);
    }

    @Test
    @Order(2)
    void createVan_whenProvidedVanInstance_returnEntityVan(){
        var van = new Van(new BigDecimal(10000), false,"DE-256CU", "Volkswagen" );

        var storeVan = vr.save(van);

        Assertions.assertEquals(van, storeVan);
    }

    @Test
    @Order(3)
    void createMotorcycle_whenProvidedMotorcycleInstance_returnMotorcycleVan(){
        var motorcycle = new Motorcycle(250,"BE-898XX", "Kawasaki" );

        var storeMotorcycle = vr.save(motorcycle);


        Assertions.assertEquals(motorcycle, storeMotorcycle);
    }


}
