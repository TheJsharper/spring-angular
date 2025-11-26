package com.jsharper.dyndns.server.crud.repositories;

import com.jsharper.dyndns.server.crud.models.User;

public interface UserRepository {
    boolean save(User user);
}
