package com.yoniwitz.app.io.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.yoniwitz.app.io.entity.AddressEntity;
import com.yoniwitz.app.io.entity.UserEntity;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {
	List<AddressEntity> findAllByUser(UserEntity userEntity);
	
	AddressEntity findByAddressId(String addressId);
}
