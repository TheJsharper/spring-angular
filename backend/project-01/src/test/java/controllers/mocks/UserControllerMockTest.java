package controllers.mocks;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.Main;
import models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {Main.class})
@AutoConfigureMockMvc
public class UserControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnFirstUser() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").isString())
                .andExpect(jsonPath("$[0].lastName").isString())
                .andExpect(jsonPath(("$[0].firstName"), is("Web-User FirstName  I")))
                .andExpect(jsonPath(("$[0].lastName"), is("Web_User- LastName I")));

    }

    @Test
    void shouldReturnUserById() throws Exception {

        var result = this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andReturn();

        var jsonString = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        List<User> users = objectMapper.readValue(jsonString, new TypeReference<List<User>>() {
        });

        User firstEl = users.stream().findFirst().orElse(null);

        var params = "/" + firstEl.getId();

        this.mockMvc.perform(get(params)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").isString())
                .andExpect(jsonPath("$.lastName").isString())
                .andExpect(jsonPath(("$.firstName"), is("Web-User FirstName  I")))
                .andExpect(jsonPath(("$.lastName"), is("Web_User- LastName I")));

    }

    @Test
    void shouldCreateNewUser() throws Exception {


        var payload = new User("Testing Mocking FirstName", "Testing Mocking LastName");

        ObjectMapper objectMapper = new ObjectMapper();

        var jsonString = objectMapper.writeValueAsString(payload);

        var result = this.mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(jsonString).characterEncoding("uf-8"))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse();

        var responseString = response.getContentAsString();

        ObjectMapper responseObject = new ObjectMapper();

        var userResponse = responseObject.readValue(responseString, User.class);


        assertEquals(payload.getFirstName(), userResponse.getFirstName());

        assertEquals(payload.getLastName(), userResponse.getLastName());

    }


    @Test
    void shouldModifyUser() throws Exception {


        var resultReturn = this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andReturn();

        var jsonString = resultReturn.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        List<User> users = objectMapper.readValue(jsonString, new TypeReference<List<User>>() {
        });

        User firstEl = users.stream().findFirst().orElse(null);

        User modifiedUser = new User(firstEl.getId(), "Updated FirstName From Testing", "Updated FirstName From Testing");

        ObjectMapper payloadMapper = new ObjectMapper();

        var jsonStringModifiedIObject = payloadMapper.writeValueAsString(modifiedUser);

        var result = this.mockMvc.perform(put("/" + firstEl.getId()).contentType(MediaType.APPLICATION_JSON).content(jsonStringModifiedIObject).characterEncoding("uf-8"))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse();

        var responseString = response.getContentAsString();

        ObjectMapper responseObject = new ObjectMapper();

        var userResponse = responseObject.readValue(responseString, User.class);


        assertEquals(modifiedUser.getId(), userResponse.getId());

        assertEquals(modifiedUser.getFirstName(), userResponse.getFirstName());

        assertEquals(modifiedUser.getLastName(), userResponse.getLastName());

        var resultReset = this.mockMvc.perform(put("/" + firstEl.getId()).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(firstEl)).characterEncoding("uf-8"))
                .andExpect(status().isOk())
                .andReturn();

    }


    @Test
    void shouldDeleteUser() throws Exception {

        var userToRemove = new User("firstName testing from delete", "lastName testing from delete");
        var jsonString = new ObjectMapper().writeValueAsString(userToRemove);

        var result = this.mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(jsonString).characterEncoding("uf-8"))
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse();

        var responseString = response.getContentAsString();

        var responseUser = new ObjectMapper().readValue(responseString, User.class);

        var contentAsString = this.mockMvc.perform(delete("/" + responseUser.getId())).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseString)).andReturn().getResponse().getContentAsString();


        var userResponseFromDelete = new ObjectMapper().readValue(contentAsString, User.class);

        assertEquals(responseUser.getId(), userResponseFromDelete.getId());

        assertEquals(responseUser.getFirstName(), userResponseFromDelete.getFirstName());

        assertEquals(responseUser.getLastName(), userResponseFromDelete.getLastName());


    }

}
