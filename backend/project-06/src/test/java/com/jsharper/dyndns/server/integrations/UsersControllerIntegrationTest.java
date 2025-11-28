package com.jsharper.dyndns.server.integrations;

import com.jsharper.dyndns.server.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT //,
        // properties = {"server.port=8081", "hostname=192.168.0.2"}
)
//@TestPropertySource(locations = "/application-test.properties"//,
// properties = {"server.port=8081"}
//)
@AutoConfigureTestRestTemplate
public class UsersControllerIntegrationTest {

    @Value("${server.port}")
    private int serverPort;

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
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
        Assertions.assertEquals(userDetailsRequestJson.getString("firstName"),
                userRest.getFirstName(), "Returned user's first Name seens to be incorrect");

        Assertions.assertEquals(userDetailsRequestJson.getString("lastName"),
                userRest.getLastName(), "Returned user's last Name seens to be incorrect");

        Assertions.assertEquals(userDetailsRequestJson.getString("email"),
                userRest.getEmail(), "Returned user's email Name seens to be incorrect");

        Assertions.assertFalse( userRest.getUserId().trim().isEmpty(), "User id should not be empty");

    }


}

