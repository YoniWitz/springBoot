package com.yoniwitz.app.ui.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yoniwitz.app.service.impl.UserServiceImpl;
import com.yoniwitz.app.shared.dto.AddressDto;
import com.yoniwitz.app.shared.dto.UserDto;
import com.yoniwitz.app.ui.model.response.UserRest;

class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserServiceImpl userService;

	UserDto userDto;
	AddressDto addressDto;
	AddressDto addressDto2;
	List<AddressDto> addressesDto;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		addressDto = new AddressDto("addressId1", "jerusalem", "Israel", "hanagid", "94725", "myType");
		addressDto2 = new AddressDto("addressId2", "ClintonHill", "USA", "Classon", "11205", "billing");
		addressesDto = new ArrayList<>();
		addressesDto.add(addressDto); 
		addressesDto.add(addressDto2);
		
		userDto = new UserDto("yoni", "witz", "test@test", "lkajdsf", addressesDto);
	}

	@Test
	void testGetUser() {
		String userId = "uakfdsjalkdjf1324314";
		when(userService.getUserByUserId(anyString())).thenReturn(userDto);
		
		UserRest userRest = userController.getUser(userId); 
		
		assertNotNull(userRest);
		assertEquals(userRest.getFirstName(), "yoni");
		assertTrue(userRest.getAddresses().size() == 2);
	}

}
