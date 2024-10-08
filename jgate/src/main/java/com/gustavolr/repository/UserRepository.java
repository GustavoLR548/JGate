package com.gustavolr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavolr.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    
}