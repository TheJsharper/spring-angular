package com.jsharper.dyndns.server.integrations;

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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT //,
        // properties = {"server.port=8081", "hostname=192.168.0.2"}
)
//@TestPropertySource(locations = "/application-test.properties"//,
// properties = {"server.port=8081"}
//)
@AutoConfigureTestRestTemplate
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersControllerIntegrationTest {

    @Value("${server.port}")
    private int serverPort;

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
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

    }

    @Test
    @DisplayName("GET /user requires JWT")
    @Order(2)
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
    @Order(3)
    void testUserLogin_whenValidCredentialsProvided_returnsJWTAuthorizationHeader() throws JSONException {
        //Arrange

        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "test@test.com");
        loginCredentials.put("password", "12345678");

        HttpEntity<String> request = new HttpEntity<>(loginCredentials.toString());


        //Act

        ResponseEntity response = testRestTemplate.postForEntity("/users/login", request, null);

        //Assert

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP Status code should be 200 OK!");

        Assertions.assertNotNull(response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0),
                "Response should contain Authorization header with JWT");
        Assertions.assertNotNull(response.getHeaders().getValuesAsList("UserID").get(0),
                "Response should contain UserID in a response header");


    }

}

