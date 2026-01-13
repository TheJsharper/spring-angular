package com.jsharper.dyndns.server.repositories.fetch;

import com.jsharper.dyndns.server.entities.fetch.PhoneFetch;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

public interface PhoneFetchRepository extends CrudRepository<@NotNull PhoneFetch,@NotNull Long> {
}
