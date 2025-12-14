package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.uuid.EmployeeUUIString;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeUUIStringRepository extends CrudRepository<EmployeeUUIString, String> {
}
