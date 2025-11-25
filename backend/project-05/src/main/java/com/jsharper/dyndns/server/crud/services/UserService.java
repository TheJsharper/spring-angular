package com.jsharper.dyndns.server.crud.services;

import com.jsharper.dyndns.server.crud.models.User;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String password, String repeatPassword);
}
