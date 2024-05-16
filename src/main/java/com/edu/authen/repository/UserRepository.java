package com.edu.authen.repository;

import com.edu.authen.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional< User> findByEmailEquals(String email);
    Optional< User> findByAccountNameEquals(String account);
}
