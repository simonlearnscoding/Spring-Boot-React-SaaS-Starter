package com.example.user;

import java.util.List;
import com.example.task.Task;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{userId}/tasks")
  public List<Task> getTasksByUser(@PathVariable Long userId) {
    return userService.getTasksByUser(userId);
  }

  @PostMapping("/{userId}/tasks")
  public Task createUserTask(
      @PathVariable Long userId,
      @RequestBody CreateTaskRequest request) {
    return userService.createTaskForUser(
        userId,
        request.title(),
        request.description());
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public User getUser(@PathVariable Long id) {
    return userService.getUserById(id).orElse(null);
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  public record CreateUserRequest(String name, String email) {
  }

  public record CreateTaskRequest(String title, String description) {
  }
}
