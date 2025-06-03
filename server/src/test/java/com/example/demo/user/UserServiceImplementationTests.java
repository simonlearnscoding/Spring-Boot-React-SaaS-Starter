package com.example.demo.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.user.UserRepository;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.example.user.User;
import com.example.user.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplementationTests {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	public void testThatCreateUserSavesUser() {
		// Arrange
		User user = new User("Simon", "simon.muscas@gmail.com");

		// Define what the mock repo should return when save() is called
		when(userRepository.save(user)).thenReturn(user);

		// Act
		User savedUser = userService.createUser(user);

		// Assert
		assertEquals("Simon", savedUser.getName());
		assertEquals("simon.muscas@gmail.com", savedUser.getEmail());

		// Verify interaction with mock
		verify(userRepository).save(user);
	}

	@Test
	public void getUserByEmail() {
		// Arrange
		User user = new User("Simon", "simon.muscas@gmail.com");
		when(userRepository.findByEmail("simon.muscas@gmail.com")).thenReturn(Optional.of(user));
		Optional<User> result = userService.getUserByEmail("simon.muscas@gmail.com");
		assertTrue(result.isPresent(), "User should be present");
		assertEquals("Simon", result.get().getName(), "User name should match");
		verify(userRepository).findByEmail("simon.muscas@gmail.com");
	}

	@Test
	public void testGetUserById() {
		// Arrange
		User user = new User("Simon", "simon.muscas@gmail.com");
		long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Optional<User> result = userService.getUserById(userId);
		assertTrue(result.isPresent(), "User should be present");
		assertEquals("Simon", result.get().getName(), "User name should match");
		verify(userRepository).findById(userId);
	}
}
