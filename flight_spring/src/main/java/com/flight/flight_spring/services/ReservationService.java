package com.flight.flight_spring.services;

import com.flight.flight_spring.domains.Reservation;
import com.flight.flight_spring.dto.ReservationRequest;

public interface ReservationService {
    public Reservation bookFlight(ReservationRequest reservationRequest);
}
