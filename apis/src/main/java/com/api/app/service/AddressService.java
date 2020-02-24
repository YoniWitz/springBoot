package com.api.app.service;

import java.util.List;

import com.api.app.shared.dto.AddressDto;

public interface AddressService {
	List<AddressDto> getAddressesByUserId(String userId);

	AddressDto getAddress(String addressId);
}
