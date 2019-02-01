package com.yonipony.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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
import com.yonipony.app.shared.dto.AddressDto;
import com.yonipony.app.shared.dto.UserDto;
import com.yonipony.app.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {
	static ModelMapper modelMapper = new ModelMapper();

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserDto createUser(UserDto userDto) {
		if (userRepository.findByEmail(userDto.getEmail()) != null)
			throw new RuntimeException("user with email already exists");
		AddressDto addressDto = new AddressDto();
		for (int i = 0; i < userDto.getAddresses().size(); i++) {
			addressDto = userDto.getAddresses().get(i);
			addressDto.setAddressId(utils.generateAddressId(30));
			addressDto.setUser(userDto);
		}

		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		userEntity.setUserId(utils.generateUserId(30));

		UserEntity storedUserEntity = userRepository.save(userEntity);

		UserDto returnUserDto = modelMapper.map(storedUserEntity, UserDto.class);
		return returnUserDto;
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

		UserDto returnUserDto = modelMapper.map(userEntity, UserDto.class);
		return returnUserDto;
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		Pageable pageable = PageRequest.of(page, limit);
		Page<UserEntity> usersEntityPage = userRepository.findAll(pageable);
		List<UserEntity> usersEntity = usersEntityPage.getContent();

		if (usersEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		List<UserDto> returnUsersDto = new ArrayList<>();

		for (UserEntity userEntity : usersEntity) {
			returnUsersDto.add(modelMapper.map(userEntity, UserDto.class));
		}

		return returnUsersDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		UserDto returnUserDto = modelMapper.map(userEntity, UserDto.class);
		return returnUserDto;
	}

	@Override
	public UserDto updateUser(UserDto user, String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());

		UserEntity updatedUserEntity = userRepository.save(userEntity);

		UserDto returnUpdatedUserDto = modelMapper.map(updatedUserEntity, UserDto.class);
		return returnUpdatedUserDto;
	}

	@Override
	public void deleteUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		userRepository.delete(userEntity);
	}
}
