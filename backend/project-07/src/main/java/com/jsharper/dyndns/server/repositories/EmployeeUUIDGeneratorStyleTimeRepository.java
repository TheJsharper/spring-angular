package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.uuid.generators.EmployeeUUIDGeneratorStyleTime;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EmployeeUUIDGeneratorStyleTimeRepository extends CrudRepository<EmployeeUUIDGeneratorStyleTime, UUID> {
}
