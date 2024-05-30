package com.flight.flight_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<com.flight.flight_spring.domains.User> findByEmail(String email);
}
