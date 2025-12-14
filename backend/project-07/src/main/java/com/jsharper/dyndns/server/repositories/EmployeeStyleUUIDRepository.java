package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.uuid.EmployeeStyleUUID;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EmployeeStyleUUIDRepository extends CrudRepository<EmployeeStyleUUID, UUID> {
}
