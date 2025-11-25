package com.jsharper.dyndns.server.crud.services;

import com.jsharper.dyndns.server.crud.models.User;

public class UserServiceImpl implements  UserService{
    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {

        if(firstName == null || firstName.trim().isEmpty()) throw  new IllegalArgumentException("FirstName should not be null or empty");
        return new User(firstName, lastName, email, password, repeatPassword);
    }
}
