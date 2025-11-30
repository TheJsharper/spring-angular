package com.jsharper.dyndns.server.containers;

import com.jsharper.dyndns.server.security.SecurityConstants;
import com.jsharper.dyndns.server.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerWithTestContainersTest {
    @Value("${app.name}")
    private String appName;

    @Value("${app.database.username}")
    private String databaseUserName;

    @Value("${app.database.password}")
    private String databasePassword;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String authorizationToken;

    @Container
    private static final MySQLContainer mySQLContainer = new MySQLContainer("mysql:9.5.0")
            .withDatabaseName("rest_app")
            .withUsername("sa")
            .withPassword("scr2t");

    @DynamicPropertySource
    private static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    static {
        mySQLContainer.start();
    }

    @Test
    @DisplayName("The test container is created and is running...")
    @Order(1)
    void testContainerIsRunning() {
        Assertions.assertTrue(mySQLContainer.isCreated(), "MySQL container hast not created");
        Assertions.assertTrue(mySQLContainer.isRunning(), "MySQL container is not running");
    }

    @Test
    @Order(2)
    void testCreateUser_whenValidDetailsProvided_returnsUserDetails() throws JSONException {
        //Arrange

        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("firstName", "Joe");
        userDetailsRequestJson.put("lastName", "Burry");
        userDetailsRequestJson.put("email", "test@test.com");
        userDetailsRequestJson.put("password", "12345678");
        userDetailsRequestJson.put("repeatPassword", "12345678");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestJson.toString(), headers);

        //Act
        ResponseEntity<UserRest> createUserDetailsEntity = testRestTemplate.postForEntity("/users", request, UserRest.class);

        UserRest userRest = createUserDetailsEntity.getBody();


        //Assert

        Assertions.assertEquals(HttpStatus.OK, createUserDetailsEntity.getStatusCode());
        Assertions.assertEquals(userDetailsRequestJson.getString("firstName"), userRest.getFirstName(), "Returned user's first Name seens to be incorrect");

        Assertions.assertEquals(userDetailsRequestJson.getString("lastName"), userRest.getLastName(), "Returned user's last Name seens to be incorrect");

        Assertions.assertEquals(userDetailsRequestJson.getString("email"), userRest.getEmail(), "Returned user's email Name seens to be incorrect");

        Assertions.assertFalse(userRest.getUserId().trim().isEmpty(), "User id should not be empty");

    }/**/

    @Test
    @DisplayName("GET /user requires JWT")
    @Order(3)
    void testGetUsers_whenMissingJWT_returns403() {

        //Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity httpEntity = new HttpEntity(null, headers);

        //Act

        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<UserRest>>() {
        });

        //Assert
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "HTTP Status code 403 Forbidden should have been returned");
    }

    @Test
    @DisplayName("/login works")
    @Order(4)
    void testUserLogin_whenValidCredentialsProvided_returnsJWTAuthorizationHeader() throws JSONException {
        //Arrange

        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "test@test.com");
        loginCredentials.put("password", "12345678");

        HttpEntity<String> request = new HttpEntity<>(loginCredentials.toString());


        //Act

        ResponseEntity response = testRestTemplate.postForEntity("/users/login", request, null);

        //Assert
        authorizationToken = response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0);


        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP Status code should be 200 OK!");

        Assertions.assertNotNull(authorizationToken,
                "Response should contain Authorization header with JWT");
        Assertions.assertNotNull(response.getHeaders().getValuesAsList("UserID").get(0),
                "Response should contain UserID in a response header");


    }

    @Test
    @DisplayName("GET /users works")
    @Order(5)
    void testGetUsers_whenValidJWTProvided_returnsUsers() {

        //Arrange

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        System.out.println("TOKEN" + authorizationToken);
        headers.setBearerAuth(authorizationToken);
        HttpEntity requestEntity = new HttpEntity(headers);

        // Act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<UserRest>>() {
                }
        );

        //Assert

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP status code should be 200");
        Assertions.assertTrue(response.getBody().size() == 1, "There should be exactly in the list");
    }
}
