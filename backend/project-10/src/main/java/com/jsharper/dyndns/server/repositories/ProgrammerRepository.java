package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.Programmer;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

public interface ProgrammerRepository extends CrudRepository<@NotNull Programmer,@NotNull Long> {
}
