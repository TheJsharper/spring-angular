package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.EmployeeTableIdGenerator;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeTableIdGeneratorRepository extends CrudRepository<EmployeeTableIdGenerator, Long> {
}
