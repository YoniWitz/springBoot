package com.yonipony.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yonipony.app.exceptions.UserServiceException;
import com.yonipony.app.io.entity.UserEntity;
import com.yonipony.app.io.repository.UserRepository;
import com.yonipony.app.service.UserService;
import com.yonipony.app.shared.Utils;
import com.yonipony.app.shared.dto.UserDto;
import com.yonipony.app.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserDto createUser(UserDto user) {

		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("user with email already exists");

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(utils.generateUserId(30));

		UserEntity storedUser = userRepository.save(userEntity);

		UserDto returnedUser = new UserDto();
		BeanUtils.copyProperties(storedUser, returnedUser);

		return returnedUser;
	}

	@Override
	public User loadUserByUsername(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<UserEntity> usersEntityPage = userRepository.findAll(pageable);
		List<UserEntity> usersEntity = usersEntityPage.getContent();
		
		if (usersEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		List<UserDto> returnValue = new ArrayList<>();
		UserDto userDto = new UserDto();
		
		for (UserEntity userEntity : usersEntity) {
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}

		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDto updateUser(UserDto user, String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());

		UserEntity updatedUser = userRepository.save(userEntity);
		UserDto returnedUser = new UserDto();
		BeanUtils.copyProperties(updatedUser, returnedUser);

		return returnedUser;
	}

	@Override
	public void deleteUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userRepository.delete(userEntity);
	}
}
