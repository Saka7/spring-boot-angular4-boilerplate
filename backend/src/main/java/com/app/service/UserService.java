package com.app.service;

import com.app.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
  void save(User user);
  void delete(Long id);
  List<User> findAll();
  User findById(Long id);
  User findByEmail(String email);
  User findByUsername(String username);
}
