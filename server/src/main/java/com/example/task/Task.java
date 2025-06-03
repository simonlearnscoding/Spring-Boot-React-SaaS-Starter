package com.example.task;

import java.time.Instant;

import jakarta.persistence.*;
import com.example.user.User;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "tasks")
public class Task {

  public enum Status {
    NEW,
    IN_PROGRESS,
    COMPLETED,
    ARCHIVED
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = true)
  private String description = "";

  @Enumerated(EnumType.STRING)
  private Status status = Status.NEW;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  public Task() {
  }

  public Task(String title, @Nullable String description, User user) {
    this.title = title;
    this.user = user;
    this.description = description != null ? description : "";
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description != null ? description : "";
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
