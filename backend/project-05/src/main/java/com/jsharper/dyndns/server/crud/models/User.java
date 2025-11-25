package com.jsharper.dyndns.server.crud.models;

import java.util.UUID;

public class User {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String passwordRepeat;
    private final String id;

    public User(String firstName, String lastName, String email, String password, String passwordRepeat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }
}
