package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
