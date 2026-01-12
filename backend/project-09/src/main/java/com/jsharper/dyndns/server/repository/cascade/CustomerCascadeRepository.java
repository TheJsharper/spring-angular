package com.jsharper.dyndns.server.repository.cascade;

import com.jsharper.dyndns.server.entities.cascade.CustomerCascade;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerCascadeRepository extends CrudRepository<CustomerCascade, Long> {
    @Query(value = "SELECT p.id, p.number, p.type, p.customer_cascade_id FROM phone_cascade as p", nativeQuery = true)
    List<Tuple> getRelationsResult();
}
