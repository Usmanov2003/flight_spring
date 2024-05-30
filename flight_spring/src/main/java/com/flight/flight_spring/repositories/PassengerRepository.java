package com.flight.flight_spring.repositories;

import com.flight.flight_spring.domains.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
