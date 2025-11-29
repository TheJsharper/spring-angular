package com.jsharper.dyndns.server.integrations;

import com.jsharper.dyndns.server.io.UserEntity;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.UUID;

@DataJpaTest
public class UserEntityIntegrationsTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private final UserEntity userEntity = new UserEntity();

    @BeforeEach
    void startup() {

        userEntity.setUserId(UUID.randomUUID().toString());

        userEntity.setFirstName("Joe");

        userEntity.setLastName("Burry");

        userEntity.setEmail("test@test.com");

        userEntity.setEncryptedPassword("12345678");

    }

    @Test
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnsStoreUserDetails() {
        //Arrange

        userEntity.setUserId(UUID.randomUUID().toString());

        userEntity.setFirstName("Joe");

        userEntity.setLastName("Burry");

        userEntity.setEmail("test@test.com");

        userEntity.setEncryptedPassword("12345678");

        //Act
        UserEntity storeUserEntity = testEntityManager.persistAndFlush(userEntity);


        //Assert

        Assertions.assertTrue(storeUserEntity.getId() > 0);

        Assertions.assertEquals(userEntity.getUserId(), storeUserEntity.getUserId());

        Assertions.assertEquals(userEntity.getFirstName(), storeUserEntity.getFirstName());

        Assertions.assertEquals(userEntity.getLastName(), storeUserEntity.getLastName());

        Assertions.assertEquals(userEntity.getEmail(), storeUserEntity.getEmail());

        Assertions.assertEquals(userEntity.getEncryptedPassword(), storeUserEntity.getEncryptedPassword());

    }

    @Test
    void testUserEntity_whenFirstNameIsTooLong_shouldThrowException() {
        //Arrange
        userEntity.setFirstName("jsjsjsjsjsjjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjjsjsjsjsjsjsjsjsjsjsjsjsjsjsjjsjsjsjsjsjjsjsjsjsjjsjs");
        var messageFailure = "Value too long for column \"FIRST_NAME";

        //Assert & Act

        var exception = Assertions.assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(userEntity)
                , "Was Expecting a PersistenceException to be thrown");

        Assertions.assertTrue(exception.getMessage().contains(messageFailure));

    }

    @Test
    void testUserEntity_whenLastNameIsTooLong_shouldThrowException() {
        //Arrange
        userEntity.setLastName("jsjsjsjsjsjjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjjsjsjsjsjsjsjsjsjsjsjsjsjsjsjjsjsjsjsjsjjsjsjsjsjjsjs");
        var messageFailure = "Value too long for column \"LAST_NAME";

        //Assert & Act

        var exception = Assertions.assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(userEntity)
                , "Was Expecting a PersistenceException to be thrown");

        Assertions.assertTrue(exception.getMessage().contains(messageFailure));

    }


    @Test
    void testUserEntity_whenEmailIsTooLong_shouldThrowException() {
        //Arrange
        userEntity.setEmail("jsjsjsjsjsjjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjjsjsjsjsjsjsjsjsjsjsjsjsjsjsjjsjsjsjsjsjjsjsjsjsjjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjjsjsjsjsjsjsjsjsjj@test.com");
        var messageFailure = "Value too long for column \"EMAIL";

        //Assert & Act

        var exception = Assertions.assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(userEntity)
                , "Was Expecting a PersistenceException to be thrown");

        Assertions.assertTrue(exception.getMessage().contains(messageFailure));

    }

    @Test
    void testUserEntity_whenEmailUniqueViolation_shouldThrowException() {
        //Arrange
        var messageFailure = "could not execute statement [Unique index or primary key violation:";
        UserEntity newUserEntity = new UserEntity();

        newUserEntity.setUserId(UUID.randomUUID().toString());

        newUserEntity.setFirstName("Joe");

        newUserEntity.setLastName("Burry");

        newUserEntity.setEmail("test@test.com");

        newUserEntity.setEncryptedPassword("12345678");

        testEntityManager.persistAndFlush(newUserEntity);


        //Assert & Act

        var exception = Assertions.assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(userEntity)
                , "Was Expecting a PersistenceException to be thrown");

        Assertions.assertTrue(exception.getMessage().contains(messageFailure));

    }

    @Test
    void testUserEntity_whenUserIdUniqueViolation_shouldThrowException() {
        //Arrange
        var messageFailure = "could not execute statement [Unique index or primary key violation:";
        UserEntity newUserEntity = new UserEntity();

        newUserEntity.setUserId("1"); // the same value

        newUserEntity.setFirstName("Joe");

        newUserEntity.setLastName("Burry");

        newUserEntity.setEmail("test@test.com");

        newUserEntity.setEncryptedPassword("12345678");

        testEntityManager.persistAndFlush(newUserEntity);

        //update existing user id
        userEntity.setUserId("1");

        //Assert & Act

        var exception = Assertions.assertThrows(PersistenceException.class, () -> testEntityManager.persistAndFlush(userEntity)
                , "Was Expecting a PersistenceException to be thrown");

        Assertions.assertTrue(exception.getMessage().contains(messageFailure));

    }

}
