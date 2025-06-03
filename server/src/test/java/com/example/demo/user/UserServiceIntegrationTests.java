package com.example.demo.user;

import com.example.user.UserService;
import com.example.user.User;
import com.example.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	void shouldCreateAndRetrieveUser() {
		// Given
		User newUser = new User("Test User", "test@example.com");

		// When
		User savedUser = userService.createUser(newUser);
		User foundUser = userService.getUserById(savedUser.getId())
				.orElseThrow();

		// Then
		assertThat(foundUser).isEqualTo(savedUser);
		assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
	}

	@Test
	void shouldRejectDuplicateEmails() {
		userService.createUser(new User("First", "duplicate@test.com"));
		assertThrows(DataIntegrityViolationException.class, () -> {
			userService.createUser(new User("Second", "duplicate@test.com"));
		});
	}

	@Test
	void shouldReturnEmptyForNonExistentUser() {
		assertThat(userService.getUserById(999L)).isEmpty();
	}
}
