package com.jsharper.dyndns.server.repositories;

import com.jsharper.dyndns.server.entities.Phone;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<@NotNull Phone, @NotNull Long> {
}
