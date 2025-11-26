package com.jsharper.dyndns.server.crud.services;

import com.jsharper.dyndns.server.crud.models.User;
import com.jsharper.dyndns.server.crud.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

   @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailVerificationServiceImpl emailVerificationService;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repeatPassword;

    @BeforeEach
    public void init() {
        //userService = new UserServiceImpl();

        firstName = "Joe";
        lastName = "Dune";
        email = "joe.dune@server.com";
        password = "123456789";
        repeatPassword = "123456789";

    }

    @Test
    void testCreateUser_whenUserDetailsProvided_returnUserObject() {
        // Arrange
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        //Assert
        assertNotNull(user, "createUser() should not have returned null");

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    void testCreateUser_whenUserCreated_returnUserObjectContainersSameFirstName() {
        //Arrange
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        // Assert
        assertNotNull(user, "createUser() should not have returned null");

        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect");

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    void testCreateUser_whenUserDetailsProvided_returnUserObjectWithProperties() {
        //Arrange
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

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
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));

    }

    @Test
    void testCreateUser_whenFistNameIsEmptyOrWhiteSpaces_throwsIllegalArgumentException() {
        //Arrange

        String firstName = "      ";

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> {

            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty first name should have caused an Illegal Argument Exception");


        //Assert

        assertEquals("FirstName should not be null or empty", exception.getMessage());


    } @Test
    void testCreateUser_whenLastNameIsEmptyOrWhitespaces_throwsIllegalArgumentException() {
        //Arrange

        String lastName = "";

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> {

            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty first name should have caused an Illegal Argument Exception");


        //Assert

        assertEquals("LastName should not be null or empty", exception.getMessage());


    }
    @DisplayName("If save() method RuntimeException, a UserServiceException is thrown")
    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException(){
        //Arrange

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(RuntimeException.class);

        //Act & Assert
        assertThrows(UserServiceException.class, ()->{
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Should have thrown UserServiceException instead");

    }
    @DisplayName("EmailNotificationException is handled")
    @Test
    void testCreateUser_whenEmailNotificationExceptionThrown_throwsUserServiceException(){
        // Arrange
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        Mockito.doThrow(EmailVerificationException.class)
                .when(emailVerificationService)
                .scheduleEmailConfirmation(Mockito.any(User.class));

      //  Mockito.doNothing().when(emailVerificationService).scheduleEmailConfirmation(Mockito.any(User.class));

        // Act & Assert
        var exception = assertThrows(UserServiceException.class, () -> {

            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Should have thrown UserServiceException instead");

        Mockito.verify(emailVerificationService, Mockito.times(1))
                .scheduleEmailConfirmation(Mockito.any(User.class));


    }

}
