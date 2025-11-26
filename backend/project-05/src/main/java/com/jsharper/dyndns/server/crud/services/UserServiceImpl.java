package com.jsharper.dyndns.server.crud.services;

import com.jsharper.dyndns.server.crud.models.User;
import com.jsharper.dyndns.server.crud.repositories.UserRepository;

public class UserServiceImpl implements  UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {

        if(firstName == null || firstName.trim().isEmpty()) throw  new IllegalArgumentException("FirstName should not be null or empty");

        if(lastName == null || lastName.trim().isEmpty()) throw  new IllegalArgumentException("LastName should not be null or empty");

        var user =  new User(firstName, lastName, email, password, repeatPassword);

        boolean isUserCreated = userRepository.save(user);
        if(!isUserCreated){
            throw  new UserServiceException("Could not create User");
        }
        return user;
    }
}
