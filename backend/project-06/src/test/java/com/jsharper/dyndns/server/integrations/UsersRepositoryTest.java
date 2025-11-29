package com.jsharper.dyndns.server.integrations;

import com.jsharper.dyndns.server.io.UserEntity;
import com.jsharper.dyndns.server.io.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.UUID;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void testFindByEmail_whenGivenCorrectEmail_returnsEntity() {
        // Arrange

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Joe");
        userEntity.setLastName("Burry");
        userEntity.setEmail("test@test.com");
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword("123456789");

        testEntityManager.persistAndFlush(userEntity);

        // Act
        UserEntity storeUser = usersRepository.findByEmail(userEntity.getEmail());

        // Assert

        Assertions.assertEquals(userEntity.getEmail(), storeUser.getEmail(), " The returned email address does not match the expected value");
    }


    @Test
    void testFindByUserId_whenGivenCorrectUserId_returnsEntity() {
        // Arrange

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Joe");
        userEntity.setLastName("Burry");
        userEntity.setEmail("test@test.com");
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword("123456789");

        testEntityManager.persistAndFlush(userEntity);

        // Act
        UserEntity storeUser = usersRepository.findByUserId(userEntity.getUserId());

        // Assert

        Assertions.assertEquals(userEntity.getUserId(), storeUser.getUserId(), " The returned user id does not match the expected value");
    }

    @Test
    void testFindByEmailEndsWith_whenGivenCorrectEmailEndsWith_returnsEntity() {
        // Arrange

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Joe");
        userEntity.setLastName("Burry");
        userEntity.setEmail("test@test.com");
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword("123456789");
        var emailAddressEnding = "test.com";

        testEntityManager.persistAndFlush(userEntity);

        // Act
        UserEntity storeUser = usersRepository.findByEmailEndsWith(emailAddressEnding);

        // Assert

        Assertions.assertEquals(userEntity.getEmail(), storeUser.getEmail(), " The returned email address does not match the expected value");
    }


    @Test
    void testFindByUserWithEmailEndWith_whenGivenEmailDomain_returnsUsersWithGivenDomain() {
        //Arrange

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Joe");
        userEntity.setLastName("Burry");
        userEntity.setEmail("test01@test.com");
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword("123456789");
        testEntityManager.persistAndFlush(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Joe");
        userEntity2.setLastName("Burry");
        userEntity2.setEmail("test02@test.com");
        userEntity2.setUserId(UUID.randomUUID().toString());
        userEntity2.setEncryptedPassword("123456789");

        testEntityManager.persistAndFlush(userEntity2);

        UserEntity userEntity3 = new UserEntity();
        userEntity3.setFirstName("Joe");
        userEntity3.setLastName("Burry");
        userEntity3.setEmail("test02@gmail.com");
        userEntity3.setUserId(UUID.randomUUID().toString());
        userEntity3.setEncryptedPassword("123456789");

        testEntityManager.persistAndFlush(userEntity3);

        String emailDomainName = "@test.com";


        //Act
        var list = usersRepository.findByUserWithEmailEndingWith(emailDomainName);
        var emails = list.stream().map(UserEntity::getEmail).toList();


        //Assert
        Assertions.assertEquals(2, list.size(), "list of users is not size the expected value");

        emails.forEach( e -> Assertions.assertTrue(e.contains(emailDomainName), "Domain email is not the expected value"));
    }

}
