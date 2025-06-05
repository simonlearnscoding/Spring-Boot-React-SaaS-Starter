package com.example.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.task.Task;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users;
  }

  public void deleteUser(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new IllegalArgumentException("User not found");
    }
    userRepository.deleteById(userId);
  }

  public List<Task> getTasksByUser(Long userId) {
    return userRepository.findById(userId)
        .map(User::getTasks)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  public Task createTaskForUser(Long userId, String title, String description) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    Task task = new Task(title, description, user);
    user.getTasks().add(task);
    userRepository.save(user);
    return task;
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User createUser(User user) {
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new DataIntegrityViolationException("Email already exists");
    }
    return userRepository.save(user);
  }
}
