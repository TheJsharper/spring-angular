package com.jsharper.dyndns.server.repositories.cascade;

import com.jsharper.dyndns.server.entities.cascade.PhoneCascade;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

public interface PhoneCascadeRepository extends CrudRepository<@NotNull PhoneCascade, @NotNull Long> {
}
