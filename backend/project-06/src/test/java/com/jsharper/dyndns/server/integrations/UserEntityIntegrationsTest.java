package com.jsharper.dyndns.server.integrations;

import com.jsharper.dyndns.server.io.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.UUID;

@DataJpaTest
public class UserEntityIntegrationsTest {

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnsStoreUserDetails() {
        //Arrange
        UserEntity userEntity = new UserEntity();

        userEntity.setUserId(UUID.randomUUID().toString());

        userEntity.setFirstName("Joe");

        userEntity.setLastName("Burry");

        userEntity.setEmail("test@test.com");

        userEntity.setEncryptedPassword("12345678");

        //Act
        UserEntity storeUserEntity = testEntityManager.persistAndFlush(userEntity);


        //Assert

        Assertions.assertTrue(storeUserEntity.getId() > 0 );

        Assertions.assertEquals(userEntity.getUserId(), storeUserEntity.getUserId());

        Assertions.assertEquals(userEntity.getFirstName(), storeUserEntity.getFirstName());

        Assertions.assertEquals(userEntity.getLastName(), storeUserEntity.getLastName());

        Assertions.assertEquals(userEntity.getEmail(), storeUserEntity.getEmail());

        Assertions.assertEquals(userEntity.getEncryptedPassword(), storeUserEntity.getEncryptedPassword());

    }
}
