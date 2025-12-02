package com.jsharper.dyndns.server.service;

import com.jsharper.dyndns.server.io.UserEntity;
import com.jsharper.dyndns.server.shared.UserDto;
import com.jsharper.dyndns.server.ui.response.UserRest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService {
    UserDto createUser(UserDto user);
    List<UserDto> getUsers(int page, int limit);
    UserDto getUser(String email);
    UserEntity getUserById(String userId);
}
