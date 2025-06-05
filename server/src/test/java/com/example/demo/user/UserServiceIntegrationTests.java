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
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	private User createTestUser() {
		User user = new User("Test User", "test@example.com");
		return userService.createUser(user);
	}

	@Test
	void shouldCreateAndRetrieveUser() {
		User savedUser = createTestUser();
		User foundUser = userService.getUserById(savedUser.getId())
				.orElseThrow();

		// Then
		assertThat(foundUser).isEqualTo(savedUser);
		assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
	}

	@Test
	void shouldDeleteUser() {
		// Given
		User user = createTestUser();
		Long userId = user.getId();

		// When
		userService.deleteUser(userId);

		// Then
		assertThat(userService.getUserById(userId)).isEmpty();
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
