package com.sravani.onlinelearningsystem.repository;

import com.sravani.onlinelearningsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);       // For instructor/student email
    Optional<User> findByUsername(String username); // For login
}
