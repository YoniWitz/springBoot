package com.api.app.io.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.api.app.io.entity.AddressEntity;
import com.api.app.io.entity.UserEntity;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {
	List<AddressEntity> findAllByUser(UserEntity userEntity);
	
	AddressEntity findByAddressId(String addressId);
}
