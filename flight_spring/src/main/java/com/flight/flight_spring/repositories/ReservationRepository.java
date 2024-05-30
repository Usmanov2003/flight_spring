package com.flight.flight_spring.repositories;

import com.flight.flight_spring.domains.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
