package com.jsharper.dyndns.server.repository;

import com.jsharper.dyndns.server.entities.Phone;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<Phone,Long> {
}
