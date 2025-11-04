package controllers.e2e;


import main.Main;
import models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import services.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes={Main.class, controllers.UserController.class, UserService.class})
public class UserControllerEndToEnd {

    @LocalServerPort
    private final  String port ="8080";

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    public void  testGetListOfUser(){
        assertThat(this.testRestTemplate.getForEntity("http://localhost:"+ port+"/", User[].class)).isNotNull();
    }


}
