package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.uuid.generators.EmployeeUUIDGeneratorStyleRandom;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EmployeeUUIDGeneratorRandomRepository extends CrudRepository<EmployeeUUIDGeneratorStyleRandom, UUID> {
}
