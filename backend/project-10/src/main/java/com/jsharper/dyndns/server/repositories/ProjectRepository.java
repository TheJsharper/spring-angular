package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.Project;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<@NotNull Project, @NotNull Long> {
}
