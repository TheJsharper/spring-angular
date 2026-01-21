package com.jsharper.dyndns.server.repositories.cascade;

import com.jsharper.dyndns.server.entities.cascade.ProgrammerCascadeInsert;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

public interface ProgrammerCascadeInsertRepository extends CrudRepository<@NotNull ProgrammerCascadeInsert, @NotNull Long> {
}
