package com.jsharper.dyndns.server.containers.rest.assured;

import com.jsharper.dyndns.server.ui.request.UserDetailsRequestModel;
import com.jsharper.dyndns.server.ui.response.UserRest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@AutoConfigureTestRestTemplate
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

    private final RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(LogDetail.ALL);

    private final ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(LogDetail.ALL);

    private String userId;
    private String token;

    static {
        mySQLContainer.start();
    }

    @BeforeAll
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.filters(requestLoggingFilter, responseLoggingFilter);

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
        /*RestAssured.requestSpecification = new ResponseSpecBuilder()
                .expectBody(anyOf(is(200), is(201), is(204)))
                .expectResponseTime(lessThan(5000L))
                .build();*/
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
                .body(userDetailsRequestModel)
                .when().post("/users")
                .then()
                // .log().all()
                .statusCode(200)
                .body("userId", notNullValue())
                .body("firstName", equalTo(userDetailsRequestModel.getFirstName()))
                .body("lastName", equalTo(userDetailsRequestModel.getLastName()))
                .body("email", equalTo(userDetailsRequestModel.getEmail()));
    }

    @Order(5)
    @Test
    void testLogin_whenValidCredentialsProvided_returnsTokenAndUserIdHeader() {
        //Arrange
        var credentials = new HashMap<String, String>();
        credentials.put("email", "joe3@gmail.com");
        credentials.put("password", "12345678");

        //Act

        var response = given().body(credentials)
                .when()
                .post("/users/login");
        response.headers().asList().forEach(h -> System.out.println("======> key===>:" + h.getName() + "-----> :" + h.getValue()));

        userId = response.headers().getValue("userId");

        token = response.headers().getValue("Authorization");

        // Assert
        Assertions.assertNotNull(userId);
        Assertions.assertNotNull(token);
        Assertions.assertFalse(userId.isEmpty());
        Assertions.assertFalse(token.isEmpty());

    }

    @Order(6)
    @Test
    @DisplayName("GET List of users")
    @Disabled
    void testGetAllUser_whenValidCredentialsProvided_returnsListOfUser() {
        // Arrange
        var response = given()
                .header(new Header("Authorization", token))
                .queryParam("page", 1)
                .queryParam("limit", 10)
                .get("/users");
        response.then().statusCode(200);

        var list = response.as(UserRest[].class);
        Assertions.assertTrue(list.length > 0);


        // Act


        // Assert

    }

    @Test
    @Order(7)
    @DisplayName("Get User By Id")
    @Disabled
    void testGetUser_withValidAuthenticationToken_returnsUser() {
        given().pathParams("userId", userId)
                //   .header("Authorization", "Bearer "+ token)
                .auth().oauth2(token)
                .when().get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(this.userId))
                .body("firstName", Matchers.notNullValue())
                .body("firstName", Matchers.notNullValue())
                .body("email", Matchers.notNullValue());
    }

    @Test
    @Order(8)
    @DisplayName("Get User By Id without Authorization")
    @Disabled
    void testGetUser_withMissingAuthHeader_returnsForbidden() {
        given().pathParams("userId", userId)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

}
