package com.jsharper.dyndns.server.repositories.fetch;

import com.jsharper.dyndns.server.entities.fetch.CustomerFetch;
import jakarta.persistence.Tuple;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerFetchRepository extends CrudRepository<@NotNull CustomerFetch,@NotNull Long> {
    @Query(value = "SELECT p.id, p.number, p.type, p.customer_fetch_id FROM phone_fetch as p", nativeQuery = true)
    List<Tuple> getRelationsResult();
}
