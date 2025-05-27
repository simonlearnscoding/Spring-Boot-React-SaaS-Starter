package com.example.demo.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.user.UserRepository;
import com.example.user.User;
import com.example.user.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

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

}
