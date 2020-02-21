package com.yoniwitz.app.ui.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.yoniwitz.app.io.entity.UserEntity;
import com.yoniwitz.app.io.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	List<UserEntity> usersEntity;

	@BeforeEach
	void setUp() throws Exception {
//		// Prepare User Entity
//	     UserEntity userEntity = new UserEntity();
//	     userEntity.setFirstName("Sergey");
//	     userEntity.setLastName("Kargopolov");
//	     userEntity.setUserId("1a2b3c");
//	     userEntity.setEncryptedPassword("xxx");
//	     userEntity.setEmail("test2@test.com");
//	     userEntity.setEmailVerificationStatus(false);
//	     
//	     // Prepare User Addresses
//	     AddressEntity addressEntity = new AddressEntity();
//	     addressEntity.setType("shipping");
//	     addressEntity.setAddressId("ahgyt74hfy");
//	     addressEntity.setCity("Vancouver");
//	     addressEntity.setCountry("Canada");
//	     addressEntity.setPostalCode("ABCCDA");
//	     addressEntity.setStreetName("123 Street Address");
//
//	     List<AddressEntity> addresses = new ArrayList<>();
//	     addresses.add(addressEntity);
//	     
//	     userEntity.setAddresses(addresses);
//	     
//	     userRepository.save(userEntity);
	}

	@Test
	void testFindAllUsersTrueStatus() {
		Pageable pageable = PageRequest.of(0, 20);
		Page<UserEntity> usersEntityPage = userRepository.findAllUsersWithNonConfirmedEmailAddress(pageable);
		usersEntity = usersEntityPage.getContent();

		assertNotNull(usersEntity);
		assertTrue(usersEntity.size() == 4);
	}

	@Test
	void testFindAllUsersByFirstName() {
		String firstName = "newname";

		usersEntity = userRepository.findUserByFirstName(firstName);

		assertNotNull(usersEntity);
		assertEquals(usersEntity.size(), 1);
		assertTrue(usersEntity.get(0).getFirstName().equals(firstName));
	}

	@Test
	void testFindAllUsersByLastName() {
		String lastName = "Kargopolov";

		usersEntity = userRepository.findUserByLastName(lastName);

		assertNotNull(usersEntity);
		assertEquals(usersEntity.size(), 3);
		assertTrue(usersEntity.get(0).getLastName().equals(lastName));
	}

	@Test
	void testFindAllUsersByWildCard() {
		String wildCard = "Kar";

		usersEntity = userRepository.findUserByWildCard(wildCard);

		assertNotNull(usersEntity);
		assertEquals(usersEntity.size(), 3);
		assertTrue(usersEntity.get(0).getLastName().equals("Kargopolov"));
	}

	@Test
	void testFindAllUsersFirstAndLastByWildCard() {
		String wildCard = "Kar";

		List<Object[]> usersEntity = userRepository.findUsersFirstNameAndLastNameByWildCard(wildCard);

		assertNotNull(usersEntity);
		assertEquals(usersEntity.size(), 3);
		assertTrue(usersEntity.get(0)[0].equals("Kargopolov"));
	}
	
	@Test
	void testUpdateFirstName() {
		String firstName = "fuckyou";
		String lastName = "Kargopolov";

		userRepository.updateFirstName(firstName, lastName);
		usersEntity = userRepository.findUserByFirstName(firstName);

		assertNotNull(usersEntity);
		assertEquals(usersEntity.size(), 3);
		assertTrue(usersEntity.get(0).getFirstName().equals(firstName));
	}
	
	@Test
	void testFindByEmail() {
		String email = "popo123456";

		UserEntity userEntity = userRepository.findUserByEmail(email);

		assertNotNull(userEntity);
		assertTrue(userEntity.getEmail().equals(email));
	}
}
