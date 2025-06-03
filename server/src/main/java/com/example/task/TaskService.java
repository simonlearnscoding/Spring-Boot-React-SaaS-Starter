package com.example.task;

import com.example.user.User;
import com.example.user.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;

  public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public Task createTask(String title, String description, Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    Task task = new Task(title, description, user);
    return taskRepository.save(task);
  }

  public List<Task> getTasksByUser(Long userId) {
    return taskRepository.findByUserId(userId);
  }

  @Transactional
  public Task updateTaskStatus(Long taskId, Task.Status newStatus) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Task not found"));

    task.setStatus(newStatus);
    return taskRepository.save(task);
  }

}
