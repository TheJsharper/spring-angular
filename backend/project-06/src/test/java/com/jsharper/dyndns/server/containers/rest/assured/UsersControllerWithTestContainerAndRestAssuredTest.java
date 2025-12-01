package com.jsharper.dyndns.server.containers.rest.assured;

import com.jsharper.dyndns.server.io.UserEntity;
import com.jsharper.dyndns.server.ui.request.UserDetailsRequestModel;
import com.jsharper.dyndns.server.ui.response.UserRest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestRestTemplate
@Testcontainers
@ActiveProfiles("test")
public class UsersControllerWithTestContainerAndRestAssuredTest {

    @LocalServerPort
    int port;

    @Container
    @ServiceConnection
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:9.5.0")
            .withDatabaseName("rest-app")
            .withUsername("sa")
            .withPassword("secr3t");


    static {
        mySQLContainer.start();
    }

    @BeforeAll
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }


    @Order(1)
    @Test
    void testContainerIsRunning() {
        Assertions.assertTrue(mySQLContainer.isCreated(), "Container has not been running");
        Assertions.assertTrue(mySQLContainer.isRunning(), "Container is not running");
    }

    @Order(2)
    @Test
    void testCreateUser_whenValidDetailsProvided_returnsCreateUser() {
        //Arrange
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("Joe");
        userDetailsRequestModel.setLastName("Burry");
        userDetailsRequestModel.setEmail("joe@gmail.com");
        userDetailsRequestModel.setPassword("12345678");
        userDetailsRequestModel.setRepeatPassword("12345678");


        //Act
        var response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDetailsRequestModel)
                .when().post("/users")
                .then()
                .extract()
                .response();
        var userRest = response.as(UserRest.class);
        //Assert
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(userDetailsRequestModel.getFirstName(), userRest.getFirstName());
        Assertions.assertEquals(userDetailsRequestModel.getLastName(), userRest.getLastName());
        Assertions.assertEquals(userDetailsRequestModel.getEmail(), userRest.getEmail());
        Assertions.assertFalse(userRest.getUserId().isEmpty());
    }

    @Order(3)
    @Test
    void testCreateUser_whenValidDetailsProvidedJson_returnsCreateUser() throws JSONException {
        //Arrange
        JSONObject userDetailsRequestModel = new JSONObject();
        userDetailsRequestModel.put("firstName", "Joe");
        userDetailsRequestModel.put("lastName", "Burry");
        userDetailsRequestModel.put("email", "joe2@gmail.com");
        userDetailsRequestModel.put("password", "12345678");
        userDetailsRequestModel.put("repeatPassword", "12345678");


        //Act
        var response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDetailsRequestModel.toString())
                .when().post("/users")
                .then()
                .extract()
                .response();
        //Assert
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(userDetailsRequestModel.get("firstName"), response.jsonPath().getString("firstName"));
        Assertions.assertEquals(userDetailsRequestModel.get("lastName"), response.jsonPath().getString("lastName"));
        Assertions.assertEquals(userDetailsRequestModel.get("email"), response.jsonPath().getString("email"));
        Assertions.assertFalse(response.jsonPath().getString("userId").isEmpty());
    }

    @Order(4)
    @Test
    void testCreateUser_whenValidDetailsProvidedFluentApi_returnsCreateUser() {
        //Arrange
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("Joe");
        userDetailsRequestModel.setLastName("Burry");
        userDetailsRequestModel.setEmail("joe3@gmail.com");
        userDetailsRequestModel.setPassword("12345678");
        userDetailsRequestModel.setRepeatPassword("12345678");


        //Act
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDetailsRequestModel)
                .when().post("/users")
                .then()
                .log().all()
                .statusCode(200)
                .body("userId", notNullValue())
                .body("firstName", equalTo(userDetailsRequestModel.getFirstName()))
                .body("lastName", equalTo(userDetailsRequestModel.getLastName()))
                .body("email", equalTo(userDetailsRequestModel.getEmail()));
    }

}
