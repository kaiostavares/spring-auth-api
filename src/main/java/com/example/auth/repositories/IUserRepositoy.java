package com.example.auth.repositories;

import com.example.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepositoy extends JpaRepository<User, UUID> {
}
