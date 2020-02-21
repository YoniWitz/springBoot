package com.yoniwitz.app.service;

import java.util.List;

import com.yoniwitz.app.shared.dto.AddressDto;

public interface AddressService {
	List<AddressDto> getAddressesByUserId(String userId);

	AddressDto getAddress(String addressId);
}
