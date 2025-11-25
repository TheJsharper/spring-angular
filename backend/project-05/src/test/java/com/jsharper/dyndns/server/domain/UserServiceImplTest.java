package com.jsharper.dyndns.server.domain;

import com.jsharper.dyndns.server.dao.UserDatabase;
import com.jsharper.dyndns.server.dao.UserDatabaseMapImpl;
import com.jsharper.dyndns.server.services.UserService;
import com.jsharper.dyndns.server.services.UserServiceImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {
    UserDatabase userDatabase;
    UserService userService;
    String createUserId;

    @BeforeAll
    void setup() {
        userDatabase = new UserDatabaseMapImpl();
        userDatabase.init();
        userService = new UserServiceImpl(userDatabase);
        createUserId = "";

    }

    @AfterAll
    void cleanup() {
        userDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {
        //Arrange
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "Joe");
        user.put("firstLastName", "Burry");

        //Act
        createUserId = userService.createUser(user);

        //Assert

        assertNotNull(createUserId, "User id should not be null");

    }

    @Test
    @Order(2)
    @DisplayName("update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdateUserDetails() {

        //Arrange
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "John L");
        user.put("firstLastName", "Smith");

        //Act
        Map updatedUserDetails = userService.updateUser(createUserId, user);

        //Assert

        //  assertNotNull(createUserId, "User id should not be null");
        assertEquals(user.get("firstName"), updatedUserDetails.get("firstName"), "Returned value of user's firstName is incorrect!!");
        assertEquals(user.get("lastName"), updatedUserDetails.get("lastName"), "Returned value of user's lastName is incorrect!!");
    }


    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUser_whenProvidedWithValidDetails_returnsUpdateUserDetails() {
        //Act
        Map userDetails = userService.getUserDetails(createUserId);

        //Assert
        assertNotNull(userDetails, "User details should not be null");
        assertEquals(createUserId, userDetails.get("userId"), "Returned user details contains incorrect user id");

    }

    @Test
    @Order(4)
    @DisplayName("delete user works")
    void testDeleteUser_whenProvidedWithValidDetails_returnsUpdateUserDetails() {

        //Act
         userService.deleteUser(createUserId);

         //Assert
        assertNull(userService.getUserDetails(createUserId), "User should not been found");
    }
}
