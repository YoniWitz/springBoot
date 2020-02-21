package com.api.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.app.io.entity.AddressEntity;
import com.api.app.io.entity.UserEntity;
import com.api.app.io.repository.AddressRepository;
import com.api.app.io.repository.UserRepository;
import com.api.app.service.AddressService;
import com.api.app.shared.dto.AddressDto;

@Service
public class AddressServiceImpl implements AddressService {
	static ModelMapper modelMapper = new ModelMapper();

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public List<AddressDto> getAddressesByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		List<AddressEntity> addressesEntity = new ArrayList<>();
		if (userEntity != null) {
			addressesEntity = addressRepository.findAllByUser(userEntity);
		}

		java.lang.reflect.Type listType = new TypeToken<List<AddressDto>>() {
		}.getType();
		List<AddressDto> returnAddressesDto = modelMapper.map(addressesEntity, listType);
		return returnAddressesDto;
	}

	@Override
	public AddressDto getAddress(String addressId) {
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		AddressDto addressDto = new AddressDto();
		if (addressEntity != null)
			addressDto = modelMapper.map(addressEntity, AddressDto.class);

		return addressDto;
	}

}
