package com.jsharper.dyndns.server.ui.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsharper.dyndns.server.service.UsersService;
import com.jsharper.dyndns.server.shared.UserDto;
import com.jsharper.dyndns.server.ui.request.UserDetailsRequestModel;
import com.jsharper.dyndns.server.ui.response.UserRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

@WebMvcTest(controllers = UsersController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;

    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsCreateUserDetails() throws Exception {
        //Arrange
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("joe");
        userDetailsRequestModel.setLastName("Burry");
        userDetailsRequestModel.setEmail("email@test.com");
        userDetailsRequestModel.setPassword("12345678");
        userDetailsRequestModel.setRepeatPassword("12345678");

       /* UserDto userDto = new UserDto();
        userDto.setEmail("joe");
        userDto.setLastName("Burry");
        userDto.setEmail("email");
        userDto.setUserId(UUID.randomUUID().toString());*/

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());

        Mockito.when(usersService.createUser(Mockito.any(UserDto.class))).thenReturn(userDto);

        var requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));


        //Act
        var result = mockMvc.perform(requestBuilder).andReturn();
        var responseBodyAsString = result.getResponse().getContentAsString();
        System.out.println(responseBodyAsString);
        var userResponse = new ObjectMapper().readValue(responseBodyAsString, UserRest.class);
        //Assert

        Assertions.assertEquals(userDetailsRequestModel.getFirstName(), userResponse.getFirstName());
        Assertions.assertEquals(userDetailsRequestModel.getLastName(), userResponse.getLastName());
        Assertions.assertEquals(userDetailsRequestModel.getEmail(), userResponse.getEmail());
        //Assertions.assertEquals(userDetailsRequestModel.ge,userResponse.get  );
    }
}
