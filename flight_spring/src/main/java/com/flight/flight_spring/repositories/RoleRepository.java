package com.flight.flight_spring.repositories;

import com.flight.flight_spring.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
