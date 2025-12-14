package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.uuid.generators.EmployeeUUIDGenerator;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EmployeeUUIDGeneratorRepository extends CrudRepository<EmployeeUUIDGenerator, UUID> {
}
