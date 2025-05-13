package com.example.user;

import org.springframework.stereotype.Service;

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
    System.out.println("Returning users: " + users.size());
    return users;
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public User createUser(User user) {
    System.out.println("Creating user: " + user.getEmail());
    return userRepository.save(user);
  }
}
