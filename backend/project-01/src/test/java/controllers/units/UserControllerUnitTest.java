package controllers.units;
import static org.assertj.core.api.Assertions.assertThat;

import controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import services.UserService;

import java.util.List;
@SpringBootTest
@ContextConfiguration(classes={controllers.UserController.class, UserService.class})
public class UserControllerUnitTest {
    @Autowired
    private UserController userController;

    @Test
    void contextLoads() throws Exception {
        assertThat(this.userController).isNotNull();
    }

    @Test
    void listOfUserTest() throws Exception {
        assertThat(this.userController.getAllUsers()).isInstanceOf(List.class);
    }

}
