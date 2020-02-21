package com.yoniwitz.app.service.imp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yoniwitz.app.exceptions.UserServiceException;
import com.yoniwitz.app.io.entity.AddressEntity;
import com.yoniwitz.app.io.entity.UserEntity;
import com.yoniwitz.app.io.repository.UserRepository;
import com.yoniwitz.app.service.impl.UserServiceImpl;
import com.yoniwitz.app.shared.Utils;
import com.yoniwitz.app.shared.dto.AddressDto;
import com.yoniwitz.app.shared.dto.UserDto;
import com.yoniwitz.app.ui.model.request.AddressModel;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	Utils utils;

	UserDto userDto;

	UserEntity userEntity;

	ModelMapper modelMapper;

	List<AddressEntity> addressesEntity;

	AddressEntity addressEntity;
	AddressEntity addressEntity2;
	AddressDto addressDto;
	AddressDto addressDto2;
	AddressModel addressModel1;
	AddressModel addressModel2;
	String cryptedPassword;
	String addressId;
	String userId;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		modelMapper = new ModelMapper();
		addressModel1 = new AddressModel("jerusalem", "Israel", "hanagid", "94725", "myType");
		addressModel2 = new AddressModel("ClintonHill", "USA", "Classon", "11205", "billing");
		addressDto = new AddressDto("addressId1", "jerusalem", "Israel", "hanagid", "94725", "myType");
		addressDto2 = new AddressDto("addressId2", "ClintonHill", "USA", "Classon", "11205", "billing");
		addressesEntity = new ArrayList<>();
		userEntity = new UserEntity("yoni", "witz", "test@test", addressesEntity);
		modelMapper = new ModelMapper();
		
		addressEntity = modelMapper.map(addressDto, AddressEntity.class);
		addressEntity2 = modelMapper.map(addressDto2, AddressEntity.class);
			
		addressesEntity.add(addressEntity);
		addressesEntity.add(addressEntity2);

		userEntity.setId(1L);

		userDto = modelMapper.map(userEntity, UserDto.class);
		userDto.setPassword("password");
		
		cryptedPassword = "cryptedPassword";
		addressId = "addressId";
		userId = "userId";
	}

	@Test
	void testCreateUser() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(cryptedPassword);
		when(utils.generateAddressId(anyInt())).thenReturn(addressId);
		when(utils.generateUserId(anyInt())).thenReturn(userId);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

		UserDto createdDto = userService.createUser(userDto);
		assertNotNull(createdDto);
		assertEquals(createdDto.getFirstName(), "yoni");
		assertEquals(createdDto.getAddresses().size(), 2);
		assertNotEquals(1, createdDto.getFirstName());
		assertEquals(createdDto.getAddresses().toString(), userEntity.getAddresses().toString());
		verify(userRepository, times(1)).save(any(UserEntity.class));
	}

	@Test
	final void testCreateUser_ThrowsRuntimeException() {
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		assertThrows(RuntimeException.class, () -> {
			userService.createUser(userDto);
		});
	}

	@Test
	void testGetUser() {
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = userService.getUser("whatnot");
		assertNotNull(userDto);
		assertEquals("yoni", userDto.getFirstName());
		assertNotEquals(1, userDto.getFirstName());
	}

	@Test
	final void testGetUser_ThrowsUserServiceException() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		assertThrows(UserServiceException.class, () -> {
			userService.getUser("whatnot");
		});
	}

}
