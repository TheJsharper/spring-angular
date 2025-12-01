package com.jsharper.dyndns.server.containers.rest.assured;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
@ActiveProfiles("test")
public class UsersControllerWithTestContainerAndRestAssuredTest {

    @Container
    @ServiceConnection
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:9.5.0")
            .withDatabaseName("rest-app")
            .withUsername("sa")
            .withPassword("secr3t");


    static {
        mySQLContainer.start();
    }

    @Order(1)
    @Test
    void testContainerIsRunning() {
        Assertions.assertTrue(mySQLContainer.isCreated(), "Container has not been running");
        Assertions.assertTrue(mySQLContainer.isRunning(), "Container is not running");
    }
}
