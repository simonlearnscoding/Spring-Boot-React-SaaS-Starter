package com.example.demo.tasks;

import com.example.task.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.user.User;
import com.example.user.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@Transactional
public class TaskServiceIntegrationTests {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	private User testUser;

	@BeforeEach
	void setUp() {
		testUser = userRepository.save(new User("Task Tester", "tester@example.com"));
	}

	@Test
	void shouldCreateAndFindTasksForUser() {
		// Given
		Task task1 = taskService.createTask("Task 1", "Description 1", testUser.getId());
		Task task2 = taskService.createTask("Task 2", null, testUser.getId());

		// When
		List<Task> userTasks = taskService.getTasksByUser(testUser.getId());

		// Then
		assertThat(userTasks)
				.hasSize(2)
				.extracting(Task::getTitle)
				.containsExactly("Task 1", "Task 2");
	}

	@Test
	void shouldUpdateTaskStatus() {
		Task task = taskService.createTask("Status Test", "Test", testUser.getId());

		Task updated = taskService.updateTaskStatus(task.getId(), Task.Status.IN_PROGRESS);

		assertThat(updated.getStatus()).isEqualTo(Task.Status.IN_PROGRESS);
		assertThat(taskRepository.findById(task.getId()).get().getStatus())
				.isEqualTo(Task.Status.IN_PROGRESS);
	}

	@Test
	void shouldFailWhenUserNotFound() {
		assertThrows(ResponseStatusException.class, () -> {
			taskService.createTask("Test", "Desc", 999L);
		});
	}
}
