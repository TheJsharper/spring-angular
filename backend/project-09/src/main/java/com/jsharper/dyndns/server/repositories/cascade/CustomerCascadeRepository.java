package com.jsharper.dyndns.server.repositories.cascade;

import com.jsharper.dyndns.server.entities.cascade.CustomerCascade;
import jakarta.persistence.Tuple;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerCascadeRepository extends CrudRepository<@NotNull CustomerCascade, @NotNull Long> {
    @Query(value = "SELECT p.id, p.number, p.type, p.customer_cascade_id FROM phone_cascade as p", nativeQuery = true)
    List<Tuple> getRelationsResult();
}
