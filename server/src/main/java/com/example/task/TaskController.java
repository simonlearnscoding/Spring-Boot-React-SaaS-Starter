package com.example.task;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public Task createTask(@RequestBody CreateTaskRequest request) {
    return taskService.createTask(
        request.title(),
        request.description(),
        request.userId());
  }

  @GetMapping("/user/{userId}")
  public List<Task> getTasksByUser(@PathVariable Long userId) {
    return taskService.getTasksByUser(userId);
  }

  @PatchMapping("/{taskId}/status")
  public Task updateStatus(
      @PathVariable Long taskId,
      @RequestBody UpdateStatusRequest request) {
    return taskService.updateTaskStatus(taskId, request.status());
  }

  // Request DTOs
  public record CreateTaskRequest(String title, String description, Long userId) {
  }

  public record UpdateStatusRequest(Task.Status status) {
  }
}
