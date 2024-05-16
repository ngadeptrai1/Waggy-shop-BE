package com.edu.authen.service;

import com.edu.authen.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
  Optional< User> findByEmail(String email);
  Optional< User> findByAccountName(String account);
  User save(User user);
  List<User> findAll();
  User update(User u);

}
