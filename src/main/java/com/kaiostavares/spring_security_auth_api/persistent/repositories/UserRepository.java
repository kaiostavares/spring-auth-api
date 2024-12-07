package com.kaiostavares.spring_security_auth_api.persistent.repositories;

import com.kaiostavares.spring_security_auth_api.persistent.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
     Optional<User> findByEmail(String email);
}