package com.jsharper.dyndns.server.repository;

import com.jsharper.dyndns.server.entities.Customer;
import jakarta.persistence.Tuple;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CustomerRepository extends CrudRepository<@NotNull Customer, @NotNull Integer> {


    @Query(value = "SELECT p.id, p.number, p.type, p.customer_id FROM phone as p", nativeQuery = true)
    List<Tuple> getRelationsResult();
}
