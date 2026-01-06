package com.jsharper.dyndns.server.repositories.inherintace;

import com.jsharper.dyndns.server.entities.inheritance.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
