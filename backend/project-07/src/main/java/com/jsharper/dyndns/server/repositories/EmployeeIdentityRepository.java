package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.EmployeeIdentity;
import org.springframework.data.repository.CrudRepository;

@SuppressWarnings("NullableProblems")
public interface EmployeeIdentityRepository extends CrudRepository<EmployeeIdentity,Long> {
}
