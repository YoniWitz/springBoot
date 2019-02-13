package com.yonipony.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.yonipony.app.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user);

	UserDto getUser(String email);

	List<UserDto> getUsers(int page, int limit);

	UserDto getUserByUserId(String userId);

	UserDto updateUser(UserDto user, String userId);

	void deleteUserByUserId(String userId);
	
	boolean verifyEmailToken(String token);
}
