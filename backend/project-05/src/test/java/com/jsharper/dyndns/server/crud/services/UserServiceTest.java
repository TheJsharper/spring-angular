package com.jsharper.dyndns.server.crud.services;

import com.jsharper.dyndns.server.crud.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repeatPassword;

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl();

        firstName = "Joe";
        lastName = "Dune";
        email = "joe.dune@server.com";
        password = "123456789";
        repeatPassword = "123456789";

    }

    @Test
    void testCreateUser_whenUserDetailsProvided_returnUserObject() {
        // Arrange


        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        //Assert
        assertNotNull(user, "createUser() should not have returned null");

    }

    @Test
    void testCreateUser_whenUserCreated_returnUserObjectContainersSameFirstName() {
        //Arrange


        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertNotNull(user, "createUser() should not have returned null");

        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect");

    }

    @Test
    void testCreateUser_whenUserDetailsProvided_returnUserObjectWithProperties() {
        //Arrange


        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertNotNull(user, "createUser() should not have returned null");

        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect");
        assertEquals(lastName, user.getLastName(), "User's last name is incorrect");
        assertEquals(email, user.getEmail(), "User's email is incorrect");
        assertEquals(password, user.getPassword(), "User's password is incorrect");
        assertEquals(repeatPassword, user.getPasswordRepeat(), "User's repeatPassword  is incorrect");
        assertNotNull(user.getId(), "User's returned null");

    }

    @Test
    void testCreateUser_whenFistNameIsEmpty_throwsIllegalArgumentException() {
        //Arrange

        String firstName = "      ";

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> {

            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty first name should have caused an Illegal Argument Exception");


        //Assert

        assertEquals("FirstName should not be null or empty", exception.getMessage());


    }
}
