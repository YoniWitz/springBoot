package com.yonipony.app.io.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.yonipony.app.io.entity.AddressEntity;
import com.yonipony.app.io.entity.UserEntity;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {
	List<AddressEntity> findAllByUser(UserEntity userEntity);
	
	AddressEntity findByAddressId(String addressId);
}
