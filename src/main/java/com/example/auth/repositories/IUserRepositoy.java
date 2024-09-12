package com.example.auth.repositories;

import com.example.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepositoy extends JpaRepository<User, UUID> {
    Optional<UserDetails> findByEmail(String email);
}
