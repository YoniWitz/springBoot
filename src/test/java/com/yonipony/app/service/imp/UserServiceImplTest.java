package com.yonipony.app.service.imp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.yonipony.app.io.entity.UserEntity;
import com.yonipony.app.io.repository.UserRepository;
import com.yonipony.app.service.impl.UserServiceImpl;
import com.yonipony.app.shared.dto.UserDto;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCreateUser() {

	}

	@Test
	void testGetUser() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("yoni");
		userEntity.setLastName("pony");

		when(userRepository.findByEmail(Mockito.anyString())).thenReturn(userEntity);

		UserDto userDto = userService.getUser("whatnot");
		assertNotNull(userDto);
		assertEquals(userEntity.getFirstName(), userDto.getFirstName());
		assertNotEquals(1, userDto.getFirstName());
	}
	
	@Test
	final void testGetUser_ThrowsUserNameNotFoundException() {
		when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
		assertThrows(UsernameNotFoundException.class,() -> {userService.getUser("whatnot");});
	}

}
