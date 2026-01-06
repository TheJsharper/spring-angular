package com.jsharper.dyndns.server.repository;

import com.jsharper.dyndns.server.entities.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
