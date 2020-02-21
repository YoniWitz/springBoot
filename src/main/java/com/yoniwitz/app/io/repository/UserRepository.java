package com.yoniwitz.app.io.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yoniwitz.app.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);

	@Query(value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = 'false'", countQuery = "select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'", nativeQuery = true)
	Page<UserEntity> findAllUsersWithNonConfirmedEmailAddress(Pageable pageableRequest);

	@Query(value = "select * from Users u where u.first_name = ?1", nativeQuery = true)
	List<UserEntity> findUserByFirstName(String firstName);

	@Query(value = "select * from Users u where u.last_name = :lastName", nativeQuery = true)
	List<UserEntity> findUserByLastName(@Param("lastName") String lastName);

	@Query(value = "select * from Users u where u.last_name LIKE %:wildCard% or u.first_name LIKE %:wildCard%", nativeQuery = true)
	List<UserEntity> findUserByWildCard(@Param("wildCard") String wildCard);

	@Query(value = "select u.last_name, u.first_name from Users u where u.last_name LIKE %:wildCard% or u.first_name LIKE %:wildCard%", nativeQuery = true)
	List<Object[]> findUsersFirstNameAndLastNameByWildCard(@Param("wildCard") String wildCard);

	@Transactional
	@Modifying
	@Query(value = "update Users U set u.first_name=:firstName where u.last_name=:lastName", nativeQuery = true)
	void updateFirstName(@Param("firstName") String firstName, @Param("lastName") String lastName);

	@Query("select user from users user where user.email = :email")
	UserEntity findUserByEmail(@Param("email") String email);
}
