package com.yonipony.app.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
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
import com.yonipony.app.shared.dto.UserDto;
import com.yonipony.app.ui.model.request.UserModel;
import com.yonipony.app.ui.model.response.ErrorMessages;
import com.yonipony.app.ui.model.response.OperationNames;
import com.yonipony.app.ui.model.response.OperationStatus;
import com.yonipony.app.ui.model.response.OperationStatuses;
import com.yonipony.app.ui.model.response.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public UserRest getUser(@PathVariable String userId) {
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();
		userDto = userService.getUserByUserId(userId);

		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "50") int limit) {
		List<UserRest> returnValue = new ArrayList<>();
		UserRest userRest = new UserRest();
		List<UserDto> userDtos = userService.getUsers(page - 1, limit);

		for (UserDto userDto : userDtos) {
			BeanUtils.copyProperties(userDto, userRest);
			returnValue.add(userRest);
		}

		return returnValue;
	}

	@PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest postUser(@RequestBody UserModel userModel) {
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();

		if (userModel.getFirstName() == null)
			throw new NullPointerException("the object is null");

		if (userModel.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		BeanUtils.copyProperties(userModel, userDto);
		UserDto createdUser = userService.createUser(userDto);

		BeanUtils.copyProperties(createdUser, returnValue);
		return returnValue;
	}

	@PutMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String userId, @RequestBody UserModel userModel) {
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userModel, userDto);

		UserDto updatedUser = userService.updateUser(userDto, userId);
		BeanUtils.copyProperties(updatedUser, returnValue);
		return returnValue;
	}

	@DeleteMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public OperationStatus deleteUser(@PathVariable String userId) {
		OperationStatus returnValue = new OperationStatus(OperationNames.DELETE.name());

		userService.deleteUserByUserId(userId);
		returnValue.setOperationStatus(OperationStatuses.SUCCESS.name());

		return returnValue;
	}
}
