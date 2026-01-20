package com.jsharper.dyndns.server.repositories.fetch;

import com.jsharper.dyndns.server.entities.fetch.ProgrammerFetch;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

public interface ProgrammerFetchRepository extends CrudRepository<@NotNull ProgrammerFetch,@NotNull Long> {
}
