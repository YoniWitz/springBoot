package com.yonipony.app.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yonipony.app.exceptions.UserServiceException;
import com.yonipony.app.service.UserService;
import com.yonipony.app.shared.dto.AddressDto;
import com.yonipony.app.shared.dto.UserDto;
import com.yonipony.app.ui.model.request.UserModel;
import com.yonipony.app.ui.model.response.AddressRest;
import com.yonipony.app.ui.model.response.ErrorMessages;
import com.yonipony.app.ui.model.response.OperationNames;
import com.yonipony.app.ui.model.response.OperationStatus;
import com.yonipony.app.ui.model.response.OperationStatuses;
import com.yonipony.app.ui.model.response.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {
	static ModelMapper modelMapper = new ModelMapper();

	@Autowired
	UserService userService;

	@GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public UserRest getUser(@PathVariable String userId) {
		UserDto userDto = userService.getUserByUserId(userId);

		return modelMapper.map(userDto, UserRest.class);
	}
	
	@GetMapping(path = "/{userId}/address", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<AddressRest> getUserAddresses(@PathVariable String userId) {
		List<AddressDto> addressesDto = userService.getUsersAddressByUserId(userId);

		List<AddressRest> addresses = new ArrayList<>();
		
		for(AddressDto addressDto : addressesDto) {
			addresses.add(modelMapper.map(addressDto, AddressRest.class));
		}
		
		return addresses;
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "50") int limit) {

		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> userDtos = userService.getUsers(page - 1, limit); // user uses entries from 1, but spring boot
																		// starts from zero
		for (UserDto userDto : userDtos) {
			returnValue.add(modelMapper.map(userDto, UserRest.class));
		}

		return returnValue;
	}

	@PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser(@RequestBody UserModel userModel) {

		if (userModel.getFirstName() == null)
			throw new NullPointerException("the first name is null");

		if (userModel.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		UserDto userDto = modelMapper.map(userModel, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);

		return modelMapper.map(createdUser, UserRest.class);
	}

	@PutMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String userId, @RequestBody UserModel userModel) {
		UserDto userDto = modelMapper.map(userModel, UserDto.class);

		UserDto updatedUser = userService.updateUser(userDto, userId);
		return modelMapper.map(updatedUser, UserRest.class);
	}

	@DeleteMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public OperationStatus deleteUser(@PathVariable String userId) {
		OperationStatus returnValue = new OperationStatus(OperationNames.DELETE.name());

		userService.deleteUserByUserId(userId);
		returnValue.setOperationStatus(OperationStatuses.SUCCESS.name());

		return returnValue;
	}
}
