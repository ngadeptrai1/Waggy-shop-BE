package com.edu.authen.repository;

import com.edu.authen.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken , UUID> {
}
