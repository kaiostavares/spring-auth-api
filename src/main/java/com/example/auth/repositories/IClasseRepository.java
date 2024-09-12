package com.example.auth.repositories;

import com.example.auth.entities.Classe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IClasseRepository extends JpaRepository<Classe, UUID> {
}
