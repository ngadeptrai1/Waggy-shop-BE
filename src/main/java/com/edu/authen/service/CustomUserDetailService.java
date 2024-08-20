package com.edu.authen.service;

import com.edu.authen.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
public interface CustomUserDetailService {
    Optional<User> findByEmail(String email);
    Optional< User> findByAccountName(String account);
    User save(User user);
    User findById(Long id);
    User update(User u);
}
