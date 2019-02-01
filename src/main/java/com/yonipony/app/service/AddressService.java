package com.yonipony.app.service;

import java.util.List;

import com.yonipony.app.shared.dto.AddressDto;

public interface AddressService {
	List<AddressDto> getAddressesByUserId(String userId);

	AddressDto getAddress(String addressId);
}
